package com.beehyv.lab.entityTests;

import com.beehyv.lab.entity.LabTest;
import com.beehyv.lab.entity.LabSample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class LabTestTest {

    @Mock
    private LabSample labSample;

    @InjectMocks
    private LabTest labTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllFields() {
        Long idValue = 1L;
        String testNameValue = "Test Name";
        String valueValue = "Test Value";
        String defaultPresentValue = "Test Default Present";
        String uomValue = "Test UOM";
        String testMethodFollowedValue = "Test Method Followed";

        when(labSample.getId()).thenReturn(1L);

        labTest.setId(idValue);
        labTest.setTestName(testNameValue);
        labTest.setValue(valueValue);
        labTest.setDefaultPresent(defaultPresentValue);
        labTest.setUom(uomValue);
        labTest.setTestMethodFollowed(testMethodFollowedValue);
        labTest.setLabSample(labSample);

        assertEquals(idValue, labTest.getId());
        assertEquals(testNameValue, labTest.getTestName());
        assertEquals(valueValue, labTest.getValue());
        assertEquals(defaultPresentValue, labTest.getDefaultPresent());
        assertEquals(uomValue, labTest.getUom());
        assertEquals(testMethodFollowedValue, labTest.getTestMethodFollowed());
        assertEquals(1L, labTest.getLabSample().getId());
    }
}