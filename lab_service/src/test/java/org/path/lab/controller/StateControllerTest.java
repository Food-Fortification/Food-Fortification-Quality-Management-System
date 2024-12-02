package org.path.lab.controller;

import org.path.lab.dto.requestDto.StateRequestDTO;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.dto.responseDto.LocationResponseDto;
import org.path.lab.dto.responseDto.StateResponseDTO;
import org.path.lab.service.StateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
        Long id = 1L;
        StateResponseDTO expectedResponse = new StateResponseDTO();

        when(stateService.getById(id)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = stateController.getById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(stateService, times(1)).getById(id);
        verifyNoMoreInteractions(stateService);
    }

    @Test
    void testCreate() {
        StateRequestDTO stateRequestDTO = new StateRequestDTO();

        doNothing().when(stateService).create(stateRequestDTO);

        ResponseEntity<?> responseEntity = stateController.create(stateRequestDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Successfully Created", responseEntity.getBody());

        verify(stateService, times(1)).create(stateRequestDTO);
        verifyNoMoreInteractions(stateService);
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        StateRequestDTO stateRequestDTO = new StateRequestDTO();

        doNothing().when(stateService).update(stateRequestDTO);

        ResponseEntity<?> responseEntity = stateController.update(id, stateRequestDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Successfully Updated", responseEntity.getBody());

        verify(stateService, times(1)).update(stateRequestDTO);
        verifyNoMoreInteractions(stateService);
    }

    @Test
    void testDelete() {
        Long id = 1L;

        doNothing().when(stateService).delete(id);

        ResponseEntity<?> responseEntity = stateController.delete(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(stateService, times(1)).delete(id);
        verifyNoMoreInteractions(stateService);
    }

    @Test
    void testGetAllStates() {
        Integer pageNumber = 1;
        Integer pageSize = 10;
        ListResponse<StateResponseDTO> expectedResponse = new ListResponse<>();

        when(stateService.findAll(pageNumber, pageSize)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = stateController.getAllStates(pageNumber, pageSize);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(stateService, times(1)).findAll(pageNumber, pageSize);
        verifyNoMoreInteractions(stateService);
    }

    @Test
    void testGetAllStatesByCountryId() {
        Long countryId = 1L;
        String search = "test";
        Integer pageNumber = 1;
        Integer pageSize = 10;
        ListResponse<LocationResponseDto> expectedResponse = new ListResponse<>();

        when(stateService.getAllStatesByCountryId(countryId, search, pageNumber, pageSize)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = stateController.getAllStatesByCountryId(countryId, search, pageNumber, pageSize);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(stateService, times(1)).getAllStatesByCountryId(countryId, search, pageNumber, pageSize);
        verifyNoMoreInteractions(stateService);
    }
}