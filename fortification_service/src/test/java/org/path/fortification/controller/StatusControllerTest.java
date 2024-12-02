package org.path.fortification.controller;

import org.path.fortification.dto.requestDto.StatusRequestDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.dto.responseDto.StatusResponseDto;
import org.path.fortification.service.StatusService;
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

public class StatusControllerTest {

    @InjectMocks
    private StatusController statusController;

    @Mock
    private StatusService statusService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateStatus() {
        StatusRequestDto dto = new StatusRequestDto();
        doNothing().when(statusService).createStatus(dto);
        ResponseEntity<?> response = statusController.createStatus(dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testGetStatusById() {
        Long id = 1L;
        StatusResponseDto dto = new StatusResponseDto();
        when(statusService.getStatusById(id)).thenReturn(dto);
        ResponseEntity<StatusResponseDto> response = statusController.getStatusById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    public void testGetAllStatuses() {
        Integer pageNumber = 1;
        Integer pageSize = 10;
        ListResponse<StatusResponseDto> listResponse = new ListResponse<>(0L, Collections.emptyList());
        when(statusService.getAllStatuses(pageNumber, pageSize)).thenReturn(listResponse);
        ResponseEntity<ListResponse<StatusResponseDto>> response = statusController.getAllStatuses(pageNumber, pageSize);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listResponse, response.getBody());
    }

    @Test
    public void testUpdateStatus() {
        Long id = 1L;
        StatusRequestDto dto = new StatusRequestDto();
        dto.setId(id);
        doNothing().when(statusService).updateStatus(dto);
        ResponseEntity<?> response = statusController.updateStatus(id, dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteStatus() {
        Long id = 1L;
        doNothing().when(statusService).deleteStatus(id);
        ResponseEntity<String> response = statusController.deleteStatus(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Status successfully deleted!", response.getBody());
    }
}