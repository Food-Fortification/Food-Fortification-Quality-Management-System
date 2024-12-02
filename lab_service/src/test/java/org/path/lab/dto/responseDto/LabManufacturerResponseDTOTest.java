package org.path.lab.dto.responseDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LabManufacturerResponseDTOTest {

    @Test
    void testAllArgsConstructor() {
        // Given
        Long id = 1L;
        Long categoryId = 2L;
        Long manufacturerId = 3L;
        LabResponseDTO lab = new LabResponseDTO();

        // When
        LabManufacturerResponseDTO labManufacturerResponseDTO = new LabManufacturerResponseDTO(id, categoryId, manufacturerId, lab);

        // Then
        assertNotNull(labManufacturerResponseDTO);
        assertEquals(id, labManufacturerResponseDTO.getId());
        assertEquals(categoryId, labManufacturerResponseDTO.getCategoryId());
        assertEquals(manufacturerId, labManufacturerResponseDTO.getManufacturerId());
        assertEquals(lab, labManufacturerResponseDTO.getLab());
    }

    @Test
    void testNoArgsConstructor() {
        // When
        LabManufacturerResponseDTO labManufacturerResponseDTO = new LabManufacturerResponseDTO();

        // Then
        assertNotNull(labManufacturerResponseDTO);
    }

    @Test
    void testGettersAndSetters() {
        // Given
        LabManufacturerResponseDTO labManufacturerResponseDTO = new LabManufacturerResponseDTO();
        Long id = 1L;
        Long categoryId = 2L;
        Long manufacturerId = 3L;
        LabResponseDTO lab = new LabResponseDTO();

        // When
        labManufacturerResponseDTO.setId(id);
        labManufacturerResponseDTO.setCategoryId(categoryId);
        labManufacturerResponseDTO.setManufacturerId(manufacturerId);
        labManufacturerResponseDTO.setLab(lab);

        // Then
        assertEquals(id, labManufacturerResponseDTO.getId());
        assertEquals(categoryId, labManufacturerResponseDTO.getCategoryId());
        assertEquals(manufacturerId, labManufacturerResponseDTO.getManufacturerId());
        assertEquals(lab, labManufacturerResponseDTO.getLab());
    }

    @Test
    void testToString() {
        // Given
        Long id = 1L;
        Long categoryId = 2L;
        Long manufacturerId = 3L;
        LabResponseDTO lab = new LabResponseDTO();
        LabManufacturerResponseDTO labManufacturerResponseDTO = new LabManufacturerResponseDTO(id, categoryId, manufacturerId, lab);

        // When
        String result = labManufacturerResponseDTO.toString();

        // Then
        assertNotNull(result);
        assertTrue(result.contains("LabManufacturerResponseDTO"));

    }
}
