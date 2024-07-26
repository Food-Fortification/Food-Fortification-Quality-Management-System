package com.beehyv.lab.entityTests;

import com.beehyv.lab.entity.LabSample;
import com.beehyv.lab.entity.Lab;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class LabSampleTest {

    @Mock
    private Lab lab;

    @InjectMocks
    private LabSample labSample;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllFields() {
        Long idValue = 1L;
        Long batchIdValue = 1L;
        Long manufacturerIdValue = 1L;
        String batchNoValue = "Test Batch";
        String lotNoValue = "Test Lot";
        Long lotIdValue = 1L;

        when(lab.getId()).thenReturn(1L);

        labSample.setId(idValue);
        labSample.setBatchId(batchIdValue);
        labSample.setManufacturerId(manufacturerIdValue);
        labSample.setBatchNo(batchNoValue);
        labSample.setLotNo(lotNoValue);
        labSample.setLotId(lotIdValue);
        labSample.setLab(lab);

        assertEquals(idValue, labSample.getId());
        assertEquals(batchIdValue, labSample.getBatchId());
        assertEquals(manufacturerIdValue, labSample.getManufacturerId());
        assertEquals(batchNoValue, labSample.getBatchNo());
        assertEquals(lotNoValue, labSample.getLotNo());
        assertEquals(lotIdValue, labSample.getLotId());
        assertEquals(1L, labSample.getLab().getId());
    }
}