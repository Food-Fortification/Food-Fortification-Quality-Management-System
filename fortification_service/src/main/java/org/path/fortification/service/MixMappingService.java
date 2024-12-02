package org.path.fortification.service;

import org.path.fortification.dto.requestDto.MixMappingCommentsRequestDto;
import org.path.fortification.dto.requestDto.MixMappingRequestDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.dto.responseDto.MixMappingResponseDto;

public interface MixMappingService {
    void createMixMapping(Long lotId, MixMappingRequestDto dto);

    MixMappingResponseDto getMixMappingById(Long id);

    ListResponse<MixMappingResponseDto> getAllMixMappingsByTargetBatch(Long sourceLotId, Integer pageNumber, Integer pageSize);

    ListResponse<MixMappingResponseDto> getAllMixMappingsBySourceLot(Long sourceLotId, Integer pageNumber, Integer pageSize);

    void updateMixMapping(Long lotId, MixMappingRequestDto dto);

    void deleteMixMapping(Long id);
    void updateBatchMixes(MixMappingCommentsRequestDto mixesInformation, Long batchId);

}
