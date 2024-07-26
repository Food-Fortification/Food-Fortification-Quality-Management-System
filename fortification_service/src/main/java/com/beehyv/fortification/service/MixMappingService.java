package com.beehyv.fortification.service;

import com.beehyv.fortification.dto.requestDto.MixMappingCommentsRequestDto;
import com.beehyv.fortification.dto.requestDto.MixMappingRequestDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.MixMappingResponseDto;

public interface MixMappingService {
    void createMixMapping(Long lotId, MixMappingRequestDto dto);

    MixMappingResponseDto getMixMappingById(Long id);

    ListResponse<MixMappingResponseDto> getAllMixMappingsByTargetBatch(Long sourceLotId, Integer pageNumber, Integer pageSize);

    ListResponse<MixMappingResponseDto> getAllMixMappingsBySourceLot(Long sourceLotId, Integer pageNumber, Integer pageSize);

    void updateMixMapping(Long lotId, MixMappingRequestDto dto);

    void deleteMixMapping(Long id);
    void updateBatchMixes(MixMappingCommentsRequestDto mixesInformation, Long batchId);

}
