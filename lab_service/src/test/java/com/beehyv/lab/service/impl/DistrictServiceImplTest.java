package com.beehyv.lab.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.beehyv.lab.dto.requestDto.DistrictRequestDTO;
import com.beehyv.lab.dto.responseDto.DistrictResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.dto.responseDto.LocationResponseDto;
import com.beehyv.lab.entity.District;
import com.beehyv.lab.entity.State;
import com.beehyv.lab.manager.DistrictManager;
import com.beehyv.lab.mapper.DTOMapper;
import com.beehyv.lab.service.impl.DistrictServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class DistrictServiceImplTest {

    @Mock
    private DistrictManager districtManager;

    @Mock
    private DTOMapper mapper;

    @InjectMocks
    private DistrictServiceImpl districtService;

    private DistrictRequestDTO districtRequestDTO;
    private DistrictResponseDTO districtResponseDTO;
    private District district;

    @BeforeEach
    void setUp() {
        // Initialize DistrictRequestDTO with the expected constructor arguments
        DistrictRequestDTO districtRequestDTO = new DistrictRequestDTO(1L, "Test District", 1L);

        // Initialize DistrictResponseDTO
        districtResponseDTO = new DistrictResponseDTO();

        // Initialize District entity
        district = new District();
        district.setId(1L);
        district.setName("Test District");
        // Set other necessary fields
    }



    @Test
    void testGetById() {
        when(districtManager.findById(1L)).thenReturn(district);

        DistrictResponseDTO result = districtService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test District", result.getName());
    }

    @Test
    void testGetById_NotFound() {
        when(districtManager.findById(1L)).thenReturn(null);

        DistrictResponseDTO result = districtService.getById(1L);

        assertNull(result);
    }




    @Test
    void testDelete() {
        districtService.delete(1L);

        verify(districtManager, times(1)).delete(1L);
    }

    @Test
    void testGetAll() {
        List<District> entities = Collections.singletonList(district);
        when(districtManager.findAll(0, 10)).thenReturn(entities);
        when(districtManager.getCount(entities.size(), 0, 10)).thenReturn(1L);

        ListResponse<DistrictResponseDTO> result = districtService.getAll(0, 10);

        assertNotNull(result);
        assertEquals(1, result.getData().size());
        assertEquals("Test District", result.getData().get(0).getName());
    }

    @Test
    void testGetAllDistrictsByStateId() {
        List<District> entities = Collections.singletonList(district);
        when(districtManager.findAllByStateId(1L, "Test", 0, 10)).thenReturn(entities);
        when(districtManager.getCountByStateId(1L, "Test")).thenReturn(1L);

        ListResponse<LocationResponseDto> result = districtService.getAllDistrictsByStateId(1L, "Test", 0, 10);

        assertNotNull(result);
        assertEquals(1, result.getData().size());
        assertEquals("Test District", result.getData().get(0).getName());
    }
}
