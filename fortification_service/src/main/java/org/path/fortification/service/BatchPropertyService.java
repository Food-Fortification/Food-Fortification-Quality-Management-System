package org.path.fortification.service;

import org.path.fortification.dto.requestDto.BatchPropertyRequestDto;
import org.path.fortification.dto.responseDto.BatchPropertyResponseDto;
import org.path.fortification.dto.responseDto.ListResponse;

public interface BatchPropertyService {
    void createBatchProperty(BatchPropertyRequestDto dto);

    BatchPropertyResponseDto getBatchPropertyById(Long id);

    ListResponse<BatchPropertyResponseDto> getAllBatchProperties(Integer pageNumber, Integer pageSize);

    void updateBatchProperty(BatchPropertyRequestDto dto);

    void deleteBatchProperty(Long id);

}
