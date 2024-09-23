package com.beehyv.iam.dto.responseDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserListResponseDtoTest {

    private UserListResponseDto userListResponseDto;

    @BeforeEach
    public void setUp() {
        userListResponseDto = new UserListResponseDto();
    }

    @Test
    public void testNoArgsConstructor() {
        UserListResponseDto dto = new UserListResponseDto();
        assertNull(dto.getId());
        assertNull(dto.getUserName());
        assertNull(dto.getFirstName());
        assertNull(dto.getLastName());
        assertNull(dto.getEmail());
        assertNull(dto.getCategory());
        assertNull(dto.getRole());
        assertNull(dto.getStatus());
    }

    @Test
    public void testAllArgsConstructor() {
        UserListResponseDto dto = new UserListResponseDto(
                1L, "username", "John", "Doe", "john@example.com", "category", "role", "active");

        assertEquals(1L, dto.getId());
        assertEquals("username", dto.getUserName());
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertEquals("john@example.com", dto.getEmail());
        assertEquals("category", dto.getCategory());
        assertEquals("role", dto.getRole());
        assertEquals("active", dto.getStatus());
    }

    @Test
    public void testSettersAndGetters() {
        userListResponseDto.setId(1L);
        userListResponseDto.setUserName("username");
        userListResponseDto.setFirstName("John");
        userListResponseDto.setLastName("Doe");
        userListResponseDto.setEmail("john@example.com");
        userListResponseDto.setCategory("category");
        userListResponseDto.setRole("role");
        userListResponseDto.setStatus("active");

        assertEquals(1L, userListResponseDto.getId());
        assertEquals("username", userListResponseDto.getUserName());
        assertEquals("John", userListResponseDto.getFirstName());
        assertEquals("Doe", userListResponseDto.getLastName());
        assertEquals("john@example.com", userListResponseDto.getEmail());
        assertEquals("category", userListResponseDto.getCategory());
        assertEquals("role", userListResponseDto.getRole());
        assertEquals("active", userListResponseDto.getStatus());
    }
}
