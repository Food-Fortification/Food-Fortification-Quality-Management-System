package org.path.lab.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VillageRequestDTOTest {

    @Test
    void constructorAndGetters_WorkCorrectly() {
        // Given
        Long id = 1L;
        String name = "Village";
        Long districtId = 2L;

        // When
        VillageRequestDTO dto = new VillageRequestDTO(id, name, districtId);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(districtId, dto.getDistrictId());
    }

    @Test
    void setters_WorkCorrectly() {
        // Given
        VillageRequestDTO dto = new VillageRequestDTO();
        Long id = 1L;
        String name = "Village";
        Long districtId = 2L;

        // When
        dto.setId(id);
        dto.setName(name);
        dto.setDistrictId(districtId);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(districtId, dto.getDistrictId());
    }
}
