package org.path.lab.service.impl;

import org.path.lab.dto.responseDto.*;
import org.path.lab.entity.SampleState;
import org.path.lab.enums.GeoAggregationType;
import org.path.lab.helper.RestHelper;
import org.path.parent.keycloakSecurity.KeycloakInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.path.lab.manager.LabManager;
import org.path.lab.manager.LabSampleManager;
import org.path.lab.manager.SampleStateGeoManager;
import org.path.lab.entity.LabSample;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DashboardServiceImplTest {

    @InjectMocks
    private DashboardServiceImpl dashboardService;

    @Mock
    private LabSampleManager labSampleManager;

    @Mock
    private SampleStateGeoManager sampleStateGeoManager;

    @Mock
    private LabManager labManager;

    @Mock
    private KeycloakInfo keycloakInfo;

    @Mock
    private RestHelper restHelper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCount() {
        // Arrange
        String createStartDate = "2023-01-01";
        String createEndDate = "2023-12-31";
        LabSample labSample = new LabSample();
        labSample.setCategoryId(1L);
        labSample.setState(new SampleState(1L,"1","1"));
        when(keycloakInfo.getUserInfo()).thenReturn(Map.of("labId", 1));
        when(keycloakInfo.getAccessToken()).thenReturn("token");
        when(restHelper.getCategories(anyString(), anyString())).thenReturn(List.of(new CategoryResponseDto(1L, "Category 1", Set.of(new CategoryResponseDto()))));
        when(labSampleManager.findAllByCreateDate(anyList(), eq(createStartDate), eq(createEndDate), anyLong()))
                .thenReturn(List.of(labSample));

        // Act
        Object result = dashboardService.getCount(createStartDate, createEndDate);

        // Assert
        assertNotNull(result);
        // Add more assertions as needed
    }

    @Test
    void getAggregatedSampleStatesCount() {
        // Arrange
        Long categoryId = 1L;
        GeoAggregationType type = GeoAggregationType.district;
        String geoId = "123";
        Integer year = 2023;
        when(sampleStateGeoManager.getSamplesGeoCount(any(),any(),any(),any(),any())).thenReturn((new Long[]{1L,2L,1L,2L,1L,2L}));


        // Act
        AggregatedResponseDto result = dashboardService.getAggregatedSampleStatesCount(categoryId, type, geoId, year);
        assertNotNull(result);
        type = GeoAggregationType.state;
         result = dashboardService.getAggregatedSampleStatesCount(categoryId, type, geoId, year);
        assertNotNull(result);
         type = GeoAggregationType.country;
         result = dashboardService.getAggregatedSampleStatesCount(categoryId, type, geoId, year);
        assertNotNull(result);

    }

    @Test
    void getSampleSateGeoCountLabs() {
        // Arrange
        Long categoryId = 1L;
        GeoAggregationType type = GeoAggregationType.district;
        String geoId = "123";
        String search = "";
        Integer year = 2023;
        Integer pageNumber = 0;
        Integer pageSize = 10;
        when(sampleStateGeoManager.getSamplesGeoCount(any(),any(),any(),any(),any())).thenReturn((new Long[]{1L,2L}));
        // Act
        ListResponse<SampleStateGeoLabResponseDto> result = dashboardService.getSampleSateGeoCountLabs(categoryId, type, geoId, search, year, pageNumber, pageSize);

        // Assert
        assertNotNull(result);
        // Add more assertions as needed
    }


    @Test
    void getCategoryCounts() {
        // Arrange
        Long categoryId = 1L;
        GeoAggregationType type = GeoAggregationType.state;
        String geoId = "123";

        DashboardResponseDto result = dashboardService.getCategoryCounts(categoryId, type, geoId);

        assertNotNull(result);

    }

    @Test
    void getYearFromDate() {
        Date date = new Date();
        Integer year = dashboardService.getYearFromDate(date);
        assertNotNull(year);

    }
}