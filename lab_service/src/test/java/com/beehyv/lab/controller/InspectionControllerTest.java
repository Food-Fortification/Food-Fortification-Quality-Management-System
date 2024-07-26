package com.beehyv.lab.controller;

import com.beehyv.lab.dto.external.ExternalInspectionRequestDto;
import com.beehyv.lab.dto.requestDto.InspectionRequestDTO;
import com.beehyv.lab.dto.responseDto.InspectionResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.enums.SampleType;
import com.beehyv.lab.service.InspectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InspectionControllerTest {

    @Mock
    private InspectionService inspectionService;

    @InjectMocks
    private InspectionController inspectionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById() {
        Long id = 1L;
        InspectionResponseDTO expectedResponse = new InspectionResponseDTO();

        when(inspectionService.getById(id)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = inspectionController.getById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(inspectionService, times(1)).getById(id);
        verifyNoMoreInteractions(inspectionService);
    }

    @Test
    void testCreate() {
        InspectionRequestDTO dto = new InspectionRequestDTO();

        when(inspectionService.create(dto)).thenReturn(1L);

        ResponseEntity<?> responseEntity = inspectionController.create(dto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        verify(inspectionService, times(1)).create(dto);
        verifyNoMoreInteractions(inspectionService);
    }

    @Test
    void testCreateExternalInspection() {
        ExternalInspectionRequestDto dto = new ExternalInspectionRequestDto();

        when(inspectionService.createExternalInspection(dto)).thenReturn(1L);

        ResponseEntity<?> responseEntity = inspectionController.createExternalInspection(dto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        verify(inspectionService, times(1)).createExternalInspection(dto);
        verifyNoMoreInteractions(inspectionService);
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        InspectionRequestDTO dto = new InspectionRequestDTO();
        dto.setId(id);

        when(inspectionService.update(dto)).thenReturn(1L);

        ResponseEntity<?> responseEntity = inspectionController.update(id, dto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(inspectionService, times(1)).update(dto);
        verifyNoMoreInteractions(inspectionService);
    }

    @Test
    void testDelete() {
        Long id = 1L;

        doNothing().when(inspectionService).delete(id);

        ResponseEntity<?> responseEntity = inspectionController.delete(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(inspectionService, times(1)).delete(id);
        verifyNoMoreInteractions(inspectionService);
    }

    @Test
    void testGetAllIdsBySampleType() {
        SampleType type = SampleType.lot;
        Long categoryId = 1L;
        String sampleState = "test";
        Integer pageNumber = 1;
        Integer pageSize = 10;
        ListResponse<Long> expectedResponse = new ListResponse<>();

        when(inspectionService.getAllIdsBySampleType(type, categoryId, sampleState, pageNumber, pageSize)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = inspectionController.getAllIdsBySampleType(type,  sampleState,categoryId, pageNumber, pageSize);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(inspectionService, times(1)).getAllIdsBySampleType(type, categoryId, sampleState, pageNumber, pageSize);
        verifyNoMoreInteractions(inspectionService);
    }

    @Test
    void testGetInspectionSampleStatus() {
        SampleType sampleType = SampleType.lot;
        Long id = 1L;
        Boolean expectedResponse = true;

        when(inspectionService.getInspectionSampleStatus(sampleType, id)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = inspectionController.getInspectionSampleStatus(sampleType, id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(inspectionService, times(1)).getInspectionSampleStatus(sampleType, id);
        verifyNoMoreInteractions(inspectionService);
    }
}