package org.path.iam.model;

import org.path.iam.enums.RoleCategoryType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class NotificationStateRoleMappingTest {

    private NotificationStateRoleMapping notificationStateRoleMapping;

    @BeforeEach
    void setUp() {
        notificationStateRoleMapping = new NotificationStateRoleMapping();
    }

    @Test
    void testNotificationStateRoleMappingInitialization() {
        // Assert
        assertNull(notificationStateRoleMapping.getId());
        assertNull(notificationStateRoleMapping.getCategoryName());
        assertNull(notificationStateRoleMapping.getRoleCategoryType());
        assertNull(notificationStateRoleMapping.getRole());
        assertNull(notificationStateRoleMapping.getNotificationState());
    }

    @Test
    void testNotificationStateRoleMappingConstructorWithArgs() {
        // Arrange
        Long id = 1L;
        String categoryName = "TestCategory";
        RoleCategoryType roleCategoryType = RoleCategoryType.LAB;
        Role role = new Role();
        NotificationState notificationState = new NotificationState();

        // Act
        NotificationStateRoleMapping mapping = new NotificationStateRoleMapping(id, categoryName, roleCategoryType, role, notificationState);

        // Assert
        assertEquals(id, mapping.getId());
        assertEquals(categoryName, mapping.getCategoryName());
        assertEquals(roleCategoryType, mapping.getRoleCategoryType());
        assertEquals(role, mapping.getRole());
        assertEquals(notificationState, mapping.getNotificationState());
    }

    @Test
    void testCategoryNameSetterGetter() {
        // Arrange
        String categoryName = "TestCategory";

        // Act
        notificationStateRoleMapping.setCategoryName(categoryName);

        // Assert
        assertEquals(categoryName, notificationStateRoleMapping.getCategoryName());
    }

    @Test
    void testRoleCategoryTypeSetterGetter() {
        // Arrange
        RoleCategoryType roleCategoryType = RoleCategoryType.LAB;

        // Act
        notificationStateRoleMapping.setRoleCategoryType(roleCategoryType);

        // Assert
        assertEquals(roleCategoryType, notificationStateRoleMapping.getRoleCategoryType());
    }

    @Test
    void testRoleSetterGetter() {
        // Arrange
        Role role = new Role();

        // Act
        notificationStateRoleMapping.setRole(role);

        // Assert
        assertEquals(role, notificationStateRoleMapping.getRole());
    }

    @Test
    void testNotificationStateSetterGetter() {
        // Arrange
        NotificationState notificationState = new NotificationState();

        // Act
        notificationStateRoleMapping.setNotificationState(notificationState);

        // Assert
        assertEquals(notificationState, notificationStateRoleMapping.getNotificationState());
    }
}
