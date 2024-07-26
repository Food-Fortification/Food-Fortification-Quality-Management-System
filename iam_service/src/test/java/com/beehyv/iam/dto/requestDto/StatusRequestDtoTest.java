package com.beehyv.iam.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StatusRequestDtoTest {

    @Test
    public void testValidConstructionAndGetters() {
        Long id = 1L;
        String name = "Active";
        String description = "The entity is currently active";

        StatusRequestDto requestDto = new StatusRequestDto(id, name, description);

        assertNotNull(requestDto);
        assertEquals(id, requestDto.getId());
        assertEquals(name, requestDto.getName());
        assertEquals(description, requestDto.getDescription());
    }

    @Test
    public void testWithOptionalId() {
        String name = "Active";
        String description = "The entity is currently active";

        StatusRequestDto requestDto = new StatusRequestDto(null, name, description);

        assertNotNull(requestDto);
        assertNull(requestDto.getId());
        assertEquals(name, requestDto.getName());
        assertEquals(description, requestDto.getDescription());
    }
}