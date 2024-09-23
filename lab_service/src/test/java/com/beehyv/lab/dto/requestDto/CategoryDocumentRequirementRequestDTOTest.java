package com.beehyv.lab.dto.requestDto;


import com.beehyv.lab.enums.CategoryDocRequirementType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDocumentRequirementRequestDTOTest {

    @Test
    void constructorAndGetters_WorkCorrectly() {
        // Given
        Long id = 1L;
        Long categoryId = 2L;
        Long docTypeId = 3L;
        Boolean isMandatory = true;
        Boolean isEnabled = false;
        CategoryDocRequirementType categoryDocRequirementType = CategoryDocRequirementType. TEST;

        // When
        CategoryDocumentRequirementRequestDTO dto = new CategoryDocumentRequirementRequestDTO(id, categoryId, docTypeId, isMandatory, isEnabled, categoryDocRequirementType);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(categoryId, dto.getCategoryId());
        assertEquals(docTypeId, dto.getDocTypeId());
        assertEquals(isMandatory, dto.getIsMandatory());
        assertEquals(isEnabled, dto.getIsEnabled());
        assertEquals(categoryDocRequirementType, dto.getCategoryDocRequirementType());
    }

    @Test
    void setters_WorkCorrectly() {
        // Given
        CategoryDocumentRequirementRequestDTO dto = new CategoryDocumentRequirementRequestDTO(1L,1L,1L,true,true,null);

        // When
        dto.setId(1L);
        dto.setCategoryId(2L);
        dto.setDocTypeId(3L);
        dto.setIsMandatory(true);
        dto.setIsEnabled(false);
        dto.setCategoryDocRequirementType(CategoryDocRequirementType. TEST);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals(2L, dto.getCategoryId());
        assertEquals(3L, dto.getDocTypeId());
        assertTrue(dto.getIsMandatory());
        assertFalse(dto.getIsEnabled());
        assertEquals(CategoryDocRequirementType. TEST, dto.getCategoryDocRequirementType());
    }
}
