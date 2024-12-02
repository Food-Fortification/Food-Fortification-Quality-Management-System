package org.path.iam.mapper;

import org.path.iam.dto.requestDto.ManufacturerPropertyRequestDto;
import org.path.iam.dto.responseDto.ManufacturerPropertyResponseDto;
import org.path.iam.model.Manufacturer;
import org.path.iam.model.ManufacturerProperty;

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
