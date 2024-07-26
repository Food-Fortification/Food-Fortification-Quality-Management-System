package com.beehyv.fortification.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LotPropertyTest {

    @Test
    void testAllFields() {
        // Arrange
        LotProperty lotProperty = new LotProperty();
        Long id = 1L;
        String name = "Test Property";
        String value = "Test Value";
        Lot lot = new Lot(1L);

        // Act
        lotProperty.setId(id);
        lotProperty.setName(name);
        lotProperty.setValue(value);
        lotProperty.setLot(lot);

        // Assert
        assertEquals(id, lotProperty.getId());
        assertEquals(name, lotProperty.getName());
        assertEquals(value, lotProperty.getValue());
        assertEquals(lot, lotProperty.getLot());
    }
}