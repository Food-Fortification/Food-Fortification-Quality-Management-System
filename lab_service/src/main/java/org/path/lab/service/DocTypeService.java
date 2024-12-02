package org.path.lab.service;

import org.path.lab.dto.requestDto.DocTypeRequestDTO;
import org.path.lab.dto.responseDto.DocTypeResponseDTO;
import org.path.lab.dto.responseDto.ListResponse;

public interface DocTypeService {
    ListResponse<DocTypeResponseDTO> getAllDocTypes(Integer pageNumber, Integer pageSize);
    DocTypeResponseDTO getDocTypeById(Long docTypeId);
    void addDocType(DocTypeRequestDTO docTypeRequestDTO);
    void updateDocTypeById(Long docTypeId, DocTypeRequestDTO docTypeRequestDTO);
    void deleteDocTypeById(Long docTypeId);
}
