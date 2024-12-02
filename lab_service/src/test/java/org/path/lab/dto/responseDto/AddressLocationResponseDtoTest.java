package org.path.lab.dto.responseDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressLocationResponseDtoTest {

    @Test
    void testAllArgsConstructor() {
        // Given
        LocationResponseDto country = new LocationResponseDto();
        LocationResponseDto state = new LocationResponseDto();
        LocationResponseDto district = new LocationResponseDto();
        LocationResponseDto village = new LocationResponseDto();
        String laneOne = "Lane One";
        String laneTwo = "Lane Two";
        String pinCode = "123456";
        Double latitude = 12.345678;
        Double longitude = 98.765432;

        // When
        AddressLocationResponseDto dto = new AddressLocationResponseDto(laneOne, laneTwo, country, state, district, village, pinCode, latitude, longitude);

        // Then
        assertNotNull(dto);
        assertEquals(laneOne, dto.getLaneOne());
        assertEquals(laneTwo, dto.getLaneTwo());
        assertEquals(country, dto.getCountry());
        assertEquals(state, dto.getState());
        assertEquals(district, dto.getDistrict());
        assertEquals(village, dto.getVillage());
        assertEquals(pinCode, dto.getPinCode());
        assertEquals(latitude, dto.getLatitude());
        assertEquals(longitude, dto.getLongitude());
    }

    @Test
    void testNoArgsConstructor() {
        // When
        AddressLocationResponseDto dto = new AddressLocationResponseDto();

        // Then
        assertNotNull(dto);
        assertNull(dto.getLaneOne());
        assertNull(dto.getLaneTwo());
        assertNull(dto.getCountry());
        assertNull(dto.getState());
        assertNull(dto.getDistrict());
        assertNull(dto.getVillage());
        assertNull(dto.getPinCode());
        assertNull(dto.getLatitude());
        assertNull(dto.getLongitude());
    }

    @Test
    void testGettersAndSetters() {
        // Given
        AddressLocationResponseDto dto = new AddressLocationResponseDto();
        LocationResponseDto country = new LocationResponseDto();
        LocationResponseDto state = new LocationResponseDto();
        LocationResponseDto district = new LocationResponseDto();
        LocationResponseDto village = new LocationResponseDto();
        String laneOne = "Lane One";
        String laneTwo = "Lane Two";
        String pinCode = "123456";
        Double latitude = 12.345678;
        Double longitude = 98.765432;

        // When
        dto.setLaneOne(laneOne);
        dto.setLaneTwo(laneTwo);
        dto.setCountry(country);
        dto.setState(state);
        dto.setDistrict(district);
        dto.setVillage(village);
        dto.setPinCode(pinCode);
        dto.setLatitude(latitude);
        dto.setLongitude(longitude);

        // Then
        assertEquals(laneOne, dto.getLaneOne());
        assertEquals(laneTwo, dto.getLaneTwo());
        assertEquals(country, dto.getCountry());
        assertEquals(state, dto.getState());
        assertEquals(district, dto.getDistrict());
        assertEquals(village, dto.getVillage());
        assertEquals(pinCode, dto.getPinCode());
        assertEquals(latitude, dto.getLatitude());
        assertEquals(longitude, dto.getLongitude());
    }

    @Test
    void testToString() {
        // Given
        LocationResponseDto country = new LocationResponseDto();
        LocationResponseDto state = new LocationResponseDto();
        LocationResponseDto district = new LocationResponseDto();
        LocationResponseDto village = new LocationResponseDto();
        String laneOne = "Lane One";
        String laneTwo = "Lane Two";
        String pinCode = "123456";
        Double latitude = 12.345678;
        Double longitude = 98.765432;

        AddressLocationResponseDto dto = new AddressLocationResponseDto(laneOne, laneTwo, country, state, district, village, pinCode, latitude, longitude);

        // When
        String toStringResult = dto.toString();

        // Then
        assertNotNull(toStringResult);

    }
}
