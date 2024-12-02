package org.path.fortification.dao;

import org.path.fortification.entity.Category;
import org.path.fortification.entity.CategoryDoc;
import org.path.fortification.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
class CategoryDaoTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Category> typedQuery;
    @Mock
    private TypedQuery<Long> typedQueryL;
    @Mock
    private TypedQuery<String> typedQueryS;

    @InjectMocks
    private CategoryDao categoryDao;

    private List<Category> categoryList;

    @BeforeEach
    void setUp() {
        when(entityManager.createQuery(anyString(), eq(Category.class))).thenReturn(typedQuery);
        when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(typedQueryL);
        when(entityManager.createQuery(anyString(), eq(String.class))).thenReturn(typedQueryS);
        when(typedQuery.setParameter(any(String.class), any())).thenReturn(typedQuery);
        when(typedQueryL.setParameter(any(String.class), any())).thenReturn(typedQueryL);
        when(typedQueryS.setParameter(any(String.class), any())).thenReturn(typedQueryS);

        categoryList = new ArrayList<>();
        categoryList.add(new Category(1L, "null", new Product(), Set.of(new Category()), Set.of(new CategoryDoc()), true, 1));
        categoryList.add(new Category(2L, "null", new Product(), Set.of(new Category()), Set.of(new CategoryDoc()), true, 1));
        categoryList.add(new Category(3L, "null", new Product(), Set.of(new Category()), Set.of(new CategoryDoc()), true, 1));
    }

    @Test
    void getCategoryBySourceCategoryId() {
        Long categoryId = 1L;
        Long stateGeoId = 1L;
        Category category1 = new Category(1L, "null", new Product(), Set.of(new Category()), Set.of(new CategoryDoc()), true, 1);
        Category category2 = new Category(2L, "null", new Product(), Set.of(new Category()), Set.of(new CategoryDoc()), true, 1);

        when(typedQuery.getResultList()).thenReturn(Arrays.asList(category1, category2));

        List<Category> result = categoryDao.getCategoryBySourceCategoryId(categoryId, stateGeoId);

        assertEquals(2, result.size());
        assertTrue(result.contains(category1));
        assertTrue(result.contains(category2));
    }

    @Test
    void findAllByIds() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);

        when(typedQuery.getResultList()).thenReturn(categoryList);

        List<Category> result = categoryDao.findAllByIds(ids);

        assertEquals(categoryList, result);
    }

    @Test
    void findAllByIndependentBatch() {
        Boolean independentBatch = true;
        Integer pageNumber = 1;
        Integer pageSize = 2;

        when(typedQuery.getResultList()).thenReturn(categoryList.subList(0, 2));

        List<Category> result = categoryDao.findAllByIndependentBatch(independentBatch, pageNumber, pageSize);

        assertEquals(2, result.size());
        assertTrue(result.contains(categoryList.get(0)));
        assertTrue(result.contains(categoryList.get(1)));
    }

    @Test
    void getCountByIndependentBatch() {
        Boolean independentBatch = true;
        Long expectedCount = 2L;

        when(typedQueryL.getSingleResult()).thenReturn(expectedCount);

        Long result = categoryDao.getCountByIndependentBatch(independentBatch);

        assertEquals(expectedCount, result);
    }

    @Test
    void findAllByNames() {
        List<String> categoryNames = Arrays.asList("Category A", "Category C");
        List<Category> expectedCategories = Arrays.asList(categoryList.get(0), categoryList.get(2));

        when(typedQuery.getResultList()).thenReturn(expectedCategories);

        List<Category> result = categoryDao.findAllByNames(categoryNames);

        assertEquals(expectedCategories, result);
    }

    @Test
    void getSourceCategory() {
        Long targetCategoryId = 1L;
        Long geoId = 1L;
        Category sourceCategory = new Category(1L, "null", new Product(), Set.of(new Category()), Set.of(new CategoryDoc()), true, 1);

        when(typedQuery.getResultList()).thenReturn(Collections.singletonList(sourceCategory));

        List<Category> result = categoryDao.getSourceCategory(targetCategoryId, geoId);

        assertEquals(1, result.size());
        assertEquals(sourceCategory, result.get(0));
    }

    @Test
    void findCategoryIdByName() {
        String categoryName = "Category A";
        Long expectedCategoryId = 1L;

        when(typedQueryL.getSingleResult()).thenReturn(expectedCategoryId);

        Long actualCategoryId = categoryDao.findCategoryIdByName(categoryName);

        assertEquals(expectedCategoryId, actualCategoryId);
    }

    @Test
    void findCategoryNameById() {
        Long categoryId = 1L;
        String expectedCategoryName = "Category A";

        when(typedQueryS.getSingleResult()).thenReturn(expectedCategoryName);

        String actualCategoryName = categoryDao.findCategoryNameById(categoryId);

        assertEquals(expectedCategoryName, actualCategoryName);
    }

    @Test
    void findCategoryByName() {
        String categoryName = "Category A";
        Category expectedCategory = categoryList.get(0);

        when(typedQuery.getSingleResult()).thenReturn(expectedCategory);

        Category actualCategory = categoryDao.findCategoryByName(categoryName);

        assertEquals(expectedCategory, actualCategory);
    }


    @Test
    void findCategoryListByName() {
        String categoryName = "Category A";
        List<Category> expectedCategories = Collections.singletonList(categoryList.get(0));

        when(typedQuery.getResultList()).thenReturn(expectedCategories);

        List<Category> result = categoryDao.findCategoryListByName(categoryName);

        assertEquals(expectedCategories, result);
    }
}