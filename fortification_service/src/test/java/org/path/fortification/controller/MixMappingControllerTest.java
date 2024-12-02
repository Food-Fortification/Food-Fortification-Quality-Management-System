package org.path.fortification.controller;

import org.path.fortification.dto.requestDto.MixMappingCommentsRequestDto;
import org.path.fortification.dto.requestDto.MixMappingRequestDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.dto.responseDto.MixMappingResponseDto;
import org.path.fortification.service.MixMappingService;
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

public class MixMappingControllerTest {

    @InjectMocks
    private MixMappingController mixMappingController;

    @Mock
    private MixMappingService mixMappingService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMixMappingById() {
        Long categoryId = 1L;
        Long batchId = 2L;
        Long id = 3L;
        MixMappingResponseDto dto = new MixMappingResponseDto();
        when(mixMappingService.getMixMappingById(id)).thenReturn(dto);
        ResponseEntity<MixMappingResponseDto> response = mixMappingController.getMixMappingById(categoryId, batchId, id);
        assertEquals(ResponseEntity.ok(dto), response);
    }

    @Test
    public void testGetAllMixMappingsForCategoryAndSourceBatch() {
        Long categoryId = 1L;
        Long batchId = 2L;
        Integer pageNumber = 1;
        Integer pageSize = 10;
        ListResponse<MixMappingResponseDto> listResponse = new ListResponse<>(0L, Collections.emptyList());
        when(mixMappingService.getAllMixMappingsByTargetBatch(batchId, pageNumber, pageSize)).thenReturn(listResponse);
        ResponseEntity<ListResponse<MixMappingResponseDto>> response = mixMappingController.getAllMixMappingsForCategoryAndSourceBatch(categoryId, batchId, pageNumber, pageSize);
        assertEquals(ResponseEntity.ok(listResponse), response);
    }


    @Test
    public void testCreateMixMapping() {
        Long categoryId = 1L;
        Long batchId = 2L;
        MixMappingRequestDto dto = new MixMappingRequestDto();
        doNothing().when(mixMappingService).createMixMapping(batchId, dto);
        ResponseEntity<?> response = mixMappingController.createMixMapping(categoryId, batchId, dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testUpdateMixMapping() {
        Long categoryId = 1L;
        Long batchId = 2L;
        Long id = 3L;
        MixMappingRequestDto dto = new MixMappingRequestDto();
        dto.setId(id);
        doNothing().when(mixMappingService).updateMixMapping(batchId, dto);
        ResponseEntity<?> response = mixMappingController.updateMixMapping(categoryId, batchId, id, dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteMixMapping() {
        Long categoryId = 1L;
        Long batchId = 2L;
        Long id = 3L;
        doNothing().when(mixMappingService).deleteMixMapping(id);
        ResponseEntity<String> response = mixMappingController.deleteMixMapping(categoryId, batchId, id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("MixMapping successfully deleted!", response.getBody());
    }

    @Test
    public void testUpdateBatchMixes() {
        Long categoryId = 1L;
        Long batchId = 2L;
        MixMappingCommentsRequestDto mixesInformation = new MixMappingCommentsRequestDto();
        doNothing().when(mixMappingService).updateBatchMixes(mixesInformation, batchId);
        ResponseEntity<?> response = mixMappingController.updateBatchMixes(mixesInformation, categoryId, batchId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}