package com.beehyv.lab.entityTests;



import com.beehyv.lab.entity.Inspection;
import com.beehyv.lab.entity.LabSample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class InspectionTest {

    @Mock
    private LabSample labSample;

    @InjectMocks
    private Inspection inspection;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllFields() {
        Long idValue = 1L;
        String requestedByValue = "Test Requester";
        boolean isBlockingValue = true;
        String commentsValue = "Test Comments";
        Boolean isExternalTestValue = true;

        when(labSample.getId()).thenReturn(1L);

        inspection.setId(idValue);
        inspection.setRequestedBy(requestedByValue);
        inspection.setBlocking(isBlockingValue);
        inspection.setComments(commentsValue);

        inspection.setLabSample(labSample);

        assertEquals(idValue, inspection.getId());
        assertEquals(requestedByValue, inspection.getRequestedBy());
        assertTrue(inspection.isBlocking());
        assertEquals(commentsValue, inspection.getComments());
        assertEquals(1L, inspection.getLabSample().getId());
    }
}