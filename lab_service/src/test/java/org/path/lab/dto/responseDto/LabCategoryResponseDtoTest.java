package org.path.lab.dto.responseDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LabCategoryResponseDtoTest {

    @Test
    void testAllArgsConstructor() {
        // Given
        Long id = 1L;
        Long categoryId = 1001L;
        Boolean isEnabled = true;
        LabResponseDTO lab = new LabResponseDTO();

        // When
        LabCategoryResponseDto labCategoryResponseDto = new LabCategoryResponseDto(id, categoryId, isEnabled, lab);

        // Then
        assertNotNull(labCategoryResponseDto);
        assertEquals(id, labCategoryResponseDto.getId());
        assertEquals(categoryId, labCategoryResponseDto.getCategoryId());
        assertEquals(isEnabled, labCategoryResponseDto.getIsEnabled());
        assertEquals(lab, labCategoryResponseDto.getLab());
    }

    @Test
    void testNoArgsConstructor() {
        // When
        LabCategoryResponseDto labCategoryResponseDto = new LabCategoryResponseDto();

        // Then
        assertNotNull(labCategoryResponseDto);
    }

    @Test
    void testGettersAndSetters() {
        // Given
        LabCategoryResponseDto labCategoryResponseDto = new LabCategoryResponseDto();
        Long id = 1L;
        Long categoryId = 1001L;
        Boolean isEnabled = true;
        LabResponseDTO lab = new LabResponseDTO();

        // When
        labCategoryResponseDto.setId(id);
        labCategoryResponseDto.setCategoryId(categoryId);
        labCategoryResponseDto.setIsEnabled(isEnabled);
        labCategoryResponseDto.setLab(lab);

        // Then
        assertEquals(id, labCategoryResponseDto.getId());
        assertEquals(categoryId, labCategoryResponseDto.getCategoryId());
        assertEquals(isEnabled, labCategoryResponseDto.getIsEnabled());
        assertEquals(lab, labCategoryResponseDto.getLab());
    }

    @Test
    void testToString() {
        // Given
        LabCategoryResponseDto labCategoryResponseDto = new LabCategoryResponseDto(1L, 1001L, true, new LabResponseDTO());

        // When
        String result = labCategoryResponseDto.toString();

        // Then
        assertNotNull(result);

    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        LabResponseDTO lab = new LabResponseDTO();
        LabCategoryResponseDto labCategoryResponseDto1 = new LabCategoryResponseDto(1L, 1001L, true, lab);
        LabCategoryResponseDto labCategoryResponseDto2 = new LabCategoryResponseDto(1L, 1001L, true, lab);


    }
}
