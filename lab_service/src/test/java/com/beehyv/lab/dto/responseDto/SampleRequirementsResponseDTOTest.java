package com.beehyv.lab.dto.responseDto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;

public class SampleRequirementsResponseDTOTest {

    @Test
    void testGettersAndSetters() {
        // Given
        SampleRequirementsResponseDTO sampleRequirementsResponseDTO = new SampleRequirementsResponseDTO();
        Long categoryId = 1L;
        List<LabTestReferenceMethodResponseDTO> labTestReferenceMethods = new ArrayList<>();
        List<CategoryDocumentRequirementResponseDTO> categoryDocumentRequirements = new ArrayList<>();

        // When
        sampleRequirementsResponseDTO.setCategoryId(categoryId);
        sampleRequirementsResponseDTO.setLabTestReferenceMethods(labTestReferenceMethods);
        sampleRequirementsResponseDTO.setCategoryDocumentRequirements(categoryDocumentRequirements);

        // Then
        assertEquals(categoryId, sampleRequirementsResponseDTO.getCategoryId());
        assertEquals(labTestReferenceMethods, sampleRequirementsResponseDTO.getLabTestReferenceMethods());
        assertEquals(categoryDocumentRequirements, sampleRequirementsResponseDTO.getCategoryDocumentRequirements());
    }
}
