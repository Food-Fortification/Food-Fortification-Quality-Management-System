package org.path.fortification.manager;

import org.path.fortification.dao.BatchDocDao;
import org.path.fortification.entity.BatchDoc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BatchDocManagerTest {

    @InjectMocks
    private BatchDocManager batchDocManager;

    @Mock
    private BatchDocDao batchDocDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllByBatchId() {
        BatchDoc batchDoc1 = new BatchDoc();
        BatchDoc batchDoc2 = new BatchDoc();
        List<BatchDoc> batchDocs = Arrays.asList(batchDoc1, batchDoc2);

        when(batchDocDao.findAllByBatchId(1L, 0, 10)).thenReturn(batchDocs);

        List<BatchDoc> result = batchDocManager.findAllByBatchId(1L, 0, 10);

        assertEquals(batchDocs, result);
    }
}