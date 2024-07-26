package com.beehyv.fortification.entity;

import com.beehyv.fortification.enums.ManufacturerCategoryAction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SourceCategoryMappingTest {

    @Test
    void testAllFields() {
        // Arrange
        SourceCategoryMapping sourceCategoryMapping = new SourceCategoryMapping();
        Long id = 1L;
        Category targetCategory = new Category(1L);
        Category sourceCategory = new Category(2L);
        Category returnCategory = new Category(3L);
        ManufacturerCategoryAction categoryAction = ManufacturerCategoryAction.LOT_TO_LOT_DISPATCH;
        Long stateGeoId = 1L;

        // Act
        sourceCategoryMapping.setId(id);
        sourceCategoryMapping.setTargetCategory(targetCategory);
        sourceCategoryMapping.setSourceCategory(sourceCategory);
        sourceCategoryMapping.setReturnCategory(returnCategory);
        sourceCategoryMapping.setCategoryAction(categoryAction);
        sourceCategoryMapping.setStateGeoId(stateGeoId);

        // Assert
        assertEquals(id, sourceCategoryMapping.getId());
        assertEquals(targetCategory, sourceCategoryMapping.getTargetCategory());
        assertEquals(sourceCategory, sourceCategoryMapping.getSourceCategory());
        assertEquals(returnCategory, sourceCategoryMapping.getReturnCategory());
        assertEquals(categoryAction, sourceCategoryMapping.getCategoryAction());
        assertEquals(stateGeoId, sourceCategoryMapping.getStateGeoId());
    }
}