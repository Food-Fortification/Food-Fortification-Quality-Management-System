package com.beehyv.iam.controller;

import com.beehyv.iam.dto.requestDto.AddressRequestDto;
import com.beehyv.iam.dto.responseDto.AddressResponseDto;
import com.beehyv.iam.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

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
    void getById_ReturnsAddressResponseDto_WhenValidIdProvided() {
        AddressResponseDto responseDto = new AddressResponseDto();
        when(addressService.getById(anyLong())).thenReturn(responseDto);

        ResponseEntity<?> responseEntity = addressController.getById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseDto, responseEntity.getBody());
    }


    @Test
    void create_ReturnsHttpStatusCreated_WhenValidAddressRequestDtoProvided() {
        AddressRequestDto requestDto = new AddressRequestDto();
        ResponseEntity<?> responseEntity = addressController.create(requestDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void update_ReturnsHttpStatusOk_WhenValidIdAndAddressRequestDtoProvided() {
        AddressRequestDto requestDto = new AddressRequestDto();
        ResponseEntity<?> responseEntity = addressController.update(1L, requestDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void delete_ReturnsHttpStatusOk_WhenValidIdProvided() {
        ResponseEntity<?> responseEntity = addressController.delete(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getByManufacturerId_ReturnsAddressResponseDto_WhenValidManufacturerIdProvided() {
        AddressResponseDto responseDto = new AddressResponseDto();
        when(addressService.getByManufacturerId(anyLong())).thenReturn(responseDto);

        ResponseEntity<?> responseEntity = addressController.getByManufacturerId(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseDto, responseEntity.getBody());
    }
}
