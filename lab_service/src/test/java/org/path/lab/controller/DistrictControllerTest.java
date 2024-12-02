package org.path.lab.controller;

import org.path.lab.dto.requestDto.DistrictRequestDTO;
import org.path.lab.dto.responseDto.DistrictResponseDTO;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.dto.responseDto.LocationResponseDto;
import org.path.lab.service.DistrictService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DistrictControllerTest {

    @Mock
    private DistrictService districtService;

    @InjectMocks
    private DistrictController districtController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById() {
        Long id = 1L;
        DistrictResponseDTO expectedResponse =  new DistrictResponseDTO();

        when(districtService.getById(id)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = districtController.getById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(districtService, times(1)).getById(id);
        verifyNoMoreInteractions(districtService);
    }

    @Test
    void testCreate() {
        DistrictRequestDTO dto = new DistrictRequestDTO(null,null,null);

        doNothing().when(districtService).create(dto);

        ResponseEntity<?> responseEntity = districtController.create(dto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Successfully Created", responseEntity.getBody());

        verify(districtService, times(1)).create(dto);
        verifyNoMoreInteractions(districtService);
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        DistrictRequestDTO dto = new DistrictRequestDTO(null,null,null);
        dto.setId(id);

        doNothing().when(districtService).update(dto);

        ResponseEntity<?> responseEntity = districtController.update(id, dto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("successfully updated", responseEntity.getBody());

        verify(districtService, times(1)).update(dto);
        verifyNoMoreInteractions(districtService);
    }

    @Test
    void testDelete() {
        Long id = 1L;

        doNothing().when(districtService).delete(id);

        ResponseEntity<?> responseEntity = districtController.delete(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(districtService, times(1)).delete(id);
        verifyNoMoreInteractions(districtService);
    }

    @Test
    void testGetAll() {
        Integer pageNumber = 1;
        Integer pageSize = 10;
        ListResponse<DistrictResponseDTO> expectedResponse = new ListResponse<>();

        when(districtService.getAll(pageNumber, pageSize)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = districtController.getAll(pageNumber, pageSize);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(districtService, times(1)).getAll(pageNumber, pageSize);
        verifyNoMoreInteractions(districtService);
    }

    @Test
    void testGetAllByStateId() {
        Long stateId = 1L;
        String search = "test";
        Integer pageNumber = 1;
        Integer pageSize = 10;
        ListResponse<LocationResponseDto> expectedResponse = new ListResponse<>();

        when(districtService.getAllDistrictsByStateId(stateId, search, pageNumber, pageSize)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = districtController.getAllByStateId(stateId, search, pageNumber, pageSize);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(districtService, times(1)).getAllDistrictsByStateId(stateId, search, pageNumber, pageSize);
        verifyNoMoreInteractions(districtService);
    }
}