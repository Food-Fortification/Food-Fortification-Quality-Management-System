package org.path.fortification.dao;

import org.path.fortification.entity.LabConfigCategory;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LabConfigCategoryDaoTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<LabConfigCategory> typedQuery;

    @InjectMocks
    private LabConfigCategoryDao labConfigCategoryDao;

    @BeforeEach
    void setUp() {
        when(entityManager.createQuery(anyString(), eq(LabConfigCategory.class)))
                .thenReturn(typedQuery);
    }

    @Test
    void testFindByCategoryId() {
        // Arrange
        Long categoryId = 1L;
        LabConfigCategory expectedLabConfigCategory = new LabConfigCategory();

        when(typedQuery.setParameter(anyString(), any()))
                .thenReturn(typedQuery);
        when(typedQuery.getSingleResult())
                .thenReturn(expectedLabConfigCategory);

        // Act
        LabConfigCategory actualLabConfigCategory = labConfigCategoryDao.findByCategoryId(categoryId);

        // Assert
        assertEquals(expectedLabConfigCategory, actualLabConfigCategory);
    }

    @Test
    void testFindByCategoryIds() {
        // Arrange
        Long sourceCategoryId = 1L;
        Long targetCategoryId = 2L;
        List<LabConfigCategory> expectedLabConfigCategories = Collections.emptyList();

        when(typedQuery.setParameter(anyString(), any()))
                .thenReturn(typedQuery);
        when(typedQuery.getResultList())
                .thenReturn(expectedLabConfigCategories);

        // Act
        List<LabConfigCategory> actualLabConfigCategories = labConfigCategoryDao.findByCategoryIds(sourceCategoryId, targetCategoryId);

        // Assert
        assertEquals(expectedLabConfigCategories, actualLabConfigCategories);
    }
}