package org.path.fortification.mapper;

import org.path.fortification.dto.requestDto.MixMappingRequestDto;
import org.path.fortification.dto.requestDto.UOMRequestDto;
import org.path.fortification.dto.responseDto.MixMappingMonitorResponseDto;
import org.path.fortification.dto.responseDto.UOMResponseDto;
import org.path.fortification.entity.MixMapping;
import org.path.fortification.entity.UOM;


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
