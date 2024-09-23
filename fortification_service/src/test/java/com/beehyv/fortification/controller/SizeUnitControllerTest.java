package com.beehyv.fortification.controller;

import com.beehyv.fortification.dto.requestDto.SizeUnitRequestDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.SizeUnitResponseDto;
import com.beehyv.fortification.service.SizeUnitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class SizeUnitControllerTest {

    @InjectMocks
    private SizeUnitController sizeUnitController;

    @Mock
    private SizeUnitService sizeUnitService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSizeUnit() {
        SizeUnitRequestDto dto = new SizeUnitRequestDto();
        doNothing().when(sizeUnitService).createSizeUnit(dto);
        ResponseEntity<?> response = sizeUnitController.createSizeUnit(dto, "categoryId");
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testGetSizeUnitById() {
        Long id = 1L;
        SizeUnitResponseDto dto = new SizeUnitResponseDto();
        when(sizeUnitService.getSizeUnitById(id)).thenReturn(dto);
        ResponseEntity<SizeUnitResponseDto> response = sizeUnitController.getSizeUnitById(id, "batchId", "categoryId");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    public void testGetAllSizeUnitsForBatch() {
        Integer pageNumber = 1;
        Integer pageSize = 10;
        ListResponse<SizeUnitResponseDto> listResponse = new ListResponse<>(0L, Collections.emptyList());
        when(sizeUnitService.getAllSizeUnits(1L, pageNumber, pageSize)).thenReturn(listResponse);
        ResponseEntity<ListResponse<SizeUnitResponseDto>> response = sizeUnitController.getAllSizeUnitsForBatch(1L, pageNumber, pageSize, "categoryId");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listResponse, response.getBody());
    }

    @Test
    public void testUpdateSizeUnit() {
        Long id = 1L;
        SizeUnitRequestDto dto = new SizeUnitRequestDto();
        dto.setId(id);
        doNothing().when(sizeUnitService).updateSizeUnit(dto);
        ResponseEntity<?> response = sizeUnitController.updateSizeUnit(id, dto, "categoryId");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteSizeUnit() {
        Long id = 1L;
        doNothing().when(sizeUnitService).deleteSizeUnit(id);
        ResponseEntity<String> response = sizeUnitController.deleteSizeUnit(id, "batchId");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SizeUnit successfully deleted!", response.getBody());
    }

    @Test
    public void testAddMultipleSizeUnits() {
        List<SizeUnitRequestDto> sizeUnits = Collections.emptyList();
        when(sizeUnitService.createSizeUnits(sizeUnits, 1L)).thenReturn(true);
        ResponseEntity<?> response = sizeUnitController.addMultipleSizeUnits(sizeUnits, 1L, 1L);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testUpdateMultipleSizeUnits() {
        List<SizeUnitRequestDto> sizeUnits = Collections.emptyList();
        when(sizeUnitService.updateSizeUnits(sizeUnits, 1L)).thenReturn(true);
        ResponseEntity<?> response = sizeUnitController.updateMultipleSizeUnits(sizeUnits, 1L, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
