package com.beehyv.iam.service;

import com.beehyv.iam.enums.GeoType;
import com.beehyv.iam.dto.responseDto.*;
import com.beehyv.iam.helper.FortificationRestHelper;
import com.beehyv.iam.manager.ManufacturerManager;
import com.beehyv.iam.model.Manufacturer;
import com.beehyv.iam.utils.DtoMapper;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DashboardService {

    @Value("${service.fortification.baseUrl}")
    private String fortificationBaseUrl;

    private final KeycloakInfo keycloakInfo;

    private final ManufacturerManager manufacturerManager;
    private final DtoMapper dtoMapper = Mappers.getMapper(DtoMapper.class);
    public DashboardResponseDto getCategoryCounts(Long categoryId, GeoType type, String geoId){
        DashboardResponseDto result = new DashboardResponseDto();
        List<DashboardCountResponseDto> dto;
        if (type.equals(GeoType.state)){
            dto = manufacturerManager.getDistrictCount(categoryId, geoId);
            result.setData(dto);
            result.setTotalManufacturerCategories(dto.stream().mapToLong(DashboardCountResponseDto::getTotalManufacturerCategories).sum());
            result.setTotal(dto.stream().mapToLong(DashboardCountResponseDto::getTotal).sum());
        }
        if (type.equals(GeoType.country)){
           dto = manufacturerManager.getStateCount(categoryId, geoId);
           result.setData(dto);
            result.setTotalManufacturerCategories(dto.stream().mapToLong(DashboardCountResponseDto::getTotalManufacturerCategories).sum());
           result.setTotal(dto.stream().mapToLong(DashboardCountResponseDto::getTotal).sum());
        }
        return result;
    }

    public Map<String, DashboardResponseDto> getCategoriesCounts(GeoType type, String geoId){
        String url = fortificationBaseUrl+"fortification/category";
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("independentBatch", false)
                .build();
        ListResponse<CategoryResponseDto> response = FortificationRestHelper
                .fetchCategoryListResponse(builder.toUriString(), keycloakInfo.getAccessToken());
        return response.getData().stream().collect(Collectors.toMap(CategoryResponseDto::getName, c -> {
            DashboardResponseDto result = new DashboardResponseDto();
            List<DashboardCountResponseDto> dto;
            if (type.equals(GeoType.state)) {
                dto = manufacturerManager.getDistrictCount(c.getId(), geoId);
                result.setCategoryId(c.getId());
                result.setTotalManufacturerCategories(dto.stream().mapToLong(DashboardCountResponseDto::getTotalManufacturerCategories).sum());
                result.setTotal(dto.stream().mapToLong(DashboardCountResponseDto::getTotal).sum());
            }
            if (type.equals(GeoType.country)) {
                dto = manufacturerManager.getStateCount(c.getId(), geoId);
                result.setCategoryId(c.getId());
                result.setTotalManufacturerCategories(dto.stream().mapToLong(DashboardCountResponseDto::getTotalManufacturerCategories).sum());
                result.setTotal(dto.stream().mapToLong(DashboardCountResponseDto::getTotal).sum());
            }
            if(type.equals(GeoType.district)){
                dto = manufacturerManager.getVillageCount(c.getId(), geoId);
                result.setCategoryId(c.getId());
                result.setTotalManufacturerCategories(dto.stream().mapToLong(DashboardCountResponseDto::getTotalManufacturerCategories).sum());
                result.setTotal(dto.stream().mapToLong(DashboardCountResponseDto::getTotal).sum());
            }
            return result;
        }));
    }


    public ListResponse<ManufacturerListDashboardResponseDto> getManufacturerListByGeoId(Long categoryId, GeoType type, String geoId, String search, Integer pageNumber, Integer pageSize){
        List<ManufacturerListDashboardResponseDto> result = new ArrayList<>();
        List<Manufacturer> list;
        Long count = null;
        if (type.equals(GeoType.district)){
            list = manufacturerManager.findAllByDistrictGeoId(categoryId, geoId, search, pageNumber, pageSize);
            result = list.stream()
                    .map(dtoMapper::mapToDto)
                    .toList();
            count = manufacturerManager.findCountByDistrictGeoId(categoryId, geoId, search);
        }
        if (type.equals(GeoType.state)){
            list = manufacturerManager.findAllByStateGeoId(categoryId, geoId, search, pageNumber, pageSize);
            result = list.stream()
                    .map(dtoMapper::mapToDto)
                    .toList();
            count = manufacturerManager.findCountByStateGeoId(categoryId, geoId, search);
        }
        if (type.equals(GeoType.country)){
            list = manufacturerManager.findAllByCountryGeoId(categoryId, geoId, search, pageNumber, pageSize);
            result = list.stream()
                    .map(dtoMapper::mapToDto)
                    .toList();
            count = manufacturerManager.findCountByCountryGeoId(categoryId, geoId, search);
        }
        return new ListResponse<>(count, result);
    }
}
