package com.beehyv.iam.controller;

import com.beehyv.iam.dto.requestDto.ManufacturerAttributeScoreRequestDto;
import com.beehyv.iam.dto.responseDto.ManufacturerAttributeScoreResponseDto;
import com.beehyv.iam.service.ManufacturerAttributeScoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class ManufacturerAttributeScoreControllerTest {

    @Mock
    private ManufacturerAttributeScoreService manufacturerAttributeScoreService;

    @InjectMocks
    private ManufacturerAttributeScoreController manufacturerAttributeScoreController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateManufacturerAttributeScore() {
        ManufacturerAttributeScoreRequestDto dto = new ManufacturerAttributeScoreRequestDto();
        Long createdId = 1L;
        when(manufacturerAttributeScoreService.create(any(ManufacturerAttributeScoreRequestDto.class))).thenReturn(createdId);

        ResponseEntity<Long> response = manufacturerAttributeScoreController.createManufacturerAttributeScore(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdId, response.getBody());
    }

    @Test
    void testUpdate() {
        ManufacturerAttributeScoreRequestDto dto = new ManufacturerAttributeScoreRequestDto();
        doNothing().when(manufacturerAttributeScoreService).update(any(ManufacturerAttributeScoreRequestDto.class));

        ResponseEntity<?> response = manufacturerAttributeScoreController.update(1L, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("successfully updated", response.getBody());
    }

    @Test
    void testDelete() {
        doNothing().when(manufacturerAttributeScoreService).delete(anyLong());

        ResponseEntity<?> response = manufacturerAttributeScoreController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetById() {
        ManufacturerAttributeScoreResponseDto responseDto = new ManufacturerAttributeScoreResponseDto();
        when(manufacturerAttributeScoreService.getById(anyLong())).thenReturn(responseDto);

        ResponseEntity<ManufacturerAttributeScoreResponseDto> response = manufacturerAttributeScoreController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }
}
