package org.path.iam.service;

import org.path.iam.dto.requestDto.AttributeRequestDto;
import org.path.iam.dto.responseDto.AttributeResponseDto;
import org.path.iam.dto.responseDto.ListResponse;
import org.path.iam.manager.AttributeManager;
import org.path.iam.model.Attribute;
import org.path.iam.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AttributeService {

    private final AttributeManager attributeManager;
    private final DtoMapper dtoMapper = Mappers.getMapper(DtoMapper.class);

    public AttributeResponseDto getById(Long id) {
        return dtoMapper.mapToDto(attributeManager.findById(id));
    }

    public ListResponse<AttributeResponseDto> findAll(Integer pageNumber, Integer pageSize) {
        List<Attribute> entities = attributeManager.findAll(pageNumber, pageSize);
        Long count = attributeManager.getCount(entities.size(), pageNumber, pageSize);
        return ListResponse.from(entities, dtoMapper::mapToDto, count);
    }

    public Long create(AttributeRequestDto dto) {
        Attribute entity = dtoMapper.mapToEntity(dto);
        entity = attributeManager.create(entity);
        return entity.getId();
    }

    public void update(AttributeRequestDto dto) {
        attributeManager.update(dtoMapper.mapToEntity(dto));
    }

    public void delete(Long id) {
        attributeManager.delete(id);
    }
}
