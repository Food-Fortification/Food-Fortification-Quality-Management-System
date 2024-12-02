package org.path.lab.dto.responseDto;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LabListResponseDTOTest {

    @Test
    void testAllArgsConstructor() {
        // Given
        Long id = 1L;
        String name = "Lab Name";
        StatusResponseDto status = new StatusResponseDto(1L, "Active", "Lab is active");
        Set<LabCategoryResponseDto> labCategoryMapping = new HashSet<>();

        // When
        LabListResponseDTO labListResponseDTO = new LabListResponseDTO(id, name, status, labCategoryMapping);

        // Then
        assertNotNull(labListResponseDTO);
        assertEquals(id, labListResponseDTO.getId());
        assertEquals(name, labListResponseDTO.getName());
        assertEquals(status, labListResponseDTO.getStatus());
        assertEquals(labCategoryMapping, labListResponseDTO.getLabCategoryMapping());
    }

    @Test
    void testNoArgsConstructor() {
        // When
        LabListResponseDTO labListResponseDTO = new LabListResponseDTO();

        // Then
        assertNotNull(labListResponseDTO);
    }

    @Test
    void testGettersAndSetters() {
        // Given
        LabListResponseDTO labListResponseDTO = new LabListResponseDTO();
        Long id = 1L;
        String name = "Lab Name";
        StatusResponseDto status = new StatusResponseDto(1L, "Active", "Lab is active");
        Set<LabCategoryResponseDto> labCategoryMapping = new HashSet<>();

        // When
        labListResponseDTO.setId(id);
        labListResponseDTO.setName(name);
        labListResponseDTO.setStatus(status);
        labListResponseDTO.setLabCategoryMapping(labCategoryMapping);

        // Then
        assertEquals(id, labListResponseDTO.getId());
        assertEquals(name, labListResponseDTO.getName());
        assertEquals(status, labListResponseDTO.getStatus());
        assertEquals(labCategoryMapping, labListResponseDTO.getLabCategoryMapping());
    }

    @Test
    void testToString() {
        // Given
        Long id = 1L;
        String name = "Lab Name";
        StatusResponseDto status = new StatusResponseDto(1L, "Active", "Lab is active");
        Set<LabCategoryResponseDto> labCategoryMapping = new HashSet<>();
        LabListResponseDTO labListResponseDTO = new LabListResponseDTO(id, name, status, labCategoryMapping);

        // When
        String result = labListResponseDTO.toString();

        // Then
        assertNotNull(result);
        assertTrue(result.contains("LabListResponseDTO"));

    }
}
