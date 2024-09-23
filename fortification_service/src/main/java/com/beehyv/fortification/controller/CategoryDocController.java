package com.beehyv.fortification.controller;

import com.beehyv.fortification.dto.requestDto.CategoryDocRequestDto;
import com.beehyv.fortification.dto.responseDto.CategoryDocResponseDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.service.CategoryDocService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Tag(name = "Category Doc Controller")
@RequestMapping("category")
public class CategoryDocController {

    private CategoryDocService service;

    @GetMapping("/{categoryId}/document")
    public ResponseEntity<ListResponse<CategoryDocResponseDto>> getRequiredDocsById(@PathVariable("categoryId") Long categoryId, @RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize) {
        ListResponse<CategoryDocResponseDto> dto = service.getRequiredDocByCategoryId(categoryId, pageNumber, pageSize);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/{categoryId}/document")
    public ResponseEntity<?> createCategoryDoc(@Valid @RequestBody CategoryDocRequestDto dto) {
        service.createCategoryDoc(dto);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @GetMapping("/{categoryId}/document/{id}")
    public ResponseEntity<CategoryDocResponseDto> getCategoryDocById(@PathVariable("id") Long id) {
        CategoryDocResponseDto dto = service.getCategoryDocById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/document")
    public ResponseEntity<ListResponse<CategoryDocResponseDto>> getAllCategoryDocs(@RequestParam(required = false) Integer pageNumber,@RequestParam(required = false) Integer pageSize) {
        ListResponse<CategoryDocResponseDto> categoryDocs = service.getAllCategoryDocs(pageNumber, pageSize);
        return new ResponseEntity<>(categoryDocs, HttpStatus.OK);
    }

    @PutMapping("/{categoryId}/document/{id}")
    public ResponseEntity<?> updateCategoryDoc(@PathVariable("id") Long id,
                                                    @RequestBody @Valid CategoryDocRequestDto dto) {
        dto.setId(id);
        service.updateCategoryDoc(dto);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}/document/{id}")
    public ResponseEntity<String> deleteCategoryDoc(@PathVariable("id") Long id) {
        service.deleteCategoryDoc(id);
        return new ResponseEntity<>("CategoryDoc successfully deleted!", HttpStatus.OK);
    }

}
