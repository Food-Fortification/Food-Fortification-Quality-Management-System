package com.beehyv.lab.entityTests;


import com.beehyv.lab.entity.CategoryDocumentRequirement;
import com.beehyv.lab.entity.DocType;
import com.beehyv.lab.enums.CategoryDocRequirementType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class CategoryDocumentRequirementTest {

    @Mock
    private DocType docType;

    @InjectMocks
    private CategoryDocumentRequirement categoryDocumentRequirement;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllFields() {
        Long idValue = 1L;
        Long categoryIdValue = 1L;
        Boolean isMandatoryValue = true;
        Boolean isEnabledValue = true;
        CategoryDocRequirementType categoryDocRequirementTypeValue = CategoryDocRequirementType.TEST;

        when(docType.getId()).thenReturn(1L);

        categoryDocumentRequirement.setId(idValue);
        categoryDocumentRequirement.setCategoryId(categoryIdValue);
        categoryDocumentRequirement.setDocType(docType);
        categoryDocumentRequirement.setIsMandatory(isMandatoryValue);
        categoryDocumentRequirement.setIsEnabled(isEnabledValue);
        categoryDocumentRequirement.setCategoryDocRequirementType(categoryDocRequirementTypeValue);

        assertEquals(idValue, categoryDocumentRequirement.getId());
        assertEquals(categoryIdValue, categoryDocumentRequirement.getCategoryId());
        assertEquals(1L, categoryDocumentRequirement.getDocType().getId());
        assertTrue(categoryDocumentRequirement.getIsMandatory());
        assertTrue(categoryDocumentRequirement.getIsEnabled());
        assertEquals(categoryDocRequirementTypeValue, categoryDocumentRequirement.getCategoryDocRequirementType());
    }
}