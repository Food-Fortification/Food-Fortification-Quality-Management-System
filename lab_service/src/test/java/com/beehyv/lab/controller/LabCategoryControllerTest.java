package com.beehyv.lab.controller;

import com.beehyv.lab.dto.requestDto.LabCategoryRequestDto;
import com.beehyv.lab.dto.responseDto.LabCategoryResponseDto;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.service.LabCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LabCategoryControllerTest {

    @Mock
    private LabCategoryService labCategoryService;

    @InjectMocks
    private LabCategoryController labCategoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById() {
        Long id = 1L;
        LabCategoryResponseDto expectedResponse = new LabCategoryResponseDto();

        when(labCategoryService.getById(id)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = labCategoryController.getById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(labCategoryService, times(1)).getById(id);
        verifyNoMoreInteractions(labCategoryService);
    }

    @Test
    void testCreate() {
        LabCategoryRequestDto dto = new LabCategoryRequestDto(null,null,null,null);

        doNothing().when(labCategoryService).create(dto);

        ResponseEntity<?> responseEntity = labCategoryController.create(dto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Successfully Created", responseEntity.getBody());

        verify(labCategoryService, times(1)).create(dto);
        verifyNoMoreInteractions(labCategoryService);
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        LabCategoryRequestDto dto = new LabCategoryRequestDto(null,null,null,null);
        dto.setId(id);

        doNothing().when(labCategoryService).update(dto);

        ResponseEntity<?> responseEntity = labCategoryController.update(id, dto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("successfully updated", responseEntity.getBody());

        verify(labCategoryService, times(1)).update(dto);
        verifyNoMoreInteractions(labCategoryService);
    }

    @Test
    void testDelete() {
        Long id = 1L;

        doNothing().when(labCategoryService).delete(id);

        ResponseEntity<?> responseEntity = labCategoryController.delete(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(labCategoryService, times(1)).delete(id);
        verifyNoMoreInteractions(labCategoryService);
    }

    @Test
    void testGetByLabId() {
        Long labId = 1L;
        ListResponse<LabCategoryResponseDto> expectedResponse = new ListResponse<>();

        when(labCategoryService.getByLabId(labId)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = labCategoryController.getByLabId(labId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(labCategoryService, times(1)).getByLabId(labId);
        verifyNoMoreInteractions(labCategoryService);
    }

    @Test
    void testGetCategoryIdsByLabId() {
        Long labId = 1L;
        List<Long>  expectedResponse = List.of(1L, 2L, 3L);

        when(labCategoryService.getCategoryIdsByLabId(labId)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = labCategoryController.getCategoryIdsByLabId(labId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(labCategoryService, times(1)).getCategoryIdsByLabId(labId);
        verifyNoMoreInteractions(labCategoryService);
    }
}