package com.beehyv.lab.dto.responseDto;

import com.beehyv.lab.dto.responseDto.LabTestResponseDTO;
import com.beehyv.lab.enums.LabSampleResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

public class LabTestResponseDTOTest {

    @Test
    void testGettersAndSetters() {
        // Given
        Long id = 1L;
        String testName = "TestName";
        String defaultPresent = "Default";
        String value = "TestValue";
        Date testDate = new Date();
        String performedBy = "Performer";
        Long labSampleId = 2L;
        Long requestStateId = 3L;
        String uom = "Unit";
        String testMethodFollowed = "Method";
        String referenceMethod = "ReferenceMethod";
        Double minValue = 0.0;
        Double maxValue = 100.0;
        LabSampleResult testResult = LabSampleResult.TEST_PASSED;
        Boolean isMandatory = true;

        // When
        LabTestResponseDTO labTestResponseDTO = new LabTestResponseDTO();
        labTestResponseDTO.setId(id);
        labTestResponseDTO.setTestName(testName);
        labTestResponseDTO.setDefaultPresent(defaultPresent);
        labTestResponseDTO.setValue(value);
        labTestResponseDTO.setTestDate(testDate);
        labTestResponseDTO.setPerformedBy(performedBy);
        labTestResponseDTO.setLabSampleId(labSampleId);
        labTestResponseDTO.setRequestStateId(requestStateId);
        labTestResponseDTO.setUom(uom);
        labTestResponseDTO.setTestMethodFollowed(testMethodFollowed);
        labTestResponseDTO.setReferenceMethod(referenceMethod);
        labTestResponseDTO.setMinValue(minValue);
        labTestResponseDTO.setMaxValue(maxValue);
        labTestResponseDTO.setTestResult(testResult);
        labTestResponseDTO.setIsMandatory(isMandatory);

        // Then
        assertEquals(id, labTestResponseDTO.getId());
        assertEquals(testName, labTestResponseDTO.getTestName());
        assertEquals(defaultPresent, labTestResponseDTO.getDefaultPresent());
        assertEquals(value, labTestResponseDTO.getValue());
        assertEquals(testDate, labTestResponseDTO.getTestDate());
        assertEquals(performedBy, labTestResponseDTO.getPerformedBy());
        assertEquals(labSampleId, labTestResponseDTO.getLabSampleId());
        assertEquals(requestStateId, labTestResponseDTO.getRequestStateId());
        assertEquals(uom, labTestResponseDTO.getUom());
        assertEquals(testMethodFollowed, labTestResponseDTO.getTestMethodFollowed());
        assertEquals(referenceMethod, labTestResponseDTO.getReferenceMethod());
        assertEquals(minValue, labTestResponseDTO.getMinValue());
        assertEquals(maxValue, labTestResponseDTO.getMaxValue());
        assertEquals(testResult, labTestResponseDTO.getTestResult());
        assertEquals(isMandatory, labTestResponseDTO.getIsMandatory());
    }
}
