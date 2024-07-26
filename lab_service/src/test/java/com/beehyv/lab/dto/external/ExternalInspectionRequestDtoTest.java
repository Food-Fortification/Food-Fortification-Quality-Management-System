package com.beehyv.lab.dto.external;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ExternalInspectionRequestDtoTest {

    @Test
    void gettersAndSetters_WorkCorrectly() {
        // Given
        String lotNo = "1234";
        Date sampleSentDate = new Date();
        Double labSampleQuantity = 10.5;

        // When
        ExternalInspectionRequestDto dto = new ExternalInspectionRequestDto();
        dto.setLotNo(lotNo);
        dto.setSampleSentDate(sampleSentDate);
        dto.setLabSampleQuantity(labSampleQuantity);

        // Then
        assertEquals(lotNo, dto.getLotNo());
        assertEquals(sampleSentDate, dto.getSampleSentDate());
        assertEquals(labSampleQuantity, dto.getLabSampleQuantity());
    }

    @Test
    void constructor_InitializesFieldsCorrectly() {
        // Given
        String lotNo = "1234";
        Date sampleSentDate = new Date();
        Double labSampleQuantity = 10.5;

        // When
        ExternalInspectionRequestDto dto = new ExternalInspectionRequestDto(lotNo, sampleSentDate, labSampleQuantity);

        // Then
        assertEquals(lotNo, dto.getLotNo());
        assertEquals(sampleSentDate, dto.getSampleSentDate());
        assertEquals(labSampleQuantity, dto.getLabSampleQuantity());
    }

    @Test
    void toString_ReturnsExpectedString() {
        // Given
        String lotNo = "1234";
        Date sampleSentDate = new Date();
        Double labSampleQuantity = 10.5;
        ExternalInspectionRequestDto dto = new ExternalInspectionRequestDto(lotNo, sampleSentDate, labSampleQuantity);

        // When
        String expectedString = "ExternalInspectionRequestDto(lotNo=1234, sampleSentDate=" + sampleSentDate + ", labSampleQuantity=10.5)";
        String actualString = dto.toString();

        // Then
        assertEquals(expectedString, actualString);
    }
}
