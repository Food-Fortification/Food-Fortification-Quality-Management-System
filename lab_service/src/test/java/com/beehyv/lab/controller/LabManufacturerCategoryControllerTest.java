package com.beehyv.lab.controller;

import com.beehyv.lab.dto.requestDto.LabManufacturerRequestDTO;
import com.beehyv.lab.dto.responseDto.LabListResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.service.LabManufacturerCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LabManufacturerCategoryControllerTest {

    @Mock
    private LabManufacturerCategoryService labManufacturerCategoryService;

    @InjectMocks
    private LabManufacturerCategoryController labManufacturerCategoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddLabManufacturerCategory() {
        LabManufacturerRequestDTO dto = new LabManufacturerRequestDTO(null,null,null,null);

        doNothing().when(labManufacturerCategoryService).create(dto);

        ResponseEntity<String> responseEntity = labManufacturerCategoryController.addLabManufacturerCategory(dto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("SUCCESS", responseEntity.getBody());

        verify(labManufacturerCategoryService, times(1)).create(dto);
        verifyNoMoreInteractions(labManufacturerCategoryService);
    }

    @Test
    void testDeleteLabManufacturerCategoryById() {
        Long id = 1L;

        doNothing().when(labManufacturerCategoryService).delete(id);

        ResponseEntity<String> responseEntity = labManufacturerCategoryController.deleteLabManufacturerCategoryById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("SUCCESS", responseEntity.getBody());

        verify(labManufacturerCategoryService, times(1)).delete(id);
        verifyNoMoreInteractions(labManufacturerCategoryService);
    }

    @Test
    void testGetLabListByManufacturerId() {
        Long manufacturerId = 1L;
        String search = "test";
        Integer pageNumber = 1;
        Integer pageSize = 10;
        ListResponse<LabListResponseDTO> expectedResponse = new ListResponse<>();

        when(labManufacturerCategoryService.getLabsByManufacturerId(search, manufacturerId, pageNumber, pageSize)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = labManufacturerCategoryController.getLabListByManufacturerId(manufacturerId, search, pageNumber, pageSize);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(labManufacturerCategoryService, times(1)).getLabsByManufacturerId(search, manufacturerId, pageNumber, pageSize);
        verifyNoMoreInteractions(labManufacturerCategoryService);
    }
}