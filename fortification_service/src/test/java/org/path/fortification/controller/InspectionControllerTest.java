package org.path.fortification.controller;


import org.path.fortification.dto.requestDto.SearchListRequest;
import org.path.fortification.dto.responseDto.BatchListResponseDTO;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.dto.responseDto.LotListResponseDTO;
import org.path.fortification.service.InspectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InspectionControllerTest {

    @Mock
    private InspectionService service;

    @InjectMocks
    private InspectionController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBatches() {
        Long categoryId = 1L;
        SearchListRequest searchListRequest = new SearchListRequest();
        ListResponse<BatchListResponseDTO> listResponse = new ListResponse<>(0L, Collections.emptyList());
        when(service.getAllBatches(categoryId, searchListRequest, null, null)).thenReturn(listResponse);
        ResponseEntity<ListResponse<BatchListResponseDTO>> response = controller.getAllBatches(categoryId, null, null, searchListRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listResponse, response.getBody());
        verify(service, times(1)).getAllBatches(categoryId, searchListRequest, null, null);
    }

    @Test
    void testGetAllLots() {
        Long categoryId = 1L;
        SearchListRequest searchListRequest = new SearchListRequest();
        ListResponse<LotListResponseDTO> listResponse = new ListResponse<>(0L, Collections.emptyList());
        when(service.getAllLots(categoryId, searchListRequest, null, null)).thenReturn(listResponse);
        ResponseEntity<ListResponse<LotListResponseDTO>> response = controller.getAllLots(categoryId, null, null, searchListRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listResponse, response.getBody());
        verify(service, times(1)).getAllLots(categoryId, searchListRequest, null, null);
    }
}