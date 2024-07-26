package com.beehyv.fortification.controller;

import com.beehyv.fortification.dto.external.ExternalLotDetailsResponseDto;
import com.beehyv.fortification.dto.requestDto.WastageRequestDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.WastageResponseDto;
import com.beehyv.fortification.enums.WastageType;
import com.beehyv.fortification.service.WastageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class WastageControllerTest {

    @InjectMocks
    private WastageController wastageController;

    @Mock
    private WastageService wastageService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateWastage() {
        WastageRequestDto dto = new WastageRequestDto();
        when(wastageService.createLotWastage(dto, 1L)).thenReturn(1L);
        ResponseEntity<Long> response = wastageController.createWastage(1L, 1L, WastageType.lot, dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testCreateExternalWastage() {
        WastageRequestDto dto = new WastageRequestDto();
        ExternalLotDetailsResponseDto result = new ExternalLotDetailsResponseDto();
        when(wastageService.createExternalLotWastage(dto, "entityNo")).thenReturn(result);
        ResponseEntity<?> response = wastageController.createExternalWastage(1L, "entityNo", WastageType.lot, dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testGetWastageById() {
        Long id = 1L;
        WastageResponseDto dto = new WastageResponseDto();
        when(wastageService.getWastageById(id)).thenReturn(dto);
        ResponseEntity<WastageResponseDto> response = wastageController.getWastageById(1L, 1L, id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    public void testGetAllWastes() {
        Integer pageNumber = 1;
        Integer pageSize = 10;
        ListResponse<WastageResponseDto> listResponse = new ListResponse<>(0L, null);
        when(wastageService.getAllWastesForLot(1L, pageNumber, pageSize)).thenReturn(listResponse);
        ResponseEntity<ListResponse<WastageResponseDto>> response = wastageController.getAllWastes(1L, 1L, WastageType.lot, pageNumber, pageSize);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listResponse, response.getBody());
    }

    @Test
    public void testUpdateWastage() {
        Long id = 1L;
        WastageRequestDto dto = new WastageRequestDto();
        dto.setId(id);
        doNothing().when(wastageService).updateLotWastage(dto, 1L);
        ResponseEntity<?> response = wastageController.updateWastage(1L, 1L, WastageType.lot, id, dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteWastage() {
        Long id = 1L;
        doNothing().when(wastageService).deleteLotWastage(id, 1L);
        ResponseEntity<String> response = wastageController.deleteWastage(1L, 1L, WastageType.lot, id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Wastage successfully deleted!", response.getBody());
    }
}
