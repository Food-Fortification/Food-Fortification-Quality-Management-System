package com.beehyv.fortification.controller;

import com.beehyv.fortification.dto.requestDto.RoleCategoryRequestDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.RoleCategoryResponseDto;
import com.beehyv.fortification.service.RoleCategoryService;
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

public class RoleCategoryControllerTest {

    @InjectMocks
    private RoleCategoryController roleCategoryController;

    @Mock
    private RoleCategoryService roleCategoryService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateRoleCategory() {
        RoleCategoryRequestDto dto = new RoleCategoryRequestDto();
        doNothing().when(roleCategoryService).createRoleCategory(dto);
        ResponseEntity<?> response = roleCategoryController.createRoleCategory(dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testGetRoleCategoryById() {
        Long id = 1L;
        RoleCategoryResponseDto dto = new RoleCategoryResponseDto();
        when(roleCategoryService.getRoleCategoryById(id)).thenReturn(dto);
        ResponseEntity<RoleCategoryResponseDto> response = roleCategoryController.getRoleCategoryById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    public void testGetAllRoleCategories() {
        Integer pageNumber = 1;
        Integer pageSize = 10;
        ListResponse<RoleCategoryResponseDto> listResponse = new ListResponse<>(0L, Collections.emptyList());
        when(roleCategoryService.getAllRoleCategories(pageNumber, pageSize)).thenReturn(listResponse);
        ResponseEntity<ListResponse<RoleCategoryResponseDto>> response = roleCategoryController.getAllRoleCategories(pageNumber, pageSize);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listResponse, response.getBody());
    }

    @Test
    public void testUpdateRoleCategory() {
        Long id = 1L;
        RoleCategoryRequestDto dto = new RoleCategoryRequestDto();
        dto.setId(id);
        doNothing().when(roleCategoryService).updateRoleCategory(dto);
        ResponseEntity<?> response = roleCategoryController.updateRoleCategory(id, dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteRoleCategory() {
        Long id = 1L;
        doNothing().when(roleCategoryService).deleteRoleCategory(id);
        ResponseEntity<String> response = roleCategoryController.deleteRoleCategory(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("RoleCategory successfully deleted!", response.getBody());
    }
}