package org.path.iam.mapper;

import org.path.iam.dto.requestDto.ManufacturerCategoryRequestDto;
import org.path.iam.dto.responseDto.ManufacturerCategoryResponseDto;
import org.path.iam.model.Manufacturer;
import org.path.iam.model.ManufacturerCategory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManufacturerCategoryMapperTest {

    @Test
    void testToDto() {
        // Prepare test data
        ManufacturerCategory manufacturerCategory = new ManufacturerCategory();
        manufacturerCategory.setId(1L);
        manufacturerCategory.setCanSkipRawMaterials(true);
        manufacturerCategory.setCategoryId(2L);
        manufacturerCategory.setIsEnabled(true);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(3L);
        manufacturerCategory.setManufacturer(manufacturer);

        // Create the mapper
        ManufacturerCategoryMapper manufacturerCategoryMapper = new ManufacturerCategoryMapper();

        // Map the entity to DTO
        ManufacturerCategoryResponseDto manufacturerCategoryResponseDto = manufacturerCategoryMapper.toDto(manufacturerCategory);

        // Assertions
        assertNotNull(manufacturerCategoryResponseDto);
        assertEquals(1L, manufacturerCategoryResponseDto.getId());
        assertEquals(2L, manufacturerCategoryResponseDto.getCategoryId());
        assertNotNull(manufacturerCategoryResponseDto.getManufacturerDto());
        assertEquals(3L, manufacturerCategoryResponseDto.getManufacturerDto().getId());
    }

    @Test
    void testToEntity() {
        // Prepare test data
        ManufacturerCategoryRequestDto manufacturerCategoryRequestDto = new ManufacturerCategoryRequestDto();
        manufacturerCategoryRequestDto.setId(1L);
        manufacturerCategoryRequestDto.setCanSkipRawMaterials(true);
        manufacturerCategoryRequestDto.setCategoryId(2L);
        manufacturerCategoryRequestDto.setIsEnabled(true);
        manufacturerCategoryRequestDto.setManufacturerId(3L);

        // Create the mapper
        ManufacturerCategoryMapper manufacturerCategoryMapper = new ManufacturerCategoryMapper();

        // Map the DTO to entity
        ManufacturerCategory manufacturerCategory = manufacturerCategoryMapper.toEntity(manufacturerCategoryRequestDto);

        // Assertions
        assertNotNull(manufacturerCategory);
        assertEquals(1L, manufacturerCategory.getId());
        assertTrue(manufacturerCategory.getCanSkipRawMaterials());
        assertEquals(2L, manufacturerCategory.getCategoryId());
        assertTrue(manufacturerCategory.getIsEnabled());
        assertNotNull(manufacturerCategory.getManufacturer());
        assertEquals(3L, manufacturerCategory.getManufacturer().getId());
    }
}
