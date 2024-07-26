package com.beehyv.lab.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryRequestDTOTest {

    @Test
    void constructorAndGetters_WorkCorrectly() {
        // Given
        Long id = 1L;
        String name = "Test Country";

        // When
        CountryRequestDTO dto = new CountryRequestDTO(id, name);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
    }

    @Test
    void setters_WorkCorrectly() {
        // Given
        CountryRequestDTO dto = new CountryRequestDTO(1L,"n");

        // When
        dto.setId(1L);
        dto.setName("Test Country");

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("Test Country", dto.getName());
    }
}
