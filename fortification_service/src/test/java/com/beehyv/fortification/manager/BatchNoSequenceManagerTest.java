package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.BatchNoSequenceDao;
import com.beehyv.fortification.entity.BatchNoId;
import com.beehyv.fortification.entity.BatchNoSequence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BatchNoSequenceManagerTest {

    @InjectMocks
    private BatchNoSequenceManager batchNoSequenceManager;

    @Mock
    private BatchNoSequenceDao batchNoSequenceDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindById() {
        BatchNoId batchNoId = new BatchNoId();
        BatchNoSequence batchNoSequence = new BatchNoSequence();
        when(batchNoSequenceDao.findById(batchNoId)).thenReturn(batchNoSequence);

        BatchNoSequence result = batchNoSequenceManager.findById(batchNoId);

        assertEquals(batchNoSequence, result);
    }

    @Test
    public void testUpdate() {
        BatchNoSequence batchNoSequence = new BatchNoSequence();
        batchNoSequence.setSequence(1);
        BatchNoSequence updatedBatchNoSequence = new BatchNoSequence();
        updatedBatchNoSequence.setSequence(2);
        when(batchNoSequenceDao.update(batchNoSequence)).thenReturn(updatedBatchNoSequence);

        BatchNoSequence result = batchNoSequenceManager.update(batchNoSequence);

        assertEquals(updatedBatchNoSequence, result);
    }
}