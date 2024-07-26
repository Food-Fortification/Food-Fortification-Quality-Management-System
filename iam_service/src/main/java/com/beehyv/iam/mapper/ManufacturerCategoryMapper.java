package com.beehyv.iam.mapper;

import com.beehyv.iam.dto.requestDto.ManufacturerCategoryRequestDto;
import com.beehyv.iam.dto.requestDto.ManufacturerRequestDto;
import com.beehyv.iam.dto.responseDto.ManufacturerCategoryResponseDto;
import com.beehyv.iam.dto.responseDto.ManufacturerResponseDto;
import com.beehyv.iam.model.Manufacturer;
import com.beehyv.iam.model.ManufacturerCategory;

public class ManufacturerCategoryMapper implements Mappable<ManufacturerCategoryResponseDto, ManufacturerCategoryRequestDto, ManufacturerCategory>{
    private static final BaseMapper<ManufacturerResponseDto, ManufacturerRequestDto, Manufacturer> manufacturerMapper = BaseMapper.getForClass(ManufacturerMapper.class);
    @Override
    public ManufacturerCategoryResponseDto toDto(ManufacturerCategory entity) {
        ManufacturerCategoryResponseDto dto = new ManufacturerCategoryResponseDto();
        dto.setId(entity.getId());
        dto.setCanSkipRawMaterials(entity.getCanSkipRawMaterials());
        dto.setCategoryId(entity.getCategoryId());
        dto.setIsEnabled(entity.getIsEnabled());
        if (entity.getManufacturer()!=null)dto.setManufacturerDto(manufacturerMapper.toDto(entity.getManufacturer()));
        return dto;
    }

    @Override
    public ManufacturerCategory toEntity(ManufacturerCategoryRequestDto dto) {
        ManufacturerCategory entity = new ManufacturerCategory();
        if (dto.getId()!= null)entity.setId(dto.getId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setIsEnabled(dto.getIsEnabled() == null || dto.getIsEnabled());
        entity.setCanSkipRawMaterials(dto.getCanSkipRawMaterials() != null && dto.getCanSkipRawMaterials());
        if(dto.getManufacturerId() != null) entity.setManufacturer(new Manufacturer(dto.getManufacturerId()));
        return entity;
    }
}
