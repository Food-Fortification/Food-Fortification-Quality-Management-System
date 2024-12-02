package org.path.lab.dto.responseDto;

import org.path.lab.enums.LabSampleResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LabSampleCreateResponseDtoTest {

    @Test
    void testAllArgsConstructor() {
        // Given
        Long id = 1L;
        Long labId = 2L;
        LabSampleResult result = LabSampleResult. TEST_PASSED;

        // When
        LabSampleCreateResponseDto labSampleCreateResponseDto = new LabSampleCreateResponseDto(id, labId, result);

        // Then
        assertNotNull(labSampleCreateResponseDto);
        assertEquals(id, labSampleCreateResponseDto.getId());
        assertEquals(labId, labSampleCreateResponseDto.getLabId());
        assertEquals(result, labSampleCreateResponseDto.getResult());
    }

    @Test
    void testNoArgsConstructor() {
        // When
        LabSampleCreateResponseDto labSampleCreateResponseDto = new LabSampleCreateResponseDto();

        // Then
        assertNotNull(labSampleCreateResponseDto);
    }

    @Test
    void testGettersAndSetters() {
        // Given
        LabSampleCreateResponseDto labSampleCreateResponseDto = new LabSampleCreateResponseDto();
        Long id = 1L;
        Long labId = 2L;
        LabSampleResult result = LabSampleResult. TEST_PASSED;

        // When
        labSampleCreateResponseDto.setId(id);
        labSampleCreateResponseDto.setLabId(labId);
        labSampleCreateResponseDto.setResult(result);

        // Then
        assertEquals(id, labSampleCreateResponseDto.getId());
        assertEquals(labId, labSampleCreateResponseDto.getLabId());
        assertEquals(result, labSampleCreateResponseDto.getResult());
    }
}
