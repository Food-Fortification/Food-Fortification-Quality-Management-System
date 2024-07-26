package com.beehyv.iam.mapper;

import com.beehyv.iam.dto.requestDto.CategoryDocRequestDto;
import com.beehyv.iam.dto.requestDto.DocTypeRequestDto;
import com.beehyv.iam.dto.responseDto.CategoryDocResponseDto;
import com.beehyv.iam.dto.responseDto.DocTypeResponseDto;
import com.beehyv.iam.model.CategoryDoc;
import com.beehyv.iam.model.DocType;

public class CategoryDocMapper implements Mappable<CategoryDocResponseDto, CategoryDocRequestDto, CategoryDoc> {
    private static final BaseMapper<DocTypeResponseDto, DocTypeRequestDto,DocType> docTypeMapper = BaseMapper.getForClass(DocTypeMapper.class);
    @Override
    public CategoryDocResponseDto toDto(CategoryDoc entity) {
        CategoryDocResponseDto dto = new CategoryDocResponseDto();
        dto.setId(entity.getId());
        dto.setIsEnabled(entity.getIsEnabled());
        dto.setCategoryId(entity.getCategoryId());
        if (entity.getDocType()!=null) dto.setDocType(docTypeMapper.toDto(entity.getDocType()));
        dto.setIsMandatory(entity.getIsMandatory());
        return dto;
    }

    @Override
    public CategoryDoc toEntity(CategoryDocRequestDto dto) {
        CategoryDoc entity = new CategoryDoc();
        if (dto.getId()!=null) entity.setId(dto.getId());
        entity.setIsEnabled(dto.getIsEnabled() == null || dto.getIsEnabled());
        entity.setCategoryId(dto.getCategoryId());
        entity.setDocType(new DocType(dto.getDocTypeId()));
        entity.setIsMandatory(dto.getIsMandatory());
        return entity;
    }
}
