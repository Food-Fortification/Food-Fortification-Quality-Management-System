package com.beehyv.fortification.controller;

import com.beehyv.fortification.dto.requestDto.RoleCategoryRequestDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.RoleCategoryResponseDto;
import com.beehyv.fortification.service.RoleCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Tag(name = "Role Category Controller")
@RequestMapping("/role/category")
public class RoleCategoryController {

    private RoleCategoryService service;

    @PostMapping
    public ResponseEntity<?> createRoleCategory(@Valid @RequestBody RoleCategoryRequestDto dto) {
        service.createRoleCategory(dto);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<RoleCategoryResponseDto> getRoleCategoryById(@PathVariable("id") Long id) {
        RoleCategoryResponseDto dto = service.getRoleCategoryById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ListResponse<RoleCategoryResponseDto>> getAllRoleCategories(@RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize) {
        ListResponse<RoleCategoryResponseDto> roleCategories = service.getAllRoleCategories(pageNumber, pageSize);
        return new ResponseEntity<>(roleCategories, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateRoleCategory(@PathVariable("id") Long id,
                                                                     @RequestBody @Valid RoleCategoryRequestDto dto) {
        dto.setId(id);
        service.updateRoleCategory(dto);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRoleCategory(@PathVariable("id") Long id) {
        service.deleteRoleCategory(id);
        return new ResponseEntity<>("RoleCategory successfully deleted!", HttpStatus.OK);
    }

}
