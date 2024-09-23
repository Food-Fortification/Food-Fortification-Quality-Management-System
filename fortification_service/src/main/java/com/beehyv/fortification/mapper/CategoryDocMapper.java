package com.beehyv.fortification.mapper;

import com.beehyv.fortification.entity.*;
import com.beehyv.fortification.dto.requestDto.CategoryDocRequestDto;
import com.beehyv.fortification.dto.responseDto.CategoryDocResponseDto;

public class CategoryDocMapper implements Mappable<CategoryDocResponseDto, CategoryDocRequestDto, CategoryDoc> {
    @Override
    public CategoryDocResponseDto toDto(CategoryDoc entity) {
        CategoryDocResponseDto dto = new CategoryDocResponseDto();
        dto.setId(entity.getId());
        dto.setIsEnabled(entity.getIsEnabled());
        dto.setCategoryId(entity.getCategory().getId());
        dto.setDocType(entity.getDocType());
        if(entity.getIsMandatory()!=null) dto.setIsMandatory(entity.getIsMandatory());
        return dto;
    }

    @Override
    public CategoryDoc toEntity(CategoryDocRequestDto dto) {
        Category category = new Category();
        category.setId(dto.getCategoryId());
        DocType docType = new DocType();
        docType.setId(dto.getDocTypeId());
        Boolean isEnabled = (dto.getIsEnabled() == null) ? true : dto.getIsEnabled();


        return new CategoryDoc(
                dto.getId(),
                category,
                docType,
                dto.getIsMandatory(),
                isEnabled
        );
    }

}
