package org.path.iam.dto.responseDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ManufacturerDispatchableQuantityResponseDtoTest {

    private ManufacturerDispatchableQuantityResponseDto manufacturerDispatchableQuantityResponseDto;

    @BeforeEach
    public void setUp() {
        manufacturerDispatchableQuantityResponseDto = new ManufacturerDispatchableQuantityResponseDto();
    }

    @Test
    public void testNoArgsConstructor() {
        ManufacturerDispatchableQuantityResponseDto dto = new ManufacturerDispatchableQuantityResponseDto();
        assertNull(dto.getId());
        assertNull(dto.getDemand());
        assertNull(dto.getSupply());
        assertNull(dto.getDistrictGeoId());
    }

    @Test
    public void testAllArgsConstructor() {
        ManufacturerDispatchableQuantityResponseDto dto = new ManufacturerDispatchableQuantityResponseDto(1L, 100.0, 200.0, "GeoId123");
        assertEquals(1L, dto.getId());
        assertEquals(100.0, dto.getDemand());
        assertEquals(200.0, dto.getSupply());
        assertEquals("GeoId123", dto.getDistrictGeoId());
    }

    @Test
    public void testSettersAndGetters() {
        manufacturerDispatchableQuantityResponseDto.setId(1L);
        manufacturerDispatchableQuantityResponseDto.setDemand(100.0);
        manufacturerDispatchableQuantityResponseDto.setSupply(200.0);
        manufacturerDispatchableQuantityResponseDto.setDistrictGeoId("GeoId123");

        assertEquals(1L, manufacturerDispatchableQuantityResponseDto.getId());
        assertEquals(100.0, manufacturerDispatchableQuantityResponseDto.getDemand());
        assertEquals(200.0, manufacturerDispatchableQuantityResponseDto.getSupply());
        assertEquals("GeoId123", manufacturerDispatchableQuantityResponseDto.getDistrictGeoId());
    }
}
