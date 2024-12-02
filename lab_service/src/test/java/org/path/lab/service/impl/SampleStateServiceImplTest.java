package org.path.lab.service.impl;

import org.path.lab.dto.requestDto.SampleStateRequestDto;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.dto.responseDto.SampleStateResponseDTO;
import org.path.lab.entity.SampleState;
import org.path.lab.mapper.DTOMapper;
import org.path.lab.manager.SampleStateManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class SampleStateServiceImplTest {

    @Mock
    private SampleStateManager sampleStateManager;

    @Mock
    private DTOMapper dtoMapper;

    @InjectMocks
    private SampleStateServiceImpl sampleStateService;

    private SampleState sampleState;
    private SampleStateRequestDto sampleStateRequestDto;
    private SampleStateResponseDTO sampleStateResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleState = new SampleState();
        sampleState.setId(1L);
        sampleStateRequestDto = new SampleStateRequestDto();
        sampleStateResponseDTO = new SampleStateResponseDTO();
    }

    @Test
    void testCreate() {
        when(dtoMapper.mapDtoToEntitySampleState(sampleStateRequestDto)).thenReturn(sampleState);

        sampleStateService.create(sampleStateRequestDto);

        verify(sampleStateManager, times(1)).create(sampleState);
    }

    @Test
    void testGetById() {
        when(sampleStateManager.findById(1L)).thenReturn(sampleState);
        when(dtoMapper.mapEntityToDtoSampleState(sampleState)).thenReturn(sampleStateResponseDTO);

        SampleStateResponseDTO result = sampleStateService.getById(1L);

        assertEquals(sampleStateResponseDTO, result);
    }

    @Test
    void testUpdate() {
        when(dtoMapper.mapDtoToEntitySampleState(sampleStateRequestDto)).thenReturn(sampleState);

        sampleStateService.update(sampleStateRequestDto);

        verify(sampleStateManager, times(1)).update(sampleState);
    }

    @Test
    void testDelete() {
        sampleStateService.delete(1L);

        verify(sampleStateManager, times(1)).delete(1L);
    }

    @Test
    void testGetAll() {
        List<SampleState> sampleStates = Arrays.asList(sampleState);
        when(sampleStateManager.findAll(0, 10)).thenReturn(sampleStates);
        when(dtoMapper.mapEntityToDtoSampleState(sampleState)).thenReturn(sampleStateResponseDTO);
        when(sampleStateManager.getCount(1, 0, 10)).thenReturn(1L);

        ListResponse<SampleStateResponseDTO> result = sampleStateService.getAll(0, 10);

        assertEquals(1, result.getData().size());
        assertEquals(sampleStateResponseDTO, result.getData().get(0));
        assertEquals(1L, result.getCount());
    }
}