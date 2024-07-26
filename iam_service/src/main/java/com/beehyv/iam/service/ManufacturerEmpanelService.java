package com.beehyv.iam.service;

import com.beehyv.iam.dto.requestDto.ManufacturerEmpanelRequestDto;
import com.beehyv.iam.dto.responseDto.*;
import com.beehyv.iam.enums.GeoType;
import com.beehyv.iam.helper.FortificationRestHelper;
import com.beehyv.iam.manager.ManufacturerEmpanelManager;
import com.beehyv.iam.manager.ManufacturerManager;
import com.beehyv.iam.model.ManufacturerEmpanel;
import com.beehyv.iam.utils.DtoMapper;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ManufacturerEmpanelService {
    private final ManufacturerEmpanelManager manager;
    private final DtoMapper dtoMapper;
    private final KeycloakInfo keycloakInfo;

    private final ManufacturerManager manufacturerManager;

    @Value("${service.fortification.baseUrl}")
    private String fortificationBaseUrl;

    public Long create(ManufacturerEmpanelRequestDto dto) {
        return manager.create(dtoMapper.mapToEntity(dto)).getId();
    }

    public ManufacturerEmpanelResponseDto getManufacturerEmpanelByID(Long id) {
        return dtoMapper.mapToDto(manager.findById(id));
    }

    public Long update(ManufacturerEmpanelRequestDto dto) {
        ManufacturerEmpanel manufacturerEmpanel = dtoMapper.mapToEntity(dto);
        return manager.update(manufacturerEmpanel).getId();
    }

    public void delete(Long id) {
        manager.delete(id);
    }

    public Map<Long, List<Long>> getAllEmpanelledManufacturers(ManufacturerEmpanelRequestDto dto) {
        Map<Long, List<Long>> manufacturersCategoryMap = new HashMap<>();
        try {
            if(dto.getSourceCategoryId()!=null)
            manufacturersCategoryMap.put(dto.getSourceCategoryId(), manager.getAllEmpanelledManufacturers(dto.getSourceCategoryId(), dto.getStateGeoId(), dto.getFromDate(), dto.getToDate()));
            if(dto.getTargetCategoryId()!=null) {
                manufacturersCategoryMap.put(dto.getTargetCategoryId(), manager.getAllEmpanelledManufacturers(dto.getTargetCategoryId(), dto.getStateGeoId(), dto.getFromDate(), dto.getToDate()));
            } }catch (Exception e){
            return manufacturersCategoryMap;
        }
        return manufacturersCategoryMap;
    }

    public Map<String, DashboardResponseDto> getCategoriesCounts(GeoType type, String geoId) {
        String url = fortificationBaseUrl + "fortification/category";
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("independentBatch", false)
                .build();
        ListResponse<CategoryResponseDto> response = FortificationRestHelper
                .fetchCategoryListResponse(builder.toUriString(), keycloakInfo.getAccessToken());

        Map<String, DashboardResponseDto> resultMap = new HashMap<>();


        return response.getData().stream().collect(Collectors.toMap(CategoryResponseDto::getName, c -> {
            DashboardResponseDto result = new DashboardResponseDto();
            List<DashboardCountResponseDto> dto;

            List<Long> empanelledManufacturers = manager.getAllEmpanelledManufacturers(c.getId(), (String) keycloakInfo.getUserInfo().get("stateGeoId"),null, null);
            if (type.equals(GeoType.state)) {
                dto = manufacturerManager.getEmpanelDistrictCount(c.getId(), geoId, empanelledManufacturers);
                result.setCategoryId(c.getId());
                result.setTotalManufacturerCategories(dto.stream().mapToLong(DashboardCountResponseDto::getTotalManufacturerCategories).sum());
                result.setTotal(dto.stream().mapToLong(DashboardCountResponseDto::getTotal).sum());
            }  if (type.equals(GeoType.country)) {
                dto = manufacturerManager.getEmpanelStateCount(c.getId(), geoId, empanelledManufacturers);
                result.setCategoryId(c.getId());
                result.setTotalManufacturerCategories(dto.stream().mapToLong(DashboardCountResponseDto::getTotalManufacturerCategories).sum());
                result.setTotal(dto.stream().mapToLong(DashboardCountResponseDto::getTotal).sum());
            }  if (type.equals(GeoType.district)) {
                dto = manufacturerManager.getEmpanelVillageCount(c.getId(), geoId, empanelledManufacturers);
                result.setCategoryId(c.getId());
                result.setTotalManufacturerCategories(dto.stream().mapToLong(DashboardCountResponseDto::getTotalManufacturerCategories).sum());
                result.setTotal(dto.stream().mapToLong(DashboardCountResponseDto::getTotal).sum());
            }
            return result;
        }));
    }

    public DashboardResponseDto getCategoryCounts(Long categoryId, GeoType type, String geoId){
        DashboardResponseDto result = new DashboardResponseDto();
        List<Long> empanelledManufacturers = manager.getAllEmpanelledManufacturers(categoryId, (String) keycloakInfo.getUserInfo().get("stateGeoId"),null, null);
        List<DashboardCountResponseDto> dto;
        if (type.equals(GeoType.state)){
            dto = manufacturerManager.getEmpanelDistrictCount(categoryId, geoId, empanelledManufacturers);
            result.setData(dto);
            result.setTotalManufacturerCategories(dto.stream().mapToLong(DashboardCountResponseDto::getTotalManufacturerCategories).sum());
            result.setTotal(dto.stream().mapToLong(DashboardCountResponseDto::getTotal).sum());
        }
        if (type.equals(GeoType.country)){
            dto = manufacturerManager.getEmpanelStateCount(categoryId, geoId, empanelledManufacturers);
            result.setData(dto);
            result.setTotalManufacturerCategories(dto.stream().mapToLong(DashboardCountResponseDto::getTotalManufacturerCategories).sum());
            result.setTotal(dto.stream().mapToLong(DashboardCountResponseDto::getTotal).sum());
        }
        return result;

    }
}
