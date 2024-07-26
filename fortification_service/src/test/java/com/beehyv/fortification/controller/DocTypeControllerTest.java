package com.beehyv.fortification.controller;

import com.beehyv.fortification.dto.requestDto.DocTypeRequestDto;
import com.beehyv.fortification.dto.responseDto.DocTypeResponseDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.service.DocTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DocTypeControllerTest {

    @Mock
    private DocTypeService service;

    @InjectMocks
    private DocTypeController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDocType() {
        DocTypeRequestDto dto = new DocTypeRequestDto();
        doNothing().when(service).createDocType(dto);
        ResponseEntity<?> response = controller.createSDocType(dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(service, times(1)).createDocType(dto);
    }

    @Test
    void testGetDocTypeById() {
        Long id = 1L;
        DocTypeResponseDto dto = new DocTypeResponseDto();
        when(service.getDocTypeById(id)).thenReturn(dto);
        ResponseEntity<DocTypeResponseDto> response = controller.getDocTypeById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
        verify(service, times(1)).getDocTypeById(id);
    }

    @Test
    void testGetAllDocTypes() {
        ListResponse<DocTypeResponseDto> listResponse = new ListResponse<>(0L, Collections.emptyList());
        when(service.getAllDocTypes(null, null)).thenReturn(listResponse);
        ResponseEntity<ListResponse<DocTypeResponseDto>> response = controller.getAllDocTypes(null, null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listResponse, response.getBody());
        verify(service, times(1)).getAllDocTypes(null, null);
    }

    @Test
    void testUpdateDocType() {
        Long id = 1L;
        DocTypeRequestDto dto = new DocTypeRequestDto();
        doNothing().when(service).updateDocType(dto);
        ResponseEntity<?> response = controller.updateDocType(id, dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).updateDocType(dto);
    }

    @Test
    void testDeleteDocType() {
        Long id = 1L;
        doNothing().when(service).deleteDocType(id);
        ResponseEntity<String> response = controller.deleteDocType(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).deleteDocType(id);
    }
}