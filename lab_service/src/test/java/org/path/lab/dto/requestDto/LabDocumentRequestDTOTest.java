package org.path.lab.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LabDocumentRequestDTOTest {

    @Test
    void constructorAndGetters_WorkCorrectly() {
        // Given
        Long id = 1L;
        String name = "Test Document";
        Long categoryDocId = 2L;
        String path = "/path/to/document";
        Long labId = 3L;

        // When
        LabDocumentRequestDTO dto = new LabDocumentRequestDTO(id, name, categoryDocId, path, labId);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(categoryDocId, dto.getCategoryDocId());
        assertEquals(path, dto.getPath());
        assertEquals(labId, dto.getLabId());
    }

    @Test
    void setters_WorkCorrectly() {
        // Given
        LabDocumentRequestDTO dto = new LabDocumentRequestDTO(1L,"M",1L,"L",1L);
        Long id = 1L;
        String name = "Test Document";
        Long categoryDocId = 2L;
        String path = "/path/to/document";
        Long labId = 3L;

        // When
        dto.setId(id);
        dto.setName(name);
        dto.setCategoryDocId(categoryDocId);
        dto.setPath(path);
        dto.setLabId(labId);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(categoryDocId, dto.getCategoryDocId());
        assertEquals(path, dto.getPath());
        assertEquals(labId, dto.getLabId());
    }
}
