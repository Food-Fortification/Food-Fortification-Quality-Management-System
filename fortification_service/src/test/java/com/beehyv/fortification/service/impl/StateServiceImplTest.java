package com.beehyv.fortification.service.impl;

import com.beehyv.fortification.dto.requestDto.StateRequestDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.StateResponseDto;
import com.beehyv.fortification.entity.State;
import com.beehyv.fortification.entity.StateType;
import com.beehyv.fortification.manager.StateManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StateServiceImplTest {

    @Mock
    private StateManager manager;

    @InjectMocks
    private StateServiceImpl stateService;

    private StateRequestDto requestDto;
    private State state;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        requestDto = new StateRequestDto();
        requestDto.setName("Active");
        requestDto.setDisplayName("Active State");
        requestDto.setActionName("Activate");
        requestDto.setType(StateType.BATCH);

        state = new State();
        state.setId(1L);
        state.setName("Active");
        state.setDisplayName("Active State");
        state.setActionName("Activate");
        state.setType(StateType.BATCH);
    }

    @Test
    void createState_ValidRequest_ShouldCreateState() {
        when(manager.create(any(State.class))).thenReturn(state);

        stateService.createState(requestDto);

        verify(manager, times(1)).create(any(State.class));
    }

    @Test
    void getStateById_ValidId_ShouldReturnStateDto() {
        when(manager.findById(state.getId())).thenReturn(state);

        StateResponseDto response = stateService.getStateById(state.getId());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(state.getId(), response.getId());
        Assertions.assertEquals(state.getName(), response.getName());
        Assertions.assertEquals(state.getDisplayName(), response.getDisplayName());
        Assertions.assertEquals(state.getActionName(), response.getActionName());
        Assertions.assertEquals(state.getType(), response.getType());
    }

    @Test
    void getAllStates_WithType_ShouldReturnStateList() {
        List<State> states = new ArrayList<>();
        State state1 = new State();
        state1.setId(1L);
        state1.setType(StateType.BATCH);
        State state2 = new State();
        state2.setId(2L);
        state2.setType(StateType.BATCH);
        states.add(state1);
        states.add(state2);

        when(manager.findAllByType(StateType.BATCH, 0, 10)).thenReturn(states);
        when(manager.getCount(states.size(), 0, 10)).thenReturn(2L);

        ListResponse<StateResponseDto> response = stateService.getAllStates(StateType.BATCH, 0, 10);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(2, response.getCount());
    }

    @Test
    void getAllStates_WithoutType_ShouldReturnStateList() {
        List<State> states = new ArrayList<>();
        State state1 = new State();
        state1.setId(1L);
        State state2 = new State();
        state2.setId(2L);
        states.add(state1);
        states.add(state2);

        when(manager.findAll(0, 10)).thenReturn(states);
        when(manager.getCount(states.size(), 0, 10)).thenReturn(2L);

        ListResponse<StateResponseDto> response = stateService.getAllStates(null, 0, 10);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(2, response.getCount());

    }

    @Test
    void updateState_ValidRequest_ShouldUpdateState() {
        requestDto.setId(state.getId());
        requestDto.setName("Updated Name");
        requestDto.setDisplayName("Updated Display Name");
        requestDto.setActionName("Updated Action Name");

        when(manager.findById(state.getId())).thenReturn(state);

        stateService.updateState(requestDto);

        Assertions.assertEquals("Updated Name", state.getName());
        Assertions.assertEquals("Updated Display Name", state.getDisplayName());
        Assertions.assertEquals("Updated Action Name", state.getActionName());
        verify(manager, times(1)).update(state);
    }

    @Test
    void deleteState_ValidId_ShouldDeleteState() {
        stateService.deleteState(state.getId());

        verify(manager, times(1)).delete(state.getId());
    }
}


//tests include-
//createState_ValidRequest_ShouldCreateState: This test verifies that the createState method creates a new State entity correctly.
//getStateById_ValidId_ShouldReturnStateDto: This test verifies that the getStateById method returns the correct StateResponseDto for a given State ID.
//getAllStates_WithType_ShouldReturnStateList: This test verifies that the getAllStates method returns a ListResponse containing the correct list of StateResponseDto objects filtered by the provided StateType.
//getAllStates_WithoutType_ShouldReturnStateList: This test verifies that the getAllStates method returns a ListResponse containing all StateResponseDto objects when no StateType is provided.
//pdateState_ValidRequest_ShouldUpdateState: This test verifies that the updateState method updates an existing State entity correctly with the provided StateRequestDto.
//deleteState_ValidId_ShouldDeleteState: This test verifies that the deleteState method deletes an existing State entity for the given ID.