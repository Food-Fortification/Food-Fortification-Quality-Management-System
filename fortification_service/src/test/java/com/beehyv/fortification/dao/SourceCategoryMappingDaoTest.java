package com.beehyv.fortification.dao;

import com.beehyv.fortification.entity.SourceCategoryMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class SourceCategoryMappingDaoTest {

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<SourceCategoryMapping> typedQuery;

    @InjectMocks
    private SourceCategoryMappingDao sourceCategoryMappingDao;

    @BeforeEach
    void setUp() {
        when(em.createQuery(any(String.class), any(Class.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(any(String.class), any())).thenReturn(typedQuery);
    }

    @Test
    void testFindByIds() {
        // Arrange
        List<SourceCategoryMapping> expectedMappings = Collections.singletonList(new SourceCategoryMapping());
        when(typedQuery.getResultList()).thenReturn(expectedMappings);

        // Act
        List<SourceCategoryMapping> actualMappings = sourceCategoryMappingDao.findByIds(1L, 1L, 1L);

        // Assert
        assertEquals(expectedMappings, actualMappings);
    }

    @Test
    void testFindBySourceId() {
        // Arrange
        List<SourceCategoryMapping> expectedMappings = Collections.singletonList(new SourceCategoryMapping());
        when(typedQuery.getResultList()).thenReturn(expectedMappings);

        // Act
        List<SourceCategoryMapping> actualMappings = sourceCategoryMappingDao.findBySourceId(1L);

        // Assert
        assertEquals(expectedMappings, actualMappings);
    }

}
