package com.beehyv.iam.manager;

import com.beehyv.iam.dao.NotificationUserTokenDao;
import com.beehyv.iam.model.NotificationUserToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NotificationUserTokenManagerTest {

    @Mock
    private NotificationUserTokenDao dao;

    @InjectMocks
    private NotificationUserTokenManager manager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreate() {
        NotificationUserToken token = new NotificationUserToken(/* provide necessary parameters */);


        NotificationUserToken createdToken = manager.create(token);

        assertEquals(token, createdToken);
        verify(dao, times(1)).create(token);
    }

    @Test
    void testUpdate() {
        NotificationUserToken token = new NotificationUserToken(/* provide necessary parameters */);
        when(dao.update(token)).thenReturn(token);

        NotificationUserToken updatedToken = manager.update(token);

        assertEquals(token, updatedToken);
        verify(dao, times(1)).update(token);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        NotificationUserToken token = new NotificationUserToken(/* provide necessary parameters */);
        when(dao.findById(id)).thenReturn(token);

        NotificationUserToken foundToken = manager.findById(id);

        assertEquals(token, foundToken);
        verify(dao, times(1)).findById(id);
    }


    @Test
    void testFindAll() {
        Integer pageNumber = 1;
        Integer pageSize = 5;
        List<NotificationUserToken> tokens = new ArrayList<>();
        // Add some tokens to the list

        when(dao.findAll(pageNumber, pageSize)).thenReturn(tokens);

        List<NotificationUserToken> foundTokens = manager.findAll(pageNumber, pageSize);

        assertEquals(tokens, foundTokens);
        verify(dao, times(1)).findAll(pageNumber, pageSize);
    }

    @Test
    void testDelete() {
        Long id = 1L;

        manager.delete(id);

        verify(dao, times(1)).delete(id);
    }

    @Test
    void testFindByRegistrationToken() {
        String registrationToken = "example_token";
        Long userId = 1L;
        NotificationUserToken token = new NotificationUserToken(/* provide necessary parameters */);
        when(dao.findByRegistrationToken(registrationToken, userId)).thenReturn(token);

        NotificationUserToken foundToken = manager.findByRegistrationToken(registrationToken, userId);

        assertEquals(token, foundToken);
        verify(dao, times(1)).findByRegistrationToken(registrationToken, userId);
    }


    @Test
    void testGetCountWithPageNumberAndPageSize() {
        int listSize = 20;
        int pageNumber = 1;
        int pageSize = 10;
        // Mocking the behavior of dao.getCount()
        when(dao.getCount()).thenReturn(100L);

        Long count = manager.getCount(listSize, pageNumber, pageSize);

        assertEquals(100L, count); // The actual count should be obtained from dao.getCount()
    }

    @Test
    void testGetCountWithNullPageNumberAndPageSize() {
        int listSize = 20;
        Integer pageNumber = null;
        Integer pageSize = null;
        // Mocking the behavior of dao.getCount()
        when(dao.getCount()).thenReturn(100L);

        Long count = manager.getCount(listSize, pageNumber, pageSize);

        assertEquals(20L, count); // When pageNumber and pageSize are null, return listSize
    }
}
