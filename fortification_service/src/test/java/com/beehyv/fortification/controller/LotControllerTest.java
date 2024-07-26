package com.beehyv.fortification.controller;

import com.beehyv.fortification.dto.external.TargetLotsExternalRequestDto;
import com.beehyv.fortification.dto.requestDto.*;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.LotListResponseDTO;
import com.beehyv.fortification.dto.responseDto.LotResponseDto;
import com.beehyv.fortification.dto.responseDto.StateResponseDto;
import com.beehyv.fortification.enums.ActionType;
import com.beehyv.fortification.enums.SampleTestResult;
import com.beehyv.fortification.service.LotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class LotControllerTest {

    @InjectMocks
    private LotController lotController;

    @Mock
    private LotService lotService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateLot() {
        LotRequestDto dto = new LotRequestDto();
        when(lotService.createLot(1L, dto)).thenReturn(1L);
        ResponseEntity<?> response = lotController.createLot(dto, 1L);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testGetLotById() {
        Long id = 1L;
        LotResponseDto dto = new LotResponseDto();
        when(lotService.getLotById(id, 1L)).thenReturn(dto);
        ResponseEntity<LotResponseDto> response = lotController.getLotById(id, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    public void testGetLotByIdException() {
        Long id = 1L;
        when(lotService.getLotById(id, 1L)).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<LotResponseDto> response = lotController.getLotById(id, 1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());

    }

    @Test
    public void testGetAllLots() {
        Integer pageNumber = 1;
        Integer pageSize = 10;
        SearchListRequest sr = new SearchListRequest();
        ListResponse<LotListResponseDTO> listResponse = new ListResponse<>(0L, Collections.emptyList());
        when(lotService.getAllLots(1L, pageNumber, pageSize, sr)).thenReturn(listResponse);

        ResponseEntity
                <ListResponse<LotListResponseDTO>> response = lotController.getAllLots(1L, pageSize, pageSize, sr);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateLot() {
        Long id = 1L;
        LotRequestDto dto = new LotRequestDto();
        dto.setId(id);
        doNothing().when(lotService).updateLot(1L, dto);
        ResponseEntity<?> response = lotController.updateLot(id, dto, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteLot() {
        Long id = 1L;
        doNothing().when(lotService).deleteLot(id);
        ResponseEntity<String> response = lotController.deleteLot(id, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Lot successfully deleted!", response.getBody());
    }

    @Test
    public void testUpdateLotStatus() {
        EntityStateRequestDTO entityStateRequestDTO = new EntityStateRequestDTO();
        ActionType actionType = ActionType.module;
        SampleTestResult sampleTestResult = SampleTestResult.TEST_PASSED;
        Boolean isExternalTest = false;
        when(lotService.updateLotState(1L, entityStateRequestDTO, actionType, sampleTestResult, isExternalTest)).thenReturn(true);
        ResponseEntity<Boolean> response = lotController.updateLotStatus(entityStateRequestDTO, 1L, 1L, actionType, sampleTestResult, isExternalTest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }

    @Test
    public void testReceiveLot() {
        LotReceiveRequestDto dto = new LotReceiveRequestDto();
        when(lotService.receiveLot(dto, 1L, new ArrayList<>())).thenReturn(true);
        ResponseEntity<Boolean> response = lotController.receiveLot(dto, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }

    @Test
    public void testAcceptLot() {
        LotReceiveRequestDto dto = new LotReceiveRequestDto();
        when(lotService.acceptLot(dto, 1L, new ArrayList<>())).thenReturn(true);
        ResponseEntity<Boolean> response = lotController.acceptLot(dto, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }

    @Test
    public void testGetLotActions() {
        List<StateResponseDto> stateResponseDtos = new ArrayList<>();
        when(lotService.getLotActions(1L, 1L, ActionType.module, "sampleState")).thenReturn(stateResponseDtos);
        ResponseEntity<List<StateResponseDto>> response = lotController.geLotActions(1L, 1L, ActionType.module, "sampleState");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(stateResponseDtos, response.getBody());
    }

    @Test
    public void testGetDetailsForUUID() {
        LotResponseDto dto = new LotResponseDto();
        when(lotService.getDetailsForUUID("uuid")).thenReturn(dto);
        ResponseEntity<LotResponseDto> response = lotController.getDetailsForUUID("uuid");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    public void testCheckLabAccess() {
        when(lotService.checkLabAccess(1L)).thenReturn(true);
        ResponseEntity<Boolean> response = lotController.checkLabAccess(1L, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }

    @Test
    public void testUpdateBatchInspectionStatus() {
        doNothing().when(lotService).updateBatchInspectionStatus(1L, true);
        ResponseEntity<?> response = lotController.updateBatchInspectionStatus(1L, true, "categoryId");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully Updated Inspection Status", response.getBody());
    }

    @Test
    public void testCreateTargetLotFromSourceLots() {
        TargetLotRequestDto dto = new TargetLotRequestDto();
        when(lotService.createTargetLotFromSourceLots(dto, 1L, false)).thenReturn(List.of(1L));
        ResponseEntity<?> response = lotController.createTargetLotFromSourceLots(dto, 1L, false);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(List.of(1L), response.getBody());
    }

    @Test
    public void testCreateTargetLotsFromSourceLots() {
        TargetLotsExternalRequestDto dto = new TargetLotsExternalRequestDto();
        Map map = new HashMap<>();
        map.put("a", "a");
        when(lotService.createTargetLotsFromSourceLots(dto, 1L)).thenReturn(map);
        ResponseEntity<?> response = lotController.createTargetLotsFromSourceLots(dto, 1L);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(map, response.getBody());
    }

    @Test
    public void testMigrateNewMappingData() {
        doNothing().when(lotService).migrateData();
        ResponseEntity<?> response = lotController.migrateNewMappingData(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully Migrated Data", response.getBody());
    }

}
