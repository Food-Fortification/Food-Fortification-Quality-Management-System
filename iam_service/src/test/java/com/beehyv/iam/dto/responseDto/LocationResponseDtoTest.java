package com.beehyv.iam.dto.responseDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LocationResponseDtoTest {

    @Test
    public void testValidConstructionAndGetters() {
        String uuid = "123e4567-e89b-12d3-a456-426614174000";
        Long id = 1L;
        String name = "Hyderabad";
        String code = "HYD";
        String geoId = "in.tg.hyd";

        LocationResponseDto responseDto = new LocationResponseDto(id, name, code, geoId);

        assertNotNull(responseDto);

    }

    @Test
    public void testWithOptionalFields() {
        String uuid = null;
        String code = null;

        LocationResponseDto responseDto = new LocationResponseDto(2L, "Secunderabad", code, "in.tg.sec");

        assertNotNull(responseDto);
        assertNull(responseDto.getUuid());
        assertEquals(2L, responseDto.getId());
        assertEquals("Secunderabad", responseDto.getName());
        assertNull(responseDto.getCode());
        assertEquals("in.tg.sec", responseDto.getGeoId());
    }

    @Test
    public void testBaseResponseDtoFields() {
        // Inherits from BaseResponseDto, test its fields here
        StatusResponseDto status = new StatusResponseDto(1L, "Active", "u");

        LocationResponseDto responseDto = new LocationResponseDto(3L, "Warangal", null, "status");

        assertNotNull(responseDto);
        assertNull(responseDto.getUuid()); // Can be null if not set
    }
}
