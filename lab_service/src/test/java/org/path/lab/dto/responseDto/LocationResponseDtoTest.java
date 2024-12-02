package org.path.lab.dto.responseDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationResponseDtoTest {

    @Test
    void testGettersAndSetters() {
        // Given
        LocationResponseDto locationResponseDto = new LocationResponseDto();
        Long id = 1L;
        String name = "Location";
        String code = "LOC";
        String geoId = "GEO123";

        // When
        locationResponseDto.setId(id);
        locationResponseDto.setName(name);
        locationResponseDto.setCode(code);
        locationResponseDto.setGeoId(geoId);

        // Then
        assertEquals(id, locationResponseDto.getId());
        assertEquals(name, locationResponseDto.getName());
        assertEquals(code, locationResponseDto.getCode());
        assertEquals(geoId, locationResponseDto.getGeoId());
    }
}
