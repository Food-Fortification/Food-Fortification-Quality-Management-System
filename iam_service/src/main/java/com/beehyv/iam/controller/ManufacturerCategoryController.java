package com.beehyv.iam.controller;

import com.beehyv.iam.dto.requestDto.ManufacturerCategoryRequestDto;
import com.beehyv.iam.service.ManufacturerCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/manufacturer-category")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class ManufacturerCategoryController {
    private final ManufacturerCategoryService manufacturerCategoryService;
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ManufacturerCategoryRequestDto manufacturerCategoryDto){
        manufacturerCategoryService.create(manufacturerCategoryDto);
        return new ResponseEntity<>("Successfully Created" ,HttpStatus.CREATED);
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ResponseEntity.ok(manufacturerCategoryService.getById(id));
    }
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@Valid @RequestBody ManufacturerCategoryRequestDto  manufacturerCategoryRequestDto){
        manufacturerCategoryRequestDto.setId(id);
        manufacturerCategoryService.update(manufacturerCategoryRequestDto);
        return new ResponseEntity<>("Successfully updated",HttpStatus.OK);
    }
    @DeleteMapping(path ="/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        manufacturerCategoryService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<?> getAllManufacturerCategories(@RequestParam(required = false) Integer pageNumber,
                                                          @RequestParam(required = false) Integer pageSize){
        return ResponseEntity.ok(manufacturerCategoryService.getAll(pageNumber,pageSize));
    }
    @GetMapping(path = "/categories")
    public ResponseEntity<?> getCategoriesForManufacturer(){
        return ResponseEntity.ok(manufacturerCategoryService.getCategoriesForManufacturer(null));
    }
    @GetMapping("/can-skip-raw-materials")
    public ResponseEntity<Boolean> getCanSkipRawMaterialsForManufacturerAndCategory(@RequestParam Long manufacturerId,
                                                                                    @RequestParam Long categoryId){
        return ResponseEntity.ok(manufacturerCategoryService.getCanSkipRawMaterialsForManufacturerAndCategory(manufacturerId, categoryId));
    }

    @GetMapping("/action-name")
    public ResponseEntity<?> getActionNameByManufacturerIdAndCategoryId(@RequestParam Long manufacturerId,
                                                                        @RequestParam Long categoryId){
        return ResponseEntity.ok(manufacturerCategoryService.getActionNameByManufacturerIdAndCategoryId(manufacturerId, categoryId));
    }

}
