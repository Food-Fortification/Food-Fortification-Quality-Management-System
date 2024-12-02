package org.path.lab.dto.responseDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DistrictResponseDTOTest {

    @Test
    void testAllArgsConstructor() {
        // Given
        Long id = 1L;
        String name = "DistrictName";
        String geoId = "GeoId";
        StateResponseDTO state = new StateResponseDTO();

        // When
        DistrictResponseDTO districtResponseDTO = new DistrictResponseDTO(id, name, geoId, state);

        // Then
        assertNotNull(districtResponseDTO);
        assertEquals(id, districtResponseDTO.getId());
        assertEquals(name, districtResponseDTO.getName());
        assertEquals(geoId, districtResponseDTO.getGeoId());
        assertEquals(state, districtResponseDTO.getState());
    }

    @Test
    void testNoArgsConstructor() {
        // When
        DistrictResponseDTO districtResponseDTO = new DistrictResponseDTO();

        // Then
        assertNotNull(districtResponseDTO);
    }

    @Test
    void testGettersAndSetters() {
        // Given
        DistrictResponseDTO districtResponseDTO = new DistrictResponseDTO();
        Long id = 1L;
        String name = "DistrictName";
        String geoId = "GeoId";
        StateResponseDTO state = new StateResponseDTO();

        // When
        districtResponseDTO.setId(id);
        districtResponseDTO.setName(name);
        districtResponseDTO.setGeoId(geoId);
        districtResponseDTO.setState(state);

        // Then
        assertEquals(id, districtResponseDTO.getId());
        assertEquals(name, districtResponseDTO.getName());
        assertEquals(geoId, districtResponseDTO.getGeoId());
        assertEquals(state, districtResponseDTO.getState());
    }

    @Test
    void testToString() {
        // Given
        Long id = 1L;
        String name = "DistrictName";
        String geoId = "GeoId";
        StateResponseDTO state = new StateResponseDTO();
        DistrictResponseDTO districtResponseDTO = new DistrictResponseDTO(id, name, geoId, state);

        // When
        String result = districtResponseDTO.toString();

        // Then
        assertNotNull(result);
        assertTrue(result.contains("DistrictResponseDTO"));

    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        Long id = 1L;
        String name = "DistrictName";
        String geoId = "GeoId";
        StateResponseDTO state = new StateResponseDTO();
        DistrictResponseDTO districtResponseDTO1 = new DistrictResponseDTO(id, name, geoId, state);
        DistrictResponseDTO districtResponseDTO2 = new DistrictResponseDTO(id, name, geoId, state);

        // Then


    }
}
