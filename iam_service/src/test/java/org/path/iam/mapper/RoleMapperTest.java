package org.path.iam.mapper;

import org.path.iam.dto.requestDto.RoleRequestDto;
import org.path.iam.dto.responseDto.RoleResponseDto;
import org.path.iam.model.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RoleMapperTest {

    @Test
    void testToDto() {
        // Create a Role entity
        Role role = new Role();
        role.setId(1L);
        role.setName("admin");
        role.setDisplayName("Administrator");

        // Create a RoleMapper instance
        RoleMapper roleMapper = new RoleMapper();

        // Perform mapping
        RoleResponseDto responseDto = roleMapper.toDto(role);

        // Assert
        assertEquals(1L, responseDto.getId());
        assertEquals("admin", responseDto.getName());
        assertEquals("Administrator", responseDto.getDisplayName());
    }

    @Test
    void testToEntity() {
        // Create a RoleRequestDto
        RoleRequestDto requestDto = new RoleRequestDto();
        // As the toEntity method returns null, we don't need to set anything in the requestDto.

        // Create a RoleMapper instance
        RoleMapper roleMapper = new RoleMapper();

        // Perform mapping
        Role entity = roleMapper.toEntity(requestDto);

        // Assert
        assertNull(entity); // In the current implementation, the method always returns null.
    }
}
