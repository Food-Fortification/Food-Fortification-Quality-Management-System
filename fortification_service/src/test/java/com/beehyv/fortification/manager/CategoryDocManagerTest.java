package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.CategoryDocDao;
import com.beehyv.fortification.entity.CategoryDoc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CategoryDocManagerTest {

    @Mock
    private CategoryDocDao categoryDocDao;

    private CategoryDocManager categoryDocManager;

    private Long categoryId;
    private Integer pageNumber;
    private Integer pageSize;
    private List<CategoryDoc> categoryDocList;

    @BeforeEach
    public void setUp() {
        categoryDocManager = new CategoryDocManager(categoryDocDao);
        categoryId = 1L;
        pageNumber = 1;
        pageSize = 10;
        categoryDocList = Arrays.asList(new CategoryDoc(), new CategoryDoc());

        when(categoryDocDao.getCount(categoryId)).thenReturn((long) categoryDocList.size());
        when(categoryDocDao.findAllByCategoryId(categoryId, pageNumber, pageSize)).thenReturn(categoryDocList);
    }

    @Test
    public void testGetCount() {
        Long result = categoryDocManager.getCount(categoryDocList.size(), categoryId, pageNumber, pageSize);
        assertEquals(categoryDocList.size(), result);
    }

    @Test
    public void testFindAllByCategoryId() {
        List<CategoryDoc> result = categoryDocManager.findAllByCategoryId(categoryId, pageNumber, pageSize);
        assertEquals(categoryDocList, result);
    }
}