package com.beehyv.fortification.service;

import com.beehyv.fortification.dto.requestDto.DocTypeRequestDto;
import com.beehyv.fortification.dto.responseDto.DocTypeResponseDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.StateResponseDto;

public interface DocTypeService {

    void createDocType(DocTypeRequestDto dto);

    DocTypeResponseDto getDocTypeById(Long id);

    ListResponse<DocTypeResponseDto> getAllDocTypes(Integer pageNumber, Integer pageSize);

    void updateDocType(DocTypeRequestDto dto);

    void deleteDocType(Long id);
}
