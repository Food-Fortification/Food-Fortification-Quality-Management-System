package com.beehyv.fortification.service;

import com.beehyv.fortification.dto.requestDto.DSLDto;
import com.beehyv.fortification.dto.requestDto.SearchListRequest;
import com.beehyv.fortification.dto.responseDto.BatchListResponseDTO;
import com.beehyv.fortification.dto.responseDto.CategoryResponseDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.LotListResponseDTO;

import java.util.List;

public interface AdminService {
    ListResponse<BatchListResponseDTO> getAllBatches(Integer pageNumber, Integer pageSize, SearchListRequest searchRequest);
    ListResponse<LotListResponseDTO> getAllLots(Integer pageNumber, Integer pageSize, SearchListRequest searchListRequest);

    List<CategoryResponseDto> getAllCategoriesForAdmin(Integer pageNumber, Integer pageSize);

    String updateDSL(DSLDto dto);
}
