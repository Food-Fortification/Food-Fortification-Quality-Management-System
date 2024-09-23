package com.beehyv.fortification.service;

import com.beehyv.fortification.dto.requestDto.ProductRequestDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.ProductResponseDto;

import java.util.List;

public interface ProductService {
    void createProduct(ProductRequestDto productDto);

    ProductResponseDto getProductById(Long productId);

    ListResponse<ProductResponseDto> getAllProducts(Integer pageNumber, Integer pageSize);

    void updateProduct(ProductRequestDto productDto);

    void deleteProduct(Long productId);

}


