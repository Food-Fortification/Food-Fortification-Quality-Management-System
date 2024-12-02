package org.path.lab.dto.responseDto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LabTestDocumentResponseDTOTest {

    @Test
    void testGettersAndSetters() {
        // Given
        Long id = 1L;
        CategoryDocumentRequirementResponseDTO categoryDocumentRequirement = new CategoryDocumentRequirementResponseDTO(null,null,null,null,null,null);
        LabTestResponseDTO labTest = new LabTestResponseDTO();
        String name = "TestDocument";
        String path = "/path/to/document";

        // When
        LabTestDocumentResponseDTO labTestDocumentResponseDTO = new LabTestDocumentResponseDTO(null,null,null,null,null);
        labTestDocumentResponseDTO.setId(id);
        labTestDocumentResponseDTO.setCategoryDocumentRequirement(categoryDocumentRequirement);
        labTestDocumentResponseDTO.setLabTest(labTest);
        labTestDocumentResponseDTO.setName(name);
        labTestDocumentResponseDTO.setPath(path);

        // Then
        assertEquals(id, labTestDocumentResponseDTO.getId());
        assertEquals(categoryDocumentRequirement, labTestDocumentResponseDTO.getCategoryDocumentRequirement());
        assertEquals(labTest, labTestDocumentResponseDTO.getLabTest());
        assertEquals(name, labTestDocumentResponseDTO.getName());
        assertEquals(path, labTestDocumentResponseDTO.getPath());
    }
}
