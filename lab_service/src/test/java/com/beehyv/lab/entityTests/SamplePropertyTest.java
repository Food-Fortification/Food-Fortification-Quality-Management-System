package com.beehyv.lab.entityTests;

import com.beehyv.lab.entity.SampleProperty;
import com.beehyv.lab.entity.LabSample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class SamplePropertyTest {

    @Mock
    private LabSample labSample;

    @InjectMocks
    private SampleProperty sampleProperty;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllFields() {
        Long idValue = 1L;
        String keySampleValue = "Test Key";
        String valueValue = "Test Value";

        when(labSample.getId()).thenReturn(1L);

        sampleProperty.setId(idValue);
        sampleProperty.setKeySample(keySampleValue);
        sampleProperty.setValue(valueValue);
        sampleProperty.setLabSample(labSample);

        assertEquals(idValue, sampleProperty.getId());
        assertEquals(keySampleValue, sampleProperty.getKeySample());
        assertEquals(valueValue, sampleProperty.getValue());
        assertEquals(1L, sampleProperty.getLabSample().getId());
    }
}