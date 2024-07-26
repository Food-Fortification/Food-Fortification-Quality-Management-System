package com.beehyv.lab.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LabManufacturerRequestDTOTest {

    @Test
    void constructorAndGetters_WorkCorrectly() {
        // Given
        Long id = 1L;
        Long manufacturerId = 2L;
        Long labId = 3L;
        Long categoryId = 4L;

        // When
        LabManufacturerRequestDTO dto = new LabManufacturerRequestDTO(id, manufacturerId, labId, categoryId);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(manufacturerId, dto.getManufacturerId());
        assertEquals(labId, dto.getLabId());
        assertEquals(categoryId, dto.getCategoryId());
    }

    @Test
    void setters_WorkCorrectly() {
        // Given
        LabManufacturerRequestDTO dto = new LabManufacturerRequestDTO(1L,1L,1L,1l);
        Long id = 1L;
        Long manufacturerId = 2L;
        Long labId = 3L;
        Long categoryId = 4L;

        // When
        dto.setId(id);
        dto.setManufacturerId(manufacturerId);
        dto.setLabId(labId);
        dto.setCategoryId(categoryId);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(manufacturerId, dto.getManufacturerId());
        assertEquals(labId, dto.getLabId());
        assertEquals(categoryId, dto.getCategoryId());
    }
}
