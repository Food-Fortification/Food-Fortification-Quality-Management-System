package com.beehyv.iam.controller;

import com.beehyv.iam.dto.requestDto.CategoryDocRequestDto;
import com.beehyv.iam.dto.responseDto.CategoryDocResponseDto;
import com.beehyv.iam.dto.responseDto.ListResponse;
import com.beehyv.iam.service.CategoryDocService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class CategoryDocControllerTest {

    @Mock
    private CategoryDocService categoryDocService;

    @InjectMocks
    private CategoryDocController categoryDocController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRequiredDocsById_ReturnsListResponse_WhenValidCategoryIdAndPageNumberAndPageSizeProvided() {
        ListResponse<CategoryDocResponseDto> responseDto = new ListResponse<>(0L, Collections.emptyList());
        when(categoryDocService.getRequiredDocByCategoryId(anyLong(), anyInt(), anyInt())).thenReturn(responseDto);

        ResponseEntity<ListResponse<CategoryDocResponseDto>> responseEntity = categoryDocController.getRequiredDocsById(1L, 1, 10);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseDto, responseEntity.getBody());
    }

    @Test
    void createCategoryDoc_ReturnsHttpStatusCreated_WhenValidRequestDtoProvided() {
        ResponseEntity<?> responseEntity = categoryDocController.createCategoryDoc(new CategoryDocRequestDto());

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Success", responseEntity.getBody());
    }

    @Test
    void getById_ReturnsCategoryDocResponseDto_WhenValidIdProvided() {
        CategoryDocResponseDto responseDto = new CategoryDocResponseDto();
        when(categoryDocService.getCategoryDocById(anyLong())).thenReturn(responseDto);

        ResponseEntity<CategoryDocResponseDto> responseEntity = categoryDocController.getById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseDto, responseEntity.getBody());
    }

    @Test
    void getAllCategoryDocs_ReturnsListResponse_WhenValidPageNumberAndPageSizeProvided() {
        ListResponse<CategoryDocResponseDto> responseDto = new ListResponse<>(0L, Collections.emptyList());
        when(categoryDocService.getAllCategoryDocs(anyInt(), anyInt())).thenReturn(responseDto);

        ResponseEntity<ListResponse<CategoryDocResponseDto>> responseEntity = categoryDocController.getAllCategoryDocs(1, 10);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseDto, responseEntity.getBody());
    }

    @Test
    void updateCategoryDoc_ReturnsHttpStatusOk_WhenValidIdAndRequestDtoProvided() {
        ResponseEntity<?> responseEntity = categoryDocController.updateCategoryDoc(1L, new CategoryDocRequestDto());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Success", responseEntity.getBody());
    }

    @Test
    void deleteCategoryDoc_ReturnsHttpStatusOk_WhenValidIdProvided() {
        ResponseEntity<String> responseEntity = categoryDocController.deleteCategoryDoc(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("CategoryDoc successfully deleted!", responseEntity.getBody());
    }
}
