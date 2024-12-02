package org.path.fortification.service;

import org.path.fortification.dto.requestDto.BatchDocRequestDto;
import org.path.fortification.dto.responseDto.BatchDocResponseDto;
import org.path.fortification.dto.responseDto.ListResponse;

public interface BatchDocService {
    void createBatchDoc(BatchDocRequestDto dto);

    BatchDocResponseDto getBatchDocById(Long id);
    ListResponse<BatchDocResponseDto> getAllBatchDocsByBatchId(Long batchId, Integer pageNumber, Integer pageSize);

    void updateBatchDoc(BatchDocRequestDto dto);

    void deleteBatchDoc(Long id);
}
