package com.beehyv.lab.entityTests;


import com.beehyv.lab.entity.DocType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DocTypeTest {

    @InjectMocks
    private DocType docType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllFields() {
        Long idValue = 1L;
        String nameValue = "Test Document Type";

        docType.setId(idValue);
        docType.setName(nameValue);

        assertEquals(idValue, docType.getId());
        assertEquals(nameValue, docType.getName());
    }
}