package org.path.fortification.service;

import org.path.fortification.dto.requestDto.DashboardRequestDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.dto.responseDto.StateGeoDto;
import org.path.fortification.entity.StateType;
import org.path.fortification.enums.GeoAggregationType;
import org.path.fortification.enums.GeoManufacturerQuantityResponseType;

import java.util.List;

public interface DashboardService {
    List<StateGeoDto> getDashboardCountData(Integer year, DashboardRequestDto dto);

    ListResponse<?> getManufacturersQuantities(GeoManufacturerQuantityResponseType responseType, Long categoryId,
                                               GeoAggregationType type, String geoId, String search, Integer year, Integer pageNumber, Integer pageSize, DashboardRequestDto dto);

    void recompileGeoData(StateType stateType);
}
