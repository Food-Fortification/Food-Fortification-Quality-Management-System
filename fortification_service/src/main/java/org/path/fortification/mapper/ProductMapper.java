package org.path.fortification.mapper;

import org.path.fortification.dto.requestDto.ProductRequestDto;
import org.path.fortification.entity.Product;
import org.path.fortification.dto.responseDto.ProductResponseDto;

public class ProductMapper implements Mappable<ProductResponseDto, ProductRequestDto, Product> {
    // Convert User JPA Entity into ProductDto
    @Override
    public ProductResponseDto toDto(Product entity) {
        ProductResponseDto dto = new ProductResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
        return dto;
    }

    // Convert ProductDto into Product JPA Entity
    @Override
    public Product toEntity(ProductRequestDto dto) {
        return new Product(
                dto.getId(),
                dto.getName(),
                dto.getDescription()
        );
    }
}
