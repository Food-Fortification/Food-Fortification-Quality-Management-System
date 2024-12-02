package org.path.iam.service;

import org.path.iam.dto.requestDto.AddressRequestDto;
import org.path.iam.dto.responseDto.AddressLocationResponseDto;
import org.path.iam.dto.responseDto.AddressResponseDto;
import org.path.iam.dto.responseDto.ListResponse;
import org.path.iam.manager.AddressManager;
import org.path.iam.model.Address;
import org.path.iam.utils.DtoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private AddressManager addressManager;

    @Mock
    private DtoMapper dtoMapper;

    @InjectMocks
    private AddressService addressService;

    private Address address;
    private AddressRequestDto addressRequestDto;
    private AddressResponseDto addressResponseDto;

    @BeforeEach
    void setUp() {
        address = new Address();
        addressRequestDto = new AddressRequestDto();
        addressResponseDto = new AddressResponseDto();
    }

    @Test
    void create() {
        when(dtoMapper.mapToEntity(addressRequestDto)).thenReturn(address);
        addressService.create(addressRequestDto);
        verify(addressManager).create(address);
    }

    @Test
    void update() {
        when(dtoMapper.mapToEntity(addressRequestDto)).thenReturn(address);
        addressService.update(addressRequestDto);
        verify(addressManager).update(address);
    }

    @Test
    void getById() {
        Long id = 1L;
        when(addressManager.findById(id)).thenReturn(address);
        when(dtoMapper.mapToDto(address)).thenReturn(addressResponseDto);
        AddressResponseDto result = addressService.getById(id);
        assertEquals(addressResponseDto, result);
    }

    @Test
    void delete() {
        Long id = 1L;
        addressService.delete(id);
        verify(addressManager).delete(id);
    }

    @Test
    void findAll() {
        List<Address> addressList = Collections.singletonList(address);
        when(addressManager.findAll(anyInt(), anyInt())).thenReturn(addressList);
        when(addressManager.getCount(anyInt(), anyInt(), anyInt())).thenReturn(1L);
        when(dtoMapper.mapToDto(address)).thenReturn(addressResponseDto);

        ListResponse<AddressResponseDto> result = addressService.findAll(1, 10);

        assertNotNull(result);
        assertEquals(1, result.getCount());
    }

    @Test
    void getCompleteAddressForManufacturer() {
        Long manufacturerId = 1L;
        when(addressManager.findByManufacturerId(manufacturerId)).thenReturn(address);

        String result = addressService.getCompleteAddressForManufacturer(manufacturerId);

        assertNotNull(result);
    }

    @Test
    void getCompleteAddressForManufacturer_WithAddress() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        AddressLocationResponseDto dto = new AddressLocationResponseDto();
        dto.setLongitude(address.getLongitude());
        dto.setLatitude(address.getLatitude());
        dto.setPinCode(address.getPinCode());
        dto.setLaneTwo(address.getLaneTwo());
        dto.setLaneOne(address.getLaneOne());


        String result = addressService.getCompleteAddressForManufacturer(address);

        assertNotNull(result);
    }

    @Test
    void getByManufacturerId() {
        Long manufacturerId = 1L;
        when(addressManager.findByManufacturerId(manufacturerId)).thenReturn(address);
        when(dtoMapper.mapToDto(address)).thenReturn(addressResponseDto);

        AddressResponseDto result = addressService.getByManufacturerId(manufacturerId);

        assertEquals(addressResponseDto, result);
    }
}
