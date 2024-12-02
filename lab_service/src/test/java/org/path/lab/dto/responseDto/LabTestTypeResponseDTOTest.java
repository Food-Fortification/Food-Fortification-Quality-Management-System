package org.path.lab.dto.responseDto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class LabTestTypeResponseDTOTest {

    @Test
    void testGettersAndSetters() {
        // Given
        Long id = 1L;
        String name = "TestType";
        Long categoryId = 2L;
        Boolean isMandatory = true;
        List<LabTestReferenceMethodResponseDTO> labTestReferenceMethods = new ArrayList<>();

        // When
        LabTestTypeResponseDTO labTestTypeResponseDTO = new LabTestTypeResponseDTO();
        labTestTypeResponseDTO.setId(id);
        labTestTypeResponseDTO.setName(name);
        labTestTypeResponseDTO.setCategoryId(categoryId);
        labTestTypeResponseDTO.setIsMandatory(isMandatory);
        labTestTypeResponseDTO.setLabTestReferenceMethods(labTestReferenceMethods);

        // Then
        assertEquals(id, labTestTypeResponseDTO.getId());
        assertEquals(name, labTestTypeResponseDTO.getName());
        assertEquals(categoryId, labTestTypeResponseDTO.getCategoryId());
        assertEquals(isMandatory, labTestTypeResponseDTO.getIsMandatory());
        assertEquals(labTestReferenceMethods, labTestTypeResponseDTO.getLabTestReferenceMethods());
    }
}
