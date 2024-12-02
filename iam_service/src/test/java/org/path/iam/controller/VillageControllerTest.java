package org.path.iam.controller;

import org.path.iam.dto.requestDto.VillageRequestDto;
import org.path.iam.dto.responseDto.ListResponse;
import org.path.iam.dto.responseDto.LocationResponseDto;
import org.path.iam.dto.responseDto.VillageResponseDto;
import org.path.iam.service.VillageService;
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
        VillageResponseDto villageResponseDto = new VillageResponseDto();
        when(villageService.getById(1L)).thenReturn(villageResponseDto);

        ResponseEntity<?> response = villageController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAll() {
        ListResponse<VillageResponseDto> listvillage = new ListResponse<>();
        when(villageService.findAll(null, null)).thenReturn(listvillage);

        ResponseEntity<?> response = villageController.getAll(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCreate() {
        VillageRequestDto requestDto = new VillageRequestDto();
        when(villageService.create(requestDto)).thenReturn(1L);

        ResponseEntity<?> response = villageController.create(requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testUpdate() {
        VillageRequestDto requestDto = new VillageRequestDto();
        requestDto.setId(1L);
        doNothing().when(villageService).update(requestDto);

        ResponseEntity<?> response = villageController.update(1L, requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully Updated", response.getBody());
    }

    @Test
    void testDelete() {
        doNothing().when(villageService).delete(1L);

        ResponseEntity<?> response = villageController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully Deleted", response.getBody());
    }

    @Test
    void testGetAllVillages() {
        ListResponse<LocationResponseDto> listlocationresponse = new ListResponse<>();
        when(villageService.getAllVillagesByDistrictId(1L, null, null)).thenReturn(listlocationresponse);

        ResponseEntity<?> response = villageController.getAllVillages(1L, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
