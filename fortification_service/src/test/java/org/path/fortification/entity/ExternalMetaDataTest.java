package org.path.fortification.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExternalMetaDataTest {

    @Test
    void testAllFields() {
        // Arrange
        ExternalMetaData externalMetaData = new ExternalMetaData();
        Long id = 1L;
        String name = "Test Name";
        String value = "Test Value";
        String externalService = "Test Service";
        Boolean blocking = true;

        // Act
        externalMetaData.setId(id);
        externalMetaData.setName(name);
        externalMetaData.setValue(value);
        externalMetaData.setExternalService(externalService);
        externalMetaData.setBlocking(blocking);

        // Assert
        assertEquals(id, externalMetaData.getId());
        assertEquals(name, externalMetaData.getName());
        assertEquals(value, externalMetaData.getValue());
        assertEquals(externalService, externalMetaData.getExternalService());
        assertEquals(blocking, externalMetaData.getBlocking());
    }
}