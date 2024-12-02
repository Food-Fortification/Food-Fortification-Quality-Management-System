package org.path.fortification.service;

import org.path.fortification.dto.requestDto.SearchListRequest;
import org.path.fortification.dto.responseDto.BatchListResponseDTO;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.dto.responseDto.LotListResponseDTO;

public interface InspectionService {
    ListResponse<BatchListResponseDTO> getAllBatches(Long categoryId, SearchListRequest searchListRequest, Integer pageNumber, Integer pageSize);

    ListResponse<LotListResponseDTO> getAllLots(Long categoryId, SearchListRequest searchListRequest, Integer pageNumber, Integer pageSize);
}
