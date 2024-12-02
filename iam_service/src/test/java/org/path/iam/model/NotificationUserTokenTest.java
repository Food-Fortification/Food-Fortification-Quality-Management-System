package org.path.iam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationUserTokenTest {

    private NotificationUserToken notificationUserToken;

    @BeforeEach
    void setUp() {
        notificationUserToken = new NotificationUserToken();
    }

    @Test
    void testNotificationUserTokenInitialization() {
        // Assert
        assertNull(notificationUserToken.getId());
        assertNull(notificationUserToken.getUser());
        assertNull(notificationUserToken.getRegistrationToken());
    }

    @Test
    void testNotificationUserTokenConstructorWithArgs() {
        // Arrange
        Long id = 1L;
        User user = new User();
        String registrationToken = "sample_token";

        // Act
        NotificationUserToken token = new NotificationUserToken(id, user, registrationToken);

        // Assert
        assertEquals(id, token.getId());
        assertEquals(user, token.getUser());
        assertEquals(registrationToken, token.getRegistrationToken());
    }

    @Test
    void testUserSetterGetter() {
        // Arrange
        User user = new User();

        // Act
        notificationUserToken.setUser(user);

        // Assert
        assertEquals(user, notificationUserToken.getUser());
    }

    @Test
    void testRegistrationTokenSetterGetter() {
        // Arrange
        String registrationToken = "sample_token";

        // Act
        notificationUserToken.setRegistrationToken(registrationToken);

        // Assert
        assertEquals(registrationToken, notificationUserToken.getRegistrationToken());
    }

    @Test
    void testToString() {
        // Arrange
        User user = new User();
        user.setId(1L);
        String registrationToken = "sample_token";
        notificationUserToken = new NotificationUserToken(1L, user, registrationToken);

        // Act
        String toStringOutput = notificationUserToken.toString();

        // Assert
        assertTrue(toStringOutput.contains("id=1"));
        assertTrue(toStringOutput.contains("registrationToken=sample_token"));
    }
}
