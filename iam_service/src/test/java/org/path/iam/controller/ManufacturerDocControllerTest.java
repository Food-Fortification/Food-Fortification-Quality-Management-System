package org.path.iam.controller;

import org.path.iam.dto.requestDto.ManufacturerDocsRequestDto;
import org.path.iam.dto.responseDto.ListResponse;
import org.path.iam.dto.responseDto.ManufacturerDocsResponseDto;
import org.path.iam.service.ManufacturerDocService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class ManufacturerDocControllerTest {

    @Mock
    private ManufacturerDocService manufacturerDocService;

    @InjectMocks
    private ManufacturerDocController manufacturerDocController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddManufacturer() {
        ManufacturerDocsRequestDto dto = new ManufacturerDocsRequestDto();
        doNothing().when(manufacturerDocService).create(any(ManufacturerDocsRequestDto.class));

        ResponseEntity<?> response = manufacturerDocController.addManufacturer(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Successfully Created", response.getBody());
    }

    @Test
    void testGetAllManufacturers() {
        ListResponse<ManufacturerDocsResponseDto> responseList = new ListResponse<>();
        when(manufacturerDocService.getALlManufacturerDocs(anyInt(), anyInt())).thenReturn(responseList);

        ResponseEntity<?> response = manufacturerDocController.getAllManufacturers(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseList, response.getBody());
    }

    @Test
    void testGetById() {
        ManufacturerDocsResponseDto responseDto = new ManufacturerDocsResponseDto();
        when(manufacturerDocService.getById(anyLong())).thenReturn(responseDto);

        ResponseEntity<?> response = manufacturerDocController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testUpdateManufacturerDoc() {
        ManufacturerDocsRequestDto dto = new ManufacturerDocsRequestDto();
        doNothing().when(manufacturerDocService).updateManufacturerDoc(any(ManufacturerDocsRequestDto.class));

        ResponseEntity<?> response = manufacturerDocController.updateManufacturerDoc(1L, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully Updated", response.getBody());
    }

    @Test
    void testDeleteManufacturerDocById() {
        doNothing().when(manufacturerDocService).deleteManufacturer(anyLong());

        ResponseEntity<?> response = manufacturerDocController.deleteManufacturerDocById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
