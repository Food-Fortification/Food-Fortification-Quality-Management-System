package org.path.fortification.entity;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BatchEventEntityTest {

    @Test
    void testAllFields() {
        // Arrange
        BatchEventEntity batchEventEntity = new BatchEventEntity();
        Long id = 1L;
        String entityId = "Test Entity";
        String manufacturerName = "Test Manufacturer";
        String manufacturerAddress = "Test Address";
        String type = "Test Type";
        String state = "Test State";
        String comments = "Test Comments";
        Timestamp dateOfAction = new Timestamp(System.currentTimeMillis());
        String createdBy = "Test Creator";
        Timestamp createdDate = new Timestamp(System.currentTimeMillis());

        // Act
        batchEventEntity.setId(id);
        batchEventEntity.setEntityId(entityId);
        batchEventEntity.setManufacturerName(manufacturerName);
        batchEventEntity.setManufacturerAddress(manufacturerAddress);
        batchEventEntity.setType(type);
        batchEventEntity.setState(state);
        batchEventEntity.setComments(comments);
        batchEventEntity.setDateOfAction(dateOfAction);
        batchEventEntity.setCreatedBy(createdBy);
        batchEventEntity.setCreatedDate(createdDate);

        // Assert
        assertEquals(id, batchEventEntity.getId());
        assertEquals(entityId, batchEventEntity.getEntityId());
        assertEquals(manufacturerName, batchEventEntity.getManufacturerName());
        assertEquals(manufacturerAddress, batchEventEntity.getManufacturerAddress());
        assertEquals(type, batchEventEntity.getType());
        assertEquals(state, batchEventEntity.getState());
        assertEquals(comments, batchEventEntity.getComments());
        assertEquals(dateOfAction, batchEventEntity.getDateOfAction());
        assertEquals(createdBy, batchEventEntity.getCreatedBy());
        assertEquals(createdDate, batchEventEntity.getCreatedDate());
    }
}
