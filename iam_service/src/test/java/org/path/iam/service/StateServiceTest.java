package org.path.iam.service;

import org.path.iam.dto.requestDto.StateRequestDto;
import org.path.iam.dto.responseDto.ListResponse;
import org.path.iam.dto.responseDto.StateResponseDto;
import org.path.iam.manager.StateManager;
import org.path.iam.model.State;
import org.path.iam.utils.DtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StateServiceTest {

    @Mock
    private StateManager stateManager;

    @Mock
    private DtoMapper dtoMapper;

    @InjectMocks
    private StateService stateService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        StateRequestDto requestDto = new StateRequestDto();
        State state = new State();
        when(dtoMapper.mapToEntity(requestDto)).thenReturn(state);

        stateService.create(requestDto);

        verify(stateManager, times(1)).create(state);
        verify(dtoMapper, times(1)).mapToEntity(requestDto);
    }

    @Test
    public void testUpdate() {
        StateRequestDto requestDto = new StateRequestDto();
        State state = new State();
        when(dtoMapper.mapToEntity(requestDto)).thenReturn(state);

        stateService.update(requestDto);

        verify(stateManager, times(1)).update(state);
        verify(dtoMapper, times(1)).mapToEntity(requestDto);
    }

    @Test
    public void testGetById() {
        Long id = 1L;
        State state = new State();
        when(stateManager.findById(id)).thenReturn(state);
        StateResponseDto responseDto = new StateResponseDto();
        when(dtoMapper.mapToResponseDto(state)).thenReturn(responseDto);

        StateResponseDto result = stateService.getById(id);

        assertEquals(responseDto, result);
        verify(stateManager, times(1)).findById(id);
        verify(dtoMapper, times(1)).mapToResponseDto(state);
    }

    @Test
    public void testFindAll() {
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<State> entities = new ArrayList<>();
        entities.add(new State());
        when(stateManager.findAll(pageNumber, pageSize)).thenReturn(entities);
        Long count = 1L;
        when(stateManager.getCount(entities.size(), pageNumber, pageSize)).thenReturn(count);
        List<StateResponseDto> responseDtos = new ArrayList<>();
        responseDtos.add(new StateResponseDto());
        when(dtoMapper.mapToResponseDto(entities.get(0))).thenReturn(responseDtos.get(0));

        ListResponse<StateResponseDto> result = stateService.findAll(pageNumber, pageSize);

        assertEquals(entities.size(), result.getData().size());
        verify(stateManager, times(1)).findAll(pageNumber, pageSize);
        verify(stateManager, times(1)).getCount(entities.size(), pageNumber, pageSize);
        verify(dtoMapper, times(entities.size())).mapToResponseDto(any(State.class));
    }

    @Test
    public void testDelete() {
        Long id = 1L;

        stateService.delete(id);

        verify(stateManager, times(1)).delete(id);
    }
}
