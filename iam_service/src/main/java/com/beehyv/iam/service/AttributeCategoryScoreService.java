package com.beehyv.iam.service;

import com.beehyv.iam.dto.requestDto.AttributeCategoryScoreRequestDto;

import com.beehyv.iam.dto.responseDto.AttributeCategoryScoreResponseDto;

import com.beehyv.iam.manager.AttributeCategoryScoreManager;

import com.beehyv.iam.manager.ManufacturerCategoryAttributesManager;
import com.beehyv.iam.model.AttributeCategoryScore;
import com.beehyv.iam.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class AttributeCategoryScoreService {
    private final AttributeCategoryScoreManager attributeCategoryScoreManager;
    private final ManufacturerCategoryAttributesManager manufacturerCategoryAttributesManager;
    private final DtoMapper dtoMapper = Mappers.getMapper(DtoMapper.class);

    public AttributeCategoryScoreResponseDto getById(Long id) {
        return dtoMapper.mapToDto(attributeCategoryScoreManager.findById(id));
    }



    public Long create(AttributeCategoryScoreRequestDto dto) {
        AttributeCategoryScore entity = dtoMapper.mapToEntity(dto);
        entity = attributeCategoryScoreManager.create(entity);
        return entity.getId();
    }

    public void update(AttributeCategoryScoreRequestDto dto) {
        attributeCategoryScoreManager.update(dtoMapper.mapToEntity(dto));
    }

    public void delete(Long id) {
        attributeCategoryScoreManager.delete(id);
    }
}
