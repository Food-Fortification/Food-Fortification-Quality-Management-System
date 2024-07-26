package com.beehyv.lab.dto.responseDto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LabDocumentResponseDTOTest {

    @Test
    void testAllArgsConstructor() {
        // Given
        Long id = 1L;
        String name = "Document Name";
        CategoryDocumentRequirementResponseDTO categoryDoc = new CategoryDocumentRequirementResponseDTO(null,null,null,null,null,null);
        String path = "/documents/path";

        // When
        LabDocumentResponseDTO labDocumentResponseDTO = new LabDocumentResponseDTO(id, name, categoryDoc, path);

        // Then
        assertNotNull(labDocumentResponseDTO);
        assertEquals(id, labDocumentResponseDTO.getId());
        assertEquals(name, labDocumentResponseDTO.getName());
        assertEquals(categoryDoc, labDocumentResponseDTO.getCategoryDoc());
        assertEquals(path, labDocumentResponseDTO.getPath());
    }

    @Test
    void testNoArgsConstructor() {
        // When
        LabDocumentResponseDTO labDocumentResponseDTO = new LabDocumentResponseDTO(null,null,null,null);

        // Then
        assertNotNull(labDocumentResponseDTO);
    }

    @Test
    void testGettersAndSetters() {
        // Given
        LabDocumentResponseDTO labDocumentResponseDTO = new LabDocumentResponseDTO(null,null,null,null);
        Long id = 1L;
        String name = "Document Name";
        CategoryDocumentRequirementResponseDTO categoryDoc = new CategoryDocumentRequirementResponseDTO(null,null,null,null,null,null);
        String path = "/documents/path";

        // When
        labDocumentResponseDTO.setId(id);
        labDocumentResponseDTO.setName(name);
        labDocumentResponseDTO.setCategoryDoc(categoryDoc);
        labDocumentResponseDTO.setPath(path);

        // Then
        assertEquals(id, labDocumentResponseDTO.getId());
        assertEquals(name, labDocumentResponseDTO.getName());
        assertEquals(categoryDoc, labDocumentResponseDTO.getCategoryDoc());
        assertEquals(path, labDocumentResponseDTO.getPath());
    }

    @Test
    void testToString() {
        // Given
        Long id = 1L;
        String name = "Document Name";
        CategoryDocumentRequirementResponseDTO categoryDoc = new CategoryDocumentRequirementResponseDTO(null,null,null,null,null,null);
        String path = "/documents/path";
        LabDocumentResponseDTO labDocumentResponseDTO = new LabDocumentResponseDTO(id, name, categoryDoc, path);

        // When
        String result = labDocumentResponseDTO.toString();

        // Then
        assertNotNull(result);
        assertTrue(result.contains("LabDocumentResponseDTO"));

    }
}
