package org.path.iam.manager;

import org.path.iam.dao.StatusDao;
import org.path.iam.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StatusManagerTest {

    @Mock
    private StatusDao statusDao;

    @InjectMocks
    private StatusManager statusManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindByName() {
        // Prepare test data
        String statusName = "test_status";
        Status expectedStatus = new Status(); // Create a dummy Status object

        // Mock behavior of DAO
        when(statusDao.findByName(statusName)).thenReturn(expectedStatus);

        // Call method under test
        Status actualStatus = statusManager.findByName(statusName);

        // Verify
        assertEquals(expectedStatus, actualStatus);
        verify(statusDao, times(1)).findByName(statusName);
    }
}
