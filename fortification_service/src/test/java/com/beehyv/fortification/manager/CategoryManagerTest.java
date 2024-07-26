package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.CategoryDao;
import com.beehyv.fortification.dto.requestDto.SearchCriteria;
import com.beehyv.fortification.dto.requestDto.SearchListRequest;
import com.beehyv.fortification.entity.Category;
import com.beehyv.fortification.entity.RoleCategoryType;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
public class CategoryManagerTest {

    Category category = new Category();
    List<Category> categoryList = List.of(category);
    @Mock
    private CategoryDao categoryDao;
    @Mock
    private KeycloakInfo keycloakInfo;
    @Mock
    private CategoryManager categoryManager;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        categoryManager = new CategoryManager(categoryDao, keycloakInfo);

        // Mock data setup

        category.setId(1L);
        category.setName("Test Category");


        when(categoryDao.findAllByIds(List.of(1L))).thenReturn(categoryList);
        when(categoryDao.findAllByNames(List.of("Test Category"))).thenReturn(categoryList);
        // Add more mock setups as needed
    }

    @Test
    public void testFindAllByIds() {
        List<Category> result = categoryManager.findAllByIds(List.of(1L));
        assertEquals(1, result.size());
        assertEquals("Test Category", result.get(0).getName());
    }

    @Test
    public void testFindAllByNames() {
        List<Category> result = categoryManager.findAllByNames(List.of("Test Category"));
        assertEquals(1, result.size());
        assertEquals("Test Category", result.get(0).getName());
    }

    @Test
    public void testFindAllByIndependentBatch() {
        Boolean independentBatch = true;
        Integer pageNumber = 1;
        Integer pageSize = 10;
        when(categoryDao.findAllByIndependentBatch(independentBatch, pageNumber, pageSize)).thenReturn(categoryList);
        List<Category> result = categoryManager.findAllByIndependentBatch(independentBatch, pageNumber, pageSize);
        assertEquals(categoryList, result);
    }

    @Test
    public void testGetCountByIndependentBatch() {
        Boolean independentBatch = true;
        Integer pageNumber = 1;
        Integer pageSize = 10;
        when(categoryDao.getCountByIndependentBatch(independentBatch)).thenReturn((long) categoryList.size());
        Long result = categoryManager.getCountByIndependentBatch(categoryList.size(), independentBatch, pageNumber, pageSize);
        assertEquals(categoryList.size(), result);
    }


    @Test
    public void testGetSourceCategory() {
        Long targetCategoryId = 1L;
        Long geoId = 1L;
        when(categoryDao.getSourceCategory(targetCategoryId, geoId)).thenReturn(categoryList);
        List<Category> result = categoryManager.getSourceCategory(targetCategoryId, geoId);
        assertEquals(categoryList, result);
    }

    @Test
    public void testFindCategoryIdByName() {
        String categoryName = "Test Category";
        when(categoryDao.findCategoryIdByName(categoryName)).thenReturn(1L);
        Long result = categoryManager.findCategoryIdByName(categoryName);
        assertEquals(1L, result);
    }

    @Test
    public void testFindCategoryByName() {
        String categoryName = "Test Category";
        when(categoryDao.findCategoryByName(categoryName)).thenReturn(category);
        Category result = categoryManager.findCategoryByName(categoryName);
        assertEquals(category, result);
    }

    @Test
    public void testFindCategoryNameById() {
        Long categoryId = 1L;
        when(categoryDao.findCategoryNameById(categoryId)).thenReturn("Test Category");
        String result = categoryManager.findCategoryNameById(categoryId);
        assertEquals("Test Category", result);
    }

    @Test
    public void testFindCategoryListByName() {
        String categoryName = "Test Category";
        when(categoryDao.findCategoryListByName(categoryName)).thenReturn(categoryList);
        List<Category> result = categoryManager.findCategoryListByName(categoryName);
        assertEquals(categoryList, result);
    }

    @Test
    public void testFindSequence() {
        when(categoryDao.findMaxSequence()).thenReturn(1);
        Integer result = categoryManager.findSequence();
        assertEquals(1, result);
    }

    @Test
    void testGetCategoryBySourceCategoryId() {
        // Arrange
        Long categoryId = 1L;
        Long stateGeoId = 1L;
        List<Category> expectedCategories = List.of(new Category());
        when(categoryDao.getCategoryBySourceCategoryId(categoryId, stateGeoId)).thenReturn(expectedCategories);

        // Act
        List<Category> actualCategories = categoryManager.getCategoryBySourceCategoryId(categoryId, stateGeoId);

        // Assert
        assertEquals(expectedCategories, actualCategories);
    }

    @Test
    void testGetCategoryAndActionBySourceCategoryId() {
        // Arrange
        Long categoryId = 1L;
        Long stateGeoId = 1L;
        Map<Long, String> expectedCategoryAndAction = new HashMap<>();
        expectedCategoryAndAction.put(1L, "Test Action");
        when(categoryDao.getCategoryAndActionBySourceCategoryId(categoryId, stateGeoId)).thenReturn(expectedCategoryAndAction);

        // Act
        Map<Long, String> actualCategoryAndAction = categoryManager.getCategoryAndActionBySourceCategoryId(categoryId, stateGeoId);

        // Assert
        assertEquals(expectedCategoryAndAction, actualCategoryAndAction);
    }

    @Test
    void testFindAllBySuperUser() {
        // Arrange
        String roleCategoryType = "TestType";
        Set<String> roles = new HashSet<>(List.of("Test_SUPERADMIN"));
        when(keycloakInfo.getUserInfo()).thenReturn(Collections.singletonMap("roles", roles));
        List<Category> expectedCategories = List.of(new Category());
        when(categoryManager.findAllByNames(List.of("Test"))).thenReturn(expectedCategories);

        // Act
        List<Category> actualCategories = categoryManager.findAllBySuperUser(roleCategoryType);

        // Assert
        assertEquals(expectedCategories, actualCategories);
    }

    @Test
    void testIsCategorySuperAdmin() {
        // Arrange
        Long categoryId = 1L;
        RoleCategoryType roleCategoryType = RoleCategoryType.MODULE;
        Category category = new Category();
        category.setName("Test");
        Set<String> roles = new HashSet<>(List.of("Test_SUPERADMIN_TEST"));
        when(categoryManager.findById(categoryId)).thenReturn(category);
        when(keycloakInfo.getUserInfo()).thenReturn(Collections.singletonMap("roles", roles));

        // Act
        boolean isSuperAdmin = categoryManager.isCategorySuperAdmin(categoryId, roleCategoryType);

        // Assert
        assertFalse(isSuperAdmin);
    }

    @Test
    void testIsCategoryInspectionUser() {
        // Arrange
        Long categoryId = 1L;
        RoleCategoryType roleCategoryType = RoleCategoryType.LAB;
        Category category = new Category();
        category.setName("Test");
        Set<String> roles = new HashSet<>(List.of("Test_INSPECTION_TEST"));
        when(categoryManager.findById(categoryId)).thenReturn(category);
        when(keycloakInfo.getUserInfo()).thenReturn(Collections.singletonMap("roles", roles));

        // Act
        boolean isInspectionUser = categoryManager.isCategoryInspectionUser(categoryId, roleCategoryType);

        // Assert
        assertFalse(isInspectionUser);
    }

    @Test
    void testGetUserCategoryIds() {
        // Arrange
        RoleCategoryType roleCategoryType = RoleCategoryType.MODULE;
        SearchListRequest searchRequest = new SearchListRequest();
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setKey("categoryIds");
        searchCriteria.setValue(Arrays.asList("1", "2"));
        searchCriteriaList.add(searchCriteria);
        searchRequest.setSearchCriteriaList(searchCriteriaList);

        List<Category> categories = List.of(new Category());

        // Act
        List<Long> actualCategoryIds = categoryManager.getUserCategoryIds(searchRequest, roleCategoryType);

        // Assert
        assertNotNull(actualCategoryIds);
        assertEquals(2, actualCategoryIds.size());
    }

}
