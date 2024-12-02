package org.path.iam.manager;

import org.path.iam.dao.CategoryDocDao;
import org.path.iam.model.CategoryDoc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryDocManagerTest {

    @Mock
    private CategoryDocDao categoryDocDao;

    @InjectMocks
    private CategoryDocManager categoryDocManager;

    @BeforeEach
    void setUp() {
        // You can initialize any mock behavior here
    }

    @Test
    void testFindAllByCategoryId() {
        // Prepare test data
        Long categoryId = 1L;
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<CategoryDoc> expectedCategoryDocs = List.of(new CategoryDoc(), new CategoryDoc());
        when(categoryDocDao.findAllByCategoryId(categoryId, pageNumber, pageSize)).thenReturn(expectedCategoryDocs);

        // Call the method to be tested
        List<CategoryDoc> actualCategoryDocs = categoryDocManager.findAllByCategoryId(categoryId, pageNumber, pageSize);

        // Verify the result
        assertEquals(expectedCategoryDocs, actualCategoryDocs);
    }

    @Test
    void testGetCount() {
        // Prepare test data
        Integer listSize = 2;
        Long categoryId = 1L;
        Integer pageNumber = 1;
        Integer pageSize = 10;
        when(categoryDocDao.getCount(categoryId)).thenReturn(2L);

        // Call the method to be tested
        Long actualCount = categoryDocManager.getCount(listSize, categoryId, pageNumber, pageSize);

        // Verify the result
        assertEquals(2L, actualCount);
    }

    @Test
    public void testGetCount_WhenPageSizeIsNull() {
        Integer listSize = 10;
        Long categoryId = 1L;
        Integer pageNumber = 1;
        Integer pageSize = null;

        Long actualCount = categoryDocManager.getCount(listSize, categoryId, pageNumber, pageSize);

        assertEquals(listSize.longValue(), actualCount);
    }

    @Test
    public void testGetCount_WhenPageNumberIsNull() {
        Integer listSize = 10;
        Long categoryId = 1L;
        Integer pageNumber = null;
        Integer pageSize = 5;

        Long actualCount = categoryDocManager.getCount(listSize, categoryId, pageNumber, pageSize);

        assertEquals(listSize.longValue(), actualCount);
    }
}
