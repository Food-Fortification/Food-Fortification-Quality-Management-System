package org.path.fortification.service.impl;

import org.path.fortification.dto.requestDto.DocTypeRequestDto;
import org.path.fortification.dto.responseDto.DocTypeResponseDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.entity.DocType;
import org.path.fortification.entity.Status;
import org.path.fortification.manager.DocTypeManager;
import org.path.fortification.mapper.BaseMapper;
import org.path.fortification.mapper.DocTypeMapper;
import org.path.fortification.service.DocTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class DocTypeServiceImpl implements DocTypeService {

    private final BaseMapper<DocTypeResponseDto, DocTypeRequestDto, DocType> mapper = BaseMapper.getForClass(DocTypeMapper.class);
    private DocTypeManager manager;

    @Override
    public void createDocType(DocTypeRequestDto dto) {
        DocType entity = mapper.toEntity(dto);
        manager.create(entity);
    }

    @Override
    public DocTypeResponseDto getDocTypeById(Long id) {
        DocType entity = manager.findById(id);
        return mapper.toDto(entity);
    }

    @Override
    public ListResponse<DocTypeResponseDto> getAllDocTypes(Integer pageNumber, Integer pageSize) {
        List<DocType> entities = manager.findAll(pageNumber, pageSize);
        Long count = manager.getCount(entities.size(), pageNumber, pageSize);
        return ListResponse.from(entities, mapper::toDto, count);
    }

    @Override
    public void updateDocType(DocTypeRequestDto dto) {
        DocType existingDocType = manager.findById(dto.getId());
        existingDocType.setName(dto.getName());
        if(dto.getName() != null) existingDocType.setName(dto.getName());
        Status status = new Status();
        if(dto.getStatusId() != null) {
            status.setId(dto.getStatusId());
            existingDocType.setStatus(status);
        }
        manager.update(existingDocType);
    }

    @Override
    public void deleteDocType(Long id) {
        manager.delete(id);
    }
}
