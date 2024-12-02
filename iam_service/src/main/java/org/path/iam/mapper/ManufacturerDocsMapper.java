package org.path.iam.mapper;

import org.path.iam.dto.requestDto.CategoryDocRequestDto;
import org.path.iam.dto.requestDto.ManufacturerDocsRequestDto;
import org.path.iam.dto.responseDto.CategoryDocResponseDto;
import org.path.iam.dto.responseDto.ManufacturerDocsResponseDto;
import org.path.iam.model.CategoryDoc;
import org.path.iam.model.Manufacturer;
import org.path.iam.model.ManufacturerDoc;
import org.path.parent.utils.FileValidator;

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
