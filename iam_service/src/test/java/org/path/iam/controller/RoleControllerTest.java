package org.path.iam.controller;

import org.path.iam.dto.requestDto.AssignRoleRequestDto;
import org.path.iam.dto.requestDto.RemoveRoleRequestDto;
import org.path.iam.dto.responseDto.ListResponse;
import org.path.iam.dto.responseDto.RoleResponseDto;
import org.path.iam.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class RoleControllerTest {

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAssignRole() {
        List<Long> responseDto = new ArrayList<>();
        when(roleService.assignRole(any(AssignRoleRequestDto.class))).thenReturn(responseDto);

        ResponseEntity<?> response = roleController.assignRole(new AssignRoleRequestDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testRemoveRole() {
        doNothing().when(roleService).removeRole(any(RemoveRoleRequestDto.class));

        ResponseEntity<?> response = roleController.removeRole(new RemoveRoleRequestDto("QQ", 1L));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully Removed Role", response.getBody());
    }

    @Test
    void testGetRoles() {
        ListResponse<RoleResponseDto> responseDto = new ListResponse<>();
        when(roleService.getRoles(anyInt(), anyInt())).thenReturn(responseDto);

        ResponseEntity<?> response = roleController.getRoles(1, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testAssignLabRole() {
        doNothing().when(roleService).assignLabRole(anyLong(), anyString(), anyLong());

        ResponseEntity<?> response = roleController.assignLabRole(1L, "ROLE_CATEGORY", 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
