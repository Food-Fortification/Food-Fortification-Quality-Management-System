package com.beehyv.lab.controller;

import com.beehyv.lab.dto.requestDto.LabTestReferenceMethodRequestDTO;
import com.beehyv.lab.dto.responseDto.LabMethodResponseDto;
import com.beehyv.lab.dto.responseDto.LabTestReferenceMethodResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.service.LabTestReferenceMethodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LabTestReferenceMethodControllerTest {

    @Mock
    private LabTestReferenceMethodService labTestReferenceMethodService;

    @InjectMocks
    private LabTestReferenceMethodController labTestReferenceMethodController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllMethods() {
        Long categoryId = 1L;
        Long labSampleId = 1L;
        Integer pageNumber = 1;
        Integer pageSize = 10;
        ListResponse<LabMethodResponseDto> expectedResponse = new ListResponse<>();

        when(labTestReferenceMethodService.getAllMethodsByCategoryId(categoryId, labSampleId, pageNumber, pageSize)).thenReturn(expectedResponse);

        ResponseEntity<ListResponse<LabMethodResponseDto>> responseEntity = labTestReferenceMethodController.getAllMethods(categoryId, labSampleId, pageNumber, pageSize);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(labTestReferenceMethodService, times(1)).getAllMethodsByCategoryId(categoryId, labSampleId, pageNumber, pageSize);
        verifyNoMoreInteractions(labTestReferenceMethodService);
    }

    @Test
    void testGetMethodById() {
        Long methodId = 1L;
        LabTestReferenceMethodResponseDTO expectedResponse = new LabTestReferenceMethodResponseDTO();

        when(labTestReferenceMethodService.getLabTestReferenceMethodById(methodId)).thenReturn(expectedResponse);

        ResponseEntity<LabTestReferenceMethodResponseDTO> responseEntity = labTestReferenceMethodController.getMethodById(methodId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(labTestReferenceMethodService, times(1)).getLabTestReferenceMethodById(methodId);
        verifyNoMoreInteractions(labTestReferenceMethodService);
    }

    @Test
    void testAddMethod() {
        LabTestReferenceMethodRequestDTO dto = new LabTestReferenceMethodRequestDTO(null,null,null,null,null,null,null,null);

        doNothing().when(labTestReferenceMethodService).addLabTestReferenceMethod(dto);

        ResponseEntity<String> responseEntity = labTestReferenceMethodController.addMethod(dto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("SUCCESS", responseEntity.getBody());

        verify(labTestReferenceMethodService, times(1)).addLabTestReferenceMethod(dto);
        verifyNoMoreInteractions(labTestReferenceMethodService);
    }

    @Test
    void testUpdateMethodById() {
        Long methodId = 1L;
        LabTestReferenceMethodRequestDTO dto = new LabTestReferenceMethodRequestDTO(null,null,null,null,null,null,null,null);

        doNothing().when(labTestReferenceMethodService).updateLabTestReferenceMethodById(methodId, dto);

        ResponseEntity<String> responseEntity = labTestReferenceMethodController.updateMethodById(methodId, dto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("SUCCESS", responseEntity.getBody());

        verify(labTestReferenceMethodService, times(1)).updateLabTestReferenceMethodById(methodId, dto);
        verifyNoMoreInteractions(labTestReferenceMethodService);
    }

    @Test
    void testDeleteMethodById() {
        Long methodId = 1L;

        doNothing().when(labTestReferenceMethodService).deleteLabTestReferenceMethodById(methodId);

        ResponseEntity<String> responseEntity = labTestReferenceMethodController.deleteMethodById(methodId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("SUCCESS", responseEntity.getBody());

        verify(labTestReferenceMethodService, times(1)).deleteLabTestReferenceMethodById(methodId);
        verifyNoMoreInteractions(labTestReferenceMethodService);
    }
}