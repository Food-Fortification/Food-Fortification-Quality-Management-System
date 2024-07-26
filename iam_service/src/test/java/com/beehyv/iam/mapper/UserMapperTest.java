package com.beehyv.iam.mapper;

import com.beehyv.iam.dto.requestDto.UserRequestDto;
import com.beehyv.iam.dto.responseDto.UserResponseDto;
import com.beehyv.iam.model.Manufacturer;
import com.beehyv.iam.model.Status;
import com.beehyv.iam.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserMapperTest {

    @Test
    void testToDto() {
        // Create a User entity
        User user = new User();
        user.setId(1L);
        user.setUserName("john_doe");
        user.setEmail("john@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setManufacturer(new Manufacturer(1L));

        // Create a UserMapper instance
        UserMapper userMapper = new UserMapper();

        // Perform mapping
        UserResponseDto responseDto = userMapper.toDto(user);

        // Assert
        assertEquals(1L, responseDto.getId());
        assertEquals("john_doe", responseDto.getUserName());
        assertEquals("john@example.com", responseDto.getEmail());
        assertEquals("John", responseDto.getFirstName());
        assertEquals("Doe", responseDto.getLastName());
        assertEquals(user.getLastLogin(), responseDto.getLastLogin());
        assertEquals(1L, responseDto.getManufacturerId());
        assertNull(responseDto.getLabId());
    }

    @Test
    void testToEntity() {
        // Create a UserRequestDto
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setId(1L);
        requestDto.setUserName("john_doe");
        requestDto.setEmail("john@example.com");
        requestDto.setFirstName("John");
        requestDto.setLastName("Doe");
        requestDto.setPassword("password");
        requestDto.setManufacturerId(1L);

        // Create a UserMapper instance
        UserMapper userMapper = new UserMapper();

        // Perform mapping
        User entity = userMapper.toEntity(requestDto);

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("john_doe", entity.getUserName());
        assertEquals("john@example.com", entity.getEmail());
        assertEquals("John", entity.getFirstName());
        assertEquals("Doe", entity.getLastName());
        assertEquals("password", entity.getPassword());
        assertEquals(1L, entity.getManufacturer().getId());
        assertNull(entity.getLabUsers()); // Assuming labUsers property is not set in requestDto
    }

    @Test
    void testToEntityWithExistingUser() {
        // Create an existing User
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUserName("existing_user");
        existingUser.setEmail("existing@example.com");
        existingUser.setFirstName("Existing");
        existingUser.setLastName("User");
        existingUser.setStatus(new Status(1L));

        // Create a UserRequestDto
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setFirstName("New");
        requestDto.setUserName("new_user");
        requestDto.setLastName("User");
        requestDto.setEmail("new@example.com");
        requestDto.setStatusId(2L);

        // Perform mapping using static method
        User entity = UserMapper.toEntity(requestDto, existingUser);

        // Assert
        assertEquals(1L, entity.getId()); // Id should remain unchanged
        assertEquals("new_user", entity.getUserName()); // Overridden value
        assertEquals("new@example.com", entity.getEmail()); // Overridden value
        assertEquals("New", entity.getFirstName()); // Updated value
        assertEquals("User", entity.getLastName()); // Unchanged value
        assertEquals(2L, entity.getStatus().getId()); // Updated value
    }
}
