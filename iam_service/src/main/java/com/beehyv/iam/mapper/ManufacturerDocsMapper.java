package com.beehyv.iam.mapper;

import com.beehyv.iam.dto.requestDto.CategoryDocRequestDto;
import com.beehyv.iam.dto.requestDto.ManufacturerDocsRequestDto;
import com.beehyv.iam.dto.responseDto.CategoryDocResponseDto;
import com.beehyv.iam.dto.responseDto.ManufacturerDocsResponseDto;
import com.beehyv.iam.model.CategoryDoc;
import com.beehyv.iam.model.Manufacturer;
import com.beehyv.iam.model.ManufacturerDoc;
import com.beehyv.parent.utils.FileValidator;

public class ManufacturerDocsMapper implements Mappable<ManufacturerDocsResponseDto,ManufacturerDocsRequestDto,ManufacturerDoc>{

    private static final BaseMapper<CategoryDocResponseDto, CategoryDocRequestDto,CategoryDoc> categoryDocMapper = BaseMapper.getForClass(CategoryDocMapper.class);
    @Override
    public ManufacturerDocsResponseDto toDto(ManufacturerDoc entity) {
        ManufacturerDocsResponseDto dto = new ManufacturerDocsResponseDto();
        dto.setId(entity.getId());
        dto.setDocName(entity.getDocName());
        dto.setDocPath(entity.getDocPath());
        dto.setDocExpiry(entity.getDocExpiry());
        if(entity.getCategoryDoc()!=null) dto.setCategoryDoc(categoryDocMapper.toDto(entity.getCategoryDoc()));
        return dto;
    }

    @Override
    public ManufacturerDoc toEntity(ManufacturerDocsRequestDto dto) {
        ManufacturerDoc entity = new ManufacturerDoc();
        if (dto.getId()!=null)entity.setId(dto.getId());
        if (dto.getManufacturerId()!=null) entity.setManufacturer(new Manufacturer(dto.getManufacturerId()));
        if (dto.getCategoryDocId()!=null) entity.setCategoryDoc(new CategoryDoc(dto.getCategoryDocId()));
        entity.setDocName(dto.getDocName());
        FileValidator.validateFileUpload(dto.getDocPath());
        entity.setDocPath(dto.getDocPath());
        entity.setDocExpiry(dto.getDocExpiry());
        return entity;
    }
}
