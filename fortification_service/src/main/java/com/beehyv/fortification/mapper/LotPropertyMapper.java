package com.beehyv.fortification.mapper;

import com.beehyv.fortification.dto.requestDto.LotPropertyRequestDto;
import com.beehyv.fortification.dto.responseDto.LotPropertyResponseDto;
import com.beehyv.fortification.entity.Lot;
import com.beehyv.fortification.entity.LotProperty;

public class LotPropertyMapper implements Mappable<LotPropertyResponseDto, LotPropertyRequestDto, LotProperty> {
    // Convert User JPA Entity into BatchPropertiesDto
    @Override
    public LotPropertyResponseDto toDto(LotProperty entity) {
        LotPropertyResponseDto dto = new LotPropertyResponseDto();
        dto.setId(entity.getId());
//        if (entity.getBatch() != null) dto.setBatchId(entity.getBatch().getId());
        dto.setName(entity.getName());
        dto.setValue(entity.getValue());
        return dto;
    }

    // Convert BatchPropertiesDto into BatchProperties JPA Entity
    @Override
    public LotProperty toEntity(LotPropertyRequestDto dto) {
        LotProperty entity = new LotProperty();
        if(dto.getLotId() != null) {
            entity.setLot(new Lot(dto.getLotId()));
        }
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setValue(dto.getValue());
        return entity;
    }
}
