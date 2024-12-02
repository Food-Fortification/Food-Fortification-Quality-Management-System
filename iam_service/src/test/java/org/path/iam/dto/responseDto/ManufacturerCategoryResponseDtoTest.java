package org.path.iam.dto.responseDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ManufacturerCategoryResponseDtoTest {

    private ManufacturerCategoryResponseDto manufacturerCategoryResponseDto;
    private ManufacturerResponseDto manufacturerResponseDto;

    @BeforeEach
    public void setUp() {
        manufacturerResponseDto = new ManufacturerResponseDto();
        manufacturerResponseDto.setId(1L);
        manufacturerResponseDto.setName("Test Manufacturer");

        manufacturerCategoryResponseDto = new ManufacturerCategoryResponseDto();
    }

    @Test
    public void testNoArgsConstructor() {
        ManufacturerCategoryResponseDto dto = new ManufacturerCategoryResponseDto();
        assertNull(dto.getId());
        assertNull(dto.getCategoryId());
        assertNull(dto.getCanSkipRawMaterials());
        assertNull(dto.getIsEnabled());
        assertNull(dto.getManufacturerDto());
    }

    @Test
    public void testAllArgsConstructor() {
        ManufacturerCategoryResponseDto dto = new ManufacturerCategoryResponseDto(1L, 2L, true, false, manufacturerResponseDto);
        assertEquals(1L, dto.getId());
        assertEquals(2L, dto.getCategoryId());
        assertEquals(true, dto.getCanSkipRawMaterials());
        assertEquals(false, dto.getIsEnabled());
        assertEquals(manufacturerResponseDto, dto.getManufacturerDto());
    }

    @Test
    public void testSettersAndGetters() {
        manufacturerCategoryResponseDto.setId(1L);
        manufacturerCategoryResponseDto.setCategoryId(2L);
        manufacturerCategoryResponseDto.setCanSkipRawMaterials(true);
        manufacturerCategoryResponseDto.setIsEnabled(false);
        manufacturerCategoryResponseDto.setManufacturerDto(manufacturerResponseDto);

        assertEquals(1L, manufacturerCategoryResponseDto.getId());
        assertEquals(2L, manufacturerCategoryResponseDto.getCategoryId());
        assertEquals(true, manufacturerCategoryResponseDto.getCanSkipRawMaterials());
        assertEquals(false, manufacturerCategoryResponseDto.getIsEnabled());
        assertEquals(manufacturerResponseDto, manufacturerCategoryResponseDto.getManufacturerDto());
    }


}
