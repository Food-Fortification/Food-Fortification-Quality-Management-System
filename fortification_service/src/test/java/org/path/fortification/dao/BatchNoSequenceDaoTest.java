package org.path.fortification.dao;

import org.path.fortification.entity.BatchNoId;
import org.path.fortification.entity.BatchNoSequence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BatchNoSequenceDaoTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<BatchNoSequence> typedQuery;

    @InjectMocks
    private BatchNoSequenceDao batchNoSequenceDao;

    @BeforeEach
    void setUp() {
        when(entityManager.createQuery(anyString(), eq(BatchNoSequence.class)))
                .thenReturn(typedQuery);
    }

    @Test
    void testFindById() {
        // Arrange
        BatchNoId batchNoId = new BatchNoId();
        BatchNoSequence expectedBatchNoSequence = new BatchNoSequence(batchNoId, 1);

        when(typedQuery.setParameter(anyString(), any()))
                .thenReturn(typedQuery);
        when(typedQuery.getSingleResult())
                .thenReturn(expectedBatchNoSequence);

        // Act
        BatchNoSequence actualBatchNoSequence = batchNoSequenceDao.findById(batchNoId);

        // Assert
        assertEquals(expectedBatchNoSequence, actualBatchNoSequence);
    }

    @Test
    void testFindById_NoResultException() {
        // Arrange
        BatchNoId batchNoId = new BatchNoId();
        BatchNoSequence expectedBatchNoSequence = new BatchNoSequence(batchNoId, 1);

        when(typedQuery.setParameter(anyString(), any()))
                .thenReturn(typedQuery);
        when(typedQuery.getSingleResult())
                .thenThrow(NoResultException.class);
        doNothing().when(entityManager).persist(any(BatchNoSequence.class));

        // Act
        BatchNoSequence actualBatchNoSequence = batchNoSequenceDao.findById(batchNoId);

        // Assert
        assertNotNull(actualBatchNoSequence);
    }
}