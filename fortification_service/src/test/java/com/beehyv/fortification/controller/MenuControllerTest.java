package com.beehyv.fortification.controller;

import com.beehyv.fortification.dto.responseDto.MenuLabResponseDto;
import com.beehyv.fortification.dto.responseDto.MenuResponseDto;
import com.beehyv.fortification.service.RoleCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MenuControllerTest {

    @InjectMocks
    private MenuController menuController;

    @Mock
    private RoleCategoryService roleCategoryService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMenu() {
        when(roleCategoryService.getAllMenuRoleCategories()).thenReturn(Collections.emptyList());
        ResponseEntity<List<MenuResponseDto>> response = menuController.getMenu();
        assertEquals(ResponseEntity.ok(Collections.emptyList()), response);
    }

    @Test
    public void testGetLabMenu() {
        when(roleCategoryService.getMenuForLab()).thenReturn(Collections.emptyList());
        ResponseEntity<List<MenuLabResponseDto>> response = menuController.getLabMenu();
        assertEquals(ResponseEntity.ok(Collections.emptyList()), response);
    }
}