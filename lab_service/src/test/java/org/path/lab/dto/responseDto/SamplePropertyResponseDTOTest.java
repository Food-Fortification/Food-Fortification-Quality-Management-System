package org.path.lab.dto.responseDto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SamplePropertyResponseDTOTest {

    @Test
    void testGettersAndSetters() {
        // Given
        SamplePropertyResponseDTO samplePropertyResponseDTO = new SamplePropertyResponseDTO();
        Long id = 1L;
        Long labSampleId = 2L;
        String keySample = "Key";
        String value = "Value";

        // When
        samplePropertyResponseDTO.setId(id);
        samplePropertyResponseDTO.setLabSampleId(labSampleId);
        samplePropertyResponseDTO.setKeySample(keySample);
        samplePropertyResponseDTO.setValue(value);

        // Then
        assertEquals(id, samplePropertyResponseDTO.getId());
        assertEquals(labSampleId, samplePropertyResponseDTO.getLabSampleId());
        assertEquals(keySample, samplePropertyResponseDTO.getKeySample());
        assertEquals(value, samplePropertyResponseDTO.getValue());
    }
}
