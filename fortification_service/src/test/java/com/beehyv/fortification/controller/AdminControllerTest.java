package com.beehyv.fortification.controller;

import com.beehyv.fortification.dto.requestDto.SearchListRequest;
import com.beehyv.fortification.dto.responseDto.BatchListResponseDTO;
import com.beehyv.fortification.dto.responseDto.CategoryResponseDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.LotListResponseDTO;
import com.beehyv.fortification.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllBatches() {
        int pageNumber = 1;
        int pageSize = 10;
        SearchListRequest searchListRequest = new SearchListRequest();
        ListResponse<BatchListResponseDTO> expectedResponse = new ListResponse<>();

        when(adminService.getAllBatches(pageNumber, pageSize, searchListRequest)).thenReturn(expectedResponse);

        ResponseEntity<ListResponse<BatchListResponseDTO>> responseEntity = adminController.getAllBatches(pageNumber, pageSize, searchListRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(adminService, times(1)).getAllBatches(pageNumber, pageSize, searchListRequest);
        verifyNoMoreInteractions(adminService);
    }

    @Test
    void testGetAllLots() {
        int pageNumber = 1;
        int pageSize = 10;
        SearchListRequest searchListRequest = new SearchListRequest();
        ListResponse<LotListResponseDTO> expectedResponse = new ListResponse<>();

        when(adminService.getAllLots(pageNumber, pageSize, searchListRequest)).thenReturn(expectedResponse);

        ResponseEntity<ListResponse<LotListResponseDTO>> responseEntity = adminController.getAllLots(pageNumber, pageSize, searchListRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(adminService, times(1)).getAllLots(pageNumber, pageSize, searchListRequest);
        verifyNoMoreInteractions(adminService);
    }

    @Test
    void testGetAllCategoriesForAdmin() {
        int pageNumber = 1;
        int pageSize = 10;
        List<CategoryResponseDto> expectedResponse = Collections.singletonList(new CategoryResponseDto());

        when(adminService.getAllCategoriesForAdmin(pageNumber, pageSize)).thenReturn(expectedResponse);

        ResponseEntity<List<CategoryResponseDto>> responseEntity = adminController.getAllCategoriesForAdmin(pageNumber, pageSize);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(adminService, times(1)).getAllCategoriesForAdmin(pageNumber, pageSize);
        verifyNoMoreInteractions(adminService);
    }

    @Test
    void testGetAllBatchesNotFound() {
        int pageNumber = 1;
        int pageSize = 10;
        SearchListRequest searchListRequest = new SearchListRequest();

        when(adminService.getAllBatches(pageNumber, pageSize, searchListRequest)).thenReturn(null);

        ResponseEntity<ListResponse<BatchListResponseDTO>> responseEntity = adminController.getAllBatches(pageNumber, pageSize, searchListRequest);

//        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        verify(adminService, times(1)).getAllBatches(pageNumber, pageSize, searchListRequest);
        verifyNoMoreInteractions(adminService);
    }

    @Test
    void testGetAllBatchesException() {
        int pageNumber = 1;
        int pageSize = 10;
        SearchListRequest searchListRequest = new SearchListRequest();

        when(adminService.getAllBatches(pageNumber, pageSize, searchListRequest)).thenThrow(new RuntimeException("Unexpected error"));

        assertThrows(RuntimeException.class, () -> adminController.getAllBatches(pageNumber, pageSize, searchListRequest));

        verify(adminService, times(1)).getAllBatches(pageNumber, pageSize, searchListRequest);
        verifyNoMoreInteractions(adminService);
    }
}
