package org.path.iam.manager;

import org.path.iam.dao.ManufacturerCategoryDao;
import org.path.iam.enums.ManufacturerCategoryAction;
import org.path.iam.model.ManufacturerCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManufacturerCategoryManagerTest {

    @Mock
    private ManufacturerCategoryDao dao;

    @InjectMocks
    private ManufacturerCategoryManager manager;

    @Test
    void testGetByCategoryId() {
        // Prepare test data
        Long categoryId = 1L;
        List<ManufacturerCategory> expectedCategories = new ArrayList<>(); // Add some categories to the list

        // Mock the behavior of the DAO
        when(dao.getByCategoryId(categoryId)).thenReturn(expectedCategories);

        // Call the method to be tested
        List<ManufacturerCategory> actualCategories = manager.getByCategoryId(categoryId);

        // Verify the result
        assertEquals(expectedCategories, actualCategories);
    }

    @Test
    public void testGetCanSkipRawMaterialsForManufacturerAndCategory() {
        Long manufacturerId = 1L;
        Long categoryId = 1L;
        when(dao.getCanSkipRawMaterialsForManufacturerAndCategory(manufacturerId, categoryId)).thenReturn(true);

        boolean result = manager.getCanSkipRawMaterialsForManufacturerAndCategory(manufacturerId, categoryId);

        assertTrue(result);
    }

    @Test
    public void testGetActionNameByManufacturerIdAndCategoryId() {
        Long manufacturerId = 1L;
        Long categoryId = 1L;
        ManufacturerCategoryAction action = ManufacturerCategoryAction.CREATION; // Example action
        when(dao.getActionNameByManufacturerIdAndCategoryId(manufacturerId, categoryId)).thenReturn(action);

        String result = manager.getActionNameByManufacturerIdAndCategoryId(manufacturerId, categoryId);

        assertEquals(action.toString(), result);
    }

    @Test
    public void testFilterManufacturersByCategory() {
        Long categoryId = 1L;
        List<Long> manufacturerIds = Arrays.asList(1L, 2L, 3L);
        List<Long> filteredIds = Arrays.asList(1L, 2L);
        when(dao.filterManufacturersByCategory(categoryId, manufacturerIds)).thenReturn(filteredIds);

        List<Long> result = manager.filterManufacturersByCategory(categoryId, manufacturerIds);

        assertEquals(filteredIds, result);
    }

    @Test
    void testFindAllByManufacturerId() {
        // Prepare test data
        Long manufacturerId = 1L;
        List<ManufacturerCategory> expectedCategories = new ArrayList<>(); // Add some categories to the list

        // Mock the behavior of the DAO
        when(dao.findAllByManufacturerId(manufacturerId)).thenReturn(expectedCategories);

        // Call the method to be tested
        List<ManufacturerCategory> actualCategories = manager.findAllByManufacturerId(manufacturerId);

        // Verify the result
        assertEquals(expectedCategories, actualCategories);
    }

    // Add more test cases for other methods as needed
}
