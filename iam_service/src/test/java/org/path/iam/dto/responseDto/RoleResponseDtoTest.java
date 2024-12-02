package org.path.iam.dto.responseDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RoleResponseDtoTest {

    private RoleResponseDto roleResponseDto;

    @BeforeEach
    public void setUp() {
        roleResponseDto = new RoleResponseDto();
    }

    @Test
    public void testNoArgsConstructor() {
        RoleResponseDto dto = new RoleResponseDto();
        assertNull(dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getDisplayName());
    }

    @Test
    public void testAllArgsConstructor() {
        RoleResponseDto dto = new RoleResponseDto(1L, "role_name", "Role Display Name");

        assertEquals(1L, dto.getId());
        assertEquals("role_name", dto.getName());
        assertEquals("Role Display Name", dto.getDisplayName());
    }

    @Test
    public void testSettersAndGetters() {
        roleResponseDto.setId(1L);
        roleResponseDto.setName("role_name");
        roleResponseDto.setDisplayName("Role Display Name");

        assertEquals(1L, roleResponseDto.getId());
        assertEquals("role_name", roleResponseDto.getName());
        assertEquals("Role Display Name", roleResponseDto.getDisplayName());
    }
}
