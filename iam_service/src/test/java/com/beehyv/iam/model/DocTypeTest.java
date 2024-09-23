package com.beehyv.iam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocTypeTest {

    private DocType docType;

    @BeforeEach
    void setUp() {
        docType = new DocType();
    }

    @Test
    void testDocTypeInitialization() {
        assertNull(docType.getId());
        assertNull(docType.getName());

    }

    @Test
    void testDocTypeConstructorWithArgs() {
        Long id = 1L;
        String name = "Document Type Name";

        DocType docType = new DocType(id, name);

        assertEquals(id, docType.getId());
        assertEquals(name, docType.getName());

    }

    @Test
    void testIdSetterGetter() {
        // Arrange
        Long id = 1L;

        // Act
        docType.setId(id);

        // Assert
        assertEquals(id, docType.getId());
    }

    @Test
    void testNameSetterGetter() {
        // Arrange
        String name = "Document Type Name";

        // Act
        docType.setName(name);

        // Assert
        assertEquals(name, docType.getName());
    }

    @Test
    void testIsDeletedSetterGetter() {
        // Act
        docType.setIsDeleted(true);

        // Assert
        assertTrue(docType.getIsDeleted());
    }


}
