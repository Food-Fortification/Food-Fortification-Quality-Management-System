package org.path.iam.controller;

import org.path.iam.dto.requestDto.AttributeCategoryRequestDto;
import org.path.iam.dto.responseDto.AttributeCategoryResponseDto;
import org.path.iam.dto.responseDto.ListResponse;
import org.path.iam.service.AttributeCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Tag(name = "Attribute Category Controller")
@RequestMapping("attribute-category")
@CrossOrigin(origins = {"*"})
public class AttributeCategoryController {

    private final AttributeCategoryService attributecategoryService;

    @GetMapping("/{id}")
    public ResponseEntity<AttributeCategoryResponseDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(attributecategoryService.getById(id));
    }
    @GetMapping
    public ResponseEntity<ListResponse<AttributeCategoryResponseDto>> getAll(@RequestParam(required = false) Integer pageNumber
            , @RequestParam(required = false) Integer pageSize){
        return ResponseEntity.ok(attributecategoryService.findAll(pageNumber,pageSize));
    }
    @PostMapping
    public ResponseEntity<Long> create(@Valid @RequestBody AttributeCategoryRequestDto dto){

        return new ResponseEntity<>(attributecategoryService.create(dto), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@Valid @RequestBody AttributeCategoryRequestDto dto){
        dto.setId(id);
        attributecategoryService.update(dto);
        return new ResponseEntity<>("Successfully Updated",HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        attributecategoryService.delete(id);
        return ResponseEntity.ok("Successfully Deleted");
    }
}
