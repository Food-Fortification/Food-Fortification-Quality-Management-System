package com.beehyv.fortification.controller;


import com.beehyv.fortification.dto.responseDto.MenuLabResponseDto;
import com.beehyv.fortification.dto.responseDto.MenuResponseDto;
import com.beehyv.fortification.service.RoleCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
@Tag(name = "Menu Controller")
@RequestMapping("/menu")
public class MenuController {
    private RoleCategoryService service;

    @GetMapping("module")
    public ResponseEntity<List<MenuResponseDto>> getMenu() {
        List<MenuResponseDto> roleCategories = service.getAllMenuRoleCategories();
        return ResponseEntity.ok(roleCategories);
    }

    @GetMapping("lab")
    public ResponseEntity<List<MenuLabResponseDto>> getLabMenu() {
        List<MenuLabResponseDto> roleCategories = service.getMenuForLab();
        return ResponseEntity.ok(roleCategories);
    }
}
