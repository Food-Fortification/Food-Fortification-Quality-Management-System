package com.beehyv.iam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExternalMetaDataTest {

    private ExternalMetaData externalMetaData;
    private PurchaseOrder purchaseOrder;

    @BeforeEach
    void setUp() {
        externalMetaData = new ExternalMetaData();
        purchaseOrder = new PurchaseOrder();
    }

    @Test
    void testExternalMetaDataInitialization() {
        // Assert initial state of externalMetaData object
        assertNull(externalMetaData.getId());
        assertNull(externalMetaData.getName());
        assertNull(externalMetaData.getValue());
        assertNull(externalMetaData.getPurchaseOrder());

    }

    @Test
    void testExternalMetaDataConstructorWithArgs() {
        // Arrange
        Long id = 1L;
        String name = "Meta Name";
        String value = "Meta Value";

        // Act
        ExternalMetaData externalMetaData = new ExternalMetaData(id, name, value, purchaseOrder);

        // Assert
        assertEquals(id, externalMetaData.getId());
        assertEquals(name, externalMetaData.getName());
        assertEquals(value, externalMetaData.getValue());
        assertEquals(purchaseOrder, externalMetaData.getPurchaseOrder());

    }

    @Test
    void testIdSetterGetter() {
        // Arrange
        Long id = 1L;

        // Act
        externalMetaData.setId(id);

        // Assert
        assertEquals(id, externalMetaData.getId());
    }

    @Test
    void testNameSetterGetter() {
        // Arrange
        String name = "Meta Name";

        // Act
        externalMetaData.setName(name);

        // Assert
        assertEquals(name, externalMetaData.getName());
    }

    @Test
    void testValueSetterGetter() {
        // Arrange
        String value = "Meta Value";

        // Act
        externalMetaData.setValue(value);

        // Assert
        assertEquals(value, externalMetaData.getValue());
    }

    @Test
    void testPurchaseOrderSetterGetter() {
        // Act
        externalMetaData.setPurchaseOrder(purchaseOrder);

        // Assert
        assertEquals(purchaseOrder, externalMetaData.getPurchaseOrder());
    }

    @Test
    void testIsDeletedSetterGetter() {
        // Act
        externalMetaData.setIsDeleted(true);

        // Assert
        assertTrue(externalMetaData.getIsDeleted());
    }

    @Test
    void testToString() {
        // Arrange
        Long id = 1L;
        String name = "Meta Name";
        String value = "Meta Value";
        externalMetaData = new ExternalMetaData(id, name, value, purchaseOrder);

        // Act
        String toStringOutput = externalMetaData.toString();

        // Assert
        assertTrue(toStringOutput.contains("id=1"));
        assertTrue(toStringOutput.contains("name=Meta Name"));
        assertTrue(toStringOutput.contains("value=Meta Value"));
    }

}