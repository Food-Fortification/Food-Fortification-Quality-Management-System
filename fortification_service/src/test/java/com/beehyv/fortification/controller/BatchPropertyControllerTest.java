package com.beehyv.fortification.controller;

import com.beehyv.fortification.dto.requestDto.BatchPropertyRequestDto;
import com.beehyv.fortification.dto.responseDto.BatchPropertyResponseDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.service.BatchPropertyService;
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

class BatchPropertyControllerTest {

    @Mock
    private BatchPropertyService service;

    @InjectMocks
    private BatchPropertyController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBatchProperty() {
        Long categoryId = 1L;
        Long batchId = 1L;
        BatchPropertyRequestDto dto = new BatchPropertyRequestDto();

        ResponseEntity<?> responseEntity = controller.createBatchProperty(categoryId, batchId, dto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Success", responseEntity.getBody());
        verify(service, times(1)).createBatchProperty(dto);
    }

    @Test
    void testGetBatchPropertyById() {
        Long categoryId = 1L;
        Long batchId = 1L;
        Long id = 1L;
        BatchPropertyResponseDto responseDto = new BatchPropertyResponseDto();

        when(service.getBatchPropertyById(id)).thenReturn(responseDto);

        ResponseEntity<BatchPropertyResponseDto> responseEntity = controller.getBatchPropertyById(categoryId, batchId, id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseDto, responseEntity.getBody());
        verify(service, times(1)).getBatchPropertyById(id);
    }

    @Test
    void testGetAllBatchProperties() {
        Long categoryId = 1L;
        Long batchId = 1L;
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<BatchPropertyResponseDto> batchProperties = new ArrayList<>();
        ListResponse<BatchPropertyResponseDto> listResponse = new ListResponse<>();

        when(service.getAllBatchProperties(pageNumber, pageSize)).thenReturn(listResponse);

        ResponseEntity<ListResponse<BatchPropertyResponseDto>> responseEntity = controller.getAllBatchProperties(categoryId, batchId, pageNumber, pageSize);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(listResponse, responseEntity.getBody());
        verify(service, times(1)).getAllBatchProperties(pageNumber, pageSize);
    }

    @Test
    void testUpdateBatchProperty() {
        Long categoryId = 1L;
        Long batchId = 1L;
        Long id = 1L;
        BatchPropertyRequestDto dto = new BatchPropertyRequestDto();
        dto.setId(id);

        ResponseEntity<?> responseEntity = controller.updateBatchProperty(categoryId, batchId, id, dto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Success", responseEntity.getBody());
        verify(service, times(1)).updateBatchProperty(dto);
    }

    @Test
    void testDeleteBatchProperty() {
        Long categoryId = 1L;
        Long batchId = 1L;
        Long id = 1L;

        ResponseEntity<String> responseEntity = controller.deleteBatchProperty(categoryId, batchId, id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("BatchProperty successfully deleted!", responseEntity.getBody());
        verify(service, times(1)).deleteBatchProperty(id);
    }
}
