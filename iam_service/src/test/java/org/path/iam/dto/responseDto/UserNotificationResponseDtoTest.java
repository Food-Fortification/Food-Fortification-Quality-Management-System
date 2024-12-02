package org.path.iam.dto.responseDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserNotificationResponseDtoTest {

    private UserNotificationResponseDto userNotificationResponseDto;

    @BeforeEach
    public void setUp() {
        userNotificationResponseDto = new UserNotificationResponseDto();
    }

    @Test
    public void testNoArgsConstructor() {
        UserNotificationResponseDto dto = new UserNotificationResponseDto();
        assertNull(dto.getNotificationUserTokens());
    }

    @Test
    public void testAllArgsConstructor() {
        Set<NotificationUserTokenResponseDto> notificationUserTokens = new HashSet<>();
        notificationUserTokens.add(new NotificationUserTokenResponseDto());
        UserNotificationResponseDto dto = new UserNotificationResponseDto(notificationUserTokens);

        assertNotNull(dto.getNotificationUserTokens());
        assertEquals(1, dto.getNotificationUserTokens().size());
    }

    @Test
    public void testSettersAndGetters() {
        Set<NotificationUserTokenResponseDto> notificationUserTokens = new HashSet<>();
        notificationUserTokens.add(new NotificationUserTokenResponseDto());

        userNotificationResponseDto.setNotificationUserTokens(notificationUserTokens);

        assertNotNull(userNotificationResponseDto.getNotificationUserTokens());
        assertEquals(1, userNotificationResponseDto.getNotificationUserTokens().size());
    }
}
