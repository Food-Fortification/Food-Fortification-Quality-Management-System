package org.path.iam.dto.responseDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryResponseDtoTest {

    @Test
    public void testValidConstructionAndGetters() {
        Long id = 1L;
        String name = "Personal Details";

        CategoryResponseDto responseDto = new CategoryResponseDto(id, name);

        assertNotNull(responseDto);
        assertEquals(id, responseDto.getId());
        assertEquals(name, responseDto.getName());
    }

    @Test
    public void testWithOptionalFields() {
        String name = "Address";

        CategoryResponseDto responseDto = new CategoryResponseDto(null, name);

        assertNotNull(responseDto);
        assertNull(responseDto.getId());
        assertEquals(name, responseDto.getName());
    }

    @Test
    public void testToString() {
        Long id = 2L;
        String name = "Work Experience";

        CategoryResponseDto responseDto = new CategoryResponseDto(id, name);

        String expectedString = "CategoryResponseDto(id=" + id + ", name=" + name + ")";
        assertEquals(expectedString, responseDto.toString());
    }
}
