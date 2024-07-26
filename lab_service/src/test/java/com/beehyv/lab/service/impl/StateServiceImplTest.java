package com.beehyv.lab.service.impl;

import com.beehyv.lab.dto.requestDto.StateRequestDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.dto.responseDto.LocationResponseDto;
import com.beehyv.lab.dto.responseDto.StateResponseDTO;
import com.beehyv.lab.entity.State;
import com.beehyv.lab.mapper.DTOMapper;
import com.beehyv.lab.manager.StateManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class StateServiceImplTest {

    @Mock
    private StateManager stateManager;

    @Mock
    private DTOMapper dtoMapper;

    @InjectMocks
    private StateServiceImpl stateService;

    private State state;
    private StateRequestDTO stateRequestDTO;
    private StateResponseDTO stateResponseDTO;
    private LocationResponseDto locationResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        state = new State();
        state.setId(1L);
        stateRequestDTO = new StateRequestDTO();
        stateResponseDTO = new StateResponseDTO();
        locationResponseDto = new LocationResponseDto();
    }

    @Test
    void testCreate() {
        when(dtoMapper.mapDtoToEntityState(stateRequestDTO)).thenReturn(state);

        stateService.create(stateRequestDTO);

        verify(stateManager, times(1)).create(any());
    }

    @Test
    void testGetByIdSuccess() {
        when(stateManager.findById(1L)).thenReturn(state);
        when(dtoMapper.mapToResponseDto(state)).thenReturn(stateResponseDTO);

        StateResponseDTO result = stateService.getById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testGetByIdNotFound() {
        when(stateManager.findById(1L)).thenReturn(null);

        StateResponseDTO result = stateService.getById(1L);

        assertNull(result);
    }

    @Test
    void testUpdate() {
        when(dtoMapper.mapDtoToEntityState(stateRequestDTO)).thenReturn(state);

        stateService.update(stateRequestDTO);

        verify(stateManager, times(1)).update(any());
    }

    @Test
    void testDelete() {
        stateService.delete(1L);

        verify(stateManager, times(1)).delete(1L);
    }

    @Test
    void testFindAll() {
        List<State> states = Arrays.asList(state);
        when(stateManager.findAll(0, 10)).thenReturn(states);
        when(dtoMapper.mapToResponseDto(state)).thenReturn(stateResponseDTO);
        when(stateManager.getCount(1, 0, 10)).thenReturn(1L);

        ListResponse<StateResponseDTO> result = stateService.findAll(0, 10);

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());

    }

    @Test
    void testGetAllStatesByCountryId() {
        List<State> states = Arrays.asList(state);
        when(stateManager.findAllByCountryId(1L, null, 0, 10)).thenReturn(states);
        when(dtoMapper.mapToDto(state)).thenReturn(locationResponseDto);
        when(stateManager.getCountForCountryIdAndSearch(1L, null)).thenReturn(1L);

        ListResponse<LocationResponseDto> result = stateService.getAllStatesByCountryId(1L, null, 0, 10);

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());

    }
}