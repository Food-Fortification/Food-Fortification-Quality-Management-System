package com.beehyv.fortification.dao;

import com.beehyv.fortification.entity.MixMapping;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MixMappingDaoTest {

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<MixMapping> typedQuery;
    @Mock
    private TypedQuery<Object[]> typedQueryO;
    @Mock
    private TypedQuery<Long> typedQueryL;

    @InjectMocks
    private MixMappingDao mixMappingDao;

    @BeforeEach
    void setUp() {
        when(em.createQuery(any(String.class), eq(MixMapping.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(any(String.class), any())).thenReturn(typedQuery);
        when(em.createQuery(any(String.class), eq(Long.class))).thenReturn(typedQueryL);
        when(typedQueryL.setParameter(any(String.class), anyLong())).thenReturn(typedQueryL);
        when(em.createQuery(any(String.class), eq(Object[].class))).thenReturn(typedQueryO);
        when(typedQueryO.setParameter(any(String.class), anyLong())).thenReturn(typedQueryO);
        when(em.createQuery(any(String.class))).thenReturn(typedQueryL);


    }

    @Test
    void testGetCountByTargetBatchId() {

        // Arrange
        Long expectedCount = 1L;
        when(typedQueryL.getSingleResult()).thenReturn(expectedCount);

        // Act
        Long actualCount = mixMappingDao.getCountByTargetBatchId(1L);

        // Assert
        assertEquals(expectedCount, actualCount);
    }

    @Test
    void testGetCountBySourceLotId() {
        // Arrange
        Long expectedCount = 1L;
        when(typedQueryL.getSingleResult()).thenReturn(expectedCount);

        // Act
        Long actualCount = mixMappingDao.getCountBySourceLotId(1L);

        // Assert
        assertEquals(expectedCount, actualCount);
    }

    @Test
    void testFindAllByTargetBatchId() {
        // Arrange
        List<MixMapping> expectedMixMappings = Collections.singletonList(new MixMapping());
        when(typedQuery.getResultList()).thenReturn(expectedMixMappings);

        // Act
        List<MixMapping> actualMixMappings = mixMappingDao.findAllByTargetBatchId(1L, 1, 10);

        // Assert
        assertEquals(expectedMixMappings, actualMixMappings);
    }

    @Test
    void testFindAllByTargetBatchIds() {
        // Arrange
        List<MixMapping> expectedMixMappings = Collections.singletonList(new MixMapping());
        when(typedQuery.getResultList()).thenReturn(expectedMixMappings);

        // Act
        List<MixMapping> actualMixMappings = mixMappingDao.findAllByTargetBatchIds(Collections.singletonList(1L));

        // Assert
        assertEquals(expectedMixMappings, actualMixMappings);
    }

    @Test
    void testFindAllBySourceLotId() {
        // Arrange
        List<MixMapping> expectedMixMappings = Collections.singletonList(new MixMapping());
        when(typedQuery.getResultList()).thenReturn(expectedMixMappings);

        // Act
        List<MixMapping> actualMixMappings = mixMappingDao.findAllBySourceLotId(1L, 1, 10);

        // Assert
        assertEquals(expectedMixMappings, actualMixMappings);
    }

    @Test
    void testFindAllByIds() {
        // Arrange
        List<MixMapping> expectedMixMappings = Collections.singletonList(new MixMapping());
        when(typedQuery.getResultList()).thenReturn(expectedMixMappings);

        // Act
        List<MixMapping> actualMixMappings = mixMappingDao.findAllByIds(Collections.singletonList(1L));

        // Assert
        assertEquals(expectedMixMappings, actualMixMappings);
    }

}