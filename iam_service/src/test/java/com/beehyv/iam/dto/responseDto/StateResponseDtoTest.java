package com.beehyv.iam.dto.responseDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StateResponseDtoTest {

    private StateResponseDto stateResponseDto;

    @BeforeEach
    public void setUp() {
        stateResponseDto = new StateResponseDto();
    }

    @Test
    public void testNoArgsConstructor() {
        StateResponseDto dto = new StateResponseDto();
        assertNull(dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getCode());
        assertNull(dto.getGeoId());
        assertNull(dto.getCountry());
    }

    @Test
    public void testAllArgsConstructor() {
        LocationResponseDto country = new LocationResponseDto();
        StateResponseDto dto = new StateResponseDto(1L, "State Name", "STATE", "GeoID", country);

        assertEquals(1L, dto.getId());
        assertEquals("State Name", dto.getName());
        assertEquals("STATE", dto.getCode());
        assertEquals("GeoID", dto.getGeoId());
        assertEquals(country, dto.getCountry());
    }

    @Test
    public void testSettersAndGetters() {
        LocationResponseDto country = new LocationResponseDto();
        stateResponseDto.setId(1L);
        stateResponseDto.setName("State Name");
        stateResponseDto.setCode("STATE");
        stateResponseDto.setGeoId("GeoID");
        stateResponseDto.setCountry(country);

        assertEquals(1L, stateResponseDto.getId());
        assertEquals("State Name", stateResponseDto.getName());
        assertEquals("STATE", stateResponseDto.getCode());
        assertEquals("GeoID", stateResponseDto.getGeoId());
        assertEquals(country, stateResponseDto.getCountry());
    }
}
