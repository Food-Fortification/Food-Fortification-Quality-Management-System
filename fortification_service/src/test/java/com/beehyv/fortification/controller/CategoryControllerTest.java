package com.beehyv.fortification.controller;

import com.beehyv.fortification.dto.requestDto.CategoryRequestDto;
import com.beehyv.fortification.dto.responseDto.CategoryDocResponseDto;
import com.beehyv.fortification.dto.responseDto.CategoryResponseDto;
import com.beehyv.fortification.dto.responseDto.CategoryRoleResponseDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @Mock
    private CategoryService service;

    @InjectMocks
    private CategoryController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCategory() {
        CategoryRequestDto dto = new CategoryRequestDto();

        ResponseEntity<?> responseEntity = controller.createCategory(dto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Success", responseEntity.getBody());
        verify(service, times(1)).createCategory(dto);
    }

    @Test
    void testGetCategoryById() {
        Long id = 1L;
        CategoryResponseDto responseDto = new CategoryResponseDto();
        CategoryDocResponseDto doc = new CategoryDocResponseDto();
        doc.setIsEnabled(true);
        Set docSet = new HashSet<>();
        docSet.add(doc);
        responseDto.setDocuments(docSet);

        when(service.getCategoryById(id)).thenReturn(responseDto);

        ResponseEntity<CategoryResponseDto> responseEntity = controller.getCategoryById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseDto, responseEntity.getBody());
        verify(service, times(1)).getCategoryById(id);
    }

    @Test
    void testGetAllCategories() {
        Integer pageNumber = 1;
        Integer pageSize = 10;
        Boolean independentBatch = true;
        List<CategoryResponseDto> categories = new ArrayList<>();
        ListResponse<CategoryResponseDto> listResponse = new ListResponse<>();

        when(service.getAllCategories(independentBatch, pageNumber, pageSize)).thenReturn(listResponse);

        ResponseEntity<ListResponse<CategoryResponseDto>> responseEntity = controller.getAllCategories(pageNumber, pageSize, independentBatch);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(listResponse, responseEntity.getBody());
        verify(service, times(1)).getAllCategories(independentBatch, pageNumber, pageSize);
    }

    @Test
    void testGetAllCategoriesForManufacturer() {
        List<CategoryResponseDto> categories = new ArrayList<>();

        when(service.getAllCategoriesForManufacturer(false)).thenReturn(categories);

        ResponseEntity<?> responseEntity = controller.getAllCategoriesForManufacturer();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(categories, responseEntity.getBody());
        verify(service, times(1)).getAllCategoriesForManufacturer(false);
    }

    @Test
    void testGetAllCategoryRolesForManufacturer() {
        Map<String, CategoryRoleResponseDto> categoryRoles = new HashMap<>();

        when(service.getAllCategoryRolesForManufacturer()).thenReturn(categoryRoles);

        ResponseEntity<?> responseEntity = controller.getAllCategoryRolesForManufacturer();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(categoryRoles, responseEntity.getBody());
        verify(service, times(1)).getAllCategoryRolesForManufacturer();
    }

    @Test
    void testGetAllCategoryRolesForManufacturerWithRequestBody() {
        Map<String, List<Long>> roleTypeList = new HashMap<>();
        Map<String, CategoryRoleResponseDto> categoryRoles = new HashMap<>();

        when(service.getAllCategoryRolesForManufacturer(roleTypeList)).thenReturn(categoryRoles);

        ResponseEntity<?> responseEntity = controller.getAllCategoryRolesForManufacturer(roleTypeList);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(categoryRoles, responseEntity.getBody());
        verify(service, times(1)).getAllCategoryRolesForManufacturer(roleTypeList);
    }

    @Test
    void testUpdateCategory() {
        Long id = 1L;
        CategoryRequestDto dto = new CategoryRequestDto();
        dto.setId(id);

        ResponseEntity<?> responseEntity = controller.updateCategory(id, dto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Success", responseEntity.getBody());
        verify(service, times(1)).updateCategory(dto);
    }

    @Test
    void testDeleteCategory() {
        Long id = 1L;

        ResponseEntity<String> responseEntity = controller.deleteCategory(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Category successfully deleted!", responseEntity.getBody());
        verify(service, times(1)).deleteCategory(id);
    }

    @Test
    void testGetNextCategoryIdById() {
        Long id = 1L;
        Long stateGeoId = 1L;
        List<Long> targetCategoryIds = new ArrayList<>();

        when(service.getNextCategoryIdById(id, stateGeoId)).thenReturn(targetCategoryIds);

        ResponseEntity<?> responseEntity = controller.getNextCategoryIdById(id, stateGeoId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(targetCategoryIds, responseEntity.getBody());
        verify(service, times(1)).getNextCategoryIdById(id, stateGeoId);
    }

    @Test
    void testGetNextCategoryActionById() {
        Long id = 1L;
        Long stateGeoId = 1L;
        Map<Long, String> tcIdsAndAction = new HashMap<>();

        when(service.getNextCategoryIdsAndActions(id, stateGeoId)).thenReturn(tcIdsAndAction);

        ResponseEntity<?> responseEntity = controller.getNextCategoryActionById(id, stateGeoId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(tcIdsAndAction, responseEntity.getBody());
        verify(service, times(1)).getNextCategoryIdsAndActions(id, stateGeoId);
    }

    @Test
    void testGetCategoryIdByName() {
        String categoryName = "Category Name";
        Long categoryId = 1L;

        when(service.getCategoryIdByName(categoryName)).thenReturn(categoryId);

        ResponseEntity<?> responseEntity = controller.getCategoryIdByName(categoryName);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(categoryId, responseEntity.getBody());
        verify(service, times(1)).getCategoryIdByName(categoryName);
    }
}