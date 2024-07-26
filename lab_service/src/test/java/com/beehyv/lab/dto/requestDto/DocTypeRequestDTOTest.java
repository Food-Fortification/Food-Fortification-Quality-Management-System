package com.beehyv.lab.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocTypeRequestDTOTest {

    @Test
    void constructorAndGetters_WorkCorrectly() {
        // Given
        Long id = 1L;
        String name = "Test Doc Type";

        // When
        DocTypeRequestDTO dto = new DocTypeRequestDTO(id, name);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
    }

    @Test
    void setters_WorkCorrectly() {
        // Given
        DocTypeRequestDTO dto = new DocTypeRequestDTO(1L,"m");
        Long id = 1L;
        String name = "Test Doc Type";

        // When
        dto.setId(id);
        dto.setName(name);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
    }
}
