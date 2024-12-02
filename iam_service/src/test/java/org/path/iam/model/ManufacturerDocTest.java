package org.path.iam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ManufacturerDocTest {

    private ManufacturerDoc manufacturerDoc;

    @BeforeEach
    void setUp() {
        manufacturerDoc = new ManufacturerDoc();
    }

    @Test
    void testManufacturerDocInitialization() {
        // Assert initial state of manufacturerDoc object
        assertNull(manufacturerDoc.getId());
        assertNull(manufacturerDoc.getManufacturer());
        assertNull(manufacturerDoc.getCategoryDoc());
        assertNull(manufacturerDoc.getDocName());
        assertNull(manufacturerDoc.getDocPath());
        assertNull(manufacturerDoc.getDocExpiry());

    }

    @Test
    void testIdSetterGetter() {
        // Arrange
        Long id = 1L;

        // Act
        manufacturerDoc.setId(id);

        // Assert
        assertEquals(id, manufacturerDoc.getId());
    }

    @Test
    void testManufacturerSetterGetter() {
        // Arrange
        Manufacturer manufacturer = new Manufacturer();

        // Act
        manufacturerDoc.setManufacturer(manufacturer);

        // Assert
        assertEquals(manufacturer, manufacturerDoc.getManufacturer());
    }

    @Test
    void testCategoryDocSetterGetter() {
        // Arrange
        CategoryDoc categoryDoc = new CategoryDoc();

        // Act
        manufacturerDoc.setCategoryDoc(categoryDoc);

        // Assert
        assertEquals(categoryDoc, manufacturerDoc.getCategoryDoc());
    }

    @Test
    void testDocNameSetterGetter() {
        // Arrange
        String docName = "Test Document";

        // Act
        manufacturerDoc.setDocName(docName);

        // Assert
        assertEquals(docName, manufacturerDoc.getDocName());
    }

    @Test
    void testDocPathSetterGetter() {
        // Arrange
        String docPath = "/path/to/document";

        // Act
        manufacturerDoc.setDocPath(docPath);

        // Assert
        assertEquals(docPath, manufacturerDoc.getDocPath());
    }

    @Test
    void testDocExpirySetterGetter() {
        // Arrange
        LocalDate docExpiry = LocalDate.now();

        // Act
        manufacturerDoc.setDocExpiry(docExpiry);

        // Assert
        assertEquals(docExpiry, manufacturerDoc.getDocExpiry());
    }

    @Test
    void testConstructorWithArgs() {
        // Arrange
        Long id = 1L;
        Manufacturer manufacturer = new Manufacturer();
        CategoryDoc categoryDoc = new CategoryDoc();
        String docName = "Test Document";
        String docPath = "/path/to/document";
        LocalDate docExpiry = LocalDate.now();

        // Act
        ManufacturerDoc manufacturerDoc = new ManufacturerDoc(id, manufacturer, categoryDoc, docName, docPath, docExpiry);

        // Assert
        assertEquals(id, manufacturerDoc.getId());
        assertEquals(manufacturer, manufacturerDoc.getManufacturer());
        assertEquals(categoryDoc, manufacturerDoc.getCategoryDoc());
        assertEquals(docName, manufacturerDoc.getDocName());
        assertEquals(docPath, manufacturerDoc.getDocPath());
        assertEquals(docExpiry, manufacturerDoc.getDocExpiry());

    }

    @Test
    void testToString() {
        // Arrange
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(1L);
        CategoryDoc categoryDoc = new CategoryDoc();
        categoryDoc.setId(1L);
        String docName = "Test Document";
        String docPath = "/path/to/document";
        LocalDate docExpiry = LocalDate.now();

        manufacturerDoc.setId(1L);
        manufacturerDoc.setManufacturer(manufacturer);
        manufacturerDoc.setCategoryDoc(categoryDoc);
        manufacturerDoc.setDocName(docName);
        manufacturerDoc.setDocPath(docPath);
        manufacturerDoc.setDocExpiry(docExpiry);

        // Act
        String toString = manufacturerDoc.toString();


    }
}
