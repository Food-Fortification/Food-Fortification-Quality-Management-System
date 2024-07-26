package com.beehyv.lab.dto.responseDto;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LabSampleResponseDtoTest {

    @Test
    void testAllArgsConstructor() {
        // Given
        Long id = 1L;
        Long batchId = 2L;
        Long manufacturerId = 3L;
        String batchNo = "BATCH123";
        String lotNo = "LOT456";
        Long lotId = 4L;
        Date testDate = new Date();
        Date sampleSentDate = new Date();
        Date receivedDate = new Date();
        String performedBy = "Tester";
        Long requestStatusId = 5L;
        Long categoryId = 6L;
        Boolean resultAccepted = true;
        Long inspectionId = 7L;
        Boolean isInspectionSample = true;
        Boolean isExternalTest = false;
        LabResponseDTO lab = new LabResponseDTO();
        SampleStateResponseDTO state = new SampleStateResponseDTO();
        Set<SamplePropertyResponseDTO> sampleProperties = new HashSet<>();
        Set<LabTestResponseDTO> labTests = new HashSet<>();
        Set<SampleTestDocumentResponseDTO> sampleTestDocuments = new HashSet<>();

        // When
        LabSampleResponseDto labSampleResponseDto = new LabSampleResponseDto(
                id, batchId, manufacturerId, batchNo, lotNo, lotId, testDate, sampleSentDate,
                receivedDate, performedBy, requestStatusId, categoryId, resultAccepted, inspectionId,
                isInspectionSample, isExternalTest, lab, state, sampleProperties, labTests, sampleTestDocuments
        );

        // Then
        assertNotNull(labSampleResponseDto);
        assertEquals(id, labSampleResponseDto.getId());
        assertEquals(batchId, labSampleResponseDto.getBatchId());
        assertEquals(manufacturerId, labSampleResponseDto.getManufacturerId());
        assertEquals(batchNo, labSampleResponseDto.getBatchNo());
        assertEquals(lotNo, labSampleResponseDto.getLotNo());
        assertEquals(lotId, labSampleResponseDto.getLotId());
        assertEquals(testDate, labSampleResponseDto.getTestDate());
        assertEquals(sampleSentDate, labSampleResponseDto.getSampleSentDate());
        assertEquals(receivedDate, labSampleResponseDto.getReceivedDate());
        assertEquals(performedBy, labSampleResponseDto.getPerformedBy());
        assertEquals(requestStatusId, labSampleResponseDto.getRequestStatusId());
        assertEquals(categoryId, labSampleResponseDto.getCategoryId());
        assertEquals(resultAccepted, labSampleResponseDto.getResultAccepted());
        assertEquals(inspectionId, labSampleResponseDto.getInspectionId());
        assertEquals(isInspectionSample, labSampleResponseDto.getIsInspectionSample());
        assertEquals(isExternalTest, labSampleResponseDto.getIsExternalTest());
        assertEquals(lab, labSampleResponseDto.getLab());
        assertEquals(state, labSampleResponseDto.getState());
        assertEquals(sampleProperties, labSampleResponseDto.getSampleProperties());
        assertEquals(labTests, labSampleResponseDto.getLabTests());
        assertEquals(sampleTestDocuments, labSampleResponseDto.getSampleTestDocuments());
    }

    @Test
    void testNoArgsConstructor() {
        // When
        LabSampleResponseDto labSampleResponseDto = new LabSampleResponseDto();

        // Then
        assertNotNull(labSampleResponseDto);
    }

    @Test
    void testGettersAndSetters() {
        // Given
        LabSampleResponseDto labSampleResponseDto = new LabSampleResponseDto();
        Long id = 1L;
        Long batchId = 2L;
        Long manufacturerId = 3L;
        String batchNo = "BATCH123";
        String lotNo = "LOT456";
        Long lotId = 4L;
        Date testDate = new Date();
        Date sampleSentDate = new Date();
        Date receivedDate = new Date();
        String performedBy = "Tester";
        Long requestStatusId = 5L;
        Long categoryId = 6L;
        Boolean resultAccepted = true;
        Long inspectionId = 7L;
        Boolean isInspectionSample = true;
        Boolean isExternalTest = false;
        LabResponseDTO lab = new LabResponseDTO();
        SampleStateResponseDTO state = new SampleStateResponseDTO();
        Set<SamplePropertyResponseDTO> sampleProperties = new HashSet<>();
        Set<LabTestResponseDTO> labTests = new HashSet<>();
        Set<SampleTestDocumentResponseDTO> sampleTestDocuments = new HashSet<>();

        // When
        labSampleResponseDto.setId(id);
        labSampleResponseDto.setBatchId(batchId);
        labSampleResponseDto.setManufacturerId(manufacturerId);
        labSampleResponseDto.setBatchNo(batchNo);
        labSampleResponseDto.setLotNo(lotNo);
        labSampleResponseDto.setLotId(lotId);
        labSampleResponseDto.setTestDate(testDate);
        labSampleResponseDto.setSampleSentDate(sampleSentDate);
        labSampleResponseDto.setReceivedDate(receivedDate);
        labSampleResponseDto.setPerformedBy(performedBy);
        labSampleResponseDto.setRequestStatusId(requestStatusId);
        labSampleResponseDto.setCategoryId(categoryId);
        labSampleResponseDto.setResultAccepted(resultAccepted);
        labSampleResponseDto.setInspectionId(inspectionId);
        labSampleResponseDto.setIsInspectionSample(isInspectionSample);
        labSampleResponseDto.setIsExternalTest(isExternalTest);
        labSampleResponseDto.setLab(lab);
        labSampleResponseDto.setState(state);
        labSampleResponseDto.setSampleProperties(sampleProperties);
        labSampleResponseDto.setLabTests(labTests);
        labSampleResponseDto.setSampleTestDocuments(sampleTestDocuments);

        // Then
        assertEquals(id, labSampleResponseDto.getId());
        assertEquals(batchId, labSampleResponseDto.getBatchId());
        assertEquals(manufacturerId, labSampleResponseDto.getManufacturerId());
        assertEquals(batchNo, labSampleResponseDto.getBatchNo());
        assertEquals(lotNo, labSampleResponseDto.getLotNo());
        assertEquals(lotId, labSampleResponseDto.getLotId());
        assertEquals(testDate, labSampleResponseDto.getTestDate());
        assertEquals(sampleSentDate, labSampleResponseDto.getSampleSentDate());
        assertEquals(receivedDate, labSampleResponseDto.getReceivedDate());
        assertEquals(performedBy, labSampleResponseDto.getPerformedBy());
        assertEquals(requestStatusId, labSampleResponseDto.getRequestStatusId());
        assertEquals(categoryId, labSampleResponseDto.getCategoryId());
        assertEquals(resultAccepted, labSampleResponseDto.getResultAccepted());
        assertEquals(inspectionId, labSampleResponseDto.getInspectionId());
        assertEquals(isInspectionSample, labSampleResponseDto.getIsInspectionSample());
        assertEquals(isExternalTest, labSampleResponseDto.getIsExternalTest());
        assertEquals(lab, labSampleResponseDto.getLab());
        assertEquals(state, labSampleResponseDto.getState());
        assertEquals(sampleProperties, labSampleResponseDto.getSampleProperties());
        assertEquals(labTests, labSampleResponseDto.getLabTests());
        assertEquals(sampleTestDocuments, labSampleResponseDto.getSampleTestDocuments());
    }
}