package com.beehyv.lab.entityTests;

import com.beehyv.lab.entity.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatusTest {

    @InjectMocks
    private Status status;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllFields() {
        Long idValue = 1L;
        String nameValue = "Test Name";
        String descriptionValue = "Test Description";

        status.setId(idValue);
        status.setName(nameValue);
        status.setDescription(descriptionValue);

        assertEquals(idValue, status.getId());
        assertEquals(nameValue, status.getName());
        assertEquals(descriptionValue, status.getDescription());
    }
}
