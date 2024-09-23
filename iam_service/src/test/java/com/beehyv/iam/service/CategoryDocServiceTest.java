package com.beehyv.iam.service;

import com.beehyv.iam.dto.requestDto.CategoryDocRequestDto;
import com.beehyv.iam.dto.responseDto.CategoryDocResponseDto;
import com.beehyv.iam.dto.responseDto.ListResponse;
import com.beehyv.iam.manager.CategoryDocManager;
import com.beehyv.iam.model.CategoryDoc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryDocServiceTest {

    @Mock
    private CategoryDocManager categoryDocManager;

    @InjectMocks
    private CategoryDocService categoryDocService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetRequiredDocByCategoryId() {
        Long categoryId = 1L;
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<CategoryDoc> categoryDocs = Collections.singletonList(new CategoryDoc());
        when(categoryDocManager.findAllByCategoryId(categoryId, pageNumber, pageSize)).thenReturn(categoryDocs);
        when(categoryDocManager.getCount(categoryDocs.size(), categoryId, pageNumber, pageSize)).thenReturn(1L);

        ListResponse<CategoryDocResponseDto> response = categoryDocService.getRequiredDocByCategoryId(categoryId, pageNumber, pageSize);

        assertNotNull(response);
        assertFalse(response.getData().isEmpty());
        assertEquals(1L, response.getCount());
    }

    @Test
    public void testCreateCategoryDoc() {
        CategoryDocRequestDto requestDto = new CategoryDocRequestDto();
        CategoryDoc entity = new CategoryDoc();
        when(categoryDocManager.create(any())).thenReturn(entity);

        categoryDocService.createCategoryDoc(requestDto);

        verify(categoryDocManager, times(1)).create(any());
    }

    @Test
    public void testGetCategoryDocById() {
        Long id = 1L;
        CategoryDoc categoryDoc = new CategoryDoc();
        when(categoryDocManager.findById(id)).thenReturn(categoryDoc);

        CategoryDocResponseDto responseDto = categoryDocService.getCategoryDocById(id);

        assertNotNull(responseDto);
    }

    @Test
    public void testGetAllCategoryDocs() {
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<CategoryDoc> categoryDocs = Collections.singletonList(new CategoryDoc());
        when(categoryDocManager.findAll(pageNumber, pageSize)).thenReturn(categoryDocs);
        when(categoryDocManager.getCount(categoryDocs.size(), pageNumber, pageSize)).thenReturn(1L);

        ListResponse<CategoryDocResponseDto> response = categoryDocService.getAllCategoryDocs(pageNumber, pageSize);

        assertNotNull(response);
        assertFalse(response.getData().isEmpty());
        assertEquals(1L, response.getCount());
    }

    @Test
    public void testUpdateCategoryDoc() {
        CategoryDocRequestDto requestDto = new CategoryDocRequestDto();
        when(categoryDocManager.update(any())).thenReturn(new CategoryDoc());

        categoryDocService.update(requestDto);

        verify(categoryDocManager, times(1)).update(any());
    }

    @Test
    public void testDeleteCategoryDoc() {
        Long id = 1L;

        categoryDocService.deleteCategoryDoc(id);

        verify(categoryDocManager, times(1)).delete(id);
    }
}
