package com.beehyv.iam.mapper;

import com.beehyv.iam.dto.requestDto.ManufacturerPropertyRequestDto;
import com.beehyv.iam.dto.responseDto.ManufacturerPropertyResponseDto;
import com.beehyv.iam.model.Manufacturer;
import com.beehyv.iam.model.ManufacturerProperty;

public class ManufacturerPropertyMapper implements Mappable<ManufacturerPropertyResponseDto, ManufacturerPropertyRequestDto, ManufacturerProperty> {
    @Override
    public ManufacturerPropertyResponseDto toDto(ManufacturerProperty entity) {
        ManufacturerPropertyResponseDto dto = new ManufacturerPropertyResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setValue(entity.getValue());
        return dto;
    }

    @Override
    public ManufacturerProperty toEntity(ManufacturerPropertyRequestDto dto) {
        ManufacturerProperty entity = new ManufacturerProperty();
        if(dto.getManufacturerId() != null) {
            entity.setManufacturer(new Manufacturer(dto.getManufacturerId()));
        }
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setValue(dto.getValue());
        return entity;
    }
}
