package com.beehyv.lab.dto.requestDto;

import com.beehyv.lab.entity.LabTestType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LabTestTypeRequestDTOTest {

    @Test
    void constructorAndGetters_WorkCorrectly() {
        // Given
        Long id = 1L;
        String name = "Test Name";
        Long categoryId = 2L;
        Boolean isMandatory = true;
        LabTestType.Type type = LabTestType.Type.  PHYSICAL;
        List<LabTestReferenceMethodRequestDTO> labTestReferenceMethods = new ArrayList<>();

        // When
        LabTestTypeRequestDTO dto = new LabTestTypeRequestDTO(id, name, categoryId, isMandatory, type, labTestReferenceMethods);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(categoryId, dto.getCategoryId());
        assertEquals(isMandatory, dto.getIsMandatory());
        assertEquals(type, dto.getType());
        assertEquals(labTestReferenceMethods, dto.getLabTestReferenceMethods());
    }

    @Test
    void setters_WorkCorrectly() {
        // Given
        LabTestTypeRequestDTO dto = new LabTestTypeRequestDTO(null,null,null,null,null,null);
        Long id = 1L;
        String name = "Test Name";
        Long categoryId = 2L;
        Boolean isMandatory = true;
        LabTestType.Type type = LabTestType.Type.  PHYSICAL;
        List<LabTestReferenceMethodRequestDTO> labTestReferenceMethods = new ArrayList<>();

        // When
        dto.setId(id);
        dto.setName(name);
        dto.setCategoryId(categoryId);
        dto.setIsMandatory(isMandatory);
        dto.setType(type);
        dto.setLabTestReferenceMethods(labTestReferenceMethods);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(categoryId, dto.getCategoryId());
        assertEquals(isMandatory, dto.getIsMandatory());
        assertEquals(type, dto.getType());
        assertEquals(labTestReferenceMethods, dto.getLabTestReferenceMethods());
    }
}
