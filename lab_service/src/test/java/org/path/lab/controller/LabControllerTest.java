package org.path.lab.controller;

import org.path.lab.dto.requestDto.LabRequestDTO;
import org.path.lab.dto.responseDto.CategoryRoleResponseDto;
import org.path.lab.dto.responseDto.LabResponseDTO;
import org.path.lab.service.LabService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LabControllerTest {

    @Mock
    private LabService labService;

    @InjectMocks
    private LabController labController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetLabById() {
        Long labId = 1L;
        LabResponseDTO expectedResponse = new LabResponseDTO();

        when(labService.getLabById(labId)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = labController.getLabById(labId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(labService, times(1)).getLabById(labId);
        verifyNoMoreInteractions(labService);
    }

    @Test
    void testAddLab() {
        LabRequestDTO dto = new LabRequestDTO();

        when(labService.createLab(dto)).thenReturn(1L);

        ResponseEntity<Long> responseEntity = labController.addLab(dto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(1L, responseEntity.getBody());

        verify(labService, times(1)).createLab(dto);
        verifyNoMoreInteractions(labService);
    }

    @Test
    void testUpdateLabById() {
        Long labId = 1L;
        LabRequestDTO dto = new LabRequestDTO();

        doNothing().when(labService).updateLabById(labId, dto);

        ResponseEntity<String> responseEntity = labController.updateLabById(labId, dto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("SUCCESS", responseEntity.getBody());

        verify(labService, times(1)).updateLabById(labId, dto);
        verifyNoMoreInteractions(labService);
    }

    @Test
    void testDeleteLabById() {
        Long labId = 1L;

        doNothing().when(labService).deleteLabById(labId);

        ResponseEntity<String> responseEntity = labController.deleteLabById(labId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("SUCCESS", responseEntity.getBody());

        verify(labService, times(1)).deleteLabById(labId);
        verifyNoMoreInteractions(labService);
    }

    @Test
    void testGetNearestLab() {
        String address = "test";
        LabResponseDTO expectedResponse = new LabResponseDTO();

        when(labService.getNearestLab(address, null, null)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = labController.getNearestLab(address);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(labService, times(1)).getNearestLab(address, null, null);
        verifyNoMoreInteractions(labService);
    }

    @Test
    void testGetAllRoleCategoriesForLab() {
        Long labId = 1L;
        Map<String, CategoryRoleResponseDto> expectedResponse = Map.of();

        when(labService.getAllRoleCategoriesForLab(labId)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = labController.getAllRoleCategoriesForLab(labId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(labService, times(1)).getAllRoleCategoriesForLab(labId);
        verifyNoMoreInteractions(labService);
    }

}