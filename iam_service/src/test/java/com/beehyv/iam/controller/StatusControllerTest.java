package com.beehyv.iam.controller;

import com.beehyv.iam.dto.requestDto.StatusRequestDto;
import com.beehyv.iam.dto.responseDto.ListResponse;
import com.beehyv.iam.dto.responseDto.StatusResponseDto;
import com.beehyv.iam.service.StatusService;
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
        StatusResponseDto rr = new StatusResponseDto();
        when(statusService.getById(1L)).thenReturn(rr);

        ResponseEntity<?> response = statusController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCreate() {
        when(statusService.create(any(StatusRequestDto.class))).thenReturn(1L);

        ResponseEntity<?> response = statusController.create(new StatusRequestDto());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testUpdate() {
        ResponseEntity<?> response = statusController.update(1L, new StatusRequestDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully Updated", response.getBody());
    }

    @Test
    void testDelete() {
        ResponseEntity<?> response = statusController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(statusService, times(1)).delete(1L);
    }

    @Test
    void testGetAllStatus() {
        ListResponse<StatusResponseDto> liststatus = new ListResponse<>();
        when(statusService.findAll(null, null)).thenReturn(liststatus);

        ResponseEntity<?> response = statusController.getAllStatus(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
