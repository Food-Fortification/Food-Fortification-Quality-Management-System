package com.beehyv.lab.dto.responseDto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StateResponseDTOTest {

    @Test
    void testGettersAndSetters() {
        // Given
        StateResponseDTO stateResponseDTO = new StateResponseDTO();
        Long id = 1L;
        String name = "TestState";
        String geoId = "TEST-GEO-ID";
        CountryResponseDTO country = new CountryResponseDTO();

        // When
        stateResponseDTO.setId(id);
        stateResponseDTO.setName(name);
        stateResponseDTO.setGeoId(geoId);
        stateResponseDTO.setCountry(country);

        // Then
        assertEquals(id, stateResponseDTO.getId());
        assertEquals(name, stateResponseDTO.getName());
        assertEquals(geoId, stateResponseDTO.getGeoId());
        assertEquals(country, stateResponseDTO.getCountry());
    }
}
