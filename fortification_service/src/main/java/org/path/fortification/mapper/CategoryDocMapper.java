package org.path.fortification.mapper;

import org.path.fortification.entity.Category;
import org.path.fortification.entity.CategoryDoc;
import org.path.fortification.entity.DocType;
import org.path.fortification.dto.requestDto.CategoryDocRequestDto;
import org.path.fortification.dto.responseDto.CategoryDocResponseDto;

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
