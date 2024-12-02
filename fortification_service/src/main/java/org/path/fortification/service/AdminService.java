package org.path.fortification.service;

import org.path.fortification.dto.requestDto.DSLDto;
import org.path.fortification.dto.requestDto.SearchListRequest;
import org.path.fortification.dto.responseDto.BatchListResponseDTO;
import org.path.fortification.dto.responseDto.CategoryResponseDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.dto.responseDto.LotListResponseDTO;

import java.util.List;

public interface AdminService {
    ListResponse<BatchListResponseDTO> getAllBatches(Integer pageNumber, Integer pageSize, SearchListRequest searchRequest);
    ListResponse<LotListResponseDTO> getAllLots(Integer pageNumber, Integer pageSize, SearchListRequest searchListRequest);

    List<CategoryResponseDto> getAllCategoriesForAdmin(Integer pageNumber, Integer pageSize);

    String updateDSL(DSLDto dto);
}
