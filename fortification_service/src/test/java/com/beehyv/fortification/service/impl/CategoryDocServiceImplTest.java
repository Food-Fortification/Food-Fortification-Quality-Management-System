package com.beehyv.fortification.service.impl;

import com.beehyv.fortification.dto.requestDto.CategoryDocRequestDto;
import com.beehyv.fortification.dto.responseDto.CategoryDocResponseDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.entity.Category;
import com.beehyv.fortification.entity.CategoryDoc;
import com.beehyv.fortification.manager.CategoryDocManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryDocServiceImplTest {

    @InjectMocks
    private CategoryDocServiceImpl categoryDocService;

    @Mock
    private CategoryDocManager categoryDocManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetRequiredDocByCategoryId() {
        when(categoryDocManager.findAllByCategoryId(anyLong(), anyInt(), anyInt()))
                .thenReturn(someListOfCategoryDocs());
        when(categoryDocManager.getCount(anyInt(), anyLong(), anyInt(), anyInt()))
                .thenReturn((long) someListOfCategoryDocs().size());

        ListResponse<CategoryDocResponseDto> response = categoryDocService.getRequiredDocByCategoryId(1L, 1, 10);

        assertNotNull(response);
        assertEquals(someListOfCategoryDocs().size(), ((ListResponse<?>) response).getData().size());
        verify(categoryDocManager, times(1)).findAllByCategoryId(anyLong(), anyInt(), anyInt());
        verify(categoryDocManager, times(1)).getCount(anyInt(), anyLong(), anyInt(), anyInt());
    }

    @Test
    public void testCreateCategoryDoc() {
        CategoryDocRequestDto requestDto = new CategoryDocRequestDto();

        categoryDocService.createCategoryDoc(requestDto);

        verify(categoryDocManager, times(1)).create(any());
    }

    @Test
    public void testGetCategoryDocById() {
        when(categoryDocManager.findById(anyLong())).thenReturn(someCategoryDoc());

        CategoryDocResponseDto response = categoryDocService.getCategoryDocById(1L);

        assertNotNull(response);
        verify(categoryDocManager, times(1)).findById(anyLong());
    }

    @Test
    public void testGetAllCategoryDocs() {
        List<CategoryDoc> mockCategoryDocs = someListOfCategoryDocs();
        when(categoryDocManager.findAll(anyInt(), anyInt())).thenReturn(mockCategoryDocs);
        when(categoryDocManager.getCount(eq(mockCategoryDocs.size()), anyInt(), anyInt())).thenReturn((long) mockCategoryDocs.size());

        ListResponse<CategoryDocResponseDto> response = categoryDocService.getAllCategoryDocs(1, 10);

        assertNotNull(response);
        assertEquals(mockCategoryDocs.size(), response.getData().size());

        verify(categoryDocManager, times(1)).findAll(anyInt(), anyInt());
    }

    @Test
    public void testUpdateCategoryDoc() {
        CategoryDoc mockCategoryDoc = someCategoryDoc();
        when(categoryDocManager.findById(anyLong())).thenReturn(mockCategoryDoc);

        CategoryDocRequestDto requestDto = new CategoryDocRequestDto();
        requestDto.setId(123L);

        categoryDocService.updateCategoryDoc(requestDto);

        verify(categoryDocManager, times(1)).findById(anyLong());
        verify(categoryDocManager, times(1)).update(any());
    }

    @Test
    public void testDeleteCategoryDoc() {
        categoryDocService.deleteCategoryDoc(1L);

        verify(categoryDocManager, times(1)).delete(anyLong());
    }


    private List<CategoryDoc> someListOfCategoryDocs() {
        List<CategoryDoc> categoryDocs = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            CategoryDoc categoryDoc = new CategoryDoc();
            Category category = new Category();
            category.setId((long) (i + 1)); // Set the ID as needed
            categoryDoc.setId((long) i);
            categoryDoc.setCategory(category);

            // Set other properties as needed
            categoryDocs.add(categoryDoc);
        }
        return categoryDocs;
    }

    private CategoryDoc someCategoryDoc() {
        CategoryDoc categoryDoc = new CategoryDoc();
        Category category = new Category();
        category.setId(1L);
        categoryDoc.setCategory(category);

        return categoryDoc;
    }

}
