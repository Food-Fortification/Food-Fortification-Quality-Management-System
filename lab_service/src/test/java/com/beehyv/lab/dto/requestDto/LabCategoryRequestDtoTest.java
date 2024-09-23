package com.beehyv.lab.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LabCategoryRequestDtoTest {

    @Test
    void constructorAndGetters_WorkCorrectly() {
        // Given
        Long id = 1L;
        Long categoryId = 2L;
        Boolean isEnabled = true;
        Long labId = 3L;

        // When
        LabCategoryRequestDto dto = new LabCategoryRequestDto(id, categoryId, isEnabled, labId);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(categoryId, dto.getCategoryId());
        assertEquals(isEnabled, dto.getIsEnabled());
        assertEquals(labId, dto.getLabId());
    }

    @Test
    void setters_WorkCorrectly() {
        // Given
        LabCategoryRequestDto dto = new LabCategoryRequestDto(1L,1L,true,1L);
        Long id = 1L;
        Long categoryId = 2L;
        Boolean isEnabled = true;
        Long labId = 3L;

        // When
        dto.setId(id);
        dto.setCategoryId(categoryId);
        dto.setIsEnabled(isEnabled);
        dto.setLabId(labId);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(categoryId, dto.getCategoryId());
        assertEquals(isEnabled, dto.getIsEnabled());
        assertEquals(labId, dto.getLabId());
    }
}
