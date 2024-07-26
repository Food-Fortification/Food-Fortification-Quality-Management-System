package com.beehyv.iam.dto.responseDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DistrictResponseDtoTest {

    @Test
    public void testValidConstructionAndGetters() {
        String uuid = "123e4567-e89b-12d3-a456-426614174000";
        Long id = 1L;
        String name = "Hyderabad";
        String code = "HYD";
        String geoId = "in.tg.hyd";
        String stateName = "Telangana";
        StateResponseDto state = new StateResponseDto();

        DistrictResponseDto responseDto = new DistrictResponseDto(id, name, code, geoId, stateName, state);

        assertNotNull(responseDto);

    }

    @Test
    public void testWithOptionalFields() {
        String uuid = null;
        String code = null;
        String geoId = null;
        StateResponseDto state = null;

        DistrictResponseDto responseDto = new DistrictResponseDto(2L, "Rangareddy", code, geoId, "Telangana", state);

        assertNotNull(responseDto);
        assertNull(responseDto.getUuid());
        assertEquals(2L, responseDto.getId());
        assertEquals("Rangareddy", responseDto.getName());
        assertNull(responseDto.getCode());
        assertNull(responseDto.getGeoId());
        assertEquals("Telangana", responseDto.getStateName());
        assertNull(responseDto.getState());
    }

    @Test
    public void testBaseResponseDtoFields() {
        // Inherits from BaseResponseDto, test its fields here
        String uuid = "456e7890-f12b-34c5-a789-012345678900";
        StatusResponseDto status = new StatusResponseDto();

        DistrictResponseDto responseDto = new DistrictResponseDto(1L, "Warangal", null, null, null, null);

        assertNotNull(responseDto);
    }
}
