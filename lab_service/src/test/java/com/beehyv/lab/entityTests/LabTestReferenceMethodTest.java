package com.beehyv.lab.entityTests;

import com.beehyv.lab.entity.LabTestReferenceMethod;
import com.beehyv.lab.entity.LabTestType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class LabTestReferenceMethodTest {

    @Mock
    private LabTestType labTestType;

    @InjectMocks
    private LabTestReferenceMethod labTestReferenceMethod;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllFields() {
        Long idValue = 1L;
        String nameValue = "Test Name";
        Double minValueValue = 1.0;
        Double maxValueValue = 2.0;
        String uomValue = "Test UOM";
        String defaultPresentValue = "Test Default Present";
        String referenceValueValue = "Test Reference Value";
        Double minPercentVariationAllowedValue = 0.1;
        Double maxPercentVariationAllowedValue = 0.2;

        when(labTestType.getId()).thenReturn(1L);

        labTestReferenceMethod.setId(idValue);
        labTestReferenceMethod.setName(nameValue);
        labTestReferenceMethod.setMinValue(minValueValue);
        labTestReferenceMethod.setMaxValue(maxValueValue);
        labTestReferenceMethod.setUom(uomValue);
        labTestReferenceMethod.setDefaultPresent(defaultPresentValue);
        labTestReferenceMethod.setReferenceValue(referenceValueValue);
        labTestReferenceMethod.setMinPercentVariationAllowed(minPercentVariationAllowedValue);
        labTestReferenceMethod.setMaxPercentVariationAllowed(maxPercentVariationAllowedValue);
        labTestReferenceMethod.setLabTestType(labTestType);

        assertEquals(idValue, labTestReferenceMethod.getId());
        assertEquals(nameValue, labTestReferenceMethod.getName());
        assertEquals(minValueValue, labTestReferenceMethod.getMinValue());
        assertEquals(maxValueValue, labTestReferenceMethod.getMaxValue());
        assertEquals(uomValue, labTestReferenceMethod.getUom());
        assertEquals(defaultPresentValue, labTestReferenceMethod.getDefaultPresent());
        assertEquals(referenceValueValue, labTestReferenceMethod.getReferenceValue());
        assertEquals(minPercentVariationAllowedValue, labTestReferenceMethod.getMinPercentVariationAllowed());
        assertEquals(maxPercentVariationAllowedValue, labTestReferenceMethod.getMaxPercentVariationAllowed());
        assertEquals(1L, labTestReferenceMethod.getLabTestType().getId());
    }
}