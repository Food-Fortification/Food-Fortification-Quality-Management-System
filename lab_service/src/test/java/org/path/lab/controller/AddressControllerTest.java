package org.path.lab.controller;

import org.path.lab.dto.requestDto.AddressRequestDTO;
import org.path.lab.dto.responseDto.AddressResponseDTO;
import org.path.lab.dto.responseDto.LabNameAddressResponseDto;
import org.path.lab.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AddressControllerTest {

    @Mock
    private AddressService addressService;

    @InjectMocks
    private AddressController addressController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById() {
        Long id = 1L;
        AddressResponseDTO expectedResponse = new AddressResponseDTO();

        when(addressService.getById(id)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = addressController.getById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(addressService, times(1)).getById(id);
        verifyNoMoreInteractions(addressService);
    }

    @Test
    void testCreate() {
        AddressRequestDTO dto = new AddressRequestDTO(null,null,null,null,null,null,null);
        String expectedResponse = "Successfully Created";

        doNothing().when(addressService).create(dto);

        ResponseEntity<?> responseEntity = addressController.create(dto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(addressService, times(1)).create(dto);
        verifyNoMoreInteractions(addressService);
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        AddressRequestDTO dto = new AddressRequestDTO(null,null,null,null,null,null,null);
        dto.setId(id);
        String expectedResponse = "successfully updated";

        doNothing().when(addressService).update(dto);

        ResponseEntity<?> responseEntity = addressController.update(id, dto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(addressService, times(1)).update(dto);
        verifyNoMoreInteractions(addressService);
    }

    @Test
    void testDelete() {
        Long id = 1L;

        doNothing().when(addressService).delete(id);

        ResponseEntity<?> responseEntity = addressController.delete(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(addressService, times(1)).delete(id);
        verifyNoMoreInteractions(addressService);
    }

    @Test
    void testGetCompleteAddressForLab() {
        List<Long> labIds = Collections.singletonList(1L);
        Map<Long, Map<String, String>> expectedResponse = Collections.singletonMap(1L, Collections.singletonMap("key", "value"));

        when(addressService.getCompleteAddressForLab(labIds)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = addressController.getCompleteAddressForLab(labIds);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(addressService, times(1)).getCompleteAddressForLab(labIds);
        verifyNoMoreInteractions(addressService);
    }

    @Test
    void testGetNameAndCompleteAddressForLab() {
        Long labId = 1L;
        LabNameAddressResponseDto expectedResponse = new LabNameAddressResponseDto();

        when(addressService.getNameAndCompleteAddressForLab(labId)).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = addressController.getNameAndCompleteAddressForLab(labId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(addressService, times(1)).getNameAndCompleteAddressForLab(labId);
        verifyNoMoreInteractions(addressService);
    }
}