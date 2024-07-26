package com.beehyv.iam.dto.responseDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressResponseDtoTest {

    @Test
    void testValidConstructionAndGetters() {
        Long id = 1L;
        String laneOne = "123 Main St";
        String laneTwo = "Apt B";
        String pinCode = "123456";
        VillageResponseDto village = new VillageResponseDto();
        Double latitude = 37.7749;
        Double longitude = -122.4194;
        ManufacturerDetailsResponseDto manufacturerDetails = new ManufacturerDetailsResponseDto();

        AddressResponseDto responseDto = new AddressResponseDto(id, laneOne, laneTwo, pinCode, village, latitude, longitude, manufacturerDetails);

        assertNotNull(responseDto);
        assertEquals(id, responseDto.getId());
        assertEquals(laneOne, responseDto.getLaneOne());
        assertEquals(laneTwo, responseDto.getLaneTwo());
        assertEquals(pinCode, responseDto.getPinCode());
        assertEquals(village, responseDto.getVillage());
        assertEquals(latitude, responseDto.getLatitude());
        assertEquals(longitude, responseDto.getLongitude());
        assertEquals(manufacturerDetails, responseDto.getManufacturerDetails());
    }

    @Test
    void testWithOptionalFields() {
        String laneOne = "456 Elm St";
        String pinCode = "987654";
        Double latitude = 40.7128;
        Double longitude = -74.0059;

        AddressResponseDto responseDto = new AddressResponseDto(null, laneOne, null, pinCode, null, latitude, longitude, null);

        assertNotNull(responseDto);
        assertNull(responseDto.getId());
        assertEquals(laneOne, responseDto.getLaneOne());
        assertNull(responseDto.getLaneTwo());
        assertEquals(pinCode, responseDto.getPinCode());
        assertNull(responseDto.getVillage());
        assertEquals(latitude, responseDto.getLatitude());
        assertEquals(longitude, responseDto.getLongitude());
        assertNull(responseDto.getManufacturerDetails());
    }
}
