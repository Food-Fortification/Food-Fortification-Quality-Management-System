package org.path.iam.manager;

import org.path.iam.dao.AttributeCategoryDao;
import org.path.iam.model.AttributeCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttributeCategoryManagerTest {

    @Mock
    private AttributeCategoryDao attributeCategoryDao;

    @InjectMocks
    private AttributeCategoryManager attributeCategoryManager;

    @BeforeEach
    void setUp() {
        // You can initialize any mock behavior here
    }

    @Test
    void testFindAll() {
        // Prepare test data
        List<AttributeCategory> expectedCategories = List.of(new AttributeCategory(), new AttributeCategory());
        when(attributeCategoryDao.findAll(1, 10)).thenReturn(expectedCategories);

        // Call the method to be tested
        List<AttributeCategory> actualCategories = attributeCategoryManager.findAll(1, 10);

        // Verify the result
        assertEquals(expectedCategories, actualCategories);
    }

    @Test
    void testFindById() {
        // Prepare test data
        long categoryId = 1L;
        AttributeCategory expectedCategory = new AttributeCategory();
        when(attributeCategoryDao.findById(categoryId)).thenReturn(expectedCategory);

        // Call the method to be tested
        AttributeCategory actualCategory = attributeCategoryManager.findById(categoryId);

        // Verify the result
        assertEquals(expectedCategory, actualCategory);
    }

    @Test
    void testCreate() {
        // Prepare test data
        AttributeCategory categoryToCreate = new AttributeCategory();

        // Call the method to be tested
        attributeCategoryManager.create(categoryToCreate);

        // Verify that the appropriate method in the DAO is called
        verify(attributeCategoryDao, times(1)).create(categoryToCreate);
    }

    @Test
    void testUpdate() {
        // Prepare test data
        AttributeCategory categoryToUpdate = new AttributeCategory();

        // Call the method to be tested
        attributeCategoryManager.update(categoryToUpdate);

        // Verify that the appropriate method in the DAO is called
        verify(attributeCategoryDao, times(1)).update(categoryToUpdate);
    }

    @Test
    void testDelete() {
        // Prepare test data
        long categoryId = 1L;

        // Call the method to be tested
        attributeCategoryManager.delete(categoryId);

        // Verify that the appropriate method in the DAO is called
        verify(attributeCategoryDao, times(1)).delete(categoryId);
    }
}
