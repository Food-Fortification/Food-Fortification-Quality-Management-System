package org.path.iam.manager;

import org.path.iam.dao.StateLabTestAccessDao;
import org.path.iam.dto.requestDto.SearchListRequest;
import org.path.iam.enums.EntityType;
import org.path.iam.model.StateLabTestAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StateLabTestAccessManagerTest {

    @Mock
    private StateLabTestAccessDao stateLabTestAccessDao;

    @InjectMocks
    private StateLabTestAccessManager stateLabTestAccessManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindByStateAndCategoryAndEntityType() {
        // Prepare test data
        Long categoryId = 1L;
        EntityType entityType = EntityType.lot;
        Long stateId = 100L;
        StateLabTestAccess expectedStateLabTestAccess = new StateLabTestAccess(); // Create a dummy StateLabTestAccess object

        // Mock behavior of DAO
        when(stateLabTestAccessDao.findByStateAndCategoryAndEntityType(categoryId, entityType, stateId)).thenReturn(expectedStateLabTestAccess);

        // Call method under test
        StateLabTestAccess actualStateLabTestAccess = stateLabTestAccessManager.findByStateAndCategoryAndEntityType(categoryId, entityType, stateId);

        // Verify
        assertEquals(expectedStateLabTestAccess, actualStateLabTestAccess);
        verify(stateLabTestAccessDao, times(1)).findByStateAndCategoryAndEntityType(categoryId, entityType, stateId);
    }

    @Test
    public void testDeleteEntity() {
        // Prepare test data
        Long categoryId = 1L;
        EntityType entityType = EntityType.lot;
        Long stateId = 100L;

        // Call method under test
        stateLabTestAccessManager.deleteEntity(categoryId, entityType, stateId);

        // Verify
        verify(stateLabTestAccessDao, times(1)).deleteEntity(categoryId, entityType, stateId);
    }

    @Test
    void testFindAllStateLabTestAccessBySearchAndFilter() {
        // Mocking input parameters
        SearchListRequest searchListRequest = new SearchListRequest(/* provide necessary arguments */);
        int pageNumber = 1;
        int pageSize = 10;

        // Mocking the behavior of dao.findAllStateLabTestAccessBySearchAndFilter()
        List<StateLabTestAccess> expectedResult = new ArrayList<>(); // Add expected results
        when(stateLabTestAccessDao.findAllStateLabTestAccessBySearchAndFilter(searchListRequest, pageNumber, pageSize))
                .thenReturn(expectedResult);

        // Calling the manager method
        List<StateLabTestAccess> actualResult = stateLabTestAccessManager.findAllStateLabTestAccessBySearchAndFilter(searchListRequest, pageNumber, pageSize);

        // Verifying results
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetCount() {
        // Mocking input parameters
        SearchListRequest searchListRequest = new SearchListRequest(/* provide necessary arguments */);

        // Mocking the behavior of dao.getCountForAllStateTestLabAccess()
        Long expectedCount = 10L; // Set the expected count
        when(stateLabTestAccessDao.getCountForAllStateTestLabAccess(searchListRequest))
                .thenReturn(expectedCount);

        // Calling the manager method
        Long actualCount = stateLabTestAccessManager.getCount(searchListRequest);

        // Verifying results
        assertEquals(expectedCount, actualCount);
    }
    // Write similar test methods for other methods in StateLabTestAccessManager
}
