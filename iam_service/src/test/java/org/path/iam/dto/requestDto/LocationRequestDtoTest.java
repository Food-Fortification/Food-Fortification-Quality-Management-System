package org.path.iam.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LocationRequestDtoTest {

    @Test
    public void testValidConstructionAndGetters() {
        Long id = 1L;
        String name = "Head Office";

        LocationRequestDto requestDto = new LocationRequestDto(id, name);

        assertNotNull(requestDto);
        assertEquals(id, requestDto.getId());
        assertEquals(name, requestDto.getName());
    }

    @Test
    public void testSetters() {
        LocationRequestDto requestDto = new LocationRequestDto();

        Long newId = 2L;
        String newName = "New York Branch";

        requestDto.setId(newId);
        requestDto.setName(newName);

        assertEquals(newId, requestDto.getId());
        assertEquals(newName, requestDto.getName());
    }
}