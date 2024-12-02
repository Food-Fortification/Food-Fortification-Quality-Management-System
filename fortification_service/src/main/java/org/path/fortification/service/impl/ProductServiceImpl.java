package org.path.fortification.service.impl;

import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.manager.ProductManager;
import org.path.fortification.dto.requestDto.ProductRequestDto;
import org.path.fortification.entity.Product;
import org.path.parent.exceptions.ResourceAlreadyExistsException;
import org.path.fortification.mapper.BaseMapper;
import org.path.fortification.mapper.ProductMapper;
import org.path.fortification.dto.responseDto.ProductResponseDto;
import org.path.fortification.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final BaseMapper<ProductResponseDto, ProductRequestDto, Product> mapper = BaseMapper.getForClass(ProductMapper.class);
    private ProductManager manager;

    @Override
    public void createProduct(ProductRequestDto dto) {
        Optional<Product> optionalUser = manager.findByName(dto.getName());
        if (optionalUser.isPresent())
            throw new ResourceAlreadyExistsException("Product Already Exists for Name");
        Product entity = mapper.toEntity(dto);
        manager.create(entity);
    }

    @Override
    public ProductResponseDto getProductById(Long productId) {
        Product product = manager.findById(productId);
        return mapper.toDto(product);
    }

    @Override
    public ListResponse<ProductResponseDto> getAllProducts(Integer pageNumber, Integer pageSize) {
        List<Product> entities = manager.findAll(pageNumber, pageSize);
        Long count = manager.getCount(entities.size(), pageNumber, pageSize);
        return ListResponse.from(entities, mapper::toDto, count);
    }

    @Override
    public void updateProduct(ProductRequestDto productDto) {
        Product existingProduct = manager.findById(productDto.getId());
        existingProduct.setName(productDto.getName());
        existingProduct.setDescription(productDto.getDescription());
        manager.update(existingProduct);
    }

    @Override
    public void deleteProduct(Long productId) {
        manager.delete(productId);
    }
}
