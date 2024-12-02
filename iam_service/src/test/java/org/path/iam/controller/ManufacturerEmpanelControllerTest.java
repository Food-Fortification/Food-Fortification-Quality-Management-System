package org.path.iam.controller;

import org.path.iam.dto.requestDto.ManufacturerEmpanelRequestDto;
import org.path.iam.dto.responseDto.DashboardResponseDto;
import org.path.iam.dto.responseDto.ManufacturerEmpanelResponseDto;
import org.path.iam.enums.GeoType;
import org.path.iam.service.ManufacturerEmpanelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class ManufacturerEmpanelControllerTest {

    @Mock
    private ManufacturerEmpanelService manufacturerEmpanelService;

    @InjectMocks
    private ManufacturerEmpanelController manufacturerEmpanelController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateManufacturerEmpanel() {
        ManufacturerEmpanelRequestDto dto = new ManufacturerEmpanelRequestDto();
        ManufacturerEmpanelResponseDto responseDto = new ManufacturerEmpanelResponseDto();
        when(manufacturerEmpanelService.create(any(ManufacturerEmpanelRequestDto.class))).thenReturn(1L);

        ResponseEntity<?> response = manufacturerEmpanelController.createManufacturerEmpanel(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testGetById() {
        ManufacturerEmpanelResponseDto responseDto = new ManufacturerEmpanelResponseDto();
        when(manufacturerEmpanelService.getManufacturerEmpanelByID(anyLong())).thenReturn(responseDto);

        ResponseEntity<?> response = manufacturerEmpanelController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testUpdateManufacturerEmpanel() {
        ManufacturerEmpanelRequestDto dto = new ManufacturerEmpanelRequestDto();
        ManufacturerEmpanelResponseDto responseDto = new ManufacturerEmpanelResponseDto();
        when(manufacturerEmpanelService.update(any(ManufacturerEmpanelRequestDto.class))).thenReturn(1L);

        ResponseEntity<?> response = manufacturerEmpanelController.updateManufacturerEmapnel(1L, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteManufacturerEmpanelById() {
        doNothing().when(manufacturerEmpanelService).delete(anyLong());

        ResponseEntity<?> response = manufacturerEmpanelController.deleteManufacturerEmapnelById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAllEmpanelledManufacturers() {
        ManufacturerEmpanelRequestDto dto = new ManufacturerEmpanelRequestDto();
        Map<Long, List<Long>> responseList = new HashMap<>();
        when(manufacturerEmpanelService.getAllEmpanelledManufacturers(any(ManufacturerEmpanelRequestDto.class))).thenReturn(responseList);

        ResponseEntity<?> response = manufacturerEmpanelController.getAllEmpanelledManufacturers(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseList, response.getBody());
    }

    @Test
    void testManufacturerCategoriesCountByGeoId() {
        Map<String, DashboardResponseDto> responseMap = new HashMap<>();
        when(manufacturerEmpanelService.getCategoriesCounts(eq(GeoType.state), anyString())).thenReturn(responseMap);

        ResponseEntity<?> response = manufacturerEmpanelController.manufacturerCategoriesCountByGeoId(GeoType.state, "geoId");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseMap, response.getBody());
    }

    @Test
    void testManufacturerCountByGeoId() {
        DashboardResponseDto responseMap = new DashboardResponseDto();
        when(manufacturerEmpanelService.getCategoryCounts(anyLong(), eq(GeoType.state), anyString())).thenReturn(responseMap);

        ResponseEntity<?> response = manufacturerEmpanelController.manufacturerCountByGeoId(1L, GeoType.state, "geoId");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseMap, response.getBody());
    }
}
