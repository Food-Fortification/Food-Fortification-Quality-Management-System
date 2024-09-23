package com.beehyv.fortification.controller;

import com.beehyv.fortification.dto.requestDto.ProductRequestDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.ProductResponseDto;
import com.beehyv.fortification.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Tag(name = "Product Controller")
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequestDto productDto){
        productService.createProduct(productDto);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("id") Long productId) {
        ProductResponseDto productDto = productService.getProductById(productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<ListResponse<ProductResponseDto>> getAllProducts(@RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize){
        ListResponse<ProductResponseDto> products = productService.getAllProducts(pageNumber, pageSize);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long productId,
                                                           @RequestBody @Valid ProductRequestDto product){
        product.setId(productId);
        productService.updateProduct(product);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long productId){
        productService.deleteProduct(productId);
        return new ResponseEntity<>("Product successfully deleted!", HttpStatus.OK);
    }

}
