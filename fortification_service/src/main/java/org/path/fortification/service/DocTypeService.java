package org.path.fortification.service;

import org.path.fortification.dto.requestDto.DocTypeRequestDto;
import org.path.fortification.dto.responseDto.DocTypeResponseDto;
import org.path.fortification.dto.responseDto.ListResponse;

public interface DocTypeService {

    void createDocType(DocTypeRequestDto dto);

    DocTypeResponseDto getDocTypeById(Long id);

    ListResponse<DocTypeResponseDto> getAllDocTypes(Integer pageNumber, Integer pageSize);

    void updateDocType(DocTypeRequestDto dto);

    void deleteDocType(Long id);
}
