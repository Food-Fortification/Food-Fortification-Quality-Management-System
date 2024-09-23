package com.beehyv.lab.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InspectionRequestDTOTest {

    @Test
    void gettersAndSetters_WorkCorrectly() {
        // Given
        Long id = 1L;
        String requestedBy = "John Doe";
        boolean isBlocking = true;
        String comments = "Sample inspection comments";
        LabSampleRequestDTO labSample = new LabSampleRequestDTO();
        Boolean externalTest = false;

        // When
        InspectionRequestDTO dto = new InspectionRequestDTO();
        dto.setId(id);
        dto.setRequestedBy(requestedBy);
        dto.setComments(comments);
        dto.setLabSample(labSample);
        dto.setExternalTest(externalTest);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(requestedBy, dto.getRequestedBy());
        assertEquals(comments, dto.getComments());
        assertEquals(labSample, dto.getLabSample());
        assertEquals(externalTest, dto.getExternalTest());
    }
}
