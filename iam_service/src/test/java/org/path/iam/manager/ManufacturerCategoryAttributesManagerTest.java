package org.path.iam.manager;

import org.path.iam.dao.ManufacturerCategoryAttributesDao;
import org.path.iam.model.ManufacturerCategoryAttributes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManufacturerCategoryAttributesManagerTest {

    @Mock
    private ManufacturerCategoryAttributesDao dao;

    @InjectMocks
    private ManufacturerCategoryAttributesManager manager;

    @Test
    void testFindByManufacturerId() {
        // Prepare test data
        Long manufacturerId = 1L;
        List<ManufacturerCategoryAttributes> expectedAttributes = new ArrayList<>(); // Add some attributes to the list

        // Mock the behavior of the DAO
        when(dao.findByManufacturerId(manufacturerId)).thenReturn(expectedAttributes);

        // Call the method to be tested
        List<ManufacturerCategoryAttributes> actualAttributes = manager.findByManufacturerId(manufacturerId);

        // Verify the result
        assertEquals(expectedAttributes, actualAttributes);
    }

    @Test
    void testFindByManufacturerIdAndUserId() {
        // Prepare test data
        Long manufacturerId = 1L;
        Long userId = 1L;
        List<ManufacturerCategoryAttributes> expectedAttributes = new ArrayList<>(); // Add some attributes to the list

        // Mock the behavior of the DAO
        when(dao.findByManufacturerIdAndUserId(manufacturerId, userId)).thenReturn(expectedAttributes);

        // Call the method to be tested
        List<ManufacturerCategoryAttributes> actualAttributes = manager.findByManufacturerIdAndUserId(manufacturerId, userId);

        // Verify the result
        assertEquals(expectedAttributes, actualAttributes);
    }

    // Add more test cases for other methods as needed
}
