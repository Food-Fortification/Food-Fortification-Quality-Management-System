package org.path.lab.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SamplePropertyRequestDTOTest {

    @Test
    void constructorAndGetters_WorkCorrectly() {
        // Given
        Long id = 1L;
        Long labSampleId = 2L;
        String keySample = "keySample";
        String value = "Sample Value";

        // When
        SamplePropertyRequestDTO dto = new SamplePropertyRequestDTO(id, labSampleId, keySample, value);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(labSampleId, dto.getLabSampleId());
        assertEquals(keySample, dto.getKeySample());
        assertEquals(value, dto.getValue());
    }

    @Test
    void setters_WorkCorrectly() {
        // Given
        SamplePropertyRequestDTO dto = new SamplePropertyRequestDTO();
        Long id = 1L;
        Long labSampleId = 2L;
        String keySample = "keySample";
        String value = "Sample Value";

        // When
        dto.setId(id);
        dto.setLabSampleId(labSampleId);
        dto.setKeySample(keySample);
        dto.setValue(value);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(labSampleId, dto.getLabSampleId());
        assertEquals(keySample, dto.getKeySample());
        assertEquals(value, dto.getValue());
    }
}
