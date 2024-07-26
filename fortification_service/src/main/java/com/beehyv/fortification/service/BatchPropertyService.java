package com.beehyv.fortification.service;

import com.beehyv.fortification.dto.requestDto.BatchPropertyRequestDto;
import com.beehyv.fortification.dto.responseDto.BatchPropertyResponseDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;

import java.util.List;

public interface BatchPropertyService {
    void createBatchProperty(BatchPropertyRequestDto dto);

    BatchPropertyResponseDto getBatchPropertyById(Long id);

    ListResponse<BatchPropertyResponseDto> getAllBatchProperties(Integer pageNumber, Integer pageSize);

    void updateBatchProperty(BatchPropertyRequestDto dto);

    void deleteBatchProperty(Long id);

}
