package org.path.iam.mapper;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.path.iam.config.EncryptionFieldConfig;
import org.path.iam.dto.requestDto.UserRequestDto;
import org.path.iam.dto.responseDto.UserResponseDto;
import org.path.iam.helper.EncryptionHelper;
import org.path.iam.model.Manufacturer;
import org.path.iam.model.Status;
import org.path.iam.model.User;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mockStatic;

class UserMapperTest {

    private void withMocks(Runnable testLogic) {
        try (MockedStatic<EncryptionHelper> mockedStatic = mockStatic(EncryptionHelper.class)) {

            mockedStatic.when(() -> EncryptionHelper.encrypt(Mockito.anyString())).thenReturn("Encrypted String");
            mockedStatic.when(() -> EncryptionHelper.decrypt(Mockito.anyString())).thenReturn("Decrypted String");

            // Execute test logic
            testLogic.run();
        }
    }

    @Test
    void testToDto() {
        withMocks(() -> {
            // Create a User entity
            User user = new User();
            user.setId(1L);
            user.setUserName("john_doe");  // This will be encrypted in the setter
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
            assertEquals("Decrypted String", responseDto.getUserName()); // Use the encrypted value here
            assertEquals("Decrypted String", responseDto.getEmail());
            assertEquals("Decrypted String", responseDto.getFirstName());
            assertEquals("Decrypted String", responseDto.getLastName());
            assertEquals(user.getLastLogin(), responseDto.getLastLogin());
            assertEquals(1L, responseDto.getManufacturerId());
            assertNull(responseDto.getLabId());
        });
    }

    @Test
    void testToEntity() {
        withMocks(()-> {
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
            assertEquals("Decrypted String", entity.getUserName());
            assertEquals("Decrypted String", entity.getEmail());
            assertEquals("Decrypted String", entity.getFirstName());
            assertEquals("Decrypted String", entity.getLastName());
            assertEquals("password", entity.getPassword());
            assertEquals(1L, entity.getManufacturer().getId());
            assertNull(entity.getLabUsers()); // Assuming labUsers property is not set in requestDto
        });
    }

    @Test
    void testToEntityWithExistingUser() {
        withMocks(()-> {
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
            assertEquals("Decrypted String", entity.getUserName()); // Overridden value
            assertEquals("Decrypted String", entity.getEmail()); // Overridden value
            assertEquals("Decrypted String", entity.getFirstName()); // Updated value
            assertEquals("Decrypted String", entity.getLastName()); // Unchanged value
            assertEquals(2L, entity.getStatus().getId()); // Updated value
        });
    }
}
