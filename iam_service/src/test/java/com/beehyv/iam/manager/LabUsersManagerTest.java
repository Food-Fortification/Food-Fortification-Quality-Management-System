package com.beehyv.iam.manager;

import com.beehyv.iam.dao.LabUsersDao;
import com.beehyv.iam.model.LabUsers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LabUsersManagerTest {

    @Mock
    private LabUsersDao dao;

    @InjectMocks
    private LabUsersManager manager;

    @Test
    void testFindUsersByLabId() {
        // Prepare test data
        Long labId = 1L;
        LabUsers labUser = new LabUsers();
        labUser.setId(1L);
        List<LabUsers> expectedUsers = Collections.singletonList(labUser);

        // Mock the behavior of the DAO
        when(dao.findUsersByLabId(labId)).thenReturn(expectedUsers);

        // Call the method to be tested
        List<LabUsers> actualUsers = manager.findUsersByLabId(labId);

        // Verify that the returned list of users matches the expected list
        assertEquals(expectedUsers, actualUsers);
    }

    // Add more test cases for other methods as needed
}
