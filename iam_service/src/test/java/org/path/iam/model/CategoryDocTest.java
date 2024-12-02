package org.path.iam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CategoryDocTest {

    private CategoryDoc categoryDoc;

    @BeforeEach
    void setUp() {
        categoryDoc = new CategoryDoc();
    }


    @Test
    void testCategoryDocConstructorWithArgs() {
        Long id = 1L;
        Long categoryId = 101L;
        DocType docType = new DocType();
        Boolean isMandatory = true;
        Boolean isEnabled = false;

        CategoryDoc doc = new CategoryDoc(id, categoryId, docType, isMandatory, isEnabled);

        assertEquals(id, doc.getId());
        assertEquals(categoryId, doc.getCategoryId());
        assertEquals(docType, doc.getDocType());
        assertEquals(isMandatory, doc.getIsMandatory());
        assertEquals(isEnabled, doc.getIsEnabled());
    }

    @Test
    void testIdSetterGetter() {
        Long id = 1L;

        categoryDoc.setId(id);

        assertEquals(id, categoryDoc.getId());
    }

    @Test
    void testCategoryIdSetterGetter() {
        Long categoryId = 101L;

        categoryDoc.setCategoryId(categoryId);

        assertEquals(categoryId, categoryDoc.getCategoryId());
    }

    @Test
    void testDocTypeSetterGetter() {
        DocType docType = new DocType();

        categoryDoc.setDocType(docType);

        assertEquals(docType, categoryDoc.getDocType());
    }

    @Test
    void testIsMandatorySetterGetter() {
        Boolean isMandatory = true;

        categoryDoc.setIsMandatory(isMandatory);

        assertEquals(isMandatory, categoryDoc.getIsMandatory());
    }

    @Test
    void testIsEnabledSetterGetter() {
        Boolean isEnabled = false;

        categoryDoc.setIsEnabled(isEnabled);

        assertEquals(isEnabled, categoryDoc.getIsEnabled());
    }

    @Test
    void testIsDeletedSetterGetter() {
        categoryDoc.setIsDeleted(true);

        assertTrue(categoryDoc.getIsDeleted());
    }

    @Test
    void testToString() {
        Long id = 1L;
        Long categoryId = 101L;
        DocType docType = new DocType();
        Boolean isMandatory = true;
        Boolean isEnabled = false;
        categoryDoc = new CategoryDoc(id, categoryId, docType, isMandatory, isEnabled);

        String toStringOutput = categoryDoc.toString();


    }

    @Test
    void testEqualsAndHashCode() {
        Long id = 1L;
        Long categoryId = 101L;
        DocType docType = new DocType();
        Boolean isMandatory = true;
        Boolean isEnabled = false;
        CategoryDoc doc1 = new CategoryDoc(id, categoryId, docType, isMandatory, isEnabled);
        CategoryDoc doc2 = new CategoryDoc(id, categoryId, docType, isMandatory, isEnabled);

    }
}
