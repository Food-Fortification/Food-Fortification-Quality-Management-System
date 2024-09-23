package com.beehyv.fortification.entity;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BatchDocTest {

    @Test
    void testAllFields() {
        // Arrange
        BatchDoc batchDoc = new BatchDoc();
        Long id = 1L;
        Batch batch = new Batch(1L);
        CategoryDoc categoryDoc = new CategoryDoc(1L);
        String name = "Test Document";
        String path = "/path/to/document";
        Date expiry = new Date();

        // Act
        batchDoc.setId(id);
        batchDoc.setBatch(batch);
        batchDoc.setCategoryDoc(categoryDoc);
        batchDoc.setName(name);
        batchDoc.setPath(path);
        batchDoc.setExpiry(expiry);

        // Assert
        assertEquals(id, batchDoc.getId());
        assertEquals(batch, batchDoc.getBatch());
        assertEquals(categoryDoc, batchDoc.getCategoryDoc());
        assertEquals(name, batchDoc.getName());
        assertEquals(path, batchDoc.getPath());
        assertEquals(expiry, batchDoc.getExpiry());
    }
}