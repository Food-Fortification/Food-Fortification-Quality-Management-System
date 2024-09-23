package com.beehyv.iam.dto.responseDto;

import com.beehyv.iam.enums.ActionType;
import com.beehyv.iam.enums.RoleCategoryType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NotificationUserTokenResponseDtoTest {

    private NotificationUserTokenResponseDto notificationUserTokenResponseDto;

    @BeforeEach
    public void setUp() {
        notificationUserTokenResponseDto = new NotificationUserTokenResponseDto();
    }

    @Test
    public void testNoArgsConstructor() {
        NotificationUserTokenResponseDto dto = new NotificationUserTokenResponseDto();
        assertNull(dto.getRoleCategoryType());
        assertNull(dto.getActionType());
        assertNull(dto.getRegistrationToken());
    }

    @Test
    public void testAllArgsConstructor() {
        NotificationUserTokenResponseDto dto = new NotificationUserTokenResponseDto(
                RoleCategoryType.MODULE, ActionType.action, "token123");

        assertEquals(RoleCategoryType.MODULE, dto.getRoleCategoryType());
        assertEquals(ActionType.action, dto.getActionType());
        assertEquals("token123", dto.getRegistrationToken());
    }

    @Test
    public void testSettersAndGetters() {
        notificationUserTokenResponseDto.setRoleCategoryType(RoleCategoryType.MODULE);
        notificationUserTokenResponseDto.setActionType(ActionType.action);
        notificationUserTokenResponseDto.setRegistrationToken("token456");

        assertEquals(RoleCategoryType.MODULE, notificationUserTokenResponseDto.getRoleCategoryType());
        assertEquals(ActionType.action, notificationUserTokenResponseDto.getActionType());
        assertEquals("token456", notificationUserTokenResponseDto.getRegistrationToken());
    }
}
