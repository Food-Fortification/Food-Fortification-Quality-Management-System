package com.beehyv.iam.manager;

import com.beehyv.iam.dao.UserRoleCategoryDao;
import com.beehyv.iam.dto.requestDto.SearchListRequest;
import com.beehyv.iam.model.UserRoleCategory;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserRoleCategoryManagerTest {

    @Mock
    private UserRoleCategoryDao userRoleCategoryDao;

    @InjectMocks
    private UserRoleCategoryManager userRoleCategoryManager;

    public UserRoleCategoryManagerTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllUsersBySearchAndFilter() {
        // Prepare test data
        SearchListRequest searchListRequest = new SearchListRequest();
        Integer pageNumber = 1;
        Integer pageSize = 10;
        Long manufacturerId = 1L;
        List<UserRoleCategory> expectedUsers = Collections.singletonList(new UserRoleCategory()); // Create a dummy list of UserRoleCategory objects

        // Mock behavior of UserRoleCategoryDao
        when(userRoleCategoryDao.findAllUsersBySearchAndFilter(searchListRequest, pageNumber, pageSize, manufacturerId)).thenReturn(expectedUsers);

        // Call method under test
        List<UserRoleCategory> actualUsers = userRoleCategoryManager.findAllUsersBySearchAndFilter(searchListRequest, pageNumber, pageSize, manufacturerId);

        // Verify
        assertEquals(expectedUsers, actualUsers);
        verify(userRoleCategoryDao, times(1)).findAllUsersBySearchAndFilter(searchListRequest, pageNumber, pageSize, manufacturerId);
    }

    @Test
    public void testGetCountForAllUsersBySearchAndFilter() {
        SearchListRequest searchListRequest = new SearchListRequest();
        Long manufacturerId = 1L;
        when(userRoleCategoryDao.getCountForALlUsersBySearchAndFilter(searchListRequest, manufacturerId)).thenReturn(5L);

        Long actualCount = userRoleCategoryManager.getCountForALlUsersBySearchAndFilter(searchListRequest, manufacturerId);

        assertEquals(5L, actualCount);
    }

    @Test
    public void testFindAllInspectionUsers() {
        SearchListRequest searchListRequest = new SearchListRequest();
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<UserRoleCategory> expectedUsers = new ArrayList<>();
        when(userRoleCategoryDao.findAllInspectionUsers(searchListRequest, pageNumber, pageSize)).thenReturn(expectedUsers);

        List<UserRoleCategory> actualUsers = userRoleCategoryManager.findAllInspectionUsers(searchListRequest, pageNumber, pageSize);

        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void testGetCountForInspectionUsers() {
        SearchListRequest searchListRequest = new SearchListRequest();
        Long expectedCount = 10L;
        when(userRoleCategoryDao.getCountForAllInspectionUsers(searchListRequest)).thenReturn(expectedCount);

        Long actualCount = userRoleCategoryManager.getCountForInspectionUsers(searchListRequest);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testFindAllLabUsersBySearchAndFilter() {
        SearchListRequest searchRequest = new SearchListRequest();
        int pageNumber = 1;
        int pageSize = 10;
        List<UserRoleCategory> expectedUsers = new ArrayList<>();
        when(userRoleCategoryDao.findAllLabUsersBySearchAndFilter(searchRequest, pageNumber, pageSize)).thenReturn(expectedUsers);

        List<UserRoleCategory> actualUsers = userRoleCategoryManager.findAllLabUsersBySearchAndFilter(searchRequest, pageNumber, pageSize);

        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void testGetCountForAllLabUsersBySearchAndFilter() {
        SearchListRequest searchRequest = new SearchListRequest();
        when(userRoleCategoryDao.getCountForAllLabUsersBySearchAndFilter(searchRequest)).thenReturn(10L);

        Long actualCount = userRoleCategoryManager.getCountForAllLabUsersBySearchAndFilter(searchRequest);

        assertEquals(10L, actualCount);
    }

    @Test
    public void testFindByUserId() {
        Long userId = 1L;
        List<UserRoleCategory> expectedUsers = new ArrayList<>();
        when(userRoleCategoryDao.findByUserId(userId)).thenReturn(expectedUsers);

        List<UserRoleCategory> actualUsers = userRoleCategoryManager.findByUserId(userId);

        assertEquals(expectedUsers, actualUsers);
    }
    // Add similar test methods for other methods in UserRoleCategoryManager
}
