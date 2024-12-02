package org.path.lab.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SampleTestDocumentRequestDTOTest {

    @Test
    void constructorAndGetters_WorkCorrectly() {
        // Given
        Long id = 1L;
        Long categoryDocId = 2L;
        Long labSampleId = 3L;
        String name = "Test Document";
        String path = "/path/to/document";

        // When
        SampleTestDocumentRequestDTO dto = new SampleTestDocumentRequestDTO(id, categoryDocId, labSampleId, name, path);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(categoryDocId, dto.getCategoryDocId());
        assertEquals(labSampleId, dto.getLabSampleId());
        assertEquals(name, dto.getName());
        assertEquals(path, dto.getPath());
    }

    @Test
    void setters_WorkCorrectly() {
        // Given
        SampleTestDocumentRequestDTO dto = new SampleTestDocumentRequestDTO();
        Long id = 1L;
        Long categoryDocId = 2L;
        Long labSampleId = 3L;
        String name = "Test Document";
        String path = "/path/to/document";

        // When
        dto.setId(id);
        dto.setCategoryDocId(categoryDocId);
        dto.setLabSampleId(labSampleId);
        dto.setName(name);
        dto.setPath(path);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(categoryDocId, dto.getCategoryDocId());
        assertEquals(labSampleId, dto.getLabSampleId());
        assertEquals(name, dto.getName());
        assertEquals(path, dto.getPath());
    }
}
