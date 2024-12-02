package org.path.lab.service.impl;

import org.path.lab.dto.requestDto.DocTypeRequestDTO;
import org.path.lab.dto.responseDto.DocTypeResponseDTO;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.entity.DocType;
import org.path.lab.manager.DocTypeManager;
import org.path.lab.mapper.DTOMapper;
import org.path.lab.service.DocTypeService;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@AllArgsConstructor
public class DocTypeServiceImpl implements DocTypeService {
    private final DTOMapper mapper = Mappers.getMapper(DTOMapper.class);
    private final DocTypeManager docTypeManager;

    @Override
    public ListResponse<DocTypeResponseDTO> getAllDocTypes(Integer pageNumber, Integer pageSize) {
        List<DocType> docTypes = docTypeManager.findAll(pageNumber, pageSize);
        Long count = docTypeManager.getCount(docTypes.size(), pageNumber, pageSize);
        return ListResponse.from(docTypes, mapper::mapEntityToDtoDocType, count);
    }

    @Override
    public DocTypeResponseDTO getDocTypeById(Long docTypeId) {
        DocType docType = docTypeManager.findById(docTypeId);
        if(docType != null) {
            return mapper.mapEntityToDtoDocType(docType);
        } else {
            return null;
        }
    }

    @Override
    public void addDocType(DocTypeRequestDTO docTypeRequestDTO) {
        docTypeManager.create(mapper.mapDtoToEntityDocType(docTypeRequestDTO));
    }

    @Override
    public void updateDocTypeById(Long docTypeId, DocTypeRequestDTO docTypeRequestDTO) {
        DocType docType = docTypeManager.findById(docTypeId);
        docTypeRequestDTO.setId(docTypeId);
        if (docType != null){
            DocType docType2 = mapper.mapDtoToEntityDocType(docTypeRequestDTO);
            docType2.setUuid(docType.getUuid());
            docTypeManager.update(docType2);
        }
    }

    @Override
    public void deleteDocTypeById(Long docTypeId) {
        DocType docType = docTypeManager.findById(docTypeId);
        if(docType != null) {
            docTypeManager.delete(docTypeId);
        }
    }
}
