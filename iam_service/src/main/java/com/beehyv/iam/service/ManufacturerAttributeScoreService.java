package com.beehyv.iam.service;

import com.beehyv.iam.dto.requestDto.ManufacturerAttributeScoreRequestDto;

import com.beehyv.iam.dto.responseDto.ManufacturerAttributeScoreResponseDto;

import com.beehyv.iam.manager.ManufacturerAttributeScoreManager;
import com.beehyv.iam.model.ManufacturerAttributeScore;
import com.beehyv.iam.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ManufacturerAttributeScoreService {
    private final ManufacturerAttributeScoreManager manufacturerAttributeScoreManager;
    private final DtoMapper dtoMapper = Mappers.getMapper(DtoMapper.class);

    public ManufacturerAttributeScoreResponseDto getById(Long id) {
        ManufacturerAttributeScore m = manufacturerAttributeScoreManager.findById(id);
        return dtoMapper.mapToDto(manufacturerAttributeScoreManager.findById(id));
    }



    public Long create(ManufacturerAttributeScoreRequestDto dto) {
        ManufacturerAttributeScore entity = dtoMapper.mapToEntity(dto);
        entity = manufacturerAttributeScoreManager.create(entity);

        return entity.getId();
    }

    public void update(ManufacturerAttributeScoreRequestDto dto) {
        manufacturerAttributeScoreManager.update(dtoMapper.mapToEntity(dto));
    }

    public void delete(Long id) {
        manufacturerAttributeScoreManager.delete(id);
    }
}
