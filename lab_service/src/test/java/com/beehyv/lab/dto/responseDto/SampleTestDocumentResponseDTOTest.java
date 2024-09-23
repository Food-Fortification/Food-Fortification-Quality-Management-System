package com.beehyv.lab.dto.responseDto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleTestDocumentResponseDTOTest {

    @Test
    void testGettersAndSetters() {
        // Given
        SampleTestDocumentResponseDTO sampleTestDocumentResponseDTO = new SampleTestDocumentResponseDTO();
        Long id = 1L;
        CategoryDocumentRequirementResponseDTO categoryDoc = new CategoryDocumentRequirementResponseDTO(null,null,null,null,null,null);
        Long labSampleId = 2L;
        String name = "Test Document";
        String path = "/path/to/document";

        // When
        sampleTestDocumentResponseDTO.setId(id);
        sampleTestDocumentResponseDTO.setCategoryDoc(categoryDoc);
        sampleTestDocumentResponseDTO.setLabSampleId(labSampleId);
        sampleTestDocumentResponseDTO.setName(name);
        sampleTestDocumentResponseDTO.setPath(path);

        // Then
        assertEquals(id, sampleTestDocumentResponseDTO.getId());
        assertEquals(categoryDoc, sampleTestDocumentResponseDTO.getCategoryDoc());
        assertEquals(labSampleId, sampleTestDocumentResponseDTO.getLabSampleId());
        assertEquals(name, sampleTestDocumentResponseDTO.getName());
        assertEquals(path, sampleTestDocumentResponseDTO.getPath());
    }
}
