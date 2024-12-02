package org.path.iam.controller;

import org.path.iam.dto.requestDto.CategoryDocRequestDto;
import org.path.iam.dto.responseDto.CategoryDocResponseDto;
import org.path.iam.dto.responseDto.ListResponse;
import org.path.iam.service.CategoryDocService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Tag(name = "Category Doc Controller")
@RequestMapping("category-document")
@CrossOrigin(origins = {"*"})
public class CategoryDocController {

    private final CategoryDocService service;

    @GetMapping("/{categoryId}/documents")
    public ResponseEntity<ListResponse<CategoryDocResponseDto>> getRequiredDocsById(@PathVariable("categoryId") Long categoryId,
                                                                                    @RequestParam(required = false) Integer pageNumber,
                                                                                    @RequestParam(required = false) Integer pageSize) {
        ListResponse<CategoryDocResponseDto> dto = service.getRequiredDocByCategoryId(categoryId, pageNumber, pageSize);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createCategoryDoc(@Valid @RequestBody CategoryDocRequestDto dto) {
        service.createCategoryDoc(dto);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDocResponseDto> getById(@PathVariable("id") Long id) {
        CategoryDocResponseDto dto = service.getCategoryDocById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ListResponse<CategoryDocResponseDto>> getAllCategoryDocs(@RequestParam(required = false) Integer pageNumber,
                                                                                   @RequestParam(required = false) Integer pageSize) {
        ListResponse<CategoryDocResponseDto> categoryDocs = service.getAllCategoryDocs(pageNumber, pageSize);
        return new ResponseEntity<>(categoryDocs, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategoryDoc(@PathVariable("id") Long id,
                                               @RequestBody @Valid CategoryDocRequestDto dto) {
        dto.setId(id);
        service.update(dto);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoryDoc(@PathVariable("id") Long id) {
        service.deleteCategoryDoc(id);
        return new ResponseEntity<>("CategoryDoc successfully deleted!", HttpStatus.OK);
    }

}
