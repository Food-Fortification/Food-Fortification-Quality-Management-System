package com.beehyv.lab.dto.responseDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressResponseDTOTest {

    @Test
    void testAllArgsConstructor() {
        // Given
        Long id = 1L;
        String laneOne = "Lane One";
        String laneTwo = "Lane Two";
        String pinCode = "123456";
        VillageResponseDTO village = new VillageResponseDTO(1L, "Village",null);
        Double latitude = 12.345678;
        Double longitude = 98.765432;

        // When
        AddressResponseDTO dto = new AddressResponseDTO(id, laneOne, laneTwo, pinCode, village, latitude, longitude);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals(laneOne, dto.getLaneOne());
        assertEquals(laneTwo, dto.getLaneTwo());
        assertEquals(pinCode, dto.getPinCode());
        assertEquals(village, dto.getVillage());
        assertEquals(latitude, dto.getLatitude());
        assertEquals(longitude, dto.getLongitude());
    }

    @Test
    void testNoArgsConstructor() {
        // When
        AddressResponseDTO dto = new AddressResponseDTO();

        // Then
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getLaneOne());
        assertNull(dto.getLaneTwo());
        assertNull(dto.getPinCode());
        assertNull(dto.getVillage());
        assertNull(dto.getLatitude());
        assertNull(dto.getLongitude());
    }

    @Test
    void testGettersAndSetters() {
        // Given
        AddressResponseDTO dto = new AddressResponseDTO();
        Long id = 1L;
        String laneOne = "Lane One";
        String laneTwo = "Lane Two";
        String pinCode = "123456";
        VillageResponseDTO village = new VillageResponseDTO(1L, "Village",null);
        Double latitude = 12.345678;
        Double longitude = 98.765432;

        // When
        dto.setId(id);
        dto.setLaneOne(laneOne);
        dto.setLaneTwo(laneTwo);
        dto.setPinCode(pinCode);
        dto.setVillage(village);
        dto.setLatitude(latitude);
        dto.setLongitude(longitude);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(laneOne, dto.getLaneOne());
        assertEquals(laneTwo, dto.getLaneTwo());
        assertEquals(pinCode, dto.getPinCode());
        assertEquals(village, dto.getVillage());
        assertEquals(latitude, dto.getLatitude());
        assertEquals(longitude, dto.getLongitude());
    }

    @Test
    void testToString() {
        // Given
        Long id = 1L;
        String laneOne = "Lane One";
        String laneTwo = "Lane Two";
        String pinCode = "123456";
        VillageResponseDTO village = new VillageResponseDTO(1L, "Village",null);
        Double latitude = 12.345678;
        Double longitude = 98.765432;

        AddressResponseDTO dto = new AddressResponseDTO(id, laneOne, laneTwo, pinCode, village, latitude, longitude);

        // When
        String toStringResult = dto.toString();

        // Then
        assertNotNull(toStringResult);

    }
}
