package org.path.lab.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SampleGeoStateEventTest {

    @Test
    void constructorAndGetters_WorkCorrectly() {
        // Given
        Long categoryId = 1L;
        Long labId = 2L;
        Long manufacturerId = 3L;
        String districtGeoId = "districtGeoId";
        String stateGeoId = "stateGeoId";
        String countryGeoId = "countryGeoId";
        String pincode = "123456";
        String state = "State";
        String action = "Action";
        Integer sampleSentYear = 2022;

        // When
        SampleGeoStateEvent event = new SampleGeoStateEvent();
        event.setCategoryId(categoryId);
        event.setLabId(labId);
        event.setManufacturerId(manufacturerId);
        event.setDistrictGeoId(districtGeoId);
        event.setStateGeoId(stateGeoId);
        event.setCountryGeoId(countryGeoId);
        event.setPincode(pincode);
        event.setState(state);
        event.setAction(action);
        event.setSampleSentYear(sampleSentYear);

        // Then
        assertEquals(categoryId, event.getCategoryId());
        assertEquals(labId, event.getLabId());
        assertEquals(manufacturerId, event.getManufacturerId());
        assertEquals(districtGeoId, event.getDistrictGeoId());
        assertEquals(stateGeoId, event.getStateGeoId());
        assertEquals(countryGeoId, event.getCountryGeoId());
        assertEquals(pincode, event.getPincode());
        assertEquals(state, event.getState());
        assertEquals(action, event.getAction());
        assertEquals(sampleSentYear, event.getSampleSentYear());
    }
}

