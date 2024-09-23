package com.beehyv.lab.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LabTestReferenceMethodRequestDTOTest {

    @Test
    void constructorAndGetters_WorkCorrectly() {
        // Given
        Long id = 1L;
        String name = "Test Name";
        Long labTestTypeId = 2L;
        Double minValue = 0.0;
        Double maxValue = 100.0;
        String uom = "Test UOM";
        String defaultPresent = "Yes";
        String referenceValue = "Test Reference Value";

        // When
        LabTestReferenceMethodRequestDTO dto = new LabTestReferenceMethodRequestDTO(id, name, labTestTypeId, minValue, maxValue, uom, defaultPresent, referenceValue);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(labTestTypeId, dto.getLabTestTypeId());
        assertEquals(minValue, dto.getMinValue());
        assertEquals(maxValue, dto.getMaxValue());
        assertEquals(uom, dto.getUom());
        assertEquals(defaultPresent, dto.getDefaultPresent());
        assertEquals(referenceValue, dto.getReferenceValue());
    }

    @Test
    void setters_WorkCorrectly() {
        // Given
        LabTestReferenceMethodRequestDTO dto = new LabTestReferenceMethodRequestDTO(null,null,null,null,null,null,null,null);
        Long id = 1L;
        String name = "Test Name";
        Long labTestTypeId = 2L;
        Double minValue = 0.0;
        Double maxValue = 100.0;
        String uom = "Test UOM";
        String defaultPresent = "Yes";
        String referenceValue = "Test Reference Value";

        // When
        dto.setId(id);
        dto.setName(name);
        dto.setLabTestTypeId(labTestTypeId);
        dto.setMinValue(minValue);
        dto.setMaxValue(maxValue);
        dto.setUom(uom);
        dto.setDefaultPresent(defaultPresent);
        dto.setReferenceValue(referenceValue);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(labTestTypeId, dto.getLabTestTypeId());
        assertEquals(minValue, dto.getMinValue());
        assertEquals(maxValue, dto.getMaxValue());
        assertEquals(uom, dto.getUom());
        assertEquals(defaultPresent, dto.getDefaultPresent());
        assertEquals(referenceValue, dto.getReferenceValue());
    }
}
