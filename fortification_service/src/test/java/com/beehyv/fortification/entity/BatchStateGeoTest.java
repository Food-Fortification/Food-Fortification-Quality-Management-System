package com.beehyv.fortification.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BatchStateGeoTest {

    @Test
    void testAllMethods() {
        // Arrange
        BatchStateGeo batchStateGeo = new BatchStateGeo();
        Double quantity = 10d;

        // Act and Assert
        batchStateGeo.addTotalQuantity(quantity);
        assertEquals(quantity, batchStateGeo.getTotalQuantity());

        batchStateGeo.addInProductionQuantity(quantity);
        assertEquals(quantity, batchStateGeo.getInProductionQuantity());
        assertEquals(quantity * 2, batchStateGeo.getTotalQuantity());

        batchStateGeo.addProducedQuantity(quantity);
        assertEquals(quantity, batchStateGeo.getProducedQuantity());

        batchStateGeo.addInTransitQuantity(quantity);
        assertEquals(quantity, batchStateGeo.getInTransitQuantity());

        batchStateGeo.addAvailableTested(quantity);
        assertEquals(quantity, batchStateGeo.getAvailableTested());

        batchStateGeo.addAvailableNotTested(quantity);
        assertEquals(quantity, batchStateGeo.getAvailableNotTested());

        batchStateGeo.addDispatchedTested(quantity);
        assertEquals(quantity, batchStateGeo.getDispatchedTested());
        assertEquals(0d, batchStateGeo.getAvailableTested());

        batchStateGeo.addDispatchedNotTested(quantity);
        assertEquals(quantity, batchStateGeo.getDispatchedNotTested());
        assertEquals(0d, batchStateGeo.getAvailableNotTested());

        batchStateGeo.addReceivedQuantity(quantity);
        assertEquals(quantity, batchStateGeo.getReceivedQuantity());
        assertEquals(0d, batchStateGeo.getInTransitQuantity());

        batchStateGeo.addApprovedQuantity(quantity);
        assertEquals(quantity, batchStateGeo.getApprovedQuantity());

        batchStateGeo.addRejectedQuantity(quantity);
        assertEquals(quantity, batchStateGeo.getRejectedQuantity());

        batchStateGeo.addBatchSampleInTransitQuantity(quantity);
        assertEquals(quantity, batchStateGeo.getBatchSampleInTransitQuantity());

        batchStateGeo.addBatchSampleTestInProgressQuantity(quantity);
        assertEquals(quantity, batchStateGeo.getBatchSampleTestInProgressQuantity());
        assertEquals(0d, batchStateGeo.getBatchSampleInTransitQuantity());

        batchStateGeo.addBatchTestedQuantity(quantity);
        assertEquals(quantity, batchStateGeo.getBatchTestedQuantity());

        batchStateGeo.addBatchTestPassedQuantity(quantity);
        assertEquals(quantity * 2, batchStateGeo.getBatchTestedQuantity());
        assertEquals(quantity, batchStateGeo.getBatchTestApprovedQuantity());

        batchStateGeo.addOnlyBatchTestPassedQuantity(quantity);
        assertEquals(quantity * 2, batchStateGeo.getBatchTestApprovedQuantity());

        batchStateGeo.addBatchTestRejectedQuantity(quantity);
        assertEquals(quantity * 3, batchStateGeo.getBatchTestedQuantity());
        assertEquals(quantity, batchStateGeo.getBatchTestRejectedQuantity());

        batchStateGeo.addOnlyBatchTestRejectedQuantity(quantity);
        assertEquals(quantity * 2, batchStateGeo.getBatchTestRejectedQuantity());

        batchStateGeo.addBatchSampleTestedQuantity(quantity);
        assertEquals(quantity * 4, batchStateGeo.getBatchTestedQuantity());


        batchStateGeo.addBatchSampleTestPassedQuantity(quantity);
        assertEquals(quantity * 5, batchStateGeo.getBatchTestedQuantity());
        assertEquals(quantity * 3, batchStateGeo.getBatchTestApprovedQuantity());

        batchStateGeo.addBatchSampleTestRejectedQuantity(quantity);
        assertEquals(quantity * 6, batchStateGeo.getBatchTestedQuantity());
        assertEquals(quantity * 3, batchStateGeo.getBatchTestRejectedQuantity());

        batchStateGeo.addLotSampleInTransitQuantity(quantity);
        assertEquals(quantity, batchStateGeo.getLotSampleInTransitQuantity());

        batchStateGeo.addLotTestInProgressQuantity(quantity);
        assertEquals(quantity, batchStateGeo.getLotSampleTestInProgressQuantity());
        assertEquals(0d, batchStateGeo.getLotSampleInTransitQuantity());

        batchStateGeo.addLotSampleTestedQuantity(quantity);
        assertEquals(quantity, batchStateGeo.getLotTestedQuantity());
        assertEquals(0d, batchStateGeo.getLotSampleTestInProgressQuantity());

        batchStateGeo.addLotSampleTestPassedQuantity(quantity);
        assertEquals(quantity * 2, batchStateGeo.getLotTestedQuantity());
        assertEquals(quantity, batchStateGeo.getLotTestApprovedQuantity());

        batchStateGeo.addLotSampleTestRejectedQuantity(quantity);
        assertEquals(quantity * 3, batchStateGeo.getLotTestedQuantity());
        assertEquals(quantity, batchStateGeo.getLotTestRejectedQuantity());

        batchStateGeo.addLotTestedQuantity(quantity);
        assertEquals(quantity * 4, batchStateGeo.getLotTestedQuantity());

        batchStateGeo.addLotTestPassedQuantity(quantity);
        assertEquals(quantity * 5, batchStateGeo.getLotTestedQuantity());
        assertEquals(quantity * 2, batchStateGeo.getLotTestApprovedQuantity());

        batchStateGeo.addLotTestRejectedQuantity(quantity);
        assertEquals(quantity * 6, batchStateGeo.getLotTestedQuantity());
        assertEquals(quantity * 2, batchStateGeo.getLotTestRejectedQuantity());

        batchStateGeo.addRejectedInTransitQuantity(quantity);
        assertEquals(quantity, batchStateGeo.getRejectedInTransitQuantity());

        batchStateGeo.addReceivedRejectedQuantity(quantity);
        assertEquals(quantity, batchStateGeo.getReceivedRejectedQuantity());
        assertEquals(0d, batchStateGeo.getRejectedInTransitQuantity());

        batchStateGeo.addLotSampleRejectedQuantity(quantity);
        assertEquals(quantity, batchStateGeo.getLotSampleRejectedQuantity());

        batchStateGeo.addLotRejected(quantity);
        assertEquals(quantity, batchStateGeo.getLotRejected());
    }
}
