package com.beehyv.fortification.controller;

import com.beehyv.fortification.dto.requestDto.UOMRequestDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.UOMResponseDto;
import com.beehyv.fortification.service.UOMService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UOMControllerTest {

    @InjectMocks
    private UOMController uomController;

    @Mock
    private UOMService uomService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUOM() {
        UOMRequestDto dto = new UOMRequestDto();
        doNothing().when(uomService).createUOM(dto);
        ResponseEntity<?> response = uomController.createUOM(dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testGetUOMById() {
        Long id = 1L;
        UOMResponseDto dto = new UOMResponseDto();
        when(uomService.getUOMById(id)).thenReturn(dto);
        ResponseEntity<UOMResponseDto> response = uomController.getUOMById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    public void testGetAllUOMs() {
        Integer pageNumber = 1;
        Integer pageSize = 10;
        ListResponse<UOMResponseDto> listResponse = new ListResponse<>(0L, Collections.emptyList());
        when(uomService.getAllUOMs(pageNumber, pageSize)).thenReturn(listResponse);
        ResponseEntity<ListResponse<UOMResponseDto>> response = uomController.getAllUOMs(pageNumber, pageSize);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listResponse, response.getBody());
    }

    @Test
    public void testUpdateUOM() {
        Long id = 1L;
        UOMRequestDto dto = new UOMRequestDto();
        dto.setId(id);
        doNothing().when(uomService).updateUOM(dto);
        ResponseEntity<?> response = uomController.updateUOM(id, dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteUOM() {
        Long id = 1L;
        doNothing().when(uomService).deleteUOM(id);
        ResponseEntity<String> response = uomController.deleteUOM(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("UOM successfully deleted!", response.getBody());
    }
}