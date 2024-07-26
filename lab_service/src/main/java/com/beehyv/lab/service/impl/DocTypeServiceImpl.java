package com.beehyv.lab.service.impl;

import com.beehyv.lab.dto.requestDto.DocTypeRequestDTO;
import com.beehyv.lab.dto.responseDto.DocTypeResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.entity.DocType;
import com.beehyv.lab.manager.DocTypeManager;
import com.beehyv.lab.mapper.DTOMapper;
import com.beehyv.lab.service.DocTypeService;
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
