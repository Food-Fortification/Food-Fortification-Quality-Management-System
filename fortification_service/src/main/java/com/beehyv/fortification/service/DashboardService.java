package com.beehyv.fortification.service;

import com.beehyv.fortification.dto.requestDto.DashboardRequestDto;
import com.beehyv.fortification.dto.requestDto.QuantityAggregatesExcelRequestDto;
import com.beehyv.fortification.dto.responseDto.*;
import com.beehyv.fortification.entity.StateType;
import com.beehyv.fortification.enums.GeoAggregationType;
import com.beehyv.fortification.enums.GeoManufacturerProductionResponseType;
import com.beehyv.fortification.enums.GeoManufacturerQuantityResponseType;
import com.beehyv.fortification.enums.GeoManufacturerTestingResponseType;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Map;

public interface DashboardService {
    List<StateGeoDto> getDashboardCountData(Integer year, DashboardRequestDto dto);

    ListResponse<?> getManufacturersQuantities(GeoManufacturerQuantityResponseType responseType, Long categoryId,
                                               GeoAggregationType type, String geoId, String search, Integer year, Integer pageNumber, Integer pageSize, DashboardRequestDto dto);

    void recompileGeoData(StateType stateType);
}
