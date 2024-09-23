package com.beehyv.lab.service;

import com.beehyv.lab.dto.requestDto.DocTypeRequestDTO;
import com.beehyv.lab.dto.responseDto.DocTypeResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;

public interface DocTypeService {
    ListResponse<DocTypeResponseDTO> getAllDocTypes(Integer pageNumber, Integer pageSize);
    DocTypeResponseDTO getDocTypeById(Long docTypeId);
    void addDocType(DocTypeRequestDTO docTypeRequestDTO);
    void updateDocTypeById(Long docTypeId, DocTypeRequestDTO docTypeRequestDTO);
    void deleteDocTypeById(Long docTypeId);
}
