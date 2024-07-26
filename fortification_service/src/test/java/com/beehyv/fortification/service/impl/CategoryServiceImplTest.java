package com.beehyv.fortification.service.impl;

import com.beehyv.fortification.dto.requestDto.CategoryRequestDto;
import com.beehyv.fortification.dto.responseDto.CategoryResponseDto;
import com.beehyv.fortification.dto.responseDto.CategoryRoleResponseDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.entity.Category;
import com.beehyv.fortification.entity.RoleCategory;
import com.beehyv.fortification.manager.CategoryManager;
import com.beehyv.fortification.manager.RoleCategoryManager;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CategoryServiceImplTest {

    @Mock
    private CategoryManager categoryManager;

    @Mock
    private RoleCategoryManager roleCategoryManager;

    @Mock
    private KeycloakInfo keycloakInfo;

    @Spy
    @InjectMocks
    private CategoryServiceImpl categoryService;
    private HashMap<String, Object> userInfo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        MockitoAnnotations.initMocks(this);
        userInfo = new HashMap<>() {{
            put("roles", Set.of("admin"));
        }};
        when(keycloakInfo.getUserInfo()).thenReturn(userInfo);
    }

    @Test
    public void testCreateCategory() {

        CategoryRequestDto requestDto = new CategoryRequestDto();

        categoryService.createCategory(requestDto);

        verify(categoryManager, times(1)).create(any(Category.class));
    }

    @Test
    public void testGetCategoryById() {
        // Given
        long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        when(categoryManager.findById(categoryId)).thenReturn(category);

        // When
        CategoryResponseDto responseDto = categoryService.getCategoryById(categoryId);

        // Then
        assertNotNull(responseDto);
        assertEquals(categoryId, responseDto.getId());
    }

    @Test
    public void testGetAllCategories() {
        // Given
        boolean independentBatch = true;
        int pageNumber = 1;
        int pageSize = 10;
        List<Category> categories = new ArrayList<>();
        // Add some categories to the list
        when(categoryManager.findAllByIndependentBatch(independentBatch, pageNumber, pageSize)).thenReturn(categories);
        when(categoryManager.getCountByIndependentBatch(anyInt(), eq(independentBatch), eq(pageNumber), eq(pageSize))).thenReturn((long) categories.size());

        // When
        ListResponse<CategoryResponseDto> response = categoryService.getAllCategories(independentBatch, pageNumber, pageSize);

        // Then
        assertNotNull(response);
        assertEquals(categories.size(), response.getData().size());
        // Add more assertions if needed
    }

    @Test
    public void testUpdateCategory() {
        // Given
        CategoryRequestDto requestDto = new CategoryRequestDto();
        // Set properties as needed

        // When
        categoryService.updateCategory(requestDto);

        // Then
        verify(categoryManager, times(1)).findById(requestDto.getId());
        verify(categoryManager, times(1)).update(any(Category.class));
    }

    @Test
    public void testDeleteCategory() {
        // Given
        long categoryId = 1L;

        // When
        categoryService.deleteCategory(categoryId);

        // Then
        verify(categoryManager, times(1)).delete(categoryId);
    }


    @Test
    public void testGetAllCategoriesForManufacturer_WithSetRoles() {
        Set<String> rolesSet = new HashSet<>(Arrays.asList("ROLE_ADMIN", "ROLE_USER"));
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("roles", rolesSet);
        when(keycloakInfo.getUserInfo()).thenReturn(userInfo);

        List<RoleCategory> roleCategories = new ArrayList<>(); // Mock role categories as needed
        when(roleCategoryManager.findAllByCategoryAndRoleNames(null)).thenReturn(roleCategories);

        List<CategoryResponseDto> result = categoryService.getAllCategoriesForManufacturer(false);

    }


    @Test
    public void testGetCategoryIdByName() {
        String categoryName = "CategoryName";
        long categoryId = 1L;
        when(categoryManager.findCategoryIdByName(categoryName)).thenReturn(categoryId);

        Long result = categoryService.getCategoryIdByName(categoryName);

        assertNotNull(result);
        assertEquals(categoryId, result);
    }

    @Test
    public void testGetNextCategoryIdsAndActions() {
        long categoryId = 1L;
        long stateGeoId = 1L;
        Map<Long, String> nextCategoryIdsAndActions = Map.of();
        when(categoryManager.getCategoryAndActionBySourceCategoryId(categoryId, stateGeoId)).thenReturn(nextCategoryIdsAndActions);

        Map<Long, String> result = categoryService.getNextCategoryIdsAndActions(categoryId, stateGeoId);

        assertNotNull(result);
    }

    @Test
    public void testGetAllCategoryRolesForManufacturer() {
        // Given
        List<String[]> roleSplitList = new ArrayList<>();
        List<RoleCategory> roleCategories = new ArrayList<>();
//        when(categoryService.getRoleCategories()).thenReturn(roleSplitList);
        when(roleCategoryManager.findAllByCategoryName(roleSplitList)).thenReturn(Collections.singletonList(roleCategories));

        // When
        Map<String, CategoryRoleResponseDto> result = categoryService.getAllCategoryRolesForManufacturer();

        // Then
        assertNotNull(result);
        verify(categoryService, times(1)).getRoleCategories();
        verify(roleCategoryManager, times(1)).findAllByCategoryName(any());
    }

    @Test
    public void testGetAllCategoryRolesForManufacturerWithRoleTypeList() {
        // Given
        Map<String, List<Long>> roleTypeList = new HashMap<>();
        List<RoleCategory> roleCategories = new ArrayList<>();
        when(roleCategoryManager.findAllByCategoryIdAndType(roleTypeList)).thenReturn(Collections.singletonList(roleCategories));

        // When
        Map<String, CategoryRoleResponseDto> result = categoryService.getAllCategoryRolesForManufacturer(roleTypeList);

        // Then
        assertNotNull(result);
        verify(roleCategoryManager, times(1)).findAllByCategoryIdAndType(roleTypeList);
    }

    @Test
    public void testRoleCategoryHelper() {
        // Given
        List<List<RoleCategory>> roleCategoriesList = new ArrayList<>();
        boolean isSuperadmin = false;

        // When
        Map<String, CategoryRoleResponseDto> result = categoryService.roleCategoryHelper(roleCategoriesList, isSuperadmin);

        // Then
        assertNotNull(result);
    }

}
