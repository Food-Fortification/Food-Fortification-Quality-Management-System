package com.beehyv.fortification.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TargetStatesAccessTest {

    @Test
    void testAllFields() {
        // Arrange
        TargetStatesAccess targetStatesAccess = new TargetStatesAccess();
        Long id = 1L;
        Category category = new Category(1L);
        State state = new State(1L);
        RoleCategory roleCategory = new RoleCategory();

        // Act
        targetStatesAccess.setId(id);
        targetStatesAccess.setCategory(category);
        targetStatesAccess.setState(state);
        targetStatesAccess.setRoleCategory(roleCategory);

        // Assert
        assertEquals(id, targetStatesAccess.getId());
        assertEquals(category, targetStatesAccess.getCategory());
        assertEquals(state, targetStatesAccess.getState());
        assertEquals(roleCategory, targetStatesAccess.getRoleCategory());
    }
}