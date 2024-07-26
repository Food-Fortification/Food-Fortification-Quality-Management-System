package com.beehyv.lab.entityTests;

import com.beehyv.lab.entity.ExternalMetaData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExternalMetaDataTest {

    @InjectMocks
    private ExternalMetaData externalMetaData;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllFields() {
        Long idValue = 1L;
        String nameValue = "Test MetaData";
        String valueValue = "Test Value";
        String externalServiceValue = "Test Service";

        externalMetaData.setId(idValue);
        externalMetaData.setName(nameValue);
        externalMetaData.setValue(valueValue);
        externalMetaData.setExternalService(externalServiceValue);

        assertEquals(idValue, externalMetaData.getId());
        assertEquals(nameValue, externalMetaData.getName());
        assertEquals(valueValue, externalMetaData.getValue());
        assertEquals(externalServiceValue, externalMetaData.getExternalService());
    }
}