package org.path.iam.dto.responseDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserResponseDtoTest {

    private UserResponseDto userResponseDto;

    @BeforeEach
    public void setUp() {
        userResponseDto = new UserResponseDto();
    }

    @Test
    public void testNoArgsConstructor() {
        UserResponseDto dto = new UserResponseDto();
        assertNull(dto.getId());
        assertNull(dto.getUserName());
        assertNull(dto.getFirstName());
        assertNull(dto.getLastName());
        assertNull(dto.getEmail());
        assertNull(dto.getManufacturerId());
        assertNull(dto.getLabId());
        assertNull(dto.getRoleCategory());
        assertNull(dto.getLastLogin());
    }

    @Test
    public void testAllArgsConstructor() {
        Set<UserRoleCategoryResponseDto> roleCategory = new HashSet<>();
        roleCategory.add(new UserRoleCategoryResponseDto());
        Date lastLogin = new Date();

        UserResponseDto dto = new UserResponseDto(
                1L, "username", "John", "Doe", "john@example.com", 1L, 1L, roleCategory, lastLogin);

        assertEquals(1L, dto.getId());
        assertEquals("username", dto.getUserName());
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertEquals("john@example.com", dto.getEmail());
        assertEquals(1L, dto.getManufacturerId());
        assertEquals(1L, dto.getLabId());
        assertNotNull(dto.getRoleCategory());
        assertEquals(1, dto.getRoleCategory().size());
        assertEquals(lastLogin, dto.getLastLogin());
    }

    @Test
    public void testSettersAndGetters() {
        Set<UserRoleCategoryResponseDto> roleCategory = new HashSet<>();
        roleCategory.add(new UserRoleCategoryResponseDto());
        Date lastLogin = new Date();

        userResponseDto.setId(1L);
        userResponseDto.setUserName("username");
        userResponseDto.setFirstName("John");
        userResponseDto.setLastName("Doe");
        userResponseDto.setEmail("john@example.com");
        userResponseDto.setManufacturerId(1L);
        userResponseDto.setLabId(1L);
        userResponseDto.setRoleCategory(roleCategory);
        userResponseDto.setLastLogin(lastLogin);

        assertEquals(1L, userResponseDto.getId());
        assertEquals("username", userResponseDto.getUserName());
        assertEquals("John", userResponseDto.getFirstName());
        assertEquals("Doe", userResponseDto.getLastName());
        assertEquals("john@example.com", userResponseDto.getEmail());
        assertEquals(1L, userResponseDto.getManufacturerId());
        assertEquals(1L, userResponseDto.getLabId());
        assertNotNull(userResponseDto.getRoleCategory());
        assertEquals(1, userResponseDto.getRoleCategory().size());
        assertEquals(lastLogin, userResponseDto.getLastLogin());
    }
}
