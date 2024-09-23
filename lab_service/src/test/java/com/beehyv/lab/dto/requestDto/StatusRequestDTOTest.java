package com.beehyv.lab.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatusRequestDTOTest {

    @Test
    void constructorAndGetters_WorkCorrectly() {
        // Given
        Long id = 1L;
        String name = "Status";
        String description = "Status Description";

        // When
        StatusRequestDTO dto = new StatusRequestDTO(id, name, description);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(description, dto.getDescription());
    }

    @Test
    void setters_WorkCorrectly() {
        // Given
        StatusRequestDTO dto = new StatusRequestDTO();
        Long id = 1L;
        String name = "Status";
        String description = "Status Description";

        // When
        dto.setId(id);
        dto.setName(name);
        dto.setDescription(description);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(description, dto.getDescription());
    }
}
