package com.beehyv.iam.manager;

import com.beehyv.iam.dao.UserDao;
import com.beehyv.iam.dto.requestDto.SearchListRequest;
import com.beehyv.iam.dto.responseDto.NotificationUserTokenResponseDto;
import com.beehyv.iam.mapper.UserRoleCategoryMapper;
import com.beehyv.iam.model.NotificationStateRoleMapping;
import com.beehyv.iam.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserManagerTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserManager userManager;

    @Mock
    private UserRoleCategoryMapper userRoleCategoryMapper;

    public UserManagerTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindByName() {
        // Prepare test data
        String userName = "testUser";
        User expectedUser = new User(); // Create a dummy User object

        // Mock behavior of UserDao
        when(userDao.findByName(userName)).thenReturn(expectedUser);

        // Call method under test
        User actualUser = userManager.findByName(userName);

        // Verify
        assertEquals(expectedUser, actualUser);
        verify(userDao, times(1)).findByName(userName);
    }

    @Test
    public void testFindByEmail() {
        // Prepare test data
        String email = "test@example.com";
        User expectedUser = new User(); // Create a dummy User object

        // Mock behavior of UserDao
        when(userDao.findByEmail(email)).thenReturn(expectedUser);

        // Call method under test
        User actualUser = userManager.findByEmail(email);

        // Verify
        assertEquals(expectedUser, actualUser);
        verify(userDao, times(1)).findByEmail(email);
    }

    @Test
    public void testFindAllByManufacturerId() {
        // Prepare test data
        Long manufacturerId = 1L;
        List<User> expectedUsers = Collections.singletonList(new User()); // Create a dummy list of User objects

        // Mock behavior of UserDao
        when(userDao.findAllByManufacturerId(manufacturerId)).thenReturn(expectedUsers);

        // Call method under test
        List<User> actualUsers = userManager.findAllByManufacturerId(manufacturerId);

        // Verify
        assertEquals(expectedUsers, actualUsers);
        verify(userDao, times(1)).findAllByManufacturerId(manufacturerId);
    }

    @Test
    public void testFindByLabUserId() {
        Long labUserId = 1L;
        User expectedUser = new User();

        when(userDao.findByLabUserId(labUserId)).thenReturn(expectedUser);

        User actualUser = userManager.findByLabUserId(labUserId);

        assertEquals(expectedUser, actualUser);
        verify(userDao, times(1)).findByLabUserId(labUserId);
    }

    @Test
    public void testGetRegistrationTokens_NoUsersReturnedFromDao() {
        // Mock DAO to return an empty list of users
        when(userDao.getRegistrationTokens(anyLong(), anyLong(), anyLong(), anyList())).thenReturn(new ArrayList<>());

        // Call the method to test
        List<NotificationUserTokenResponseDto> result = userManager.getRegistrationTokens(1L, 2L, 3L, new ArrayList<>(), new ArrayList<>());

        // Assert that the result is an empty list
        assertEquals(0, result.size());
    }

    @Test
    public void testGetRegistrationTokens_UsersWithNoRelevantIdsReturnedFromDao() {
        // Mock DAO to return a list of users without relevant IDs
        List<User> users = new ArrayList<>();
        // Add users without relevant lab or manufacturer IDs
        when(userDao.getRegistrationTokens(anyLong(), anyLong(), anyLong(), anyList())).thenReturn(users);

        // Call the method to test
        List<NotificationUserTokenResponseDto> result = userManager.getRegistrationTokens(1L, 2L, 3L, new ArrayList<>(), new ArrayList<>());

        // Assert that the result is an empty list
        assertEquals(0, result.size());
    }

    @Test
    public void testFindAllBySearchAndFilter() {
        SearchListRequest searchListRequest = new SearchListRequest();
        Integer pageNumber = 1;
        Integer pageSize = 10;
        Long manufacturerId = 1L;
        List<User> expectedUsers = new ArrayList<>();

        when(userDao.findAllBySearchAndFilter(searchListRequest, pageNumber, pageSize, manufacturerId)).thenReturn(expectedUsers);

        List<User> actualUsers = userManager.findAllBySearchAndFilter(searchListRequest, pageNumber, pageSize, manufacturerId);

        assertEquals(expectedUsers, actualUsers);
        verify(userDao, times(1)).findAllBySearchAndFilter(searchListRequest, pageNumber, pageSize, manufacturerId);
    }

    @Test
    public void testGetCountBySearchAndFilter() {
        SearchListRequest searchListRequest = new SearchListRequest();
        Long manufacturerId = 1L;
        Long expectedCount = 5L;

        when(userDao.getCountBySearchAndFilter(searchListRequest, manufacturerId)).thenReturn(expectedCount);

        Long actualCount = userManager.getCountBySearchAndFilter(searchListRequest, manufacturerId);

        assertEquals(expectedCount, actualCount);
        verify(userDao, times(1)).getCountBySearchAndFilter(searchListRequest, manufacturerId);
    }

    @Test
    public void testGetRegistrationTokens() {
        Long manufacturerId = 1L;
        Long labId = 2L;
        Long targetManufacturerId = 3L;
        List<String> labUserRoles = new ArrayList<>();
        List<NotificationStateRoleMapping> notificationStateRoleMappingList = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<NotificationUserTokenResponseDto> expectedResponse = new ArrayList<>();

        // Mocking the behavior of UserDao
        when(userDao.getRegistrationTokens(manufacturerId, labId, targetManufacturerId, labUserRoles)).thenReturn(users);

        // Testing the method
        List<NotificationUserTokenResponseDto> actualResponse = userManager.getRegistrationTokens(manufacturerId, labId, targetManufacturerId, labUserRoles, notificationStateRoleMappingList);

        // Verifying the result
        assertEquals(expectedResponse, actualResponse);
        verify(userDao, times(1)).getRegistrationTokens(manufacturerId, labId, targetManufacturerId, labUserRoles);
    }

    @Test
    public void testDeleteRegistrationToken() {
        String registrationToken = "test_token";

        // Testing the method
        userManager.deleteRegistrationToken(registrationToken);

        // Verifying that the method in UserDao was called
        verify(userDao, times(1)).deleteRegistrationToken(registrationToken);
    }

    @Test
    void testGetRegistrationTokens_EmptyUserList() {
        when(userDao.getRegistrationTokens(anyLong(), anyLong(), anyLong(), anyList())).thenReturn(new ArrayList<>());

        List<NotificationUserTokenResponseDto> result = userManager.getRegistrationTokens(1L, 2L, 3L, new ArrayList<>(), new ArrayList<>());

        assertEquals(0, result.size());
    }


}
