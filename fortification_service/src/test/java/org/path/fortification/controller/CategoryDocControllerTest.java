package org.path.fortification.controller;

import org.path.fortification.dto.requestDto.CategoryDocRequestDto;
import org.path.fortification.dto.responseDto.CategoryDocResponseDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.service.CategoryDocService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CategoryDocControllerTest {

    @Mock
    private CategoryDocService service;

    @InjectMocks
    private CategoryDocController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRequiredDocsById() {
        Long categoryId = 1L;
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<CategoryDocResponseDto> categoryDocs = new ArrayList<>();
        ListResponse<CategoryDocResponseDto> listResponse = new ListResponse<>();

        when(service.getRequiredDocByCategoryId(categoryId, pageNumber, pageSize)).thenReturn(listResponse);

        ResponseEntity<ListResponse<CategoryDocResponseDto>> responseEntity = controller.getRequiredDocsById(categoryId, pageNumber, pageSize);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(listResponse, responseEntity.getBody());
        verify(service, times(1)).getRequiredDocByCategoryId(categoryId, pageNumber, pageSize);
    }

    @Test
    void testCreateCategoryDoc() {
        CategoryDocRequestDto dto = new CategoryDocRequestDto();

        ResponseEntity<?> responseEntity = controller.createCategoryDoc(dto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Success", responseEntity.getBody());
        verify(service, times(1)).createCategoryDoc(dto);
    }

    @Test
    void testGetCategoryDocById() {
        Long id = 1L;
        CategoryDocResponseDto responseDto = new CategoryDocResponseDto();

        when(service.getCategoryDocById(id)).thenReturn(responseDto);

        ResponseEntity<CategoryDocResponseDto> responseEntity = controller.getCategoryDocById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseDto, responseEntity.getBody());
        verify(service, times(1)).getCategoryDocById(id);
    }

    @Test
    void testGetAllCategoryDocs() {
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<CategoryDocResponseDto> categoryDocs = new ArrayList<>();
        ListResponse<CategoryDocResponseDto> listResponse = new ListResponse<>();

        when(service.getAllCategoryDocs(pageNumber, pageSize)).thenReturn(listResponse);

        ResponseEntity<ListResponse<CategoryDocResponseDto>> responseEntity = controller.getAllCategoryDocs(pageNumber, pageSize);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(listResponse, responseEntity.getBody());
        verify(service, times(1)).getAllCategoryDocs(pageNumber, pageSize);
    }

    @Test
    void testUpdateCategoryDoc() {
        Long categoryId = 1L;
        Long id = 1L;
        CategoryDocRequestDto dto = new CategoryDocRequestDto();
        dto.setId(id);

        ResponseEntity<?> responseEntity = controller.updateCategoryDoc(id, dto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Success", responseEntity.getBody());
        verify(service, times(1)).updateCategoryDoc(dto);
    }

    @Test
    void testDeleteCategoryDoc() {
        Long categoryId = 1L;
        Long id = 1L;

        ResponseEntity<String> responseEntity = controller.deleteCategoryDoc(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("CategoryDoc successfully deleted!", responseEntity.getBody());
        verify(service, times(1)).deleteCategoryDoc(id);
    }
}