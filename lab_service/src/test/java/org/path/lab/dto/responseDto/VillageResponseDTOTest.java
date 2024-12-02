package org.path.lab.dto.responseDto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VillageResponseDTOTest {

    @Test
    void testGettersAndSetters() {
        // Given
        VillageResponseDTO villageResponseDTO = new VillageResponseDTO();
        Long id = 1L;
        String name = "TestVillage";
        DistrictResponseDTO district = new DistrictResponseDTO();

        // When
        villageResponseDTO.setId(id);
        villageResponseDTO.setName(name);
        villageResponseDTO.setDistrict(district);

        // Then
        assertEquals(id, villageResponseDTO.getId());
        assertEquals(name, villageResponseDTO.getName());
        assertNotNull(villageResponseDTO.getDistrict());
    }
}
