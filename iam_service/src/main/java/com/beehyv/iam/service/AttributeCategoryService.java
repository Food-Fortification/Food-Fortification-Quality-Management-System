package com.beehyv.iam.service;

import com.beehyv.iam.dto.requestDto.AttributeCategoryRequestDto;
import com.beehyv.iam.dto.responseDto.AttributeCategoryResponseDto;
import com.beehyv.iam.dto.responseDto.ListResponse;
import com.beehyv.iam.manager.AttributeCategoryManager;
import com.beehyv.iam.model.AttributeCategory;
import com.beehyv.iam.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AttributeCategoryService {

    private final AttributeCategoryManager attributeCategoryManager;
    private final DtoMapper dtoMapper = Mappers.getMapper(DtoMapper.class);

    public AttributeCategoryResponseDto getById(Long id) {
        return dtoMapper.mapToDto(attributeCategoryManager.findById(id));
    }


    public ListResponse<AttributeCategoryResponseDto> findAll(Integer pageNumber, Integer pageSize) {
        List<AttributeCategory> entities = attributeCategoryManager.findAll(pageNumber, pageSize);
        Long count = attributeCategoryManager.getCount(entities.size(), pageNumber, pageSize);
        return ListResponse.from(entities, dtoMapper::mapToDto, count);
    }

    public Long create(AttributeCategoryRequestDto dto) {
        AttributeCategory entity = dtoMapper.mapToEntity(dto);
        entity = attributeCategoryManager.create(entity);
        return entity.getId();
    }

    public void update(AttributeCategoryRequestDto dto) {
        attributeCategoryManager.update(dtoMapper.mapToEntity(dto));
    }

    public void delete(Long id) {
        attributeCategoryManager.delete(id);
    }
}
