package org.path.lab.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationRequestDTOTest {

    @Test
    void constructorAndGetters_WorkCorrectly() {
        // Given
        String laneOne = "Lane One";
        String laneTwo = "Lane Two";
        String country = "Country";
        String state = "State";
        String district = "District";
        String village = "Village";
        String pinCode = "123456";
        Double latitude = 12.345;
        Double longitude = 67.890;

        // When
        LocationRequestDTO dto = new LocationRequestDTO(laneOne, laneTwo, country, state, district, village, pinCode, latitude, longitude);

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
    void setters_WorkCorrectly() {
        // Given
        LocationRequestDTO dto = new LocationRequestDTO();
        String laneOne = "Lane One";
        String laneTwo = "Lane Two";
        String country = "Country";
        String state = "State";
        String district = "District";
        String village = "Village";
        String pinCode = "123456";
        Double latitude = 12.345;
        Double longitude = 67.890;

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
}
