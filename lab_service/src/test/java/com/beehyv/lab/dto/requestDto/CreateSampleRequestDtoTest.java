package com.beehyv.lab.dto.requestDto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreateSampleRequestDtoTest {

    @Test
    void constructorAndGetters_WorkCorrectly() {
        // Given
        Long batchId = 1L;
        List<LabTestRequestDTO> tests = new ArrayList<>(); // Initialize with test data

        // When
        CreateSampleRequestDto dto = new CreateSampleRequestDto(batchId, tests);

        // Then
        assertEquals(batchId, dto.getBatchId());
        assertEquals(tests, dto.getTests());
    }

    @Test
    void setters_WorkCorrectly() {
        // Given
        CreateSampleRequestDto dto = new CreateSampleRequestDto();
        Long batchId = 1L;
        List<LabTestRequestDTO> tests = new ArrayList<>(); // Initialize with test data

        // When
        dto.setBatchId(batchId);
        dto.setTests(tests);

        // Then
        assertEquals(batchId, dto.getBatchId());
        assertEquals(tests, dto.getTests());
    }

    @Test
    void toString_ReturnsExpectedString() {
        // Given
        Long batchId = 1L;
        List<LabTestRequestDTO> tests = new ArrayList<>(); // Initialize with test data
        CreateSampleRequestDto dto = new CreateSampleRequestDto(batchId, tests);

        // When
        String expectedString = "CreateSampleRequestDto(batchId=1, tests=" + tests.toString() + ")";
        String actualString = dto.toString();

        // Then
        assertEquals(expectedString, actualString);
    }
}
