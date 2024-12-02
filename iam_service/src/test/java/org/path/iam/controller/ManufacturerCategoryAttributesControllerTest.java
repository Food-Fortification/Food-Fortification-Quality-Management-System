package org.path.iam.controller;

import org.path.iam.dto.requestDto.ManufacturerCategoryAttributesRequestDto;
import org.path.iam.dto.responseDto.ListResponse;
import org.path.iam.dto.responseDto.ManufacturerCategoryAttributesResponseDto;
import org.path.iam.service.ManufacturerCategoryAttributesService;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class ManufacturerCategoryAttributesControllerTest {

    @Mock
    private ManufacturerCategoryAttributesService manufacturerAttributesService;

    @InjectMocks
    private ManufacturerCategoryAttributesController manufacturerCategoryAttributesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateManufacturer() {
        ManufacturerCategoryAttributesRequestDto dto = new ManufacturerCategoryAttributesRequestDto();
        Long createdId = 1L;
        when(manufacturerAttributesService.create(any(ManufacturerCategoryAttributesRequestDto.class))).thenReturn(createdId);

        ResponseEntity<Long> response = manufacturerCategoryAttributesController.createManufacturer(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdId, response.getBody());
    }

    @Test
    void testUpdate() {
        ManufacturerCategoryAttributesRequestDto dto = new ManufacturerCategoryAttributesRequestDto();
        doNothing().when(manufacturerAttributesService).update(any(ManufacturerCategoryAttributesRequestDto.class));

        ResponseEntity<?> response = manufacturerCategoryAttributesController.update(1L, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("successfully updated", response.getBody());
    }

    @Test
    void testDelete() {
        doNothing().when(manufacturerAttributesService).delete(anyLong());

        ResponseEntity<?> response = manufacturerCategoryAttributesController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetById() {
        ManufacturerCategoryAttributesResponseDto responseDto = new ManufacturerCategoryAttributesResponseDto();
        when(manufacturerAttributesService.getById(anyLong())).thenReturn(responseDto);

        ResponseEntity<ManufacturerCategoryAttributesResponseDto> response = manufacturerCategoryAttributesController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testGetAll() {
        ListResponse<ManufacturerCategoryAttributesResponseDto> listResponse = new ListResponse<>();
        when(manufacturerAttributesService.findAll(anyInt(), anyInt())).thenReturn(listResponse);

        ResponseEntity<ListResponse<ManufacturerCategoryAttributesResponseDto>> response = manufacturerCategoryAttributesController.getAll(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listResponse, response.getBody());
    }

    @Test
    void testGetAllManufacturerAttributesForManufacturer() {
        List<ManufacturerCategoryAttributesResponseDto> responseDtoList = new ArrayList<>();
        when(manufacturerAttributesService.getAllManufacturerAttributesForManufacturer(anyLong(), anyLong())).thenReturn(responseDtoList);

        ResponseEntity<List<ManufacturerCategoryAttributesResponseDto>> response = manufacturerCategoryAttributesController.getAllManufacturerAttributesForManufacturer(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDtoList, response.getBody());
    }
}
