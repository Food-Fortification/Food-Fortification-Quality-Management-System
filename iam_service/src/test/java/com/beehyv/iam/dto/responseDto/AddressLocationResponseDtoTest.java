package com.beehyv.iam.dto.responseDto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressLocationResponseDtoTest {

    @Test
    void testValidConstructionAndGetters() {
        String laneOne = "123 Main St";
        String laneTwo = "Apt B";
        LocationResponseDto country = new LocationResponseDto(1L, "USA", "US", "USA123");
        LocationResponseDto state = new LocationResponseDto(2L, "California", "CA", "CA123");
        LocationResponseDto district = new LocationResponseDto(3L, "San Francisco", "SF", "SF123");
        LocationResponseDto village = null; // optional
        String pinCode = "123456";
        Double latitude = 37.7749;
        Double longitude = -122.4194;

        AddressLocationResponseDto responseDto = new AddressLocationResponseDto(laneOne, laneTwo, country, state, district, village, pinCode, latitude, longitude);

        assertNotNull(responseDto);
        assertEquals(laneOne, responseDto.getLaneOne());
        assertEquals(laneTwo, responseDto.getLaneTwo());
        assertEquals(country, responseDto.getCountry());
        assertEquals(state, responseDto.getState());
        assertEquals(district, responseDto.getDistrict());
        assertEquals(village, responseDto.getVillage());
        assertEquals(pinCode, responseDto.getPinCode());
        assertEquals(latitude, responseDto.getLatitude());
        assertEquals(longitude, responseDto.getLongitude());
    }

    @Test
    void testWithOptionalFields() {
        String laneOne = "456 Elm St";
        LocationResponseDto country = new LocationResponseDto(1L, "India", "IN", "IND123");
        String pinCode = "987654";
        Double latitude = 40.7128;
        Double longitude = -74.0059;

        AddressLocationResponseDto responseDto = new AddressLocationResponseDto(laneOne, null, country, null, null, null, pinCode, latitude, longitude);

        assertNotNull(responseDto);
        assertEquals(laneOne, responseDto.getLaneOne());
        assertNull(responseDto.getLaneTwo());
        assertEquals(country, responseDto.getCountry());
        assertNull(responseDto.getState());
        assertNull(responseDto.getDistrict());
        assertNull(responseDto.getVillage());
        assertEquals(pinCode, responseDto.getPinCode());
        assertEquals(latitude, responseDto.getLatitude());
        assertEquals(longitude, responseDto.getLongitude());
    }

    @Test
    void testJsonIncludeProperties() throws Exception {
        String laneOne = "123 Main St";
        LocationResponseDto country = new LocationResponseDto(1L, "USA", "US", "USA123");
        LocationResponseDto state = new LocationResponseDto(2L, "California", "CA", "CA123");
        LocationResponseDto district = new LocationResponseDto(3L, "San Francisco", "SF", "SF123");
        LocationResponseDto village = null; // optional
        String pinCode = "123456";
        Double latitude = 37.7749;
        Double longitude = -122.4194;

        AddressLocationResponseDto responseDto = new AddressLocationResponseDto(laneOne, "tt", country, state, district, village, pinCode, latitude, longitude);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(responseDto);

        // Assert that only specified properties from @JsonIncludeProperties are present in JSON for location objects
        assertTrue(json.contains("\"id\":"));
        assertTrue(json.contains("\"name\":"));
        assertTrue(json.contains("\"code\":"));
        assertTrue(json.contains("\"geoId\":"));
        assertFalse(json.contains("\"locationDetails\":")); // Assuming no such property exists
    }
}
