package com.beehyv.fortification.mapper;

import com.beehyv.fortification.dto.requestDto.BatchPropertyRequestDto;
import com.beehyv.fortification.entity.Batch;
import com.beehyv.fortification.entity.BatchProperty;
import com.beehyv.fortification.dto.responseDto.BatchPropertyResponseDto;

public class BatchPropertyMapper implements Mappable<BatchPropertyResponseDto, BatchPropertyRequestDto, BatchProperty> {
    // Convert User JPA Entity into BatchPropertiesDto
    @Override
    public BatchPropertyResponseDto toDto(BatchProperty entity) {
        BatchPropertyResponseDto dto = new BatchPropertyResponseDto();
        dto.setId(entity.getId());
//        if (entity.getBatch() != null) dto.setBatchId(entity.getBatch().getId());
        dto.setName(entity.getName());
        dto.setValue(entity.getValue());
        return dto;
    }

    // Convert BatchPropertiesDto into BatchProperties JPA Entity
    @Override
    public BatchProperty toEntity(BatchPropertyRequestDto dto) {
        BatchProperty entity = new BatchProperty();
        if(dto.getBatchId() != null) {
            entity.setBatch(new Batch(dto.getBatchId()));
        }
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setValue(dto.getValue());
        return entity;
    }
}
