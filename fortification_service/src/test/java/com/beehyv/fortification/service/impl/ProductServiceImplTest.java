package com.beehyv.fortification.service.impl;

import com.beehyv.fortification.dto.requestDto.ProductRequestDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.ProductResponseDto;
import com.beehyv.fortification.entity.Product;
import com.beehyv.fortification.manager.ProductManager;
import com.beehyv.parent.exceptions.ResourceAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ProductServiceImplTest {

    @Mock
    private ProductManager manager;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductRequestDto requestDto;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        requestDto = new ProductRequestDto();
        requestDto.setName("Product 1");
        requestDto.setDescription("This is product 1");

        product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setDescription("This is product 1");

        when(manager.findByName(requestDto.getName())).thenReturn(Optional.empty());
        when(manager.create(any(Product.class))).thenReturn(product);
        when(manager.findById(product.getId())).thenReturn(product);
    }

    @Test
    void createProduct_ValidRequest_ShouldCreateProduct() {
        productService.createProduct(requestDto);

        verify(manager, times(1)).create(any(Product.class));
    }

    @Test
    void createProduct_ProductAlreadyExists_ShouldThrowException() {
        when(manager.findByName(requestDto.getName())).thenReturn(Optional.of(product));

        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> {
            productService.createProduct(requestDto);
        });
    }

    @Test
    void getProductById_ValidId_ShouldReturnProductDto() {
        ProductResponseDto response = productService.getProductById(product.getId());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(product.getId(), response.getId());
        Assertions.assertEquals(product.getName(), response.getName());
        Assertions.assertEquals(product.getDescription(), response.getDescription());
    }

    @Test
    void getAllProducts_ValidRequest_ShouldReturnProductList() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1L);
        Product product2 = new Product();
        product2.setId(2L);
        products.add(product1);
        products.add(product2);

        when(manager.findAll(0, 10)).thenReturn(products);
        when(manager.getCount(products.size(), 0, 10)).thenReturn(2L);

        ListResponse<ProductResponseDto> response = productService.getAllProducts(0, 10);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(2, response.getCount());

    }

    @Test
    void updateProduct_ValidRequest_ShouldUpdateProduct() {
        requestDto.setId(product.getId());
        requestDto.setName("Updated Name");
        requestDto.setDescription("Updated description");

        productService.updateProduct(requestDto);

        Assertions.assertEquals("Updated Name", product.getName());
        Assertions.assertEquals("Updated description", product.getDescription());
        verify(manager, times(1)).update(product);
    }

    @Test
    void deleteProduct_ValidId_ShouldDeleteProduct() {
        productService.deleteProduct(product.getId());

        verify(manager, times(1)).delete(product.getId());
    }
}


// test includes-
//createProduct_ValidRequest_ShouldCreateProduct: This test verifies that the createProduct method creates a new Product entity correctly.
//createProduct_ProductAlreadyExists_ShouldThrowException: This test verifies that the createProduct method throws a ResourceAlreadyExistsException when a product with the same name already exists.
//getProductById_ValidId_ShouldReturnProductDto: This test verifies that the getProductById method returns the correct ProductResponseDto for a given Product ID.
//getAllProducts_ValidRequest_ShouldReturnProductList: This test verifies that the getAllProducts method returns a ListResponse containing the correct list of ProductResponseDto objects.
//updateProduct_ValidRequest_ShouldUpdateProduct: This test verifies that the updateProduct method updates an existing Product entity correctly with the provided ProductRequestDto.
//deleteProduct_ValidId_ShouldDeleteProduct: This test verifies that the deleteProduct method deletes an existing Product entity for the given ID.