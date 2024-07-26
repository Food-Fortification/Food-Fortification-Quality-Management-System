package com.beehyv.fortification.mapper;

import com.beehyv.fortification.dto.requestDto.UOMRequestDto;
import com.beehyv.fortification.dto.requestDto.WastageRequestDto;
import com.beehyv.fortification.dto.responseDto.UOMResponseDto;
import com.beehyv.fortification.dto.responseDto.WastageResponseDto;
import com.beehyv.fortification.entity.Batch;
import com.beehyv.fortification.entity.Lot;
import com.beehyv.fortification.entity.UOM;
import com.beehyv.fortification.entity.Wastage;

public class WastageMapper implements Mappable<WastageResponseDto, WastageRequestDto, Wastage> {
    // Convert User JPA Entity into BatchWastageDto
    private final BaseMapper<UOMResponseDto, UOMRequestDto, UOM> uomMapper = BaseMapper.getForClass(UOMMapper.class);
    @Override
    public WastageResponseDto toDto(Wastage entity) {
        WastageResponseDto dto = new WastageResponseDto();
        dto.setId(entity.getId());
//        if (entity.getBatch() != null) dto.setBatchId(entity.getBatch().getId());
        dto.setComments(entity.getComments());

        dto.setReportedDate(entity.getReportedDate());
        dto.setWastageQuantity(entity.getWastageQuantity() * entity.getUom().getConversionFactorKg());
        if (entity.getUom() != null) dto.setUom(uomMapper.toDto(entity.getUom()));
        return dto;
    }

    // Convert BatchWastageDto into BatchWastage JPA Entity
    @Override
    public Wastage toEntity(WastageRequestDto dto) {
        Wastage entity = new Wastage();
        if(dto.getBatchId() != null) {
            entity.setBatch(new Batch(dto.getBatchId()));
        }
        if(dto.getLotId() != null) {
            entity.setLot(new Lot(dto.getLotId()));
        }
        if(dto.getUomId() != null) {
            entity.setUom(new UOM(dto.getUomId()));
        }
        entity.setWastageQuantity(dto.getWastageQuantity());
        entity.setComments(dto.getComments());
        entity.setReportedDate(dto.getReportedDate());
        return entity;
    }
}
