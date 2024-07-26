package com.beehyv.lab.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressRequestDTOTest {

    @Test
    void constructorAndGetters_WorkCorrectly() {
        // Given
        Long id = 1L;
        String laneOne = "Lane 1";
        String laneTwo = "Lane 2";
        Long villageId = 100L;
        String pinCode = "123456";
        Double latitude = 12.345;
        Double longitude = 67.890;

        // When
        AddressRequestDTO dto = new AddressRequestDTO(id, laneOne, laneTwo, villageId, pinCode, latitude, longitude);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(laneOne, dto.getLaneOne());
        assertEquals(laneTwo, dto.getLaneTwo());
        assertEquals(villageId, dto.getVillageId());
        assertEquals(pinCode, dto.getPinCode());
        assertEquals(latitude, dto.getLatitude());
        assertEquals(longitude, dto.getLongitude());
    }

    @Test
    void setters_WorkCorrectly() {
        // Given
        AddressRequestDTO dto = new AddressRequestDTO(1L,"n","n",1L,"K",1.0,1.0);

        // When
        dto.setId(1L);
        dto.setLaneOne("Lane 1");
        dto.setLaneTwo("Lane 2");
        dto.setVillageId(100L);
        dto.setPinCode("123456");
        dto.setLatitude(12.345);
        dto.setLongitude(67.890);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("Lane 1", dto.getLaneOne());
        assertEquals("Lane 2", dto.getLaneTwo());
        assertEquals(100L, dto.getVillageId());
        assertEquals("123456", dto.getPinCode());
        assertEquals(12.345, dto.getLatitude());
        assertEquals(67.890, dto.getLongitude());
    }
}
