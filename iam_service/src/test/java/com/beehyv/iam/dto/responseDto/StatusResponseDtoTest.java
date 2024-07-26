package com.beehyv.iam.dto.responseDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StatusResponseDtoTest {

    private StatusResponseDto statusResponseDto;

    @BeforeEach
    public void setUp() {
        statusResponseDto = new StatusResponseDto();
    }

    @Test
    public void testNoArgsConstructor() {
        StatusResponseDto dto = new StatusResponseDto();
        assertNull(dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getDescription());
    }

    @Test
    public void testAllArgsConstructor() {
        StatusResponseDto dto = new StatusResponseDto(1L, "Status Name", "Status Description");

        assertEquals(1L, dto.getId());
        assertEquals("Status Name", dto.getName());
        assertEquals("Status Description", dto.getDescription());
    }

    @Test
    public void testSettersAndGetters() {
        statusResponseDto.setId(1L);
        statusResponseDto.setName("Status Name");
        statusResponseDto.setDescription("Status Description");

        assertEquals(1L, statusResponseDto.getId());
        assertEquals("Status Name", statusResponseDto.getName());
        assertEquals("Status Description", statusResponseDto.getDescription());
    }
}
