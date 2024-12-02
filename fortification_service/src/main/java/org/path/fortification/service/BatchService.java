package org.path.fortification.service;

import org.path.fortification.dto.requestDto.BatchRequestDto;
import org.path.fortification.dto.requestDto.EntityStateRequestDTO;
import org.path.fortification.dto.requestDto.SearchListRequest;
import org.path.fortification.dto.responseDto.*;
import org.path.fortification.enums.ActionType;
import org.path.fortification.enums.EventTest;
import org.path.fortification.enums.SampleTestResult;

import java.util.Date;
import java.util.List;

public interface BatchService {
    Long createBatch(BatchRequestDto batchRequestDto);

    BatchResponseDto getBatchById(Long categoryId, Long batchId);

    ListResponse<BatchListResponseDTO> getAllBatches(Long categoryId, Integer pageNumber, Integer pageSize, SearchListRequest searchRequest);

    void updateBatch(BatchRequestDto batchRequestDto);

    void deleteBatch(Long batchId);

    boolean updateBatchStatus(Long categoryId, EntityStateRequestDTO entityStateRequestDTO, ActionType actionType, SampleTestResult sampleTestResult);

    List<StateResponseDto> getBatchActions(Long categoryId, Long batchId, ActionType actionType, String sampleState);

    ListResponse<BatchListResponseDTO> getBatchInventory(Long categoryId, Integer pageNumber, Integer pageSize, SearchListRequest searchListRequest);

    BatchMonitorResponseDto getHistoryForBatch(Long batchId);

    BatchResponseDto getDetailsForUUID(String uuid);

    Boolean checkLabAccess(Long batchId);

    void updateBatchInspectionStatus(Long batchId, Boolean isBlocked);

    ListResponse<BatchListResponseDTO> getFilteredBatch(String search);

    BatchResponseDto getBatchByIdForEventUpdate(Long id);

    void eventUpdateBody(String encrypted, EventTest param);

    ListResponse getAllBatchesInSuperMonitor(String responseType, Date fromDate, Date toDate, Long categoryId, Integer pageNumber, Integer pageSize, SearchListRequest searchRequest, Long manufactuerId);

}
