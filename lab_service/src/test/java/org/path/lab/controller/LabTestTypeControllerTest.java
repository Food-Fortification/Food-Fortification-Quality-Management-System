package org.path.lab.controller;

import org.path.lab.dto.requestDto.LabTestTypeRequestDTO;
import org.path.lab.dto.requestDto.SearchListRequest;
import org.path.lab.dto.responseDto.LabTestTypeResponseDTO;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.entity.LabTestType;
import org.path.lab.service.LabTestTypeService;
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

class LabTestTypeControllerTest {

    @Mock
    private LabTestTypeService labTestTypeService;

    @InjectMocks
    private LabTestTypeController labTestTypeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllLabTestTypes() {
        SearchListRequest searchListRequest = new SearchListRequest();
        Integer pageNumber = 1;
        Integer pageSize = 10;
        ListResponse<LabTestTypeResponseDTO> expectedResponse = new ListResponse<>();

        when(labTestTypeService.getAllLabTestTypes(searchListRequest, pageNumber, pageSize)).thenReturn(expectedResponse);

        ResponseEntity<ListResponse<LabTestTypeResponseDTO>> responseEntity = labTestTypeController.getAllLabTestTypes(searchListRequest, pageNumber, pageSize);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(labTestTypeService, times(1)).getAllLabTestTypes(searchListRequest, pageNumber, pageSize);
        verifyNoMoreInteractions(labTestTypeService);
    }

    @Test
    void testGetLabTestTypeById() {
        Long labTestTypeId = 1L;
        LabTestTypeResponseDTO expectedResponse = new LabTestTypeResponseDTO();

        when(labTestTypeService.getLabTestTypeById(labTestTypeId)).thenReturn(expectedResponse);

        ResponseEntity<LabTestTypeResponseDTO> responseEntity = labTestTypeController.getLabTestTypeById(labTestTypeId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(labTestTypeService, times(1)).getLabTestTypeById(labTestTypeId);
        verifyNoMoreInteractions(labTestTypeService);
    }

    @Test
    void testGetLabTestTypeByType() {
        LabTestType.Type type = LabTestType.Type.PHYSICAL; // replace with actual type
        Long categoryId = 1L;
        Integer pageNumber = 1;
        Integer pageSize = 10;
        LabTestTypeResponseDTO expectedResponse = new LabTestTypeResponseDTO();

        when(labTestTypeService.getLabTestTypesByType(type, categoryId, pageNumber, pageSize)).thenReturn(List.of(expectedResponse));

        ResponseEntity<?> responseEntity = labTestTypeController.getLabTestTypeByType(type, categoryId, pageNumber, pageSize);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(List.of(expectedResponse), responseEntity.getBody());

        verify(labTestTypeService, times(1)).getLabTestTypesByType(type, categoryId, pageNumber, pageSize);
        verifyNoMoreInteractions(labTestTypeService);
    }

    @Test
    void testAddLabTestType() {
        LabTestTypeRequestDTO labTestTypeRequestDTO = new LabTestTypeRequestDTO(null,null,null,null,null,null);

        doNothing().when(labTestTypeService).addLabTestType(labTestTypeRequestDTO);

        ResponseEntity<String> responseEntity = labTestTypeController.addLabTestType(labTestTypeRequestDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("SUCCESS", responseEntity.getBody());

        verify(labTestTypeService, times(1)).addLabTestType(labTestTypeRequestDTO);
        verifyNoMoreInteractions(labTestTypeService);
    }

    @Test
    void testUpdateLabTestTypeById() {
        Long labTestTypeId = 1L;
        LabTestTypeRequestDTO labTestTypeRequestDTO = new LabTestTypeRequestDTO(null,null,null,null,null,null);

        doNothing().when(labTestTypeService).updateLabTestTypeById(labTestTypeId, labTestTypeRequestDTO);

        ResponseEntity<String> responseEntity = labTestTypeController.updateLabTestTypeById(labTestTypeId, labTestTypeRequestDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("SUCCESS", responseEntity.getBody());

        verify(labTestTypeService, times(1)).updateLabTestTypeById(labTestTypeId, labTestTypeRequestDTO);
        verifyNoMoreInteractions(labTestTypeService);
    }

    @Test
    void testDeleteLabTestTypeById() {
        Long labTestTypeId = 1L;

        doNothing().when(labTestTypeService).deleteLabTestTypeById(labTestTypeId);

        ResponseEntity<String> responseEntity = labTestTypeController.deleteLabTestTypeById(labTestTypeId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("SUCCESS", responseEntity.getBody());

        verify(labTestTypeService, times(1)).deleteLabTestTypeById(labTestTypeId);
        verifyNoMoreInteractions(labTestTypeService);
    }
}