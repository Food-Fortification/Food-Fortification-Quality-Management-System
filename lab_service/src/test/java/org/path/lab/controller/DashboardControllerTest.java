package org.path.lab.controller;

import org.path.lab.dto.responseDto.AggregatedResponseDto;
import org.path.lab.dto.responseDto.DashboardResponseDto;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.dto.responseDto.SampleStateGeoLabResponseDto;
import org.path.lab.enums.GeoAggregationType;
import org.path.lab.service.impl.DashboardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DashboardControllerTest {

    @Mock
    private DashboardServiceImpl dashboardService;

    @InjectMocks
    private DashboardController dashboardController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCount() {
        String create_date_start = "2022-01-01";
        String create_date_end = "2022-12-31";
        Object expectedResponse = new Object();

        when(dashboardService.getCount(create_date_start, create_date_end)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = dashboardController.getCount(create_date_start, create_date_end);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(dashboardService, times(1)).getCount(create_date_start, create_date_end);
        verifyNoMoreInteractions(dashboardService);
    }

    @Test
    void testLabsCountByGeoId() {
        Long categoryId = 1L;
        GeoAggregationType type = GeoAggregationType.state;
        String geoId = "IN";
        DashboardResponseDto expectedResponse = new DashboardResponseDto();

        when(dashboardService.getCategoryCounts(categoryId, type, geoId)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = dashboardController.labsCountByGeoId(categoryId, type, geoId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(dashboardService, times(1)).getCategoryCounts(categoryId, type, geoId);
        verifyNoMoreInteractions(dashboardService);
    }

    @Test
    void testLabCategoriesCountByGeoId() {
        GeoAggregationType type = GeoAggregationType.state;
        String geoId = "IN";
        Map<String, DashboardResponseDto> expectedResponse = Map.of("key", new DashboardResponseDto());

        when(dashboardService.getCategoriesCounts(type, geoId)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = dashboardController.labCategoriesCountByGeoId(type, geoId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(dashboardService, times(1)).getCategoriesCounts(type, geoId);
        verifyNoMoreInteractions(dashboardService);
    }

    @Test
    void testGetAggregatedSampleStatesCount() {
        Long categoryId = 1L;
        GeoAggregationType type = GeoAggregationType.state;
        Integer year = 2022;
        String geoId = "IN";
        AggregatedResponseDto expectedResponse = new AggregatedResponseDto();

        when(dashboardService.getAggregatedSampleStatesCount(categoryId, type, geoId, year)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = dashboardController.getAggregatedSampleStatesCount(categoryId, type, year, geoId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(dashboardService, times(1)).getAggregatedSampleStatesCount(categoryId, type, geoId, year);
        verifyNoMoreInteractions(dashboardService);
    }

    @Test
    void testGetLabsGeoSampleCount() {
        Long categoryId = 1L;
        GeoAggregationType type = GeoAggregationType.state;
        Integer year = 2022;
        String geoId = "IN";
        Integer pageNumber = 1;
        Integer pageSize = 10;
        String search = "test";
        ListResponse<SampleStateGeoLabResponseDto> expectedResponse = new ListResponse<>();

        when(dashboardService.getSampleSateGeoCountLabs(categoryId, type, geoId, search, year, pageNumber, pageSize)).thenReturn(expectedResponse);

        ResponseEntity<ListResponse<SampleStateGeoLabResponseDto>> responseEntity = dashboardController.getLabsGeoSampleCount(categoryId, type, geoId, year, pageNumber, pageSize, search);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(dashboardService, times(1)).getSampleSateGeoCountLabs(categoryId, type, geoId, search, year, pageNumber, pageSize);
        verifyNoMoreInteractions(dashboardService);
    }

    @Test
    void testRecompileSampleGeoData() {
        doNothing().when(dashboardService).recompile();

        ResponseEntity<String> responseEntity = dashboardController.recompileSampleGeoData();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Success", responseEntity.getBody());

        verify(dashboardService, times(1)).recompile();
        verifyNoMoreInteractions(dashboardService);
    }
}