package org.path.fortification.controller;

import org.path.fortification.dto.requestDto.BatchRequestDto;
import org.path.fortification.dto.requestDto.EntityStateRequestDTO;
import org.path.fortification.dto.requestDto.SearchListRequest;
import org.path.fortification.dto.responseDto.*;
import org.path.fortification.enums.ActionType;
import org.path.fortification.enums.SampleTestResult;
import org.path.fortification.service.BatchService;
import org.path.fortification.service.CategoryService;
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

class BatchControllerTest {

    @Mock
    private BatchService batchService;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private BatchController batchController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBatch() {
        Long categoryId = 1L;
        BatchRequestDto batchRequestDto = new BatchRequestDto();
        Long id = 1L;

        when(batchService.createBatch(batchRequestDto)).thenReturn(id);

        ResponseEntity<?> responseEntity = batchController.createBatch(categoryId, batchRequestDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(id, responseEntity.getBody());
        verify(batchService, times(1)).createBatch(batchRequestDto);
    }

    @Test
    void testGetBatchById() {
        Long categoryId = 1L;
        Long id = 1L;
        BatchResponseDto batchResponseDto = new BatchResponseDto();

        when(batchService.getBatchById(categoryId, id)).thenReturn(batchResponseDto);

        ResponseEntity<BatchResponseDto> responseEntity = batchController.getBatchById(categoryId, id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(batchResponseDto, responseEntity.getBody());
        verify(batchService, times(1)).getBatchById(categoryId, id);
    }

    @Test
    void testGetAllBatches() {
        Long categoryId = 1L;
        Integer pageNumber = 1;
        Integer pageSize = 10;
        SearchListRequest searchListRequest = new SearchListRequest();
        List<BatchListResponseDTO> batchList = new ArrayList<>();
        ListResponse<BatchListResponseDTO> listResponse = new ListResponse<>();

        when(batchService.getAllBatches(categoryId, pageNumber, pageSize, searchListRequest)).thenReturn(listResponse);

        ResponseEntity<ListResponse<BatchListResponseDTO>> responseEntity = batchController.getAllBatches(categoryId, pageNumber, pageSize, searchListRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(listResponse, responseEntity.getBody());
        verify(batchService, times(1)).getAllBatches(categoryId, pageNumber, pageSize, searchListRequest);
    }

    @Test
    void testGetBatchInventory() {
        Long categoryId = 1L;
        Integer pageNumber = 1;
        Integer pageSize = 10;
        SearchListRequest searchListRequest = new SearchListRequest();
        List<BatchListResponseDTO> batchList = new ArrayList<>();
        ListResponse<BatchListResponseDTO> listResponse = new ListResponse<>();

        when(batchService.getBatchInventory(categoryId, pageNumber, pageSize, searchListRequest)).thenReturn(listResponse);

        ResponseEntity<ListResponse<BatchListResponseDTO>> responseEntity = batchController.getBatchInventory(categoryId, pageNumber, pageSize, searchListRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(listResponse, responseEntity.getBody());
        verify(batchService, times(1)).getBatchInventory(categoryId, pageNumber, pageSize, searchListRequest);
    }

    @Test
    void testUpdateBatchStatus() {
        Long categoryId = 1L;
        ActionType actionType = ActionType.module;
        SampleTestResult sampleTestResult = SampleTestResult.TEST_PASSED;
        EntityStateRequestDTO entityStateRequestDTO = new EntityStateRequestDTO();
        boolean isUpdated = true;

        when(batchService.updateBatchStatus(categoryId, entityStateRequestDTO, actionType, sampleTestResult)).thenReturn(isUpdated);

        ResponseEntity<Boolean> responseEntity = batchController.updateBatchStatus(entityStateRequestDTO, categoryId, actionType, sampleTestResult);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(isUpdated, responseEntity.getBody());
        verify(batchService, times(1)).updateBatchStatus(categoryId, entityStateRequestDTO, actionType, sampleTestResult);
    }


    @Test
    void testGetBatchActions() {
        Long categoryId = 1L;
        Long batchId = 1L;
        ActionType actionType = ActionType.module;
        String sampleState = "PENDING";
        List<StateResponseDto> stateResponseDtos = new ArrayList<>();

        when(batchService.getBatchActions(categoryId, batchId, actionType, sampleState)).thenReturn(stateResponseDtos);

        ResponseEntity<List<StateResponseDto>> responseEntity = batchController.getBatchActions(batchId, categoryId, actionType, sampleState);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(stateResponseDtos, responseEntity.getBody());
        verify(batchService, times(1)).getBatchActions(categoryId, batchId, actionType, sampleState);
    }

    @Test
    void testUpdateBatch() {
        Long categoryId = 1L;
        Long id = 1L;
        BatchRequestDto batchRequestDto = new BatchRequestDto();
        batchRequestDto.setId(id);

        ResponseEntity<?> responseEntity = batchController.updateBatch(categoryId, id, batchRequestDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Success", responseEntity.getBody());
        verify(batchService, times(1)).updateBatch(batchRequestDto);
    }

    @Test
    void testDeleteBatch() {
        Long categoryId = 1L;
        Long id = 1L;

        ResponseEntity<String> responseEntity = batchController.deleteBatch(categoryId, id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Batch successfully deleted!", responseEntity.getBody());
        verify(batchService, times(1)).deleteBatch(id);
    }

    @Test
    void testGetHistoryForBatch() {
        Long categoryId = 1L;
        Long batchId = 1L;
        List<Object> history = new ArrayList<>();
        BatchMonitorResponseDto batchMonitorResponseDto = new BatchMonitorResponseDto();

        when(batchService.getHistoryForBatch(batchId)).thenReturn(batchMonitorResponseDto);

        ResponseEntity<?> responseEntity = batchController.getHistoryForBatch(categoryId, batchId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(batchMonitorResponseDto, responseEntity.getBody());
        verify(batchService, times(1)).getHistoryForBatch(batchId);
    }

    @Test
    void testGetDetailsForUUID() {
        String uuid = "test-uuid";
        BatchResponseDto batchResponseDto = new BatchResponseDto();

        when(batchService.getDetailsForUUID(uuid)).thenReturn(batchResponseDto);

        ResponseEntity<BatchResponseDto> responseEntity = batchController.getDetailsForUUID(uuid);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(batchResponseDto, responseEntity.getBody());
        verify(batchService, times(1)).getDetailsForUUID(uuid);
    }

    @Test
    void testCheckLabAccess() {
        Long categoryId = 1L;
        Long batchId = 1L;
        boolean hasAccess = true;

        when(batchService.checkLabAccess(batchId)).thenReturn(hasAccess);

        ResponseEntity<Boolean> responseEntity = batchController.checkLabAccess(categoryId, batchId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(hasAccess, responseEntity.getBody());
        verify(batchService, times(1)).checkLabAccess(batchId);
    }

    @Test
    void testUpdateBatchInspectionStatus() {
        Long batchId = 1L;
        String categoryId = "1";
        boolean isBlocked = true;

        ResponseEntity<?> responseEntity = batchController.updateBatchInspectionStatus(batchId, isBlocked, categoryId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Successfully Updated Inspection Status", responseEntity.getBody());
        verify(batchService, times(1)).updateBatchInspectionStatus(batchId, isBlocked);
    }
}