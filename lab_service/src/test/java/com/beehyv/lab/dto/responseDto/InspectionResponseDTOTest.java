package com.beehyv.lab.dto.responseDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InspectionResponseDTOTest {

    @Test
    void testAllArgsConstructor() {
        // Given
        Long id = 1L;
        String requestedBy = "John Doe";
        boolean isBlocking = true;
        String comments = "Test comments";
        LabSampleResponseDto labSample = new LabSampleResponseDto();

        // When
        InspectionResponseDTO inspectionResponseDTO = new InspectionResponseDTO();

        // Then
        assertNotNull(inspectionResponseDTO);


    }

    @Test
    void testNoArgsConstructor() {
        // When
        InspectionResponseDTO inspectionResponseDTO = new InspectionResponseDTO();

        // Then
        assertNotNull(inspectionResponseDTO);
    }

    @Test
    void testGettersAndSetters() {
        // Given
        InspectionResponseDTO inspectionResponseDTO = new InspectionResponseDTO();
        Long id = 1L;
        String requestedBy = "John Doe";
        boolean isBlocking = true;
        String comments = "Test comments";
        LabSampleResponseDto labSample = new LabSampleResponseDto();

        // When
        inspectionResponseDTO.setId(id);
        inspectionResponseDTO.setRequestedBy(requestedBy);
        inspectionResponseDTO.setBlocking(isBlocking);
        inspectionResponseDTO.setComments(comments);
        inspectionResponseDTO.setLabSample(labSample);

        // Then
        assertEquals(id, inspectionResponseDTO.getId());
        assertEquals(requestedBy, inspectionResponseDTO.getRequestedBy());
        assertEquals(isBlocking, inspectionResponseDTO.isBlocking());
        assertEquals(comments, inspectionResponseDTO.getComments());
        assertEquals(labSample, inspectionResponseDTO.getLabSample());
    }

    @Test
    void testToString() {
        // Given
        InspectionResponseDTO inspectionResponseDTO = new InspectionResponseDTO();

        // When
        String result = inspectionResponseDTO.toString();

        // Then
        assertNotNull(result);
        assertTrue(result.contains("InspectionResponseDTO"));
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        InspectionResponseDTO inspectionResponseDTO1 = new InspectionResponseDTO();
        InspectionResponseDTO inspectionResponseDTO2 = new InspectionResponseDTO();

        // Then

    }
}
