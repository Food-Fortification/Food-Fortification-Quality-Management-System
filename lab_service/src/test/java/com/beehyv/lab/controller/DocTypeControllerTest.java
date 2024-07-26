package com.beehyv.lab.controller;

import com.beehyv.lab.dto.requestDto.DocTypeRequestDTO;
import com.beehyv.lab.dto.responseDto.DocTypeResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.service.DocTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DocTypeControllerTest {

    @Mock
    private DocTypeService docTypeService;

    @InjectMocks
    private DocTypeController docTypeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDocTypes() {
        Integer pageNumber = 1;
        Integer pageSize = 10;
        ListResponse<DocTypeResponseDTO> expectedResponse = new ListResponse<>();

        when(docTypeService.getAllDocTypes(pageNumber, pageSize)).thenReturn(expectedResponse);

        ResponseEntity<ListResponse<DocTypeResponseDTO>> responseEntity = docTypeController.getAllDocTypes(pageNumber, pageSize);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(docTypeService, times(1)).getAllDocTypes(pageNumber, pageSize);
        verifyNoMoreInteractions(docTypeService);
    }

    @Test
    void testGetDocTypeById() {
        Long docTypeId = 1L;
        DocTypeResponseDTO expectedResponse = new DocTypeResponseDTO(null,null);

        when(docTypeService.getDocTypeById(docTypeId)).thenReturn(expectedResponse);

        ResponseEntity<DocTypeResponseDTO> responseEntity = docTypeController.getDocTypeById(docTypeId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(docTypeService, times(1)).getDocTypeById(docTypeId);
        verifyNoMoreInteractions(docTypeService);
    }

    @Test
    void testAddDocType() {
        DocTypeRequestDTO dto = new DocTypeRequestDTO(null,null);

        doNothing().when(docTypeService).addDocType(dto);

        ResponseEntity<String> responseEntity = docTypeController.addDocType(dto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("SUCCESS", responseEntity.getBody());

        verify(docTypeService, times(1)).addDocType(dto);
        verifyNoMoreInteractions(docTypeService);
    }

    @Test
    void testUpdateDocTypeById() {
        Long docTypeId = 1L;
        DocTypeRequestDTO dto = new DocTypeRequestDTO(null,null);

        doNothing().when(docTypeService).updateDocTypeById(docTypeId, dto);

        ResponseEntity<String> responseEntity = docTypeController.updateDocTypeById(docTypeId, dto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("SUCCESS", responseEntity.getBody());

        verify(docTypeService, times(1)).updateDocTypeById(docTypeId, dto);
        verifyNoMoreInteractions(docTypeService);
    }

    @Test
    void testDeleteDocTypeById() {
        Long docTypeId = 1L;

        doNothing().when(docTypeService).deleteDocTypeById(docTypeId);

        ResponseEntity<String> responseEntity = docTypeController.deleteDocTypeById(docTypeId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("SUCCESS", responseEntity.getBody());

        verify(docTypeService, times(1)).deleteDocTypeById(docTypeId);
        verifyNoMoreInteractions(docTypeService);
    }
}