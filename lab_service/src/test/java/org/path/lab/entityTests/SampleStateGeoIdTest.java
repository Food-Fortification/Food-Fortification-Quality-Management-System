package org.path.lab.entityTests;

import org.path.lab.entity.SampleStateGeoId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SampleStateGeoIdTest {

    @InjectMocks
    private SampleStateGeoId sampleStateGeoId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllFields() {
        Long categoryIdValue = 1L;
        Long labIdValue = 1L;
        Long manufacturerIdValue = 1L;
        String districtGeoIdValue = "Test District GeoId";
        String stateGeoIdValue = "Test State GeoId";
        String countryGeoIdValue = "Test Country GeoId";
        Integer sampleSentYearValue = 2022;

        sampleStateGeoId.setCategoryId(categoryIdValue);
        sampleStateGeoId.setLabId(labIdValue);
        sampleStateGeoId.setManufacturerId(manufacturerIdValue);
        sampleStateGeoId.setDistrictGeoId(districtGeoIdValue);
        sampleStateGeoId.setStateGeoId(stateGeoIdValue);
        sampleStateGeoId.setCountryGeoId(countryGeoIdValue);
        sampleStateGeoId.setSampleSentYear(sampleSentYearValue);

        assertEquals(categoryIdValue, sampleStateGeoId.getCategoryId());
        assertEquals(labIdValue, sampleStateGeoId.getLabId());
        assertEquals(manufacturerIdValue, sampleStateGeoId.getManufacturerId());
        assertEquals(districtGeoIdValue, sampleStateGeoId.getDistrictGeoId());
        assertEquals(stateGeoIdValue, sampleStateGeoId.getStateGeoId());
        assertEquals(countryGeoIdValue, sampleStateGeoId.getCountryGeoId());
        assertEquals(sampleSentYearValue, sampleStateGeoId.getSampleSentYear());
    }
}