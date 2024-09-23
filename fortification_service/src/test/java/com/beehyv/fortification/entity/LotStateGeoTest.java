package com.beehyv.fortification.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LotStateGeoTest {

    @Test
    void testSelectedMethods() {
        // Arrange
        LotStateGeo lotStateGeo = new LotStateGeo();
        Double quantity = 10d;

        // Act and Assert
        lotStateGeo.addInTransitQuantity(quantity);
        assertEquals(quantity, lotStateGeo.getInTransitQuantity());

        lotStateGeo.addReceivedQuantity(quantity);
        assertEquals(quantity, lotStateGeo.getReceivedQuantity());
        assertEquals(0d, lotStateGeo.getInTransitQuantity());

        lotStateGeo.addApprovedQuantity(quantity);
        assertEquals(quantity, lotStateGeo.getApprovedQuantity());

        lotStateGeo.addRejectedQuantity(quantity);
        assertEquals(quantity, lotStateGeo.getRejectedQuantity());

        lotStateGeo.addSampleInTransitQuantity(quantity);
        assertEquals(quantity, lotStateGeo.getSampleInTransitQuantity());

        lotStateGeo.addTestInProgressQuantity(quantity);
        assertEquals(quantity, lotStateGeo.getTestInProgressQuantity());
        assertEquals(0d, lotStateGeo.getSampleInTransitQuantity());

        lotStateGeo.addTestedQuantity(quantity);
        assertEquals(quantity, lotStateGeo.getTestedQuantity());

        lotStateGeo.addSampleTestedQuantity(quantity);
        assertEquals(quantity * 2, lotStateGeo.getTestedQuantity());
        assertEquals(0d, lotStateGeo.getTestInProgressQuantity());

        lotStateGeo.addUsedQuantity(quantity);
        assertEquals(quantity, lotStateGeo.getUsedQuantity());

        lotStateGeo.addSampleRejectedQuantity(quantity);
        assertEquals(quantity, lotStateGeo.getSampleRejectedQuantity());
    }
}