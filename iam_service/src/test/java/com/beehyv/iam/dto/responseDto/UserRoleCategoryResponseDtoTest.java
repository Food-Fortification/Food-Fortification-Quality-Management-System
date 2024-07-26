package com.beehyv.iam.dto.responseDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserRoleCategoryResponseDtoTest {

    private UserRoleCategoryResponseDto userRoleCategoryResponseDto;

    @BeforeEach
    public void setUp() {
        userRoleCategoryResponseDto = new UserRoleCategoryResponseDto();
    }

    @Test
    public void testNoArgsConstructor() {
        UserRoleCategoryResponseDto dto = new UserRoleCategoryResponseDto();
        assertNull(dto.getId());
        assertNull(dto.getUser());
        assertNull(dto.getRole());
        assertNull(dto.getCategory());
    }

    @Test
    public void testAllArgsConstructor() {
        UserResponseDto user = new UserResponseDto();
        RoleResponseDto role = new RoleResponseDto();
        String category = "category";

        UserRoleCategoryResponseDto dto = new UserRoleCategoryResponseDto(1L, user, role, category);

        assertEquals(1L, dto.getId());
        assertEquals(user, dto.getUser());
        assertEquals(role, dto.getRole());
        assertEquals(category, dto.getCategory());
    }

    @Test
    public void testSettersAndGetters() {
        UserResponseDto user = new UserResponseDto();
        RoleResponseDto role = new RoleResponseDto();
        String category = "category";

        userRoleCategoryResponseDto.setId(1L);
        userRoleCategoryResponseDto.setUser(user);
        userRoleCategoryResponseDto.setRole(role);
        userRoleCategoryResponseDto.setCategory(category);

        assertEquals(1L, userRoleCategoryResponseDto.getId());
        assertEquals(user, userRoleCategoryResponseDto.getUser());
        assertEquals(role, userRoleCategoryResponseDto.getRole());
        assertEquals(category, userRoleCategoryResponseDto.getCategory());
    }
}
