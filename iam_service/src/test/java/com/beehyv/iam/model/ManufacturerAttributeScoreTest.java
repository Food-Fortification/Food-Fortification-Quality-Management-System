package com.beehyv.iam.model;

import com.beehyv.iam.enums.ComplianceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ManufacturerAttributeScoreTest {

    private ManufacturerAttributeScore manufacturerAttributeScore;

    @BeforeEach
    void setUp() {
        manufacturerAttributeScore = new ManufacturerAttributeScore();
    }

    @Test
    void testManufacturerAttributeScoreInitialization() {
        // Assert initial state of manufacturerAttributeScore object
        assertNull(manufacturerAttributeScore.getId());
        assertNull(manufacturerAttributeScore.getAttribute());
        assertNull(manufacturerAttributeScore.getCompliance());
        assertNull(manufacturerAttributeScore.getValue());
        assertNull(manufacturerAttributeScore.getAttributeCategoryScore());
    }

    @Test
    void testIdSetterGetter() {
        // Arrange
        Long id = 1L;

        // Act
        manufacturerAttributeScore.setId(id);

        // Assert
        assertEquals(id, manufacturerAttributeScore.getId());
    }

    @Test
    void testAttributeSetterGetter() {
        // Arrange
        Attribute attribute = new Attribute();

        // Act
        manufacturerAttributeScore.setAttribute(attribute);

        // Assert
        assertEquals(attribute, manufacturerAttributeScore.getAttribute());
    }

    @Test
    void testComplianceSetterGetter() {
        // Arrange
        ComplianceType complianceType = ComplianceType.C;

        // Act
        manufacturerAttributeScore.setCompliance(complianceType);

        // Assert
        assertEquals(complianceType, manufacturerAttributeScore.getCompliance());
    }

    @Test
    void testValueSetterGetter() {
        // Arrange
        String value = "Test Value";

        // Act
        manufacturerAttributeScore.setValue(value);

        // Assert
        assertEquals(value, manufacturerAttributeScore.getValue());
    }

    @Test
    void testAttributeCategoryScoreSetterGetter() {
        // Arrange
        AttributeCategoryScore attributeCategoryScore = new AttributeCategoryScore();

        // Act
        manufacturerAttributeScore.setAttributeCategoryScore(attributeCategoryScore);

        // Assert
        assertEquals(attributeCategoryScore, manufacturerAttributeScore.getAttributeCategoryScore());
    }

    @Test
    void testConstructorWithArgs() {
        // Arrange
        Long id = 1L;
        Attribute attribute = new Attribute();
        ComplianceType complianceType = ComplianceType.C;
        String value = "Test Value";
        AttributeCategoryScore attributeCategoryScore = new AttributeCategoryScore();

        // Act
        ManufacturerAttributeScore manufacturerAttributeScore = new ManufacturerAttributeScore(id, attribute, complianceType, value, attributeCategoryScore);

        // Assert
        assertEquals(id, manufacturerAttributeScore.getId());
        assertEquals(attribute, manufacturerAttributeScore.getAttribute());
        assertEquals(complianceType, manufacturerAttributeScore.getCompliance());
        assertEquals(value, manufacturerAttributeScore.getValue());
        assertEquals(attributeCategoryScore, manufacturerAttributeScore.getAttributeCategoryScore());

    }

    @Test
    void testToString() {
        // Arrange
        Attribute attribute = new Attribute();
        attribute.setId(1L);
        attribute.setName("Attribute Name");
        ComplianceType complianceType = ComplianceType.C;
        String value = "Test Value";
        AttributeCategoryScore attributeCategoryScore = new AttributeCategoryScore();
        attributeCategoryScore.setId(1L);

        manufacturerAttributeScore.setId(1L);
        manufacturerAttributeScore.setAttribute(attribute);
        manufacturerAttributeScore.setCompliance(complianceType);
        manufacturerAttributeScore.setValue(value);
        manufacturerAttributeScore.setAttributeCategoryScore(attributeCategoryScore);

        // Act
        String toString = manufacturerAttributeScore.toString();

        // Assert
    }
}
