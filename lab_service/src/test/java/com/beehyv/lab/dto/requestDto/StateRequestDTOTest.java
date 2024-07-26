package com.beehyv.lab.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StateRequestDTOTest {

    @Test
    void constructorAndGetters_WorkCorrectly() {
        // Given
        Long id = 1L;
        String name = "State";
        Long countryId = 2L;

        // When
        StateRequestDTO dto = new StateRequestDTO(id, name, countryId);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(countryId, dto.getCountryId());
    }

    @Test
    void setters_WorkCorrectly() {
        // Given
        StateRequestDTO dto = new StateRequestDTO();
        Long id = 1L;
        String name = "State";
        Long countryId = 2L;

        // When
        dto.setId(id);
        dto.setName(name);
        dto.setCountryId(countryId);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(countryId, dto.getCountryId());
    }
}
