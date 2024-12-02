package org.path.fortification.mapper;

import org.path.fortification.dto.requestDto.CategoryDocRequestDto;
import org.path.fortification.dto.requestDto.CategoryRequestDto;
import org.path.fortification.dto.requestDto.ProductRequestDto;
import org.path.fortification.dto.responseDto.CategoryDocResponseDto;
import org.path.fortification.dto.responseDto.ProductResponseDto;
import org.path.fortification.entity.Category;
import org.path.fortification.entity.CategoryDoc;
import org.path.fortification.entity.Product;
import org.path.fortification.dto.responseDto.CategoryResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.stream.Collectors;

@Slf4j
public class CategoryMapper implements Mappable<CategoryResponseDto, CategoryRequestDto, Category> {
    private final BaseMapper<ProductResponseDto, ProductRequestDto, Product> productMapper = BaseMapper.getForClass(ProductMapper.class);
    private final BaseMapper<CategoryDocResponseDto, CategoryDocRequestDto, CategoryDoc> categoryDocMapper = BaseMapper.getForClass(CategoryDocMapper.class);
    // Convert Category JPA Entity into CategoryDto
    public CategoryResponseDto toDto(Category entity) {
        return this.toDto(entity, true);
    }

    public CategoryResponseDto toDto(Category entity, boolean withSources) {
        CategoryResponseDto dto = new CategoryResponseDto();
        BeanUtils.copyProperties(entity, dto);
        if(entity.getProduct() != null) dto.setProduct(productMapper.toDto(entity.getProduct()));
        if(withSources && entity.getSourceCategories() != null) {
            dto.setSourceCategories(entity.getSourceCategories()
                    .stream()
                    .map(c -> this.toDto(c, false)).collect(Collectors.toSet()));
        }
        if(entity.getDocuments() != null) {
            dto.setDocuments(entity.getDocuments().stream().map(categoryDocMapper::toDto).collect(Collectors.toSet()));
        }
        return dto;
    }

    // Convert CategoryDto into Category JPA Entity
    public Category toEntity(CategoryRequestDto dto) {
        Category entity = new Category();
        BeanUtils.copyProperties(dto, entity);
        if(dto.getProductId() != null) entity.setProduct(new Product(dto.getProductId()));
        if(dto.getSourceCategoryIds() != null) {
            entity.setSourceCategories(dto.getSourceCategoryIds().stream()
                    .map(Category::new).collect(Collectors.toSet()));
        }
        if(dto.getDocuments() != null) {
            entity.setDocuments(dto.getDocuments().stream()
                    .map(categoryDocMapper::toEntity)
                            .peek(d -> d.setCategory(entity))
                    .collect(Collectors.toSet()));
        }
        return entity;
    }
}
