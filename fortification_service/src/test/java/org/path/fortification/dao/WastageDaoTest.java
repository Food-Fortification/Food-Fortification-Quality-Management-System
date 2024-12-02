package org.path.fortification.dao;

import org.path.fortification.entity.Wastage;
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
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class WastageDaoTest {

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<Long> typedQuery;
    @Mock
    private TypedQuery<Wastage> typedQueryW;

    @InjectMocks
    private WastageDao wastageDao;

    @BeforeEach
    void setUp() {
        when(em.createQuery(any(String.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(any(String.class), any())).thenReturn(typedQuery);
        when(em.createQuery(any(String.class), eq(Wastage.class))).thenReturn(typedQueryW);
        when(typedQueryW.setParameter(any(String.class), any())).thenReturn(typedQueryW);

    }

    @Test
    void testGetCountByBatchId() {
        // Arrange
        Long expectedCount = 1L;
        when(typedQuery.getSingleResult()).thenReturn(expectedCount);

        // Act
        Long actualCount = wastageDao.getCountByBatchId(1L);

        // Assert
        assertEquals(expectedCount, actualCount);
    }

    @Test
    void testGetCountByLotId() {
        // Arrange
        Long expectedCount = 1L;
        when(typedQuery.getSingleResult()).thenReturn(expectedCount);

        // Act
        Long actualCount = wastageDao.getCountByLotId(1L);

        // Assert
        assertEquals(expectedCount, actualCount);
    }

    @Test
    void testFindAllByBatchId() {
        // Arrange
        List<Wastage> expectedWastages = Collections.singletonList(new Wastage());
        when(typedQueryW.getResultList()).thenReturn(expectedWastages);

        // Act
        List<Wastage> actualWastages = wastageDao.findAllByBatchId(1L, 1, 10);

        // Assert
        assertEquals(expectedWastages, actualWastages);
    }

    @Test
    void testFindAllByLotId() {
        // Arrange
        List<Wastage> expectedWastages = Collections.singletonList(new Wastage());
        when(typedQueryW.getResultList()).thenReturn(expectedWastages);

        // Act
        List<Wastage> actualWastages = wastageDao.findAllByLotId(1L, 1, 10);

        // Assert
        assertEquals(expectedWastages, actualWastages);
    }

}