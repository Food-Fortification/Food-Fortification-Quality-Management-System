package com.beehyv.fortification.mapper;

import com.beehyv.fortification.dto.requestDto.UOMRequestDto;
import com.beehyv.fortification.entity.UOM;
import com.beehyv.fortification.dto.responseDto.UOMResponseDto;

public class UOMMapper implements Mappable<UOMResponseDto, UOMRequestDto, UOM> {
    // Convert UOM JPA Entity into UOMDto
    public UOMResponseDto toDto(UOM entity) {
        if(entity == null) return null;
        return new UOMResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getConversionFactorKg()
        );
    }

    // Convert UOMDto into UOM JPA Entity
    public UOM toEntity(UOMRequestDto dto) {
        return new UOM(
                dto.getId(),
                dto.getName(),
                dto.getConversionFactorKg()
        );
    }
}
