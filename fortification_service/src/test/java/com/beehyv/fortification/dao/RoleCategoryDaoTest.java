package com.beehyv.fortification.dao;

import com.beehyv.fortification.entity.RoleCategory;
import com.beehyv.fortification.entity.RoleCategoryType;
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
class RoleCategoryDaoTest {

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<RoleCategory> typedQuery;

    @InjectMocks
    private RoleCategoryDao roleCategoryDao;

    @BeforeEach
    void setUp() {
        when(em.createQuery(any(String.class), any(Class.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(any(String.class), any())).thenReturn(typedQuery);
    }

    @Test
    void testFindAllByIdAndRoleCategoryType() {
        // Arrange
        List<RoleCategory> expectedRoleCategories = Collections.singletonList(new RoleCategory());
        when(typedQuery.getResultList()).thenReturn(expectedRoleCategories);

        // Act
        List<RoleCategory> actualRoleCategories = roleCategoryDao.findAllByIdAndRoleCategoryType(Collections.singleton(1L), RoleCategoryType.LAB);

        // Assert
        assertEquals(expectedRoleCategories, actualRoleCategories);
    }

    @Test
    void testFindByCategoryAndState() {
        // Arrange
        List<RoleCategory> expectedRoleCategories = Collections.singletonList(new RoleCategory());
        when(typedQuery.getResultList()).thenReturn(expectedRoleCategories);

        // Act
        List<RoleCategory> actualRoleCategories = roleCategoryDao.findByCategoryAndState(1L, 1L);

        // Assert
        assertEquals(expectedRoleCategories, actualRoleCategories);
    }

    @Test
    void testFindByCategoryName() {
        // Arrange
        List<RoleCategory> expectedRoleCategories = Collections.singletonList(new RoleCategory());
        when(typedQuery.getResultList()).thenReturn(expectedRoleCategories);

        // Act
        List<RoleCategory> actualRoleCategories = roleCategoryDao.findByCategoryName("test", RoleCategoryType.LAB);

        // Assert
        assertEquals(expectedRoleCategories, actualRoleCategories);
    }


    @Test
    void testFindByRoleAndCategoryNames() {
        // Arrange
        RoleCategory expectedRoleCategory = new RoleCategory();
        when(typedQuery.getSingleResult()).thenReturn(expectedRoleCategory);

        // Act
        RoleCategory actualRoleCategory = roleCategoryDao.findByRoleAndCategoryNames("test", "test", RoleCategoryType.LAB);

        // Assert
        assertEquals(expectedRoleCategory, actualRoleCategory);
    }

    @Test
    void testFindListByRoleAndCategoryNames() {
        // Arrange
        List<RoleCategory> expectedRoleCategories = Collections.singletonList(new RoleCategory());
        when(typedQuery.getResultList()).thenReturn(expectedRoleCategories);

        // Act
        List<RoleCategory> actualRoleCategories = roleCategoryDao.findListByRoleAndCategoryNames("test", "test", RoleCategoryType.LAB);

        // Assert
        assertEquals(expectedRoleCategories, actualRoleCategories);
    }

    @Test
    void testFindByCategoryIdAndType() {
        // Arrange
        List<RoleCategory> expectedRoleCategories = Collections.singletonList(new RoleCategory());
        when(typedQuery.getResultList()).thenReturn(expectedRoleCategories);

        // Act
        List<RoleCategory> actualRoleCategories = roleCategoryDao.findByCategoryIdAndType(1L, RoleCategoryType.LAB);

        // Assert
        assertEquals(expectedRoleCategories, actualRoleCategories);
    }

}