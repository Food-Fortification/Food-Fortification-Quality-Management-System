package com.beehyv.iam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LabUsersTest {

    private LabUsers labUsers;

    @BeforeEach
    void setUp() {
        labUsers = new LabUsers();
    }

    @Test
    void testLabUsersInitialization() {
        // Assert initial state of labUsers object
        assertNull(labUsers.getId());
        assertNull(labUsers.getLabId());
        assertNotNull(labUsers.getUser());
        assertTrue(labUsers.getUser().isEmpty());

    }

    @Test
    void testLabUsersConstructorWithArgs() {
        // Arrange
        Long labId = 1L;
        User user1 = new User();
        User user2 = new User();
        Set<User> users = new HashSet<>();
        users.add(user1);
        users.add(user2);

        // Act
        LabUsers labUsers = new LabUsers(labId, users);

        // Assert
        assertEquals(labId, labUsers.getLabId());
        assertEquals(2, labUsers.getUser().size());
        assertTrue(labUsers.getUser().contains(user1));
        assertTrue(labUsers.getUser().contains(user2));

    }

    @Test
    void testIdSetterGetter() {
        // Arrange
        Long id = 1L;

        // Act
        labUsers.setId(id);

        // Assert
        assertEquals(id, labUsers.getId());
    }

    @Test
    void testLabIdSetterGetter() {
        // Arrange
        Long labId = 1L;

        // Act
        labUsers.setLabId(labId);

        // Assert
        assertEquals(labId, labUsers.getLabId());
    }

    @Test
    void testUserSetterGetter() {
        // Arrange
        User user1 = new User();
        User user2 = new User();
        Set<User> users = new HashSet<>();
        users.add(user1);
        users.add(user2);

        // Act
        labUsers.setUser(users);

        // Assert
        assertEquals(2, labUsers.getUser().size());
        assertTrue(labUsers.getUser().contains(user1));
        assertTrue(labUsers.getUser().contains(user2));
    }

    @Test
    void testAddUser() {
        // Arrange
        User user = new User();

        // Act
        labUsers.getUser().add(user);

        // Assert
        assertEquals(1, labUsers.getUser().size());
        assertTrue(labUsers.getUser().contains(user));
    }

    @Test
    void testRemoveUser() {
        // Arrange
        User user = new User();
        labUsers.getUser().add(user);

        // Act
        labUsers.getUser().remove(user);

        // Assert
        assertTrue(labUsers.getUser().isEmpty());
    }

    @Test
    void testIsDeletedSetterGetter() {
        // Act
        labUsers.setIsDeleted(true);

        // Assert
        assertTrue(labUsers.getIsDeleted());
    }


}
