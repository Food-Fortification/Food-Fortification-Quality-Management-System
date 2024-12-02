package org.path.lab.dto.requestDto;

import org.path.lab.enums.LabSampleResult;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LabTestRequestDTOTest {

    @Test
    void constructorAndGetters_WorkCorrectly() {
        // Given
        Long id = 1L;
        Long labTestReferenceMethodId = 2L;
        String testName = "Test Name";
        String defaultPresent = "Yes";
        String value = "Test Value";
        Date testDate = new Date();
        String performedBy = "Test Performer";
        Long labSampleId = 3L;
        Long requestStateId = 4L;
        String uom = "Test UOM";
        String testMethodFollowed = "Test Method Followed";
        String referenceMethod = "Reference Method";
        Double minValue = 0.0;
        Double maxValue = 100.0;
        LabSampleResult testResult = LabSampleResult.TEST_PASSED;
        Boolean isMandatory = true;

        // When
        LabTestRequestDTO dto = new LabTestRequestDTO(id, labTestReferenceMethodId, testName, defaultPresent, value,
                testDate, performedBy, labSampleId, requestStateId, uom, testMethodFollowed, referenceMethod, minValue,
                maxValue, testResult, isMandatory);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(labTestReferenceMethodId, dto.getLabTestReferenceMethodId());
        assertEquals(testName, dto.getTestName());
        assertEquals(defaultPresent, dto.getDefaultPresent());
        assertEquals(value, dto.getValue());
        assertEquals(testDate, dto.getTestDate());
        assertEquals(performedBy, dto.getPerformedBy());
        assertEquals(labSampleId, dto.getLabSampleId());
        assertEquals(requestStateId, dto.getRequestStateId());
        assertEquals(uom, dto.getUom());
        assertEquals(testMethodFollowed, dto.getTestMethodFollowed());
        assertEquals(referenceMethod, dto.getReferenceMethod());
        assertEquals(minValue, dto.getMinValue());
        assertEquals(maxValue, dto.getMaxValue());
        assertEquals(testResult, dto.getTestResult());
        assertEquals(isMandatory, dto.getIsMandatory());
    }

    @Test
    void setters_WorkCorrectly() {
        // Given
        LabTestRequestDTO dto = new LabTestRequestDTO();
        Long id = 1L;
        Long labTestReferenceMethodId = 2L;
        String testName = "Test Name";
        String defaultPresent = "Yes";
        String value = "Test Value";
        Date testDate = new Date();
        String performedBy = "Test Performer";
        Long labSampleId = 3L;
        Long requestStateId = 4L;
        String uom = "Test UOM";
        String testMethodFollowed = "Test Method Followed";
        String referenceMethod = "Reference Method";
        Double minValue = 0.0;
        Double maxValue = 100.0;
        LabSampleResult testResult = LabSampleResult.TEST_PASSED;
        Boolean isMandatory = true;

        // When
        dto.setId(id);
        dto.setLabTestReferenceMethodId(labTestReferenceMethodId);
        dto.setTestName(testName);
        dto.setDefaultPresent(defaultPresent);
        dto.setValue(value);
        dto.setTestDate(testDate);
        dto.setPerformedBy(performedBy);
        dto.setLabSampleId(labSampleId);
        dto.setRequestStateId(requestStateId);
        dto.setUom(uom);
        dto.setTestMethodFollowed(testMethodFollowed);
        dto.setReferenceMethod(referenceMethod);
        dto.setMinValue(minValue);
        dto.setMaxValue(maxValue);
        dto.setTestResult(testResult);
        dto.setIsMandatory(isMandatory);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(labTestReferenceMethodId, dto.getLabTestReferenceMethodId());
        assertEquals(testName, dto.getTestName());
        assertEquals(defaultPresent, dto.getDefaultPresent());
        assertEquals(value, dto.getValue());
        assertEquals(testDate, dto.getTestDate());
        assertEquals(performedBy, dto.getPerformedBy());
        assertEquals(labSampleId, dto.getLabSampleId());
        assertEquals(requestStateId, dto.getRequestStateId());
        assertEquals(uom, dto.getUom());
        assertEquals(testMethodFollowed, dto.getTestMethodFollowed());
        assertEquals(referenceMethod, dto.getReferenceMethod());
        assertEquals(minValue, dto.getMinValue());
        assertEquals(maxValue, dto.getMaxValue());
        assertEquals(testResult, dto.getTestResult());
        assertEquals(isMandatory, dto.getIsMandatory());
    }
}
