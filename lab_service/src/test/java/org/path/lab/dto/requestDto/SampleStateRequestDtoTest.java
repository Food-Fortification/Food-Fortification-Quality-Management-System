package org.path.lab.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SampleStateRequestDtoTest {

    @Test
    void constructorAndGetters_WorkCorrectly() {
        // Given
        Long id = 1L;
        String name = "State";
        String displayName = "State Display Name";

        // When
        SampleStateRequestDto dto = new SampleStateRequestDto(id, name, displayName);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(displayName, dto.getDisplayName());
    }

    @Test
    void setters_WorkCorrectly() {
        // Given
        SampleStateRequestDto dto = new SampleStateRequestDto();
        Long id = 1L;
        String name = "State";
        String displayName = "State Display Name";

        // When
        dto.setId(id);
        dto.setName(name);
        dto.setDisplayName(displayName);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(displayName, dto.getDisplayName());
    }
}
