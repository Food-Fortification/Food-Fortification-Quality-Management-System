package org.path.fortification.dao;

import org.path.fortification.entity.BatchDoc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BatchDocDaoTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<BatchDoc> typedQuery;

    @InjectMocks
    private BatchDocDao batchDocDao;

    @BeforeEach
    void setUp() {
        when(entityManager.createQuery(anyString(), eq(BatchDoc.class)))
                .thenReturn(typedQuery);
        when(typedQuery.setParameter(anyString(), any()))
                .thenReturn(typedQuery);
    }

    @Test
    void testFindAllByBatchId() {
        // Arrange
        Long batchId = 1L;
        Integer pageNumber = 1;
        Integer pageSize = 10;

        BatchDoc batchDoc1 = new BatchDoc();
        batchDoc1.setId(1L);
        BatchDoc batchDoc2 = new BatchDoc();
        batchDoc2.setId(2L);
        List<BatchDoc> expectedBatchDocs = Arrays.asList(batchDoc1, batchDoc2);

        when(typedQuery.setFirstResult(anyInt()))
                .thenReturn(typedQuery);
        when(typedQuery.setMaxResults(anyInt()))
                .thenReturn(typedQuery);
        when(typedQuery.getResultList())
                .thenReturn(expectedBatchDocs);

        // Act
        List<BatchDoc> actualBatchDocs = batchDocDao.findAllByBatchId(batchId, pageNumber, pageSize);

        // Assert
        assertEquals(expectedBatchDocs, actualBatchDocs);
    }

    @Test
    void testCreate() {
        // Arrange
        BatchDoc batchDoc = new BatchDoc();
        batchDoc.setId(1L);

        // Act
        batchDocDao.create(batchDoc);

        // Assert
        verify(entityManager).persist(batchDoc);
    }

    @Test
    void testFindById() {
        // Arrange
        Long id = 1L;
        BatchDoc expectedBatchDoc = new BatchDoc();
        expectedBatchDoc.setId(id);

        when(entityManager.find(BatchDoc.class, id)).thenReturn(expectedBatchDoc);

        // Act
        BatchDoc actualBatchDoc = batchDocDao.findById(id);

        // Assert
        assertEquals(expectedBatchDoc, actualBatchDoc);
    }

    @Test
    void testUpdate() {
        // Arrange
        BatchDoc batchDoc = new BatchDoc();
        batchDoc.setId(1L);

        // Act
        batchDocDao.update(batchDoc);

        // Assert
        verify(entityManager).merge(batchDoc);
    }

    @Test
    void testDelete() {
        // Arrange
        Long id = 1L;
        BatchDoc batchDoc = new BatchDoc();
        batchDoc.setId(id);

        when(entityManager.find(BatchDoc.class, id)).thenReturn(batchDoc);

        // Act
        batchDocDao.delete(id);

        // Assert
        verify(entityManager).remove(batchDoc);
    }
}
