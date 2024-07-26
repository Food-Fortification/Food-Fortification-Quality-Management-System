package com.beehyv.lab.controller;

import com.beehyv.lab.dto.requestDto.VillageRequestDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.dto.responseDto.LocationResponseDto;
import com.beehyv.lab.dto.responseDto.VillageResponseDTO;
import com.beehyv.lab.service.VillageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class VillageControllerTest {

    @Mock
    private VillageService villageService;

    @InjectMocks
    private VillageController villageController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById() {
        Long id = 1L;
        VillageResponseDTO expectedResponse = new VillageResponseDTO();

        when(villageService.getById(id)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = villageController.getById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(villageService, times(1)).getById(id);
        verifyNoMoreInteractions(villageService);
    }

    @Test
    void testCreate() {
        VillageRequestDTO villageRequestDTO = new VillageRequestDTO();

        when(villageService.create(villageRequestDTO)).thenReturn(1L);

        ResponseEntity<?> responseEntity = villageController.create(villageRequestDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(1L, responseEntity.getBody());

        verify(villageService, times(1)).create(villageRequestDTO);
        verifyNoMoreInteractions(villageService);
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        VillageRequestDTO villageRequestDTO = new VillageRequestDTO();

        doNothing().when(villageService).update(villageRequestDTO);

        ResponseEntity<?> responseEntity = villageController.update(id, villageRequestDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("successfully updated", responseEntity.getBody());

        verify(villageService, times(1)).update(villageRequestDTO);
        verifyNoMoreInteractions(villageService);
    }

    @Test
    void testDelete() {
        Long id = 1L;

        doNothing().when(villageService).delete(id);

        ResponseEntity<?> responseEntity = villageController.delete(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(villageService, times(1)).delete(id);
        verifyNoMoreInteractions(villageService);
    }

    @Test
    void testGetAll() {
        Integer pageNumber = 1;
        Integer pageSize = 10;
        ListResponse<VillageResponseDTO> expectedResponse = new ListResponse<>();

        when(villageService.findAll(pageNumber, pageSize)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = villageController.getAll(pageNumber, pageSize);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(villageService, times(1)).findAll(pageNumber, pageSize);
        verifyNoMoreInteractions(villageService);
    }


}