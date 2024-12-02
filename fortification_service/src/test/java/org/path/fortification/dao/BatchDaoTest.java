package org.path.fortification.dao;


import org.path.fortification.dto.requestDto.SearchListRequest;
import org.path.fortification.entity.Batch;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BatchDaoTest {

    @Mock
    private TypedQuery<Batch> typedQueryBatch;
    @Mock
    private TypedQuery<Long> typedQueryLong;
    @Mock
    private EntityManager entityManager;


    @InjectMocks
    private BatchDao batchDao;

    @BeforeEach
    void setUp() {
        when(entityManager.createQuery(anyString(), eq(Batch.class)))
                .thenReturn(typedQueryBatch);
        when(entityManager.createQuery(anyString(), eq(Long.class)))
                .thenReturn(typedQueryLong);
    }

    @Test
    void testFindAllBatches() {
        // Arrange
        Long categoryId = 1L;
        Long manufacturerId = 2L;
        Integer pageNumber = 1;
        Integer pageSize = 10;
        SearchListRequest searchRequest = new SearchListRequest();
        Boolean remQuantity = true;
        Boolean labTested = true;
        List<String> states = new ArrayList<>();
        Date from = new Date();
        Date to = new Date();

        Batch batch1 = new Batch();
        batch1.setId(1L);
        Batch batch2 = new Batch();
        batch2.setId(2L);
        List<Batch> expectedBatches = Arrays.asList(batch1, batch2);

        when(typedQueryBatch.setParameter(anyString(), any()))
                .thenReturn(typedQueryBatch);
        when(typedQueryBatch.setFirstResult(anyInt()))
                .thenReturn(typedQueryBatch);
        when(typedQueryBatch.setMaxResults(anyInt()))
                .thenReturn(typedQueryBatch);
        when(typedQueryBatch.getResultList())
                .thenReturn(expectedBatches);

        // Act
        List<Batch> actualBatches = batchDao.findAllBatches(states, from, to, categoryId, manufacturerId, pageNumber, pageSize, searchRequest, remQuantity, labTested);

        // Assert
        assertEquals(expectedBatches, actualBatches);
    }

    @Test
    void testGetCount() {
        // Arrange
        Long categoryId = 1L;
        Long manufacturerId = 2L;
        Long stateId = 3L;
        SearchListRequest searchRequest = new SearchListRequest();
        List<String> states = new ArrayList<>();
        Date from = new Date();
        Date to = new Date();
        Boolean remQuantity = true;
        when(entityManager.createQuery(anyString(), eq(Long.class)))
                .thenReturn(typedQueryLong);
        when(typedQueryLong.setParameter(anyString(), any()))
                .thenReturn(typedQueryLong);
        when(typedQueryLong.getSingleResult())
                .thenReturn(10L);

        // Act
        Long actualCount = batchDao.getCount(states,categoryId, manufacturerId, stateId, searchRequest, remQuantity, from, to);

        // Assert
        assertEquals(10L, actualCount);
    }


    @Test
    void testGatCountForInspection() {
        // Arrange
        Long categoryId = 1L;
        SearchListRequest searchRequest = new SearchListRequest();
        List<Long> testManufacturerIds = Arrays.asList(3L, 4L);

        when(entityManager.createQuery(anyString(), eq(Long.class)))
                .thenReturn(typedQueryLong);
        when(typedQueryLong.setParameter(anyString(), any()))
                .thenReturn(typedQueryLong);
        when(typedQueryLong.getSingleResult())
                .thenReturn(10L);

        // Act
        Long actualCount = batchDao.gatCountForInspection(categoryId, searchRequest, testManufacturerIds);

        // Assert
        assertEquals(10L, actualCount);
    }

    @Test
    void testFindAllBatchesForInspection() {
        // Arrange
        Long categoryId = 1L;
        SearchListRequest searchListRequest = new SearchListRequest();
        List<Long> testManufacturerIds = Arrays.asList(3L, 4L);
        List<Long> batchIds = Arrays.asList(5L, 6L);
        Integer pageNumber = 1;
        Integer pageSize = 10;

        Batch batch1 = new Batch();
        batch1.setId(1L);
        Batch batch2 = new Batch();
        batch2.setId(2L);
        List<Batch> expectedBatches = Arrays.asList(batch1, batch2);

        when(typedQueryBatch.setParameter(anyString(), any()))
                .thenReturn(typedQueryBatch);
        when(typedQueryBatch.setFirstResult(anyInt()))
                .thenReturn(typedQueryBatch);
        when(typedQueryBatch.setMaxResults(anyInt()))
                .thenReturn(typedQueryBatch);
        when(typedQueryBatch.getResultList())
                .thenReturn(expectedBatches);

        // Act
        List<Batch> actualBatches = batchDao.findAllBatchesForInspection(categoryId, searchListRequest, testManufacturerIds, batchIds, pageNumber, pageSize);

        // Assert
        assertEquals(expectedBatches, actualBatches);
    }

    @Test
    void testDelete() {
        // Arrange
        Long id = 1L;
        Batch batch = new Batch();
        batch.setId(id);

        Session session = mock(Session.class);
        when(entityManager.unwrap(Session.class)).thenReturn(session);
        when(session.get(Batch.class, id)).thenReturn(batch);

        // Act
        batchDao.delete(id);

        // Assert
        Mockito.verify(session).delete(batch);
    }

    @Test
    void testFindByIdAndManufacturerId() {
        // Arrange
        Long id = 1L;
        Long manufacturerId = 2L;

        Batch expectedBatch = new Batch();
        expectedBatch.setId(id);
        expectedBatch.setManufacturerId(manufacturerId);

        when(typedQueryBatch.setParameter(anyString(), any()))
                .thenReturn(typedQueryBatch);
        when(typedQueryBatch.getSingleResult())
                .thenReturn(expectedBatch);

        // Act
        Batch actualBatch = batchDao.findByIdAndManufacturerId(id, manufacturerId);

        // Assert
        assertEquals(expectedBatch, actualBatch);
    }

    @Test
    void testFindByUUID() {
        // Arrange
        String uuid = "test-uuid";

        Batch expectedBatch = new Batch();
        expectedBatch.setUuid(uuid);

        when(typedQueryBatch.setParameter(anyString(), any()))
                .thenReturn(typedQueryBatch);
        when(typedQueryBatch.getSingleResult())
                .thenReturn(expectedBatch);

        // Act
        Batch actualBatch = batchDao.findByUUID(uuid);

        // Assert
        assertEquals(expectedBatch, actualBatch);
    }

}

