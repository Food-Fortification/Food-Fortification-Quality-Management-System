package org.path.fortification.service;

import org.path.fortification.dto.requestDto.ProductRequestDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.dto.responseDto.ProductResponseDto;

public interface ProductService {
    void createProduct(ProductRequestDto productDto);

    ProductResponseDto getProductById(Long productId);

    ListResponse<ProductResponseDto> getAllProducts(Integer pageNumber, Integer pageSize);

    void updateProduct(ProductRequestDto productDto);

    void deleteProduct(Long productId);

}


