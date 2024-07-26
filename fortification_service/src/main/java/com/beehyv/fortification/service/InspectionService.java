package com.beehyv.fortification.service;

import com.beehyv.fortification.dto.requestDto.SearchListRequest;
import com.beehyv.fortification.dto.responseDto.BatchListResponseDTO;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.LotListResponseDTO;

public interface InspectionService {
    ListResponse<BatchListResponseDTO> getAllBatches(Long categoryId, SearchListRequest searchListRequest, Integer pageNumber, Integer pageSize);

    ListResponse<LotListResponseDTO> getAllLots(Long categoryId, SearchListRequest searchListRequest, Integer pageNumber, Integer pageSize);
}
