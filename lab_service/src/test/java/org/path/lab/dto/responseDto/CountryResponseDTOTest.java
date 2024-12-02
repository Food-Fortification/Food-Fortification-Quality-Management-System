package org.path.lab.dto.responseDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryResponseDTOTest {

    @Test
    void testConstructorWithAllFields() {
        // Given
        Long id = 1L;
        String name = "India";
        String geoId = "IN";

        // When
        CountryResponseDTO dto = new CountryResponseDTO(id, name, geoId);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(geoId, dto.getGeoId());
    }

    @Test
    void testNoArgsConstructor() {
        // When
        CountryResponseDTO dto = new CountryResponseDTO();

        // Then
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getGeoId());
    }

    @Test
    void testGetId() {
        // Given
        Long id = 1L;
        CountryResponseDTO dto = new CountryResponseDTO();
        dto.setId(id);

        // When
        Long result = dto.getId();

        // Then
        assertEquals(id, result);
    }

    @Test
    void testSetId() {
        // Given
        CountryResponseDTO dto = new CountryResponseDTO();
        Long id = 1L;

        // When
        dto.setId(id);

        // Then
        assertEquals(id, dto.getId());
    }

    @Test
    void testGetName() {
        // Given
        String name = "India";
        CountryResponseDTO dto = new CountryResponseDTO();
        dto.setName(name);

        // When
        String result = dto.getName();

        // Then
        assertEquals(name, result);
    }

    @Test
    void testSetName() {
        // Given
        CountryResponseDTO dto = new CountryResponseDTO();
        String name = "India";

        // When
        dto.setName(name);

        // Then
        assertEquals(name, dto.getName());
    }

    @Test
    void testGetGeoId() {
        // Given
        String geoId = "IN";
        CountryResponseDTO dto = new CountryResponseDTO();
        dto.setGeoId(geoId);

        // When
        String result = dto.getGeoId();

        // Then
        assertEquals(geoId, result);
    }

    @Test
    void testSetGeoId() {
        // Given
        CountryResponseDTO dto = new CountryResponseDTO();
        String geoId = "IN";

        // When
        dto.setGeoId(geoId);

        // Then
        assertEquals(geoId, dto.getGeoId());
    }

    @Test
    void testToString() {
        // Given
        Long id = 1L;
        String name = "India";
        String geoId = "IN";
        CountryResponseDTO dto = new CountryResponseDTO(id, name, geoId);

        // When
        String result = dto.toString();

        // Then
        assertNotNull(result);
        assertTrue(result.contains("CountryResponseDTO"));

    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        Long id1 = 1L;
        String name1 = "India";
        String geoId1 = "IN";

        Long id2 = 1L;
        String name2 = "India";
        String geoId2 = "IN";

        CountryResponseDTO dto1 = new CountryResponseDTO(id1, name1, geoId1);
        CountryResponseDTO dto2 = new CountryResponseDTO(id2, name2, geoId2);

    }

    @Test
    void testNotEqualsAndHashCode() {
        // Given
        Long id1 = 1L;
        String name1 = "India";
        String geoId1 = "IN";

        Long id2 = 2L;
        String name2 = "USA";
        String geoId2 = "US";

        CountryResponseDTO dto1 = new CountryResponseDTO(id1, name1, geoId1);
        CountryResponseDTO dto2 = new CountryResponseDTO(id2, name2, geoId2);

        // Then
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }
}
