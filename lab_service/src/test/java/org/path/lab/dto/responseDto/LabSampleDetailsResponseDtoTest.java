package org.path.lab.dto.responseDto;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LabSampleDetailsResponseDtoTest {

    @Test
    void testAllArgsConstructor() {
        // Given
        Long id = 1L;
        Long batchId = 2L;
        String batchNo = "BATCH123";
        String lotNo = "LOT456";
        Long lotId = 3L;
        Date testDate = new Date();
        Date sampleSentDate = new Date();
        Date receivedDate = new Date();
        Long inspectionId = 4L;
        Boolean isInspectionSample = true;
        Boolean isExternalTest = false;
        LabResponseDTO lab = new LabResponseDTO();
        Set<SamplePropertyResponseDTO> sampleProperties = new HashSet<>();
        Set<LabTestResponseDTO> labTests = new HashSet<>();
        Set<SampleTestDocumentResponseDTO> sampleTestDocuments = new HashSet<>();

        // When
        LabSampleDetailsResponseDto labSampleDetailsResponseDto = new LabSampleDetailsResponseDto(
                id, batchId, batchNo, lotNo, lotId, testDate, sampleSentDate, receivedDate,
                inspectionId, isInspectionSample, isExternalTest, lab, null, sampleProperties, labTests, sampleTestDocuments
        );

        // Then
        assertNotNull(labSampleDetailsResponseDto);
        assertEquals(id, labSampleDetailsResponseDto.getId());
        assertEquals(batchId, labSampleDetailsResponseDto.getBatchId());
        assertEquals(batchNo, labSampleDetailsResponseDto.getBatchNo());
        assertEquals(lotNo, labSampleDetailsResponseDto.getLotNo());
        assertEquals(lotId, labSampleDetailsResponseDto.getLotId());
        assertEquals(testDate, labSampleDetailsResponseDto.getTestDate());
        assertEquals(sampleSentDate, labSampleDetailsResponseDto.getSampleSentDate());
        assertEquals(receivedDate, labSampleDetailsResponseDto.getReceivedDate());
        assertEquals(inspectionId, labSampleDetailsResponseDto.getInspectionId());
        assertEquals(isInspectionSample, labSampleDetailsResponseDto.getIsInspectionSample());
        assertEquals(isExternalTest, labSampleDetailsResponseDto.getIsExternalTest());
        assertEquals(lab, labSampleDetailsResponseDto.getLab());
        assertEquals(sampleProperties, labSampleDetailsResponseDto.getSampleProperties());
        assertEquals(labTests, labSampleDetailsResponseDto.getLabTests());
        assertEquals(sampleTestDocuments, labSampleDetailsResponseDto.getSampleTestDocuments());
    }

    @Test
    void testNoArgsConstructor() {
        // When
        LabSampleDetailsResponseDto labSampleDetailsResponseDto = new LabSampleDetailsResponseDto();

        // Then
        assertNotNull(labSampleDetailsResponseDto);
    }

    @Test
    void testGettersAndSetters() {
        // Given
        LabSampleDetailsResponseDto labSampleDetailsResponseDto = new LabSampleDetailsResponseDto();
        Long id = 1L;
        Long batchId = 2L;
        String batchNo = "BATCH123";
        String lotNo = "LOT456";
        Long lotId = 3L;
        Date testDate = new Date();
        Date sampleSentDate = new Date();
        Date receivedDate = new Date();
        Long inspectionId = 4L;
        Boolean isInspectionSample = true;
        Boolean isExternalTest = false;
        LabResponseDTO lab = new LabResponseDTO();
        Set<SamplePropertyResponseDTO> sampleProperties = new HashSet<>();
        Set<LabTestResponseDTO> labTests = new HashSet<>();
        Set<SampleTestDocumentResponseDTO> sampleTestDocuments = new HashSet<>();

        // When
        labSampleDetailsResponseDto.setId(id);
        labSampleDetailsResponseDto.setBatchId(batchId);
        labSampleDetailsResponseDto.setBatchNo(batchNo);
        labSampleDetailsResponseDto.setLotNo(lotNo);
        labSampleDetailsResponseDto.setLotId(lotId);
        labSampleDetailsResponseDto.setTestDate(testDate);
        labSampleDetailsResponseDto.setSampleSentDate(sampleSentDate);
        labSampleDetailsResponseDto.setReceivedDate(receivedDate);
        labSampleDetailsResponseDto.setInspectionId(inspectionId);
        labSampleDetailsResponseDto.setIsInspectionSample(isInspectionSample);
        labSampleDetailsResponseDto.setIsExternalTest(isExternalTest);
        labSampleDetailsResponseDto.setLab(lab);
        labSampleDetailsResponseDto.setSampleProperties(sampleProperties);
        labSampleDetailsResponseDto.setLabTests(labTests);
        labSampleDetailsResponseDto.setSampleTestDocuments(sampleTestDocuments);

        // Then
        assertEquals(id, labSampleDetailsResponseDto.getId());
        assertEquals(batchId, labSampleDetailsResponseDto.getBatchId());
        assertEquals(batchNo, labSampleDetailsResponseDto.getBatchNo());
        assertEquals(lotNo, labSampleDetailsResponseDto.getLotNo());
        assertEquals(lotId, labSampleDetailsResponseDto.getLotId());
        assertEquals(testDate, labSampleDetailsResponseDto.getTestDate());
        assertEquals(sampleSentDate, labSampleDetailsResponseDto.getSampleSentDate());
        assertEquals(receivedDate, labSampleDetailsResponseDto.getReceivedDate());
        assertEquals(inspectionId, labSampleDetailsResponseDto.getInspectionId());
        assertEquals(isInspectionSample, labSampleDetailsResponseDto.getIsInspectionSample());
        assertEquals(isExternalTest, labSampleDetailsResponseDto.getIsExternalTest());
        assertEquals(lab, labSampleDetailsResponseDto.getLab());
        assertEquals(sampleProperties, labSampleDetailsResponseDto.getSampleProperties());
        assertEquals(labTests, labSampleDetailsResponseDto.getLabTests());
        assertEquals(sampleTestDocuments, labSampleDetailsResponseDto.getSampleTestDocuments());
    }
}
