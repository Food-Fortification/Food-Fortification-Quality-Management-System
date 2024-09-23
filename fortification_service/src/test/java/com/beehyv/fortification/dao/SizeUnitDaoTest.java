package com.beehyv.fortification.dao;

import com.beehyv.fortification.entity.SizeUnit;
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
class SizeUnitDaoTest {

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<SizeUnit> typedQuery;
    @Mock
    private TypedQuery<Long> typedQueryL;

    @InjectMocks
    private SizeUnitDao sizeUnitDao;

    @BeforeEach
    void setUp() {
        when(em.createQuery(any(String.class), eq(SizeUnit.class))).thenReturn(typedQuery);
        when(em.createQuery(any(String.class))).thenReturn(typedQueryL);
        when(typedQuery.setParameter(any(String.class), any())).thenReturn(typedQuery);
        when(typedQueryL.setParameter(any(String.class), any())).thenReturn(typedQueryL);
    }

    @Test
    void testGetCount() {
        // Arrange
        Long expectedCount = 1L;
        when(typedQueryL.getSingleResult()).thenReturn(expectedCount);

        // Act
        Long actualCount = sizeUnitDao.getCount(1L);

        // Assert
        assertEquals(expectedCount, actualCount);
    }

    @Test
    void testFindAllByBatchId() {
        // Arrange
        List<SizeUnit> expectedSizeUnits = Collections.singletonList(new SizeUnit());
        when(typedQuery.getResultList()).thenReturn(expectedSizeUnits);

        // Act
        List<SizeUnit> actualSizeUnits = sizeUnitDao.findAllByBatchId(1L, 1, 10);

        // Assert
        assertEquals(expectedSizeUnits, actualSizeUnits);
    }

}