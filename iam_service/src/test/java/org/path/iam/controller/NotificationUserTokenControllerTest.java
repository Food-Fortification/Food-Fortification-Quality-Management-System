package org.path.iam.controller;

import org.path.iam.dto.requestDto.NotificationUserTokenRequestDto;
import org.path.iam.dto.responseDto.NotificationUserTokenResponseDto;
import org.path.iam.service.NotificationUserTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class NotificationUserTokenControllerTest {

    @Mock
    private NotificationUserTokenService notificationUserTokenService;

    @InjectMocks
    private NotificationUserTokenController notificationUserTokenController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        NotificationUserTokenRequestDto dto = new NotificationUserTokenRequestDto();
        when(notificationUserTokenService.create(any(NotificationUserTokenRequestDto.class))).thenReturn(1L);

        ResponseEntity<?> response = notificationUserTokenController.create(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testGetRegistrationTokens() {
        List<NotificationUserTokenResponseDto> tokens = new ArrayList<>();
        when(notificationUserTokenService.getRegistrationTokens(anyLong(), anyLong(), anyLong(), anyLong(), anyString())).thenReturn(tokens);

        ResponseEntity<?> response = notificationUserTokenController.getRegistrationTokens(1L, 1L, 1L, 1L, "state");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tokens, response.getBody());
    }

    @Test
    void testDeleteManufacturerDocById() {
        doNothing().when(notificationUserTokenService).deleteRegistrationToken(anyString());

        ResponseEntity<?> response = notificationUserTokenController.deleteManufacturerDocById("token");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
