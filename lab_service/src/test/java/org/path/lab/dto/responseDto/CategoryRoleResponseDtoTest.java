package org.path.lab.dto.responseDto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryRoleResponseDtoTest {

    @Test
    void testConstructorWithAllFields() {
        // Given
        Long id = 1L;
        List<String> roles = new ArrayList<>();
        roles.add("ADMIN");
        roles.add("USER");

        // When
        CategoryRoleResponseDto dto = new CategoryRoleResponseDto(id, roles);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals(roles, dto.getRoles());
    }

    @Test
    void testNoArgsConstructor() {
        // When
        CategoryRoleResponseDto dto = new CategoryRoleResponseDto();

        // Then
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getRoles());
    }

    @Test
    void testGetId() {
        // Given
        Long id = 1L;
        CategoryRoleResponseDto dto = new CategoryRoleResponseDto();
        dto.setId(id);

        // When
        Long result = dto.getId();

        // Then
        assertEquals(id, result);
    }

    @Test
    void testSetId() {
        // Given
        CategoryRoleResponseDto dto = new CategoryRoleResponseDto();
        Long id = 1L;

        // When
        dto.setId(id);

        // Then
        assertEquals(id, dto.getId());
    }

    @Test
    void testGetRoles() {
        // Given
        List<String> roles = new ArrayList<>();
        roles.add("ADMIN");
        roles.add("USER");
        CategoryRoleResponseDto dto = new CategoryRoleResponseDto();
        dto.setRoles(roles);

        // When
        List<String> result = dto.getRoles();

        // Then
        assertEquals(roles, result);
    }

    @Test
    void testSetRoles() {
        // Given
        CategoryRoleResponseDto dto = new CategoryRoleResponseDto();
        List<String> roles = new ArrayList<>();
        roles.add("ADMIN");
        roles.add("USER");

        // When
        dto.setRoles(roles);

        // Then
        assertEquals(roles, dto.getRoles());
    }

    @Test
    void testToString() {
        // Given
        Long id = 1L;
        List<String> roles = new ArrayList<>();
        roles.add("ADMIN");
        roles.add("USER");
        CategoryRoleResponseDto dto = new CategoryRoleResponseDto(id, roles);

        // When
        String result = dto.toString();

        // Then
        assertNotNull(result);
        assertTrue(result.contains("CategoryRoleResponseDto"));
        assertTrue(result.contains("id=1"));
        assertTrue(result.contains("roles=[ADMIN, USER]"));
    }

    @Test
    void testBuilder() {
        // Given
        Long id = 1L;
        List<String> roles = new ArrayList<>();
        roles.add("ADMIN");
        roles.add("USER");

        // When
        CategoryRoleResponseDto dto = CategoryRoleResponseDto.builder()
                .id(id)
                .roles(roles)
                .build();

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals(roles, dto.getRoles());
    }

    @Test
    void testAddRole() {
        // Given
        CategoryRoleResponseDto dto = new CategoryRoleResponseDto();
        List<String> roles = new ArrayList<>();
        roles.add("ADMIN");
        dto.setRoles(roles);

        // When
        dto.getRoles().add("USER");

        // Then
        assertEquals(2, dto.getRoles().size());
        assertTrue(dto.getRoles().contains("USER"));
    }

    @Test
    void testRemoveRole() {
        // Given
        CategoryRoleResponseDto dto = new CategoryRoleResponseDto();
        List<String> roles = new ArrayList<>();
        roles.add("ADMIN");
        dto.setRoles(roles);

        // When
        dto.getRoles().remove("ADMIN");

        // Then
        assertTrue(dto.getRoles().isEmpty());
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        List<String> roles1 = new ArrayList<>();
        roles1.add("ADMIN");
        roles1.add("USER");

        List<String> roles2 = new ArrayList<>();
        roles2.add("ADMIN");
        roles2.add("USER");

        CategoryRoleResponseDto dto1 = new CategoryRoleResponseDto(1L, roles1);
        CategoryRoleResponseDto dto2 = new CategoryRoleResponseDto(1L, roles2);

        // Then

    }
}
