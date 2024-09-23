package com.beehyv.iam.controller;

import com.beehyv.iam.dto.requestDto.StateRequestDto;
import com.beehyv.iam.dto.responseDto.ListResponse;
import com.beehyv.iam.dto.responseDto.LocationResponseDto;
import com.beehyv.iam.dto.responseDto.StateResponseDto;
import com.beehyv.iam.service.StateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class StateControllerTest {

    @Mock
    private StateService stateService;

    @InjectMocks
    private StateController stateController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById() {
        StateResponseDto responseDto = new StateResponseDto();
        when(stateService.getById(anyLong())).thenReturn(responseDto);

        ResponseEntity<?> response = stateController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testCreate() {
        doNothing().when(stateService).create(any(StateRequestDto.class));

        ResponseEntity<?> response = stateController.create(new StateRequestDto());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Successfully Created", response.getBody());
    }

    @Test
    void testUpdate() {
        doNothing().when(stateService).update(any(StateRequestDto.class));

        StateRequestDto dto = new StateRequestDto();
        ResponseEntity<?> response = stateController.update(1L, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully Updated", response.getBody());
    }

    @Test
    void testDelete() {
        doNothing().when(stateService).delete(anyLong());

        ResponseEntity<?> response = stateController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAllStates() {
        ListResponse<StateResponseDto> responseDto = new ListResponse<>();
        when(stateService.findAll(anyInt(), anyInt())).thenReturn(responseDto);

        ResponseEntity<?> response = stateController.getAllStates(1, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testGetAllStatesByCountryId() {
        ListResponse<LocationResponseDto> responseDto = new ListResponse<>();
        when(stateService.getAllStatesByCountryId(anyLong(), anyString(), anyInt(), anyInt())).thenReturn(responseDto);

        ResponseEntity<?> response = stateController.getAllStatesByCountryId(1L, "search", 1, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }
}
