package org.path.lab.dto.requestDto;

import org.path.lab.enums.GeoAggregationType;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DashboardRequestDtoTest {

    @Test
    void gettersAndSetters_WorkCorrectly() {
        // Given
        Date fromDate = new Date();
        Date toDate = new Date();
        Long categoryId = 1L;
        GeoAggregationType type = GeoAggregationType.state;
        String geoId = "123";
        String sourceStateGeoId = "456";
        String targetStateGeoId = "789";
        String sourceDistrictGeoId = "1011";
        String targetDistrictGeoId = "1213";
        String sourceCountryGeoId = "1415";
        String targetCountryGeoId = "1617";
        String empanelledStateGeoId = "1819";

        // When
        DashboardRequestDto dto = new DashboardRequestDto();
        dto.setFromDate(fromDate);
        dto.setToDate(toDate);
        dto.setCategoryId(categoryId);
        dto.setType(type);
        dto.setGeoId(geoId);
        dto.setSourceStateGeoId(sourceStateGeoId);
        dto.setTargetStateGeoId(targetStateGeoId);
        dto.setSourceDistrictGeoId(sourceDistrictGeoId);
        dto.setTargetDistrictGeoId(targetDistrictGeoId);
        dto.setSourceCountryGeoId(sourceCountryGeoId);
        dto.setTargetCountryGeoId(targetCountryGeoId);
        dto.setEmpanelledStateGeoId(empanelledStateGeoId);

        // Then
        assertEquals(fromDate, dto.getFromDate());
        assertEquals(toDate, dto.getToDate());
        assertEquals(categoryId, dto.getCategoryId());
        assertEquals(type, dto.getType());
        assertEquals(geoId, dto.getGeoId());
        assertEquals(sourceStateGeoId, dto.getSourceStateGeoId());
        assertEquals(targetStateGeoId, dto.getTargetStateGeoId());
        assertEquals(sourceDistrictGeoId, dto.getSourceDistrictGeoId());
        assertEquals(targetDistrictGeoId, dto.getTargetDistrictGeoId());
        assertEquals(sourceCountryGeoId, dto.getSourceCountryGeoId());
        assertEquals(targetCountryGeoId, dto.getTargetCountryGeoId());
        assertEquals(empanelledStateGeoId, dto.getEmpanelledStateGeoId());
    }
}
