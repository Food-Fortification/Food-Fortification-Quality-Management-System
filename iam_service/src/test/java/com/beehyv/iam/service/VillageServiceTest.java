package com.beehyv.iam.service;

import com.beehyv.iam.dto.requestDto.VillageRequestDto;
import com.beehyv.iam.dto.responseDto.ListResponse;
import com.beehyv.iam.dto.responseDto.LocationResponseDto;
import com.beehyv.iam.dto.responseDto.VillageResponseDto;
import com.beehyv.iam.manager.VillageManager;
import com.beehyv.iam.model.Village;
import com.beehyv.iam.utils.DtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class VillageServiceTest {

    @Mock
    private VillageManager villageManager;

    @Mock
    private DtoMapper dtoMapper;

    @InjectMocks
    private VillageService villageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        VillageRequestDto requestDto = new VillageRequestDto();
        Village village = new Village();
        village.setId(1L);
        when(dtoMapper.mapToEntity(requestDto)).thenReturn(village);
        when(villageManager.create(village)).thenReturn(village);

        Long result = villageService.create(requestDto);

        assertEquals(1L, result);
        verify(dtoMapper, times(1)).mapToEntity(requestDto);
        verify(villageManager, times(1)).create(village);
    }

    @Test
    public void testUpdate() {
        VillageRequestDto requestDto = new VillageRequestDto();
        Village village = new Village();
        when(dtoMapper.mapToEntity(requestDto)).thenReturn(village);

        villageService.update(requestDto);

        verify(dtoMapper, times(1)).mapToEntity(requestDto);
        verify(villageManager, times(1)).update(village);
    }

    @Test
    public void testGetById() {
        Long id = 1L;
        Village village = new Village();
        when(villageManager.findById(id)).thenReturn(village);
        VillageResponseDto responseDto = new VillageResponseDto();
        when(dtoMapper.mapToResponseDto(village)).thenReturn(responseDto);

        // Act
        VillageResponseDto result = villageService.getById(id);

        // Assert
        assertEquals(responseDto, result);
        verify(villageManager, times(1)).findById(id);
        verify(dtoMapper, times(1)).mapToResponseDto(village);
    }

    @Test
    void testFindAll_SuccessfulRetrieval() {
        // Prepare test data
        Integer pageNumber = 1;
        Integer pageSize = 10;
        Village village = new Village();
        List<Village> villages = Collections.singletonList(village);
        VillageResponseDto villageResponseDto = new VillageResponseDto();

        // Mock behavior
        when(villageManager.findAll(pageNumber, pageSize)).thenReturn(villages);
        when(villageManager.getCount(villages.size(), pageNumber, pageSize)).thenReturn(1L);
        when(dtoMapper.mapToResponseDto(village)).thenReturn(villageResponseDto);

        // Call the method
        ListResponse<VillageResponseDto> response = villageService.findAll(pageNumber, pageSize);

        // Verify the response
        assertNotNull(response);
        assertEquals(1, response.getData().size());
    }

    @Test
    void testDelete_SuccessfulDeletion() {
        // Prepare test data
        Long id = 1L;

        // Mock behavior
        doNothing().when(villageManager).delete(id);

        // Call the method
        villageService.delete(id);

        // Verify interactions
        verify(villageManager, times(1)).delete(id);
    }

    @Test
    void testGetAllVillagesByDistrictId_SuccessfulRetrieval() {
        // Prepare test data
        Long districtId = 1L;
        Integer pageNumber = 1;
        Integer pageSize = 10;
        Village village = new Village();
        List<Village> villages = Collections.singletonList(village);
        LocationResponseDto locationResponseDto = new LocationResponseDto();

        // Mock behavior
        when(villageManager.findAllByDistrictId(districtId, pageNumber, pageSize)).thenReturn(villages);
        when(villageManager.getCount(villages.size(), pageNumber, pageSize)).thenReturn(1L);
        when(dtoMapper.mapToDto(village)).thenReturn(locationResponseDto);

        // Call the method
        ListResponse<LocationResponseDto> response = villageService.getAllVillagesByDistrictId(districtId, pageNumber, pageSize);

        // Verify the response
        assertNotNull(response);
        assertEquals(1, response.getData().size());
    }
}
