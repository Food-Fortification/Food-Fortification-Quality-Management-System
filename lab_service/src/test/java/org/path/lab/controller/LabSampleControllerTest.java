package org.path.lab.controller;

import org.path.lab.dto.requestDto.LabSampleRequestDTO;
import org.path.lab.dto.responseDto.LabSampleCreateResponseDto;
import org.path.lab.dto.responseDto.LabSampleResponseDto;
import org.path.lab.service.LabSampleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LabSampleControllerTest {

    @Mock
    private LabSampleService labSampleService;

    @InjectMocks
    private LabSampleController labSampleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateLabSample() {
        LabSampleRequestDTO dto = new LabSampleRequestDTO();
        Boolean self = true;

        when(labSampleService.createLabSample(dto, self)).thenReturn(new LabSampleCreateResponseDto());

        ResponseEntity<?> responseEntity = labSampleController.createLabSample(dto, self);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        verify(labSampleService, times(1)).createLabSample(dto, self);
        verifyNoMoreInteractions(labSampleService);
    }

    @Test
    void testDeleteLabSampleById() {
        Long sampleId = 1L;

        doNothing().when(labSampleService).deleteLabSample(sampleId);

        ResponseEntity<String> responseEntity = labSampleController.deleteLabSampleById(sampleId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Success", responseEntity.getBody());

        verify(labSampleService, times(1)).deleteLabSample(sampleId);
        verifyNoMoreInteractions(labSampleService);
    }

    @Test
    void testGetLabSampleById() {
        Long sampleId = 1L;
        LabSampleResponseDto expectedResponse = new LabSampleResponseDto();

        when(labSampleService.getLabSampleById(sampleId)).thenReturn(expectedResponse);

        ResponseEntity<LabSampleResponseDto> responseEntity = labSampleController.getLabSampleById(sampleId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(labSampleService, times(1)).getLabSampleById(sampleId);
        verifyNoMoreInteractions(labSampleService);
    }


}