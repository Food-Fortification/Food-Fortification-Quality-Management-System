package org.path.lab.controller;

import org.path.lab.dto.requestDto.StatusRequestDTO;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.dto.responseDto.StatusResponseDto;
import org.path.lab.service.StatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StatusControllerTest {

    @Mock
    private StatusService statusService;

    @InjectMocks
    private StatusController statusController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById() {
        Long id = 1L;
        StatusResponseDto expectedResponse = new StatusResponseDto();

        when(statusService.getById(id)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = statusController.getById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(statusService, times(1)).getById(id);
        verifyNoMoreInteractions(statusService);
    }

    @Test
    void testCreate() {
        StatusRequestDTO statusRequestDTO = new StatusRequestDTO();

        when(statusService.create(statusRequestDTO)).thenReturn(1L);

        ResponseEntity<?> responseEntity = statusController.create(statusRequestDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(1L, responseEntity.getBody());

        verify(statusService, times(1)).create(statusRequestDTO);
        verifyNoMoreInteractions(statusService);
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        StatusRequestDTO statusRequestDTO = new StatusRequestDTO();

        doNothing().when(statusService).update(statusRequestDTO);

        ResponseEntity<?> responseEntity = statusController.update(id, statusRequestDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Successfully Updated", responseEntity.getBody());

        verify(statusService, times(1)).update(statusRequestDTO);
        verifyNoMoreInteractions(statusService);
    }

    @Test
    void testDelete() {
        Long id = 1L;

        doNothing().when(statusService).delete(id);

        ResponseEntity<?> responseEntity = statusController.delete(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(statusService, times(1)).delete(id);
        verifyNoMoreInteractions(statusService);
    }

    @Test
    void testGetAllStatus() {
        Integer pageNumber = 1;
        Integer pageSize = 10;
        ListResponse<StatusResponseDto> expectedResponse = new ListResponse<>();

        when(statusService.findAll(pageNumber, pageSize)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = statusController.getAllStatus(pageNumber, pageSize);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(statusService, times(1)).findAll(pageNumber, pageSize);
        verifyNoMoreInteractions(statusService);
    }
}