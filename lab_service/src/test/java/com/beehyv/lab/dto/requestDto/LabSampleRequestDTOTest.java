package com.beehyv.lab.dto.requestDto;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LabSampleRequestDTOTest {

    @Test
    void constructorAndGetters_WorkCorrectly() {
        // Given
        Long id = 1L;
        Long batchId = 2L;
        Long manufacturerId = 3L;
        String batchNo = "TestBatchNo";
        String lotNo = "TestLotNo";
        Long lotId = 4L;
        Date testDate = new Date();
        Date sampleSentDate = new Date();
        Date receivedDate = new Date();
        String performedBy = "TestPerformer";
        Long labId = 5L;
        String requestStatusId = "TestRequestStatusId";
        Long categoryId = 6L;
        Set<SamplePropertyRequestDTO> sampleProperties = new HashSet<>();
        Set<LabTestRequestDTO> labTests = new HashSet<>();
        Set<SampleTestDocumentRequestDTO> sampleTestDocuments = new HashSet<>();
        Boolean resultAccepted = true;
        Long stateId = 7L;
        String externalManufacturerId = "TestExternalManufacturerId";
        Double percentageCategoryMix = 0.5;

        // When
        LabSampleRequestDTO dto = new LabSampleRequestDTO(id, batchId, manufacturerId, batchNo, lotNo, lotId, testDate,
                sampleSentDate, receivedDate, performedBy, labId, requestStatusId, categoryId, sampleProperties,
                labTests, sampleTestDocuments, resultAccepted, stateId, externalManufacturerId, percentageCategoryMix);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(batchId, dto.getBatchId());
        assertEquals(manufacturerId, dto.getManufacturerId());
        assertEquals(batchNo, dto.getBatchNo());
        assertEquals(lotNo, dto.getLotNo());
        assertEquals(lotId, dto.getLotId());
        assertEquals(testDate, dto.getTestDate());
        assertEquals(sampleSentDate, dto.getSampleSentDate());
        assertEquals(receivedDate, dto.getReceivedDate());
        assertEquals(performedBy, dto.getPerformedBy());
        assertEquals(labId, dto.getLabId());
        assertEquals(requestStatusId, dto.getRequestStatusId());
        assertEquals(categoryId, dto.getCategoryId());
        assertEquals(sampleProperties, dto.getSampleProperties());
        assertEquals(labTests, dto.getLabTests());
        assertEquals(sampleTestDocuments, dto.getSampleTestDocuments());
        assertEquals(resultAccepted, dto.getResultAccepted());
        assertEquals(stateId, dto.getStateId());
        assertEquals(externalManufacturerId, dto.getExternalManufacturerId());
        assertEquals(percentageCategoryMix, dto.getPercentageCategoryMix());
    }

    @Test
    void setters_WorkCorrectly() {
        // Given
        LabSampleRequestDTO dto = new LabSampleRequestDTO();
        Long id = 1L;
        Long batchId = 2L;
        Long manufacturerId = 3L;
        String batchNo = "TestBatchNo";
        String lotNo = "TestLotNo";
        Long lotId = 4L;
        Date testDate = new Date();
        Date sampleSentDate = new Date();
        Date receivedDate = new Date();
        String performedBy = "TestPerformer";
        Long labId = 5L;
        String requestStatusId = "TestRequestStatusId";
        Long categoryId = 6L;
        Set<SamplePropertyRequestDTO> sampleProperties = new HashSet<>();
        Set<LabTestRequestDTO> labTests = new HashSet<>();
        Set<SampleTestDocumentRequestDTO> sampleTestDocuments = new HashSet<>();
        Boolean resultAccepted = true;
        Long stateId = 7L;
        String externalManufacturerId = "TestExternalManufacturerId";
        Double percentageCategoryMix = 0.5;

        // When
        dto.setId(id);
        dto.setBatchId(batchId);
        dto.setManufacturerId(manufacturerId);
        dto.setBatchNo(batchNo);
        dto.setLotNo(lotNo);
        dto.setLotId(lotId);
        dto.setTestDate(testDate);
        dto.setSampleSentDate(sampleSentDate);
        dto.setReceivedDate(receivedDate);
        dto.setPerformedBy(performedBy);
        dto.setLabId(labId);
        dto.setRequestStatusId(requestStatusId);
        dto.setCategoryId(categoryId);
        dto.setSampleProperties(sampleProperties);
        dto.setLabTests(labTests);
        dto.setSampleTestDocuments(sampleTestDocuments);
        dto.setResultAccepted(resultAccepted);
        dto.setStateId(stateId);
        dto.setExternalManufacturerId(externalManufacturerId);
        dto.setPercentageCategoryMix(percentageCategoryMix);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(batchId, dto.getBatchId());
        assertEquals(manufacturerId, dto.getManufacturerId());
        assertEquals(batchNo, dto.getBatchNo());
        assertEquals(lotNo, dto.getLotNo());
        assertEquals(lotId, dto.getLotId());
        assertEquals(testDate, dto.getTestDate());
        assertEquals(sampleSentDate, dto.getSampleSentDate());
        assertEquals(receivedDate, dto.getReceivedDate());
        assertEquals(performedBy, dto.getPerformedBy());
        assertEquals(labId, dto.getLabId());
        assertEquals(requestStatusId, dto.getRequestStatusId());
        assertEquals(categoryId, dto.getCategoryId());
        assertEquals(sampleProperties, dto.getSampleProperties());
        assertEquals(labTests, dto.getLabTests());

    }}