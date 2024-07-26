package com.beehyv.lab.dto.responseDto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LabMethodResponseDtoTest {

    @Test
    void testAllArgsConstructor() {
        // Given
        LabTestTypeResponseDTO labTestTypeResponses = new LabTestTypeResponseDTO();
        List<LabTestReferenceMethodResponseDTO> labTestReferenceMethodResponses = new ArrayList<>();

        // When
        LabMethodResponseDto labMethodResponseDto = new LabMethodResponseDto(labTestTypeResponses, labTestReferenceMethodResponses);

        // Then
        assertNotNull(labMethodResponseDto);
        assertEquals(labTestTypeResponses, labMethodResponseDto.getLabTestTypeResponses());
        assertEquals(labTestReferenceMethodResponses, labMethodResponseDto.getLabTestReferenceMethodResponses());
    }

    @Test
    void testNoArgsConstructor() {
        // When
        LabMethodResponseDto labMethodResponseDto = new LabMethodResponseDto();

        // Then
        assertNotNull(labMethodResponseDto);
    }

    @Test
    void testGettersAndSetters() {
        // Given
        LabMethodResponseDto labMethodResponseDto = new LabMethodResponseDto();
        LabTestTypeResponseDTO labTestTypeResponses = new LabTestTypeResponseDTO();
        List<LabTestReferenceMethodResponseDTO> labTestReferenceMethodResponses = new ArrayList<>();

        // When
        labMethodResponseDto.setLabTestTypeResponses(labTestTypeResponses);
        labMethodResponseDto.setLabTestReferenceMethodResponses(labTestReferenceMethodResponses);

        // Then
        assertEquals(labTestTypeResponses, labMethodResponseDto.getLabTestTypeResponses());
        assertEquals(labTestReferenceMethodResponses, labMethodResponseDto.getLabTestReferenceMethodResponses());
    }
}
