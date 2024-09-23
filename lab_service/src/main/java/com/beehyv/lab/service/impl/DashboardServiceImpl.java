package com.beehyv.lab.service.impl;


import com.beehyv.lab.dto.responseDto.*;
import com.beehyv.lab.entity.Lab;
import com.beehyv.lab.entity.LabSample;
import com.beehyv.lab.entity.SampleStateGeo;
import com.beehyv.lab.entity.SampleStateGeoId;
import com.beehyv.lab.enums.GeoAggregationType;
import com.beehyv.lab.helper.Constants;
import com.beehyv.lab.helper.RestHelper;
import com.beehyv.lab.manager.LabManager;
import com.beehyv.lab.manager.LabSampleManager;
import com.beehyv.lab.manager.SampleStateGeoManager;
import com.beehyv.lab.mapper.DTOMapper;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class DashboardServiceImpl {


   private final LabSampleManager labSampleManager;
   private final SampleStateGeoManager sampleStateGeoManager;
   private final LabManager labManager;

   private final DTOMapper dtoMapper  = Mappers.getMapper(DTOMapper.class);

   private final  KeycloakInfo keycloakInfo;

    private final RestHelper restHelper;



    public Object getCount(String create_start_date, String create_end_date){
        Long labId = (long) (int) keycloakInfo.getUserInfo().get("labId");
        String url = Constants.FORTIFICATION_BASE_URL+ "category/manufacturer-all";
        List<CategoryResponseDto> categories = restHelper.getCategories(url, keycloakInfo.getAccessToken());
        List<LabSample> labSampleList = labSampleManager.findAllByCreateDate(categories.stream().map(CategoryResponseDto::getId).collect(Collectors.toList()),
                create_start_date,create_end_date,labId);
        Object V =labSampleList.stream().collect(Collectors.groupingBy(d -> d.getState().getName(), HashMap::new,
                Collectors.groupingBy(d->{
                    CategoryResponseDto category = categories.stream().filter(d1 -> Objects.equals(d1.getId(), d.getCategoryId())).findFirst().get();
                    return category.getName();
                }, Collectors.counting())));

        return V;
    }

    public AggregatedResponseDto getAggregatedSampleStatesCount(Long categoryId, GeoAggregationType type, String geoId, Integer year) {
        String groupBy, filterBy;
        switch (type) {
            case district -> {
                groupBy = "districtGeoId";
                filterBy = "stateGeoId";
            }
            case state -> {
                groupBy = "stateGeoId";
                filterBy = "countryGeoId";
            }
            default -> {
                groupBy = "stateGeoId";
                filterBy = null;
            }
        }
        List<Long> activeLabIds = labManager.getAllActiveLabIds();
        Long[] sums = sampleStateGeoManager.getSamplesGeoCount(categoryId, filterBy, geoId, activeLabIds,
                year == null ? getYearFromDate(new Date()) : year);
        List<SampleStateCountResponseDto> results = sampleStateGeoManager.getAggregatedGeoSamplesCount(categoryId, groupBy,
                filterBy, geoId, activeLabIds,
                year == null ? getYearFromDate(new Date()) : year);
        AggregatedSampleStateGeoCountResponseDto responseDto = new AggregatedSampleStateGeoCountResponseDto();
        responseDto.setTestedCount(sums[0]);
        responseDto.setInTransitCount(sums[1]);
        responseDto.setUnderTestingCount(sums[2]);
        responseDto.setRejectedCount(sums[3]);
        responseDto.setData(results);
        return responseDto;
    }

    public ListResponse<SampleStateGeoLabResponseDto> getSampleSateGeoCountLabs(Long categoryId, GeoAggregationType type,
                                                                                String geoId, String search, Integer year,
                                                                                Integer pageNumber, Integer pageSize) {
        String filterBy = switch (type) {
            case district -> "districtGeoId";
            case state -> "stateGeoId";
            case country -> "countryGeoId";
        };
        Map<Long, Lab> activeLabs = labManager.findAllByCategoryIdAndGeoId(categoryId, filterBy, geoId, null, null);
        Set<Long> activeLabIds = activeLabs.keySet();
        List<SampleStateGeo> sampleStateGeos = sampleStateGeoManager.findAllByCategoryIdAndGeoId(categoryId, filterBy, geoId,
                activeLabIds, year, pageNumber, pageSize,search);
        Long sampleStateGeoCount = sampleStateGeoManager.findAllByCategoryIdAndGeoIdCount(sampleStateGeos.size(),
                categoryId, filterBy, geoId, year, pageNumber, pageSize, activeLabIds, search);

        List<SampleStateGeoLabResponseDto> dtoList = new ArrayList<>();
        for(SampleStateGeo sampleStateGeo: sampleStateGeos){
            boolean isPresent = false;
            for(SampleStateGeoLabResponseDto dto : dtoList){
                if(Objects.equals(dto.getLabId(), sampleStateGeo.getStateGeoId().getLabId())){
                    isPresent=true;
                    dto.setInTransitCount(dto.getInTransitCount()+sampleStateGeo.getInTransitCount());
                    dto.setTestedCount(dto.getTestedCount()+sampleStateGeo.getTestedCount());
                    dto.setRejectedCount(dto.getRejectedCount()+sampleStateGeo.getRejectedCount());
                    dto.setUnderTestingCount(dto.getUnderTestingCount()+sampleStateGeo.getUnderTestingCount());
                    sampleStateGeoCount--;
                }
            }
            if(!isPresent){
                SampleStateGeoLabResponseDto responseDto = new SampleStateGeoLabResponseDto();
                BeanUtils.copyProperties(sampleStateGeo, responseDto);
                Lab lab = activeLabs.getOrDefault(sampleStateGeo.getStateGeoId().getLabId(), null);
                if(lab==null) continue;
                responseDto.setLabId(lab.getId());
                responseDto.setLabName(lab.getName());
                responseDto.setLabAddress(dtoMapper.convertAddressToCompleteAddress(lab.getAddress()));
                dtoList.add(responseDto);
            }
        }
        return new ListResponse<>(sampleStateGeoCount, dtoList);
    }

    public void recompile() {
        int pageNumber = 0, pageSize = 30;
        AtomicReference<AddressResponseDTO> address = new AtomicReference<>(null);
        AtomicReference<Long> currentManufacturerId = new AtomicReference<>(null);
        while (true) {
            try {
                List<Long[]> objects = labSampleManager
                        .findAllAggregateByManufacturerIdAndLabIdAndCategoryId(pageNumber, pageSize);
                for(int i = 0; i < objects.size(); i++) {
                    Long[] d = objects.get(i);
                    if (d == null || d[0] == null) {
                        log.info("Manufacturer id is null at {{}}/{{}}", pageNumber, pageSize);
                    }
                    if(!Objects.equals(currentManufacturerId.get(), d[0])) {
                        currentManufacturerId.set(d[0]);
                        address.set(this.getManufacturerAddress(currentManufacturerId.get()));
                    }
                    SampleStateGeo sampleStateGeo;
                    SampleStateGeoId stateGeoId = SampleStateGeoId.builder()
                            .manufacturerId(d[0])
                            .labId(d[1])
                            .categoryId(d[2])
                            .sampleSentYear(d[3].intValue())
                            .build();
                    if(address.get() != null && address.get().getVillage() != null && address.get().getVillage().getDistrict() != null) {
                        stateGeoId.setDistrictGeoId(address.get().getVillage().getDistrict().getGeoId());
                        if(address.get().getVillage().getDistrict().getState() != null) {
                            stateGeoId.setStateGeoId(address.get().getVillage().getDistrict().getState().getGeoId());
                            if(address.get().getVillage().getDistrict().getState().getCountry() != null) {
                                stateGeoId.setCountryGeoId(address.get().getVillage().getDistrict().getState().getCountry().getGeoId());
                            }
                        }
                    }

                    sampleStateGeo = new SampleStateGeo();
                    sampleStateGeo.setStateGeoId(stateGeoId);
                    sampleStateGeo.setInTransitCount(d[4]);
                    sampleStateGeo.setUnderTestingCount(d[5]);
                    sampleStateGeo.setTestedCount(d[6]);
                    sampleStateGeo.setRejectedCount(d[7]);
                    sampleStateGeoManager.update(sampleStateGeo);
                }
                if (objects.size() < pageSize) break;
                pageNumber++;
            } catch (Exception exception) {
                log.info("Recompile failure at page {{}}/{{}}", pageNumber, pageSize);
                break;
            }
        }
    }

    public DashboardResponseDto getCategoryCounts(Long categoryId, GeoAggregationType type, String geoId){
        DashboardResponseDto result = new DashboardResponseDto();
        List<DashboardCountResponseDto> dto;
        if (type.equals(GeoAggregationType.state)){
            dto = labManager.getDistrictCount(categoryId, geoId);
            result.setData(dto);
            result.setTotalLabCategories(dto.stream().mapToLong(DashboardCountResponseDto::getTotalLabCategories).sum());
            result.setTotal(dto.stream().mapToLong(DashboardCountResponseDto::getTotal).sum());
        }
        if (type.equals(GeoAggregationType.country)){
            dto = labManager.getStateCount(categoryId, geoId);
            result.setData(dto);
            result.setTotalLabCategories(dto.stream().mapToLong(DashboardCountResponseDto::getTotalLabCategories).sum());
            result.setTotal(dto.stream().mapToLong(DashboardCountResponseDto::getTotal).sum());
        }
        return result;
    }

    public Map<String, DashboardResponseDto> getCategoriesCounts(GeoAggregationType type, String geoId){
        String url = Constants.FORTIFICATION_BASE_URL +"category";
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("independentBatch", false)
                .build();
        ListResponse<CategoryResponseDto> response = restHelper
                .fetchCategoryListResponse(builder.toUriString(), keycloakInfo.getAccessToken());
        return response.getData().stream().collect(Collectors.toMap(CategoryResponseDto::getName, c -> {
            DashboardResponseDto result = new DashboardResponseDto();
            List<DashboardCountResponseDto> dto;
            if (type.equals(GeoAggregationType.state)) {
                dto = labManager.getDistrictCount(c.getId(), geoId);
                result.setCategoryId(c.getId());
                result.setTotalLabCategories(dto.stream().mapToLong(DashboardCountResponseDto::getTotalLabCategories).sum());
                result.setTotal(dto.stream().mapToLong(DashboardCountResponseDto::getTotal).sum());
            }
            if (type.equals(GeoAggregationType.country)) {
                dto = labManager.getStateCount(c.getId(), geoId);
                result.setCategoryId(c.getId());
                result.setTotalLabCategories(dto.stream().mapToLong(DashboardCountResponseDto::getTotalLabCategories).sum());
                result.setTotal(dto.stream().mapToLong(DashboardCountResponseDto::getTotal).sum());
            }
            return result;
        }));
    }



    private AddressResponseDTO getManufacturerAddress(Long manufacturerId) {
        String url = Constants.IAM_BASE_URL+ "address/manufacturer/" + manufacturerId;
        return restHelper.fetchResponse(url, AddressResponseDTO.class, keycloakInfo.getAccessToken());
    }

    public Integer getYearFromDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
}
