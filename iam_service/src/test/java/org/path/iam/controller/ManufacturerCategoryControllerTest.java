package org.path.iam.controller;

import org.path.iam.dto.requestDto.ManufacturerCategoryRequestDto;
import org.path.iam.dto.responseDto.ListResponse;
import org.path.iam.dto.responseDto.ManufacturerCategoryResponseDto;
import org.path.iam.service.ManufacturerCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class ManufacturerCategoryControllerTest {

    @Mock
    private ManufacturerCategoryService manufacturerCategoryService;

    @InjectMocks
    private ManufacturerCategoryController manufacturerCategoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        ManufacturerCategoryRequestDto dto = new ManufacturerCategoryRequestDto();
        doNothing().when(manufacturerCategoryService).create(any(ManufacturerCategoryRequestDto.class));

        ResponseEntity<?> response = manufacturerCategoryController.create(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Successfully Created", response.getBody());
    }

    @Test
    void testGetById() {
        ManufacturerCategoryResponseDto responseDto = new ManufacturerCategoryResponseDto();
        when(manufacturerCategoryService.getById(anyLong())).thenReturn(responseDto);

        ResponseEntity<?> response = manufacturerCategoryController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testUpdate() {
        ManufacturerCategoryRequestDto dto = new ManufacturerCategoryRequestDto();
        doNothing().when(manufacturerCategoryService).update(any(ManufacturerCategoryRequestDto.class));

        ResponseEntity<?> response = manufacturerCategoryController.update(1L, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully updated", response.getBody());
    }

    @Test
    void testDelete() {
        doNothing().when(manufacturerCategoryService).deleteById(anyLong());

        ResponseEntity<?> response = manufacturerCategoryController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAllManufacturerCategories() {
        ListResponse<ManufacturerCategoryResponseDto> responseList = new ListResponse<>();
        when(manufacturerCategoryService.getAll(anyInt(), anyInt())).thenReturn(responseList);

        ResponseEntity<?> response = manufacturerCategoryController.getAllManufacturerCategories(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseList, response.getBody());
    }

    @Test
    void testGetCategoriesForManufacturer() {
        List<Long> categories = List.of(1L, 2L, 3L);
        when(manufacturerCategoryService.getCategoriesForManufacturer(anyLong())).thenReturn(categories);

        ResponseEntity<?> response = manufacturerCategoryController.getCategoriesForManufacturer();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetCanSkipRawMaterialsForManufacturerAndCategory() {
        boolean canSkip = true;
        when(manufacturerCategoryService.getCanSkipRawMaterialsForManufacturerAndCategory(anyLong(), anyLong())).thenReturn(canSkip);

        ResponseEntity<Boolean> response = manufacturerCategoryController.getCanSkipRawMaterialsForManufacturerAndCategory(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(canSkip, response.getBody());
    }

    @Test
    void testGetActionNameByManufacturerIdAndCategoryId() {
        String actionName = "someAction";
        when(manufacturerCategoryService.getActionNameByManufacturerIdAndCategoryId(anyLong(), anyLong())).thenReturn(actionName);

        ResponseEntity<?> response = manufacturerCategoryController.getActionNameByManufacturerIdAndCategoryId(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(actionName, response.getBody());
    }
}
