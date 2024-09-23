package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.StateDao;
import com.beehyv.fortification.entity.State;
import com.beehyv.fortification.entity.StateType;
import com.beehyv.fortification.enums.ActionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class StateManagerTest {

    @Mock
    private StateDao stateDao;

    private StateManager stateManager;

    private String name;
    private List<String> names;
    private StateType stateType;
    private Integer pageNumber;
    private Integer pageSize;
    private ActionType actionType;
    private State state;

    @BeforeEach
    public void setUp() {
        stateManager = new StateManager(stateDao);
        name = "state1";
        names = Arrays.asList("state1", "state2");
        stateType = StateType.LOT; // replace with actual StateType
        pageNumber = 1;
        pageSize = 10;
        actionType = ActionType.lab; // replace with actual ActionType
        state = new State();

        when(stateDao.findByName(name)).thenReturn(state);
        when(stateDao.findByNames(names)).thenReturn(Collections.singletonList(state));
        when(stateDao.findByActionNames(names, stateType)).thenReturn(Collections.singletonList(state));
        when(stateDao.findAllByType(stateType, pageNumber, pageSize)).thenReturn(Collections.singletonList(state));
        when(stateDao.findAllByActionType(actionType)).thenReturn(Collections.singletonList(state));
    }

    @Test
    public void testFindByName() {
        State result = stateManager.findByName(name);
        assertEquals(state, result);
    }

    @Test
    public void testFindByNames() {
        List<State> result = stateManager.findByNames(names);
        assertEquals(Collections.singletonList(state), result);
    }

    @Test
    public void testFindByActionNames() {
        List<State> result = stateManager.findByActionNames(names, stateType);
        assertEquals(Collections.singletonList(state), result);
    }

    @Test
    public void testFindAllByType() {
        List<State> result = stateManager.findAllByType(stateType, pageNumber, pageSize);
        assertEquals(Collections.singletonList(state), result);
    }

    @Test
    public void testFindAllByActionType() {
        List<State> result = stateManager.findAllByActionType(actionType);
        assertEquals(Collections.singletonList(state), result);
    }
}