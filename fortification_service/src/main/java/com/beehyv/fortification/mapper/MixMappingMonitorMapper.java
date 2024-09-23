package com.beehyv.fortification.mapper;

import com.beehyv.fortification.dto.requestDto.MixMappingRequestDto;
import com.beehyv.fortification.dto.requestDto.UOMRequestDto;
import com.beehyv.fortification.dto.responseDto.*;
import com.beehyv.fortification.entity.MixMapping;
import com.beehyv.fortification.entity.UOM;


public class MixMappingMonitorMapper implements ListMappable<MixMappingMonitorResponseDto, MixMappingRequestDto, MixMapping>{
    private final BaseMapper<UOMResponseDto, UOMRequestDto, UOM> uomMapper = BaseMapper.getForClass(UOMMapper.class);

    @Override
    public MixMappingMonitorResponseDto toListDto(MixMapping entity) {
        MixMappingMonitorResponseDto dto = new MixMappingMonitorResponseDto();
        dto.setId(entity.getId());
        dto.setQuantityUsed(entity.getQuantityUsed());
        dto.setUom(uomMapper.toDto(entity.getUom()));
        return dto;
    }
}
