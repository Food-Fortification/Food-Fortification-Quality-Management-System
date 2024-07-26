package com.beehyv.iam.mapper;

import com.beehyv.iam.dto.requestDto.ManufacturerPropertyRequestDto;
import com.beehyv.iam.dto.responseDto.ManufacturerPropertyResponseDto;
import com.beehyv.iam.model.ManufacturerProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ManufacturerPropertyMapperTest {

    private ManufacturerPropertyMapper manufacturerPropertyMapper;

    @BeforeEach
    void setUp() {
        manufacturerPropertyMapper = new ManufacturerPropertyMapper();
    }

    @Test
    void testToDto() {
        // Create ManufacturerProperty object
        ManufacturerProperty manufacturerProperty = new ManufacturerProperty();
        manufacturerProperty.setId(1L);
        manufacturerProperty.setName("Test Property");
        manufacturerProperty.setValue("Test Value");

        // Perform mapping
        ManufacturerPropertyResponseDto responseDto = manufacturerPropertyMapper.toDto(manufacturerProperty);

        // Assertions
        assertEquals(1L, responseDto.getId());
        assertEquals("Test Property", responseDto.getName());
        assertEquals("Test Value", responseDto.getValue());
    }

    @Test
    void testToEntity() {
        // Create ManufacturerPropertyRequestDto object
        ManufacturerPropertyRequestDto requestDto = new ManufacturerPropertyRequestDto();
        requestDto.setManufacturerId(1L);
        requestDto.setId(1L);
        requestDto.setName("Test Property");
        requestDto.setValue("Test Value");

        // Perform mapping
        ManufacturerProperty entity = manufacturerPropertyMapper.toEntity(requestDto);

        // Assertions
        assertEquals(1L, entity.getId());
        assertEquals("Test Property", entity.getName());
        assertEquals("Test Value", entity.getValue());
        assertEquals(1L, entity.getManufacturer().getId());
    }
}
