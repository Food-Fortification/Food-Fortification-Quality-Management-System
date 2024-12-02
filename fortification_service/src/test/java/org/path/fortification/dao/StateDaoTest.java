package org.path.fortification.dao;

import org.path.fortification.entity.State;
import org.path.fortification.entity.StateType;
import org.path.fortification.enums.ActionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class StateDaoTest {

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<State> typedQuery;

    @InjectMocks
    private StateDao stateDao;

    @BeforeEach
    void setUp() {
        when(em.createQuery(any(String.class), any(Class.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(any(String.class), any())).thenReturn(typedQuery);
    }

    @Test
    void testFindByName() {
        // Arrange
        State expectedState = new State();
        when(typedQuery.getSingleResult()).thenReturn(expectedState);

        // Act
        State actualState = stateDao.findByName("test");

        // Assert
        assertEquals(expectedState, actualState);
    }

    @Test
    void testFindByNames() {
        // Arrange
        List<State> expectedStates = Collections.singletonList(new State());
        when(typedQuery.getResultList()).thenReturn(expectedStates);

        // Act
        List<State> actualStates = stateDao.findByNames(Collections.singletonList("test"));

        // Assert
        assertEquals(expectedStates, actualStates);
    }

    @Test
    void testFindAllByType() {
        // Arrange
        List<State> expectedStates = Collections.singletonList(new State());
        when(typedQuery.getResultList()).thenReturn(expectedStates);

        // Act
        List<State> actualStates = stateDao.findAllByType(StateType.BATCH, 1, 10);

        // Assert
        assertEquals(expectedStates, actualStates);
    }

    @Test
    void testFindAllByActionType() {
        // Arrange
        List<State> expectedStates = Collections.singletonList(new State());
        when(typedQuery.getResultList()).thenReturn(expectedStates);

        // Act
        List<State> actualStates = stateDao.findAllByActionType(ActionType.lab);

        // Assert
        assertEquals(expectedStates, actualStates);
    }

    @Test
    void testFindByActionNames() {
        // Arrange
        List<State> expectedStates = Collections.singletonList(new State());
        when(typedQuery.getResultList()).thenReturn(expectedStates);

        // Act
        List<State> actualStates = stateDao.findByActionNames(Collections.singletonList("test"), StateType.LOT);

        // Assert
        assertEquals(expectedStates, actualStates);
    }

}