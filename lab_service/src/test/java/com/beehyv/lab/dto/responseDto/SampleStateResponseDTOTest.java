package com.beehyv.lab.dto.responseDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleStateResponseDTOTest {

    @Test
    void testGettersAndSetters() {
        // Given
        SampleStateResponseDTO sampleStateResponseDTO = new SampleStateResponseDTO();
        Long id = 1L;
        String name = "State1";
        String displayName = "State One";

        // When
        sampleStateResponseDTO.setId(id);
        sampleStateResponseDTO.setName(name);
        sampleStateResponseDTO.setDisplayName(displayName);

        // Then
        assertEquals(id, sampleStateResponseDTO.getId());
        assertEquals(name, sampleStateResponseDTO.getName());
        assertEquals(displayName, sampleStateResponseDTO.getDisplayName());
    }
}
