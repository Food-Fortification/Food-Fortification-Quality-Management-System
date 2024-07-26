package com.beehyv.fortification.controller;

import com.beehyv.fortification.dto.requestDto.ProductRequestDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.ProductResponseDto;
import com.beehyv.fortification.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProduct() {
        ProductRequestDto dto = new ProductRequestDto();
        doNothing().when(productService).createProduct(dto);
        ResponseEntity<?> response = productController.createProduct(dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testGetProductById() {
        Long id = 1L;
        ProductResponseDto dto = new ProductResponseDto();
        when(productService.getProductById(id)).thenReturn(dto);
        ResponseEntity<ProductResponseDto> response = productController.getProductById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    public void testGetAllProducts() {
        Integer pageNumber = 1;
        Integer pageSize = 10;
        ListResponse<ProductResponseDto> listResponse = new ListResponse<>(0L, Collections.emptyList());
        when(productService.getAllProducts(pageNumber, pageSize)).thenReturn(listResponse);
        ResponseEntity<ListResponse<ProductResponseDto>> response = productController.getAllProducts(pageNumber, pageSize);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listResponse, response.getBody());
    }

    @Test
    public void testUpdateProduct() {
        Long id = 1L;
        ProductRequestDto dto = new ProductRequestDto();
        dto.setId(id);
        doNothing().when(productService).updateProduct(dto);
        ResponseEntity<?> response = productController.updateProduct(id, dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteProduct() {
        Long id = 1L;
        doNothing().when(productService).deleteProduct(id);
        ResponseEntity<String> response = productController.deleteProduct(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product successfully deleted!", response.getBody());
    }
}
