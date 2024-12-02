package org.path.iam.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StateRequestDtoTest {

    @Test
    public void testValidConstructionAndGetters() {
        Long id = 1L;
        String name = "California";
        Long countryId = 2L;

        StateRequestDto requestDto = new StateRequestDto(id, name, countryId);

        assertNotNull(requestDto);
        assertEquals(id, requestDto.getId());
        assertEquals(name, requestDto.getName());
        assertEquals(countryId, requestDto.getCountryId());
    }

    @Test
    public void testWithOptionalId() {
        String name = "California";
        Long countryId = 2L;

        StateRequestDto requestDto = new StateRequestDto(null, name, countryId);

        assertNotNull(requestDto);
        assertNull(requestDto.getId());
        assertEquals(name, requestDto.getName());
        assertEquals(countryId, requestDto.getCountryId());
    }
}