package org.path.lab.service.impl;

import org.path.lab.dto.requestDto.VillageRequestDTO;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.dto.responseDto.LocationResponseDto;
import org.path.lab.dto.responseDto.VillageResponseDTO;
import org.path.lab.entity.Village;
import org.path.lab.mapper.DTOMapper;
import org.path.lab.manager.VillageManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class VillageServiceImplTest {

    @Mock
    private VillageManager villageManager;

    @Mock
    private DTOMapper mapper;

    @InjectMocks
    private VillageServiceImpl villageService;

    private Village village= new Village();
    private VillageRequestDTO villageRequestDTO;
    private VillageResponseDTO villageResponseDTO;
    private LocationResponseDto locationResponseDto;

    @BeforeEach
    void setUp() {
        village.setId(1L);
        villageRequestDTO = new VillageRequestDTO();
        villageResponseDTO = new VillageResponseDTO();
        locationResponseDto = new LocationResponseDto();

        when(mapper.mapDtoToEntityVillage(any())).thenReturn(village);
        when(villageManager.create(any())).thenReturn(village);
    }

    @Test
    void testCreateVillage() {


        Long villageId = villageService.create(villageRequestDTO);

        assertEquals(village.getId(), villageId);
        verify(villageManager, times(1)).create(any());
    }

    @Test
    void testGetByIdSuccess() {
        when(villageManager.findById(1L)).thenReturn(village);
        when(mapper.mapToResponseDto(any(Village.class))).thenReturn(villageResponseDTO);

        VillageResponseDTO result = villageService.getById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testGetByIdNotFound() {
        when(villageManager.findById(1L)).thenReturn(null);

        VillageResponseDTO result = villageService.getById(1L);

        assertNull(result);
    }

    @Test
    void testUpdateVillage() {
        when(mapper.mapDtoToEntityVillage(villageRequestDTO)).thenReturn(village);

        villageService.update(villageRequestDTO);

        verify(villageManager, times(1)).update(any());
    }

    @Test
    void testDeleteVillage() {
        villageService.delete(1L);

        verify(villageManager, times(1)).delete(1L);
    }

    @Test
    void testFindAllVillages() {
        List<Village> villages = Arrays.asList(village);
        when(villageManager.findAll(0, 10)).thenReturn(villages);
        when(mapper.mapToResponseDto(village)).thenReturn(villageResponseDTO);
        when(villageManager.getCount(1, 0, 10)).thenReturn(1L);

        ListResponse<VillageResponseDTO> result = villageService.findAll(0, 10);

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());

    }

    @Test
    void testGetAllVillagesByDistrictId() {
        List<Village> villages = Arrays.asList(village);
        when(villageManager.findAllByDistrictId(1L, 0, 10)).thenReturn(villages);
        when(mapper.mapToDto(village)).thenReturn(locationResponseDto);
        when(villageManager.getCount(1L)).thenReturn(1L);

        ListResponse<LocationResponseDto> result = villageService.getAllVillagesByDistrictId(1L, 0, 10);

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());

    }
}








//Explanations:
//
//testCreateVillage: This test case verifies the create method of the VillageServiceImpl. It mocks the mapper.mapDtoToEntityVillage and villageManager.create methods, and asserts that the correct village ID is returned and that the villageManager.create method was called exactly once with the correct arguments.
//testGetByIdSuccess: This test case verifies the getById method when a valid ID is provided. It mocks the villageManager.findById and mapper.mapToResponseDto methods, and asserts that the correct VillageResponseDTO is returned.
//testGetByIdNotFound: This test case verifies the getById method when an invalid or non-existent ID is provided. It mocks the villageManager.findById method to return null, and asserts that the method returns null.
//testUpdateVillage: This test case verifies the update method. It mocks the mapper.mapDtoToEntityVillage method, and verifies that the villageManager.update method is called exactly once with the correct arguments.
//testDeleteVillage: This test case verifies the delete method. It verifies that the villageManager.delete method is called exactly once with the correct arguments.
//testFindAllVillages: This test case verifies the findAll method. It mocks the villageManager.findAll, mapper.mapToResponseDto, and villageManager.getCount methods, and asserts that the correct ListResponse is returned with the expected data and count.
//testGetAllVillagesByDistrictId: This test case verifies the getAllVillagesByDistrictId method. It mocks the villageManager.findAllByDistrictId, mapper.mapToDto, and villageManager.getCount methods, and asserts that the correct ListResponse is returned with the expected data and count.