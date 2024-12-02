package org.path.iam.model;

import org.path.iam.enums.AttributeScoreType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class AttributeTest {

    private Attribute attribute;
    private AttributeCategory attributeCategory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        attributeCategory = new AttributeCategory();
        attributeCategory.setId(1L);

        attribute = new Attribute();
        attribute.setId(1L);
        attribute.setName("Test Attribute");
        attribute.setIsActive(true);
        attribute.setWeightage(10.0);
        attribute.setTotalScore(100);
        attribute.setDefaultScore(50);
        attribute.setType(AttributeScoreType.BOOLEAN);
        attribute.setCategoryId(1L);
        attribute.setAttributeCategory(attributeCategory);
    }

    @Test
    public void testNoArgsConstructor() {
        Attribute newAttribute = new Attribute();
        assertNull(newAttribute.getId());
        assertNull(newAttribute.getName());
        assertNull(newAttribute.getIsActive());
        assertNull(newAttribute.getWeightage());
        assertNull(newAttribute.getTotalScore());
        assertNull(newAttribute.getDefaultScore());
        assertNull(newAttribute.getType());
        assertNull(newAttribute.getCategoryId());
        assertNull(newAttribute.getAttributeCategory());
    }

    @Test
    public void testAllArgsConstructor() {
        Attribute newAttribute = new Attribute(2L, "New Attribute", true, 20.0, 200, 100, AttributeScoreType.BOOLEAN, 2L, attributeCategory);
        assertEquals(2L, newAttribute.getId());
        assertEquals("New Attribute", newAttribute.getName());
        assertTrue(newAttribute.getIsActive());
        assertEquals(20.0, newAttribute.getWeightage());
        assertEquals(200, newAttribute.getTotalScore());
        assertEquals(100, newAttribute.getDefaultScore());
        assertEquals(AttributeScoreType.BOOLEAN, newAttribute.getType());
        assertEquals(2L, newAttribute.getCategoryId());
        assertEquals(attributeCategory, newAttribute.getAttributeCategory());
    }

    @Test
    public void testSettersAndGetters() {
        assertEquals(1L, attribute.getId());
        assertEquals("Test Attribute", attribute.getName());
        assertTrue(attribute.getIsActive());
        assertEquals(10.0, attribute.getWeightage());
        assertEquals(100, attribute.getTotalScore());
        assertEquals(50, attribute.getDefaultScore());
        assertEquals(AttributeScoreType.BOOLEAN, attribute.getType());
        assertEquals(1L, attribute.getCategoryId());
        assertEquals(attributeCategory, attribute.getAttributeCategory());

        attribute.setId(2L);
        attribute.setName("Updated Attribute");
        attribute.setIsActive(false);
        attribute.setWeightage(15.0);
        attribute.setTotalScore(150);
        attribute.setDefaultScore(75);
        attribute.setType(AttributeScoreType.BOOLEAN);
        attribute.setCategoryId(2L);
        AttributeCategory newCategory = new AttributeCategory();
        newCategory.setId(2L);
        attribute.setAttributeCategory(newCategory);

        assertEquals(2L, attribute.getId());
        assertEquals("Updated Attribute", attribute.getName());
        assertFalse(attribute.getIsActive());
        assertEquals(15.0, attribute.getWeightage());
        assertEquals(150, attribute.getTotalScore());
        assertEquals(75, attribute.getDefaultScore());
        assertEquals(AttributeScoreType.BOOLEAN, attribute.getType());
        assertEquals(2L, attribute.getCategoryId());
        assertEquals(newCategory, attribute.getAttributeCategory());
    }


}
