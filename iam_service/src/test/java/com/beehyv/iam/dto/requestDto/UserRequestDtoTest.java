package com.beehyv.iam.dto.requestDto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserRequestDtoTest {

    @Test
    public void testValidConstructionAndGetters() {
        Long id = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        Long manufacturerId = 2L;
        Long labId = 3L;
        String userName = "jdoe";
        String password = "password123";
        List<RoleRequestDto> roles = new ArrayList<>();
        roles.add(new RoleRequestDto());
        Boolean isEnabled = true;

        UserRequestDto requestDto = new UserRequestDto(id, firstName, lastName, email, manufacturerId, labId, userName, password, roles, isEnabled);

        assertNotNull(requestDto);
        assertEquals(id, requestDto.getId());
        assertEquals(firstName, requestDto.getFirstName());
        assertEquals(lastName, requestDto.getLastName());
        assertEquals(email, requestDto.getEmail());
        assertEquals(manufacturerId, requestDto.getManufacturerId());
        assertEquals(labId, requestDto.getLabId());
        assertEquals(userName, requestDto.getUserName());
        assertEquals(password, requestDto.getPassword()); // Password should be hashed in production
        assertEquals(roles, requestDto.getRolesMap());
        assertTrue(requestDto.getIsEnabled());
    }

    @Test
    public void testWithOptionalId() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        Long manufacturerId = 2L;
        Long labId = 3L;
        String userName = "jdoe";
        String password = "password123"; // Replace with secure hashing in production
        List<RoleRequestDto> roles = new ArrayList<>();
        roles.add(new RoleRequestDto());
        Boolean isEnabled = true;

        UserRequestDto requestDto = new UserRequestDto(null, firstName, lastName, email, manufacturerId, labId, userName, password, roles, isEnabled);

        assertNotNull(requestDto);
        assertNull(requestDto.getId());
        assertEquals(firstName, requestDto.getFirstName());
    }


    @Test
    public void testWithEmptyStrings() {
        // Adjust expected behavior based on your implementation (null pointer exception or constraint violation)
        String email = "john.doe@example.com";
        Long manufacturerId = 2L;
        Long labId = 3L;
        String userName = "jdoe";
        String password = "password123"; // Replace with secure hashing in production
        List<RoleRequestDto> roles = new ArrayList<>();
        // Add valid RoleRequestDto objects to roles list
        roles.add(new RoleRequestDto());
        Boolean isEnabled = true;


    }
}