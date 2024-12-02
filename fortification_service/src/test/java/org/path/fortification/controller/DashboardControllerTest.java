package org.path.fortification.controller;

import org.path.fortification.dto.requestDto.DashboardRequestDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.dto.responseDto.StateGeoDto;
import org.path.fortification.entity.StateType;
import org.path.fortification.enums.GeoAggregationType;
import org.path.fortification.enums.GeoManufacturerQuantityResponseType;
import org.path.fortification.service.DashboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DashboardControllerTest {

    @Mock
    private DashboardService service;

    @InjectMocks
    private DashboardController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCount() {
        Integer year = 2022;
        DashboardRequestDto dto = new DashboardRequestDto();
        when(service.getDashboardCountData(year, dto)).thenReturn(Collections.singletonList(new StateGeoDto()));
        ResponseEntity<List<StateGeoDto>> response = controller.getCount(year, dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).getDashboardCountData(year, dto);
    }

    // ... existing test class code ...

    @Test
    void testGetManufacturerQuantities() {
        // Initialize test data
        GeoManufacturerQuantityResponseType responseType = GeoManufacturerQuantityResponseType.testing;
        Long categoryId = 1L;
        GeoAggregationType type = GeoAggregationType.state;
        String geoId = "someGeoId";
        Integer year = 2022;
        Integer pageNumber = 1;
        Integer pageSize = 5;
        String search = "someSearch";
        DashboardRequestDto dto = new DashboardRequestDto();

        // Mock service method
        when(service.getManufacturersQuantities(responseType, categoryId, type, geoId, search, year, pageNumber, pageSize, dto))
                .thenReturn(new ListResponse<>(0L, Collections.emptyList()));

        // Call controller method and assert response
        ResponseEntity<ListResponse<?>> response = (ResponseEntity<ListResponse<?>>) controller.getManufacturerQuantities(responseType, categoryId, type, geoId, year, pageNumber, pageSize, search, dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).getManufacturersQuantities(responseType, categoryId, type, geoId, search, year, pageNumber, pageSize, dto);
    }

    @Test
    void testRecompileGeoData() {
        String stateTypeName = "Batch";
        StateType stateType = StateType.valueOf(stateTypeName.toUpperCase());
        doNothing().when(service).recompileGeoData(stateType);
        ResponseEntity<String> response = controller.recompileGeoData(stateTypeName);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", response.getBody());
        verify(service, times(1)).recompileGeoData(stateType);
    }
}