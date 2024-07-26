package com.beehyv.lab.entityTests;

import com.beehyv.lab.entity.SampleStateGeo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SampleStateGeoTest {

    @InjectMocks
    private SampleStateGeo sampleStateGeo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllFields() {
        Long inTransitCountValue = 1L;
        Long underTestingCountValue = 1L;
        Long testedCountValue = 1L;
        Long rejectedCountValue = 1L;

        sampleStateGeo.setInTransitCount(inTransitCountValue);
        sampleStateGeo.setUnderTestingCount(underTestingCountValue);
        sampleStateGeo.setTestedCount(testedCountValue);
        sampleStateGeo.setRejectedCount(rejectedCountValue);

        assertEquals(inTransitCountValue, sampleStateGeo.getInTransitCount());
        assertEquals(underTestingCountValue, sampleStateGeo.getUnderTestingCount());
        assertEquals(testedCountValue, sampleStateGeo.getTestedCount());
        assertEquals(rejectedCountValue, sampleStateGeo.getRejectedCount());
    }
}