package org.path.lab.entityTests;

import org.path.lab.entity.SampleState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SampleStateTest {

    @InjectMocks
    private SampleState sampleState;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllFields() {
        Long idValue = 1L;
        String nameValue = "Test Name";
        String displayNameValue = "Test Display Name";

        sampleState.setId(idValue);
        sampleState.setName(nameValue);
        sampleState.setDisplayName(displayNameValue);

        assertEquals(idValue, sampleState.getId());
        assertEquals(nameValue, sampleState.getName());
        assertEquals(displayNameValue, sampleState.getDisplayName());
    }
}