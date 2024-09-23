package com.beehyv.lab.controller;

import com.beehyv.lab.dto.requestDto.LabTestRequestDTO;
import com.beehyv.lab.dto.responseDto.LabTestResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.service.LabTestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LabTestControllerTest {

    @Mock
    private LabTestService labTestService;

    @InjectMocks
    private LabTestController labTestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllLabTests() {
        Integer pageNumber = 1;
        Integer pageSize = 10;
        ListResponse<LabTestResponseDTO> expectedResponse = new ListResponse<>();

        when(labTestService.getAllLabTests(pageNumber, pageSize)).thenReturn(expectedResponse);

        ResponseEntity<ListResponse<LabTestResponseDTO>> responseEntity = labTestController.getAllLabTests(pageNumber, pageSize);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(labTestService, times(1)).getAllLabTests(pageNumber, pageSize);
        verifyNoMoreInteractions(labTestService);
    }

    @Test
    void testGetLabTestById() {
        Long testId = 1L;
        LabTestResponseDTO expectedResponse = new LabTestResponseDTO();

        when(labTestService.getLabTestById(testId)).thenReturn(expectedResponse);

        ResponseEntity<LabTestResponseDTO> responseEntity = labTestController.getLabTestById(testId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(labTestService, times(1)).getLabTestById(testId);
        verifyNoMoreInteractions(labTestService);
    }

    @Test
    void testUpdateLabTestById() {
        Long testId = 1L;
        LabTestRequestDTO dto = new LabTestRequestDTO();

        doNothing().when(labTestService).updateLabTestById(testId, dto);

        ResponseEntity<String> responseEntity = labTestController.updateLabTestById(testId, dto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("SUCCESS", responseEntity.getBody());

        verify(labTestService, times(1)).updateLabTestById(testId, dto);
        verifyNoMoreInteractions(labTestService);
    }

    @Test
    void testDeleteLabTestById() {
        Long testId = 1L;

        doNothing().when(labTestService).deleteLabTestById(testId);

        ResponseEntity<String> responseEntity = labTestController.deleteLabTestById(testId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("SUCCESS", responseEntity.getBody());

        verify(labTestService, times(1)).deleteLabTestById(testId);
        verifyNoMoreInteractions(labTestService);
    }

    @Test
    void testGetLabTestDetails() {
        Long batchId = 1L;
        Integer pageNumber = 1;
        Integer pageSize = 10;
        ListResponse<LabTestResponseDTO> expectedResponse = new ListResponse<>();

        when(labTestService.getDetailsByBatchId(batchId, pageNumber, pageSize)).thenReturn(expectedResponse);

        ResponseEntity<ListResponse<LabTestResponseDTO>> responseEntity = labTestController.getLabTestDetails(batchId, pageNumber, pageSize);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(labTestService, times(1)).getDetailsByBatchId(batchId, pageNumber, pageSize);
        verifyNoMoreInteractions(labTestService);
    }
}