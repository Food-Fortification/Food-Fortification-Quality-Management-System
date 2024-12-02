package org.path.iam.manager;

import org.path.iam.dao.StateDao;
import org.path.iam.model.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StateManagerTest {

    @Mock
    private StateDao stateDao;

    @InjectMocks
    private StateManager stateManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllByCountryId() {
        // Prepare test data
        Long countryId = 1L;
        String search = "test";
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<State> expectedStates = new ArrayList<>(); // Create a dummy list of State objects

        // Mock behavior of DAO
        when(stateDao.findAllByCountryId(countryId, search, pageNumber, pageSize)).thenReturn(expectedStates);

        // Call method under test
        List<State> actualStates = stateManager.findAllByCountryId(countryId, search, pageNumber, pageSize);

        // Verify
        assertEquals(expectedStates, actualStates);
        verify(stateDao, times(1)).findAllByCountryId(countryId, search, pageNumber, pageSize);
    }

    @Test
    public void testGetCountForCountryIdAndSearch() {
        // Prepare test data
        Long countryId = 1L;
        String search = "test";
        Long expectedCount = 5L;

        // Mock behavior of DAO
        when(stateDao.getCountForCountryIdAndSearch(countryId, search)).thenReturn(expectedCount);

        // Call method under test
        Long actualCount = stateManager.getCountForCountryIdAndSearch(countryId, search);

        // Verify
        assertEquals(expectedCount, actualCount);
        verify(stateDao, times(1)).getCountForCountryIdAndSearch(countryId, search);
    }

    @Test
    public void testFindByCountryGeoId() {
        String geoId = "C001";
        List<State> expectedStates = new ArrayList<>();
        expectedStates.add(new State());
        expectedStates.add(new State());
        when(stateDao.findByCountryGeoId(geoId)).thenReturn(expectedStates);

        List<State> actualStates = stateManager.findByCountryGeoId(geoId);

        assertEquals(expectedStates, actualStates);
    }

    @Test
    public void testFindByNameAndCountryId() {
        String stateName = "State1";
        Long countryId = 1L;
        State expectedState = new State();
        when(stateDao.findByNameAndCountryId(stateName, countryId)).thenReturn(expectedState);

        State actualState = stateManager.findByNameAndCountryId(stateName, countryId);

        assertEquals(expectedState, actualState);
    }
    // Write similar test methods for other methods in StateManager
}
