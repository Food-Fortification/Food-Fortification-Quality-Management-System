package com.beehyv.fortification.service;

import com.beehyv.fortification.dto.requestDto.BatchRequestDto;
import com.beehyv.fortification.dto.requestDto.EntityStateRequestDTO;
import com.beehyv.fortification.dto.requestDto.PremixBatchByFrkDTO;
import com.beehyv.fortification.dto.requestDto.SearchListRequest;
import com.beehyv.fortification.dto.responseDto.*;
import com.beehyv.fortification.enums.ActionType;
import org.springframework.core.io.Resource;
import com.beehyv.fortification.enums.EventTest;
import com.beehyv.fortification.enums.SampleTestResult;

import java.util.List;

public interface BatchService {
    Long createBatch(BatchRequestDto batchRequestDto);

    BatchResponseDto getBatchById(Long categoryId, Long batchId);

    ListResponse<BatchListResponseDTO> getAllBatches(Long categoryId, Integer pageNumber, Integer pageSize, SearchListRequest searchRequest);

    void updateBatch(BatchRequestDto batchRequestDto);

    void deleteBatch(Long batchId);

    boolean updateBatchStatus(Long categoryId, EntityStateRequestDTO entityStateRequestDTO, ActionType actionType, SampleTestResult sampleTestResult);

    boolean dispatchExternalBatch(Long categoryId, EntityStateRequestDTO entityStateRequestDTO);

    List<StateResponseDto> getBatchActions(Long categoryId, Long batchId, ActionType actionType, String sampleState);

    ListResponse<BatchListResponseDTO> getBatchInventory(Long categoryId, Integer pageNumber, Integer pageSize, SearchListRequest searchListRequest);

    BatchMonitorResponseDto getHistoryForBatch(Long batchId);

    BatchResponseDto getDetailsForUUID(String uuid);

    Boolean checkLabAccess(Long batchId);

    void updateBatchInspectionStatus(Long batchId, Boolean isBlocked);

    Resource getBatchInventoryExcel(ListResponse<BatchListResponseDTO> batches);

    ListResponse<BatchListResponseDTO> getFilteredBatch(String search);

    Long createSelfDeclardBatch(PremixBatchByFrkDTO premixBatchByFrkDTO);

    BatchResponseDto getBatchByIdForEventUpdate(Long id);

    void eventUpdateBody(String encrypted, EventTest param);
}
