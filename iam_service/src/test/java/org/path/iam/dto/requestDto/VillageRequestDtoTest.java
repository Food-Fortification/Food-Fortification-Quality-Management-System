package org.path.iam.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VillageRequestDtoTest {

    @Test
    public void testValidConstructionAndGetters() {
        Long id = 1L;
        String name = "My Village";
        Long districtId = 2L;

        VillageRequestDto requestDto = new VillageRequestDto(id, name, districtId);

        assertNotNull(requestDto);
        assertEquals(id, requestDto.getId());
        assertEquals(name, requestDto.getName());
        assertEquals(districtId, requestDto.getDistrictId());
    }

    @Test
    public void testWithOptionalId() {
        String name = "Another Village";
        Long districtId = 3L;

        VillageRequestDto requestDto = new VillageRequestDto(null, name, districtId);

        assertNotNull(requestDto);
        assertNull(requestDto.getId());
        assertEquals(name, requestDto.getName());
        assertEquals(districtId, requestDto.getDistrictId());
    }
}