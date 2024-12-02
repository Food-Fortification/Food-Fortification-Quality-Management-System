package org.path.iam.mapper;

import org.path.iam.dto.requestDto.CategoryDocRequestDto;
import org.path.iam.dto.requestDto.DocTypeRequestDto;
import org.path.iam.dto.responseDto.CategoryDocResponseDto;
import org.path.iam.dto.responseDto.DocTypeResponseDto;
import org.path.iam.model.CategoryDoc;
import org.path.iam.model.DocType;

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
