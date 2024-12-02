package org.path.iam.dto.responseDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class VillageResponseDtoTest {

    private VillageResponseDto villageResponseDto;

    @BeforeEach
    public void setUp() {
        villageResponseDto = new VillageResponseDto();
    }

    @Test
    public void testNoArgsConstructor() {
        VillageResponseDto dto = new VillageResponseDto();
        assertNull(dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getCode());
        assertNull(dto.getGeoId());
        assertNull(dto.getDistrict());
    }

    @Test
    public void testAllArgsConstructor() {
        DistrictResponseDto district = new DistrictResponseDto();
        VillageResponseDto dto = new VillageResponseDto(1L, "Village Name", "CODE", "GEOID", district);

        assertEquals(1L, dto.getId());
        assertEquals("Village Name", dto.getName());
        assertEquals("CODE", dto.getCode());
        assertEquals("GEOID", dto.getGeoId());
        assertEquals(district, dto.getDistrict());
    }

    @Test
    public void testSettersAndGetters() {
        DistrictResponseDto district = new DistrictResponseDto();
        villageResponseDto.setId(1L);
        villageResponseDto.setName("Village Name");
        villageResponseDto.setCode("CODE");
        villageResponseDto.setGeoId("GEOID");
        villageResponseDto.setDistrict(district);

        assertEquals(1L, villageResponseDto.getId());
        assertEquals("Village Name", villageResponseDto.getName());
        assertEquals("CODE", villageResponseDto.getCode());
        assertEquals("GEOID", villageResponseDto.getGeoId());
        assertEquals(district, villageResponseDto.getDistrict());
    }
}
