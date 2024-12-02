package org.path.lab.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LabTestDocumentRequestDTOTest {

    @Test
    void constructorAndGetters_WorkCorrectly() {
        // Given
        Long id = 1L;
        Long categoryDocumentRequirementId = 2L;
        Long labTestId = 3L;
        String name = "Test Document";
        String path = "/path/to/document";

        // When
        LabTestDocumentRequestDTO dto = new LabTestDocumentRequestDTO(id, categoryDocumentRequirementId, labTestId, name, path);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(categoryDocumentRequirementId, dto.getCategoryDocumentRequirementId());
        assertEquals(labTestId, dto.getLabTestId());
        assertEquals(name, dto.getName());
        assertEquals(path, dto.getPath());
    }

    @Test
    void setters_WorkCorrectly() {
        // Given
        LabTestDocumentRequestDTO dto = new LabTestDocumentRequestDTO(1L,1L,1L,"L","L");
        Long id = 1L;
        Long categoryDocumentRequirementId = 2L;
        Long labTestId = 3L;
        String name = "Test Document";
        String path = "/path/to/document";

        // When
        dto.setId(id);
        dto.setCategoryDocumentRequirementId(categoryDocumentRequirementId);
        dto.setLabTestId(labTestId);
        dto.setName(name);
        dto.setPath(path);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(categoryDocumentRequirementId, dto.getCategoryDocumentRequirementId());
        assertEquals(labTestId, dto.getLabTestId());
        assertEquals(name, dto.getName());
        assertEquals(path, dto.getPath());
    }
}
