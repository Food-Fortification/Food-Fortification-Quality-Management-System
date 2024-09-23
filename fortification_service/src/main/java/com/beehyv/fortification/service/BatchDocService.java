package com.beehyv.fortification.service;

import com.beehyv.fortification.dto.requestDto.BatchDocRequestDto;
import com.beehyv.fortification.dto.responseDto.BatchDocResponseDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;

import java.util.List;

public interface BatchDocService {
    void createBatchDoc(BatchDocRequestDto dto);

    BatchDocResponseDto getBatchDocById(Long id);
    ListResponse<BatchDocResponseDto> getAllBatchDocsByBatchId(Long batchId, Integer pageNumber, Integer pageSize);

    void updateBatchDoc(BatchDocRequestDto dto);

    void deleteBatchDoc(Long id);
}
