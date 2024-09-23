package com.beehyv.iam.model;

import com.beehyv.iam.enums.AttributeScoreType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AttributeCategoryTest {

    private AttributeCategory attributeCategory;
    private Attribute attribute;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        attributeCategory = new AttributeCategory();
        attributeCategory.setId(1L);
        attributeCategory.setCategory("Test Category");

        attribute = new Attribute();
        attribute.setId(1L);
        attribute.setName("Test Attribute");
        attribute.setAttributeCategory(attributeCategory);

        Set<Attribute> attributes = new HashSet<>();
        attributes.add(attribute);
        attributeCategory.setAttributes(attributes);
    }

    @Test
    public void testNoArgsConstructor() {
        AttributeCategory newCategory = new AttributeCategory();
        assertNull(newCategory.getId());
        assertNull(newCategory.getCategory());
        assertNull(newCategory.getAttributes());
    }

    @Test
    public void testAllArgsConstructor() {
        Set<Attribute> attributes = new HashSet<>();
        Attribute newAttribute = new Attribute(2L, "New Attribute", true, 20.0, 200, 100, AttributeScoreType.BOOLEAN, 2L, attributeCategory);
        attributes.add(newAttribute);
        AttributeCategory newCategory = new AttributeCategory(2L, "New Category", attributes);

        assertEquals(2L, newCategory.getId());
        assertEquals("New Category", newCategory.getCategory());
        assertEquals(attributes, newCategory.getAttributes());
    }

    @Test
    public void testSettersAndGetters() {
        assertEquals(1L, attributeCategory.getId());
        assertEquals("Test Category", attributeCategory.getCategory());
        assertEquals(1, attributeCategory.getAttributes().size());

        attributeCategory.setId(2L);
        attributeCategory.setCategory("Updated Category");
        Attribute newAttribute = new Attribute(2L, "New Attribute", true, 20.0, 200, 100, AttributeScoreType.BOOLEAN, 2L, attributeCategory);
        Set<Attribute> newAttributes = new HashSet<>();
        newAttributes.add(newAttribute);
        attributeCategory.setAttributes(newAttributes);

        assertEquals(2L, attributeCategory.getId());
        assertEquals("Updated Category", attributeCategory.getCategory());
        assertEquals(1, attributeCategory.getAttributes().size());
    }

}
