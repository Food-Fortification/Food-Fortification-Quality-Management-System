package com.beehyv.fortification.controller;

import com.beehyv.fortification.dto.requestDto.StateRequestDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.StateResponseDto;
import com.beehyv.fortification.service.StateService;
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

public class StateControllerTest {

    @InjectMocks
    private StateController stateController;

    @Mock
    private StateService stateService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateState() {
        StateRequestDto dto = new StateRequestDto();
        doNothing().when(stateService).createState(dto);
        ResponseEntity<?> response = stateController.createState(dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testGetStateById() {
        Long id = 1L;
        StateResponseDto dto = new StateResponseDto();
        when(stateService.getStateById(id)).thenReturn(dto);
        ResponseEntity<StateResponseDto> response = stateController.getStateById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    public void testGetAllStates() {
        Integer pageNumber = 1;
        Integer pageSize = 10;
        ListResponse<StateResponseDto> listResponse = new ListResponse<>(0L, Collections.emptyList());
        when(stateService.getAllStates(null, pageNumber, pageSize)).thenReturn(listResponse);
        ResponseEntity<ListResponse<StateResponseDto>> response = stateController.getAllStates(pageNumber, pageSize, null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listResponse, response.getBody());
    }

    @Test
    public void testUpdateState() {
        Long id = 1L;
        StateRequestDto dto = new StateRequestDto();
        dto.setId(id);
        doNothing().when(stateService).updateState(dto);
        ResponseEntity<?> response = stateController.updateState(id, dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteState() {
        Long id = 1L;
        doNothing().when(stateService).deleteState(id);
        ResponseEntity<String> response = stateController.deleteState(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("State successfully deleted!", response.getBody());
    }
}
