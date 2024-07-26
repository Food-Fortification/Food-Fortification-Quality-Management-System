package com.beehyv.lab.service.impl;

import com.beehyv.lab.dto.requestDto.AddressRequestDTO;
import com.beehyv.lab.dto.responseDto.AddressResponseDTO;
import com.beehyv.lab.dto.responseDto.LabNameAddressResponseDto;
import com.beehyv.lab.entity.Address;
import com.beehyv.lab.entity.Lab;
import com.beehyv.lab.helper.ServiceUtils;
import com.beehyv.lab.manager.AddressManager;
import com.beehyv.lab.manager.LabManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AddressServiceImplTest {

    @Mock
    private AddressManager addressManager;

    @Mock
    private LabManager labManager;

    @InjectMocks
    private AddressServiceImpl addressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateAddress() {
        AddressRequestDTO requestDTO = new AddressRequestDTO(
                1L, "Updated Lane One", "Updated Lane Two", 456L, "654321", 34.56, 78.90
        );        addressService.create(requestDTO);
        verify(addressManager, times(1)).create(any(Address.class));
    }

    @Test
    void testUpdateAddress() {
        AddressRequestDTO requestDTO = new AddressRequestDTO(
                1L, "Updated Lane One", "Updated Lane Two", 456L, "654321", 34.56, 78.90
        );        addressService.update(requestDTO);
        verify(addressManager, times(1)).update(any(Address.class));
    }

    @Test
    void testDeleteAddress() {
        Long id = 1L;
        addressService.delete(id);
        verify(addressManager, times(1)).delete(id);
    }


    @Test
    void testGetCompleteAddressForLab() {
        Long labId = 1L;
        Lab lab = new Lab();

        when(labManager.findAllByIds(Collections.singleton(labId))).thenReturn(Map.of(labId, lab));

        try (MockedStatic<ServiceUtils> mockedServiceUtils = mockStatic(ServiceUtils.class)) {
            mockedServiceUtils.when(() -> ServiceUtils.getCompleteAddressForLab(any(Lab.class)))
                    .thenReturn("Mocked Complete Address");

            Map<Long, Map<String, String>> result = addressService.getCompleteAddressForLab(Collections.singletonList(labId));

            assertEquals(1, result.size());
        }
    }

    @Test
    void testGetNameAndCompleteAddressForLab() {
        Long labId = 1L;
        Lab lab = new Lab();
        lab.setId(labId);
        lab.setName("Test Lab");

        when(labManager.findById(labId)).thenReturn(lab);

        try (MockedStatic<ServiceUtils> mockedServiceUtils = mockStatic(ServiceUtils.class)) {
            mockedServiceUtils.when(() -> ServiceUtils.getCompleteAddressForLab(any(Lab.class)))
                    .thenReturn("Mocked Complete Address");

            LabNameAddressResponseDto responseDto = addressService.getNameAndCompleteAddressForLab(labId);

            assertEquals(labId, responseDto.getId());
            assertEquals("Test Lab", responseDto.getName());
            assertEquals("Mocked Complete Address", responseDto.getCompleteAddress());
        }
    }
}
