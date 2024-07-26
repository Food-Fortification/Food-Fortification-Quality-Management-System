package com.beehyv.iam.dto.responseDto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryRoleResponseDtoTest {

    @Test
    public void testValidConstructionAndGetters() {
        Long id = 1L;
        List<String> roles = new ArrayList<>();
        roles.add("Admin");
        roles.add("Manager");

        CategoryRoleResponseDto responseDto = new CategoryRoleResponseDto(id, roles);

        assertNotNull(responseDto);
        assertEquals(id, responseDto.getId());
        assertEquals(roles, responseDto.getRoles());
    }

    @Test
    public void testWithEmptyList() {
        Long id = 2L;
        List<String> roles = new ArrayList<>();

        CategoryRoleResponseDto responseDto = new CategoryRoleResponseDto(id, roles);

        assertNotNull(responseDto);
        assertEquals(id, responseDto.getId());
        assertTrue(responseDto.getRoles().isEmpty());
    }

    @Test
    public void testBuilderPattern() {
        Long id = 3L;
        List<String> roles = List.of("User", "Editor");

        CategoryRoleResponseDto responseDto = CategoryRoleResponseDto.builder()
                .id(id)
                .roles(roles)
                .build();

        assertNotNull(responseDto);
        assertEquals(id, responseDto.getId());
        assertEquals(roles, responseDto.getRoles());
    }

    @Test
    public void testToString() {
        Long id = 4L;
        List<String> roles = List.of("Viewer");

        CategoryRoleResponseDto responseDto = new CategoryRoleResponseDto(id, roles);

        String expectedString = "CategoryRoleResponseDto(id=" + id + ", roles=" + roles + ")";
        assertEquals(expectedString, responseDto.toString());
    }
}
