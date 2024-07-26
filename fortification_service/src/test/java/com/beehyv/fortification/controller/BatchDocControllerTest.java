package com.beehyv.fortification.controller;

import com.beehyv.fortification.dto.requestDto.BatchDocRequestDto;
import com.beehyv.fortification.dto.responseDto.BatchDocResponseDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.service.BatchDocService;
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

class BatchDocControllerTest {

    @Mock
    private BatchDocService service;

    @InjectMocks
    private BatchDocController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBatchDoc() {
        BatchDocRequestDto dto = new BatchDocRequestDto();

        ResponseEntity<?> responseEntity = controller.createBatchDoc(dto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Success", responseEntity.getBody());
        verify(service, times(1)).createBatchDoc(dto);
    }

    @Test
    void testGetBatchDocById() {
        Long id = 1L;
        BatchDocResponseDto responseDto = new BatchDocResponseDto();

        when(service.getBatchDocById(id)).thenReturn(responseDto);

        ResponseEntity<BatchDocResponseDto> responseEntity = controller.getBatchDocById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseDto, responseEntity.getBody());
        verify(service, times(1)).getBatchDocById(id);
    }

    @Test
    void testGetAllBatchDocs() {
        Long batchId = 1L;
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<BatchDocResponseDto> batchDocs = new ArrayList<>();
        ListResponse<BatchDocResponseDto> listResponse = new ListResponse<>();

        when(service.getAllBatchDocsByBatchId(batchId, pageNumber, pageSize)).thenReturn(listResponse);

        ResponseEntity<ListResponse<BatchDocResponseDto>> responseEntity = controller.getAllBatchDocs(batchId, pageNumber, pageSize);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(listResponse, responseEntity.getBody());
        verify(service, times(1)).getAllBatchDocsByBatchId(batchId, pageNumber, pageSize);
    }

    @Test
    void testUpdateBatchDoc() {
        Long id = 1L;
        BatchDocRequestDto dto = new BatchDocRequestDto();
        dto.setId(id);

        ResponseEntity<?> responseEntity = controller.updateBatchDoc(id, dto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Success", responseEntity.getBody());
        verify(service, times(1)).updateBatchDoc(dto);
    }

    @Test
    void testDeleteBatchDoc() {
        Long id = 1L;

        ResponseEntity<String> responseEntity = controller.deleteBatchDoc(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("BatchDoc successfully deleted!", responseEntity.getBody());
        verify(service, times(1)).deleteBatchDoc(id);
    }
}