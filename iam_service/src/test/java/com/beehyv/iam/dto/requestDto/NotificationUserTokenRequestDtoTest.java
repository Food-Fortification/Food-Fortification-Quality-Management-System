package com.beehyv.iam.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NotificationUserTokenRequestDtoTest {

    @Test
    public void testValidConstructionAndGetters() {
        String userName = "john.doe";
        String registrationToken = "some-long-token-string";

        NotificationUserTokenRequestDto requestDto = new NotificationUserTokenRequestDto(userName, registrationToken);

        assertNotNull(requestDto);
        assertEquals(userName, requestDto.getUserName());
        assertEquals(registrationToken, requestDto.getRegistrationToken());
    }

    @Test
    public void testSetters() {
        NotificationUserTokenRequestDto requestDto = new NotificationUserTokenRequestDto();

        String newUserName = "jane.smith";
        String newRegistrationToken = "another-token-string";

        requestDto.setUserName(newUserName);
        requestDto.setRegistrationToken(newRegistrationToken);

        assertEquals(newUserName, requestDto.getUserName());
        assertEquals(newRegistrationToken, requestDto.getRegistrationToken());
    }

    // Add additional test cases for negative scenarios here (optional)
}
