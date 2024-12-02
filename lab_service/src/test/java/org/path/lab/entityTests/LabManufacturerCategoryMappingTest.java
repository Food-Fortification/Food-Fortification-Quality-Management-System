package org.path.lab.entityTests;

import org.path.lab.entity.LabManufacturerCategoryMapping;
import org.path.lab.entity.Lab;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class LabManufacturerCategoryMappingTest {

    @Mock
    private Lab lab;

    @InjectMocks
    private LabManufacturerCategoryMapping labManufacturerCategoryMapping;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllFields() {
        Long idValue = 1L;
        Long categoryIdValue = 1L;
        Long manufacturerIdValue = 1L;

        when(lab.getId()).thenReturn(1L);

        labManufacturerCategoryMapping.setId(idValue);
        labManufacturerCategoryMapping.setCategoryId(categoryIdValue);
        labManufacturerCategoryMapping.setLab(lab);
        labManufacturerCategoryMapping.setManufacturerId(manufacturerIdValue);

        assertEquals(idValue, labManufacturerCategoryMapping.getId());
        assertEquals(categoryIdValue, labManufacturerCategoryMapping.getCategoryId());
        assertEquals(1L, labManufacturerCategoryMapping.getLab().getId());
        assertEquals(manufacturerIdValue, labManufacturerCategoryMapping.getManufacturerId());
    }
}