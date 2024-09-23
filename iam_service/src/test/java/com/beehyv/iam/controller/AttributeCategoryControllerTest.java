package com.beehyv.iam.controller;

import com.beehyv.iam.dto.requestDto.AttributeCategoryRequestDto;
import com.beehyv.iam.dto.responseDto.AttributeCategoryResponseDto;
import com.beehyv.iam.dto.responseDto.ListResponse;
import com.beehyv.iam.service.AttributeCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class AttributeCategoryControllerTest {

    @Mock
    private AttributeCategoryService attributeCategoryService;

    @InjectMocks
    private AttributeCategoryController attributeCategoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getById_ReturnsAttributeCategoryResponseDto_WhenValidIdProvided() {
        AttributeCategoryResponseDto responseDto = new AttributeCategoryResponseDto();
        when(attributeCategoryService.getById(anyLong())).thenReturn(responseDto);

        ResponseEntity<AttributeCategoryResponseDto> responseEntity = attributeCategoryController.getById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseDto, responseEntity.getBody());
    }

    @Test
    void getAll_ReturnsListOfAttributeCategoryResponseDto_WhenValidParametersProvided() {
        ListResponse<AttributeCategoryResponseDto> responseDto = new ListResponse<>();
        when(attributeCategoryService.findAll(any(), any())).thenReturn(responseDto);

        ResponseEntity<ListResponse<AttributeCategoryResponseDto>> responseEntity = attributeCategoryController.getAll(1, 10);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseDto, responseEntity.getBody());
    }

    @Test
    void create_ReturnsHttpStatusCreated_WhenValidAttributeCategoryRequestDtoProvided() {
        ResponseEntity<Long> responseEntity = attributeCategoryController.create(new AttributeCategoryRequestDto());

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void update_ReturnsHttpStatusOk_WhenValidIdAndAttributeCategoryRequestDtoProvided() {
        ResponseEntity<?> responseEntity = attributeCategoryController.update(1L, new AttributeCategoryRequestDto());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void delete_ReturnsHttpStatusOk_WhenValidIdProvided() {
        ResponseEntity<?> responseEntity = attributeCategoryController.delete(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
