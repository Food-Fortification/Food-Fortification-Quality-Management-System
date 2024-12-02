package org.path.lab.dto.responseDto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LabTestReferenceMethodResponseDTOTest {

    @Test
    void testGettersAndSetters() {
        // Given
        Long id = 1L;
        String name = "TestReferenceMethod";
        LabTestTypeResponseDTO labTestType = new LabTestTypeResponseDTO();
        Double minValue = 0.0;
        Double maxValue = 100.0;
        String uom = "unit";
        String defaultPresent = "default";
        String referenceValue = "reference";

        // When
        LabTestReferenceMethodResponseDTO labTestReferenceMethodResponseDTO = new LabTestReferenceMethodResponseDTO();
        labTestReferenceMethodResponseDTO.setId(id);
        labTestReferenceMethodResponseDTO.setName(name);
        labTestReferenceMethodResponseDTO.setLabTestType(labTestType);
        labTestReferenceMethodResponseDTO.setMinValue(minValue);
        labTestReferenceMethodResponseDTO.setMaxValue(maxValue);
        labTestReferenceMethodResponseDTO.setUom(uom);
        labTestReferenceMethodResponseDTO.setDefaultPresent(defaultPresent);
        labTestReferenceMethodResponseDTO.setReferenceValue(referenceValue);

        // Then
        assertEquals(id, labTestReferenceMethodResponseDTO.getId());
        assertEquals(name, labTestReferenceMethodResponseDTO.getName());
        assertEquals(labTestType, labTestReferenceMethodResponseDTO.getLabTestType());
        assertEquals(minValue, labTestReferenceMethodResponseDTO.getMinValue());
        assertEquals(maxValue, labTestReferenceMethodResponseDTO.getMaxValue());
        assertEquals(uom, labTestReferenceMethodResponseDTO.getUom());
        assertEquals(defaultPresent, labTestReferenceMethodResponseDTO.getDefaultPresent());
        assertEquals(referenceValue, labTestReferenceMethodResponseDTO.getReferenceValue());
    }
}
