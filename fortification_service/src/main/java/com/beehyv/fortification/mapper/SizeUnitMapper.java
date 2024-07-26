package com.beehyv.fortification.mapper;

import com.beehyv.fortification.dto.requestDto.SizeUnitRequestDto;
import com.beehyv.fortification.dto.requestDto.UOMRequestDto;
import com.beehyv.fortification.dto.responseDto.SizeUnitResponseDto;
import com.beehyv.fortification.dto.responseDto.UOMResponseDto;
import com.beehyv.fortification.entity.Batch;
import com.beehyv.fortification.entity.Lot;
import com.beehyv.fortification.entity.SizeUnit;
import com.beehyv.fortification.entity.UOM;

public class SizeUnitMapper implements Mappable<SizeUnitResponseDto, SizeUnitRequestDto, SizeUnit> {
    private final BaseMapper<UOMResponseDto, UOMRequestDto, UOM> uomMapper = BaseMapper.getForClass(UOMMapper.class);
    // Convert SizeUnit JPA Entity into SizeUnitDto
    @Override
    public SizeUnitResponseDto toDto(SizeUnit entity) {
        if(entity == null) return null;
        SizeUnitResponseDto dto = new SizeUnitResponseDto();
        dto.setId(entity.getId());
        dto.setQuantity(entity.getQuantity());
        dto.setSize(entity.getSize());
        dto.setIsDispatched(entity.getIsDispatched());
//        if(entity.getBatch() != null) {
//            dto.setBatchId(entity.getBatch().getId());
//        }
        if(entity.getUom() != null) {
            dto.setUom(uomMapper.toDto(entity.getUom()));
        }
        return dto;
    }

    // Convert SizeUnitDto into SizeUnit JPA Entity
    @Override
    public SizeUnit toEntity(SizeUnitRequestDto dto) {
        SizeUnit entity = new SizeUnit();
        entity.setId(dto.getId());
        entity.setSize(dto.getSize());
        entity.setQuantity(dto.getQuantity());
        entity.setIsDispatched(dto.getIsDispatched() != null && dto.getIsDispatched());
        if(dto.getUomId() != null) entity.setUom(new UOM(dto.getUomId()));
        if (dto.getBatchId() != null) entity.setBatch(new Batch(dto.getBatchId()));
        if (dto.getLotId() != null) entity.setLot(new Lot(dto.getLotId()));
        return entity;
    }
}
