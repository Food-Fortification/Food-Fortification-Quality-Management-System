package com.beehyv.Immudb.controller;

import com.beehyv.Immudb.dto.ListResponse;
import com.beehyv.Immudb.dto.NotificationResponseDto;
import com.beehyv.Immudb.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NotificationControllerTest {

    @InjectMocks
    NotificationController notificationController;

    @Mock
    NotificationService notificationService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetNotificationCount() {
        when(notificationService.getNotificationCount()).thenReturn(10L);

        ResponseEntity<?> response = notificationController.getNotificationCount();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(10L, response.getBody());
        verify(notificationService, times(1)).getNotificationCount();
    }

    @Test
    public void testGetNotifications() {
        when(notificationService.getNotifications(1, 10)).thenReturn(new ListResponse<NotificationResponseDto> ());

        ResponseEntity<?> response = notificationController.getNotifications(1, 10);

        assertEquals(200, response.getStatusCodeValue());
        verify(notificationService, times(1)).getNotifications(1, 10);
    }
}