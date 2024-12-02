package org.path.fortification.controller;

import org.path.fortification.dto.requestDto.CategoryRequestDto;
import org.path.fortification.dto.responseDto.CategoryResponseDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Tag(name = "Category Controller")
@RequestMapping("/category")
public class CategoryController {

    private CategoryService service;

    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequestDto dto) {
        service.createCategory(dto);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @GetMapping("{id}/batch/requirements")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable("id") Long id) {
        CategoryResponseDto dto = service.getCategoryById(id);
        dto.getDocuments().removeIf(c -> c.getIsEnabled().equals(false));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ListResponse<CategoryResponseDto>> getAllCategories(@RequestParam(required = false) Integer pageNumber,
                                                                              @RequestParam(required = false) Integer pageSize,
                                                                              @RequestParam(required = false) Boolean independentBatch) {
        ListResponse<CategoryResponseDto> categories = service.getAllCategories(independentBatch, pageNumber, pageSize);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/manufacturer-all")
    public ResponseEntity<?> getAllCategoriesForManufacturer() {
        return ResponseEntity.ok(service.getAllCategoriesForManufacturer(false));
    }

    @GetMapping("/role-categories")
    public ResponseEntity<?> getAllCategoryRolesForManufacturer() {
        return ResponseEntity.ok(service.getAllCategoryRolesForManufacturer());
    }

    @PostMapping("/manufacturer/role-categories")
    public ResponseEntity<?> getAllCategoryRolesForManufacturer(@RequestBody Map<String, List<Long>> roleTypeList) {
        return ResponseEntity.ok(service.getAllCategoryRolesForManufacturer(roleTypeList));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Long id,
                                            @RequestBody @Valid CategoryRequestDto dto) {
        dto.setId(id);
        service.updateCategory(dto);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id) {
        service.deleteCategory(id);
        return new ResponseEntity<>("Category successfully deleted!", HttpStatus.OK);
    }

    @GetMapping("{id}/next")
    public ResponseEntity<?> getNextCategoryIdById(@PathVariable("id") Long id, @RequestParam(required = false) Long stateGeoId) {
        List<Long> targetCategoryIds = service.getNextCategoryIdById(id, stateGeoId);
        return new ResponseEntity<>(targetCategoryIds, HttpStatus.OK);
    }

    @GetMapping("{id}/next/action")
    public ResponseEntity<?>getNextCategoryActionById(@PathVariable("id") Long id, @RequestParam(required = false) Long stateGeoId){
        Map<Long, String> tcIdsAndAction = service.getNextCategoryIdsAndActions(id,stateGeoId);
        return new ResponseEntity<>(tcIdsAndAction,HttpStatus.OK);
    }

    @GetMapping("/categoryName/{categoryName}")
    public ResponseEntity<?> getCategoryIdByName(@PathVariable String categoryName){
        return new ResponseEntity<>(service.getCategoryIdByName(categoryName),HttpStatus.OK);
    }

}
