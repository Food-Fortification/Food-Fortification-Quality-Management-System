package com.beehyv.lab.controller;

import com.beehyv.lab.dto.requestDto.SampleStateRequestDto;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.dto.responseDto.SampleStateResponseDTO;
import com.beehyv.lab.service.SampleStateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SampleStateControllerTest {

    @Mock
    private SampleStateService sampleStateService;

    @InjectMocks
    private SampleStateController sampleStateController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllSampleStates() {
        Integer pageNumber = 1;
        Integer pageSize = 10;
        ListResponse<SampleStateResponseDTO> expectedResponse = new ListResponse<>();

        when(sampleStateService.getAll(pageNumber, pageSize)).thenReturn(expectedResponse);

        ResponseEntity<ListResponse<SampleStateResponseDTO>> responseEntity = sampleStateController.getAllSampleStates(pageNumber, pageSize);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(sampleStateService, times(1)).getAll(pageNumber, pageSize);
        verifyNoMoreInteractions(sampleStateService);
    }

    @Test
    void testGetById() {
        Long id = 1L;
        SampleStateResponseDTO expectedResponse = new SampleStateResponseDTO();

        when(sampleStateService.getById(id)).thenReturn(expectedResponse);

        ResponseEntity<SampleStateResponseDTO> responseEntity = sampleStateController.getById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(sampleStateService, times(1)).getById(id);
        verifyNoMoreInteractions(sampleStateService);
    }

    @Test
    void testCreate() {
        SampleStateRequestDto sampleStateRequestDto = new SampleStateRequestDto();

        doNothing().when(sampleStateService).create(sampleStateRequestDto);

        ResponseEntity<?> responseEntity = sampleStateController.create(sampleStateRequestDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("SUCCESS", responseEntity.getBody());

        verify(sampleStateService, times(1)).create(sampleStateRequestDto);
        verifyNoMoreInteractions(sampleStateService);
    }

    @Test
    void testUpdateDocTypeById() {
        Long id = 1L;
        SampleStateRequestDto sampleStateRequestDto = new SampleStateRequestDto();

        doNothing().when(sampleStateService).update(sampleStateRequestDto);

        ResponseEntity<String> responseEntity = sampleStateController.updateDocTypeById(id, sampleStateRequestDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("SUCCESS", responseEntity.getBody());

        verify(sampleStateService, times(1)).update(sampleStateRequestDto);
        verifyNoMoreInteractions(sampleStateService);
    }

}