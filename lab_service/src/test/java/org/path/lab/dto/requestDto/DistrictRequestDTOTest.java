package org.path.lab.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DistrictRequestDTOTest {

    @Test
    void constructorAndGetters_WorkCorrectly() {
        // Given
        Long id = 1L;
        String name = "Test District";
        Long stateId = 2L;

        // When
        DistrictRequestDTO dto = new DistrictRequestDTO(id, name, stateId);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(stateId, dto.getStateId());
    }

    @Test
    void setters_WorkCorrectly() {
        // Given
        DistrictRequestDTO dto = new DistrictRequestDTO(1L,"n",1L);
        Long id = 1L;
        String name = "Test District";
        Long stateId = 2L;

        // When
        dto.setId(id);
        dto.setName(name);
        dto.setStateId(stateId);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(stateId, dto.getStateId());
    }
}
