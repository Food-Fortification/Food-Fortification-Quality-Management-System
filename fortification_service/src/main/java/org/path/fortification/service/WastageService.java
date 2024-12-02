package org.path.fortification.service;

import org.path.fortification.dto.requestDto.WastageRequestDto;
import org.path.fortification.dto.responseDto.WastageResponseDto;
import org.path.fortification.dto.responseDto.ListResponse;

import java.util.List;

public interface WastageService {
    Long createBatchWastage(WastageRequestDto dto, Long batchId);
    Long createLotWastage(WastageRequestDto dto, Long lotId) ;
    ListResponse<WastageResponseDto> getAllWastesForLot(Long lotId, Integer pageNumber, Integer pageSize);

    WastageResponseDto getWastageById(Long id);

    List<WastageResponseDto> getAllBatchWastages(Integer pageNumber, Integer pageSize);

    ListResponse<WastageResponseDto> getAllWastesForBatch(Long batchId, Integer pageNumber, Integer pageSize);

    void updateBatchWastage(WastageRequestDto dto, Long batchId);
    void updateLotWastage(WastageRequestDto dto, Long lotId);

    void deleteBatchWastage(Long id,Long batchId);
    void deleteLotWastage(Long id,Long lotId);

}
