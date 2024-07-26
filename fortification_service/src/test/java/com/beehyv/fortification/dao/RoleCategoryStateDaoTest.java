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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleCategoryStateDaoTest {

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<RoleCategory> typedQuery;

    @InjectMocks
    private RoleCategoryStateDao roleCategoryStateDao;

    @BeforeEach
    void setUp() {
        when(em.createQuery(any(String.class), eq(RoleCategory.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(any(String.class), any())).thenReturn(typedQuery);
    }

    @Test
    void testFindAllByIdAndRoleCategoryType() {
        // Arrange
        List<RoleCategory> expectedRoleCategories = Collections.singletonList(new RoleCategory());
        when(typedQuery.getResultList()).thenReturn(expectedRoleCategories);

        // Act
        List<RoleCategory> actualRoleCategories = roleCategoryStateDao.findAllByIdAndRoleCategoryType(Collections.singleton(1L), RoleCategoryType.LAB);

        // Assert
        assertEquals(expectedRoleCategories, actualRoleCategories);
    }

    @Test
    void testFindByStateAndCategory() {
        // Arrange
        List<RoleCategory> expectedRoleCategoryStates = Collections.singletonList(new RoleCategory());
        when(typedQuery.getResultList()).thenReturn(expectedRoleCategoryStates);

        // Act
        List<RoleCategory> actualRoleCategoryStates = roleCategoryStateDao.findByCategoryAndState(1L, 1L);

        // Assert
        assertEquals(expectedRoleCategoryStates, actualRoleCategoryStates);
    }

    @Test
    void testFindByStateName() {
        // Arrange
        List<RoleCategory> expectedRoleCategoryStates = Collections.singletonList(new RoleCategory());
        when(typedQuery.getResultList()).thenReturn(expectedRoleCategoryStates);

        // Act
        List<RoleCategory> actualRoleCategoryStates = roleCategoryStateDao.findByCategoryName("test", RoleCategoryType.LAB);

        // Assert
        assertEquals(expectedRoleCategoryStates, actualRoleCategoryStates);
    }

    @Test
    void testFindByRoleAndStateNames() {
        // Arrange
        RoleCategory expectedRoleCategoryState = new RoleCategory();
        when(typedQuery.getSingleResult()).thenReturn(expectedRoleCategoryState);

        // Act
        RoleCategory actualRoleCategoryState = roleCategoryStateDao.findByRoleAndCategoryNames("test", "test", RoleCategoryType.LAB);

        // Assert
        assertEquals(expectedRoleCategoryState, actualRoleCategoryState);
    }

    @Test
    void testFindListByRoleAndStateNames() {
        // Arrange
        List<RoleCategory> expectedRoleCategoryStates = Collections.singletonList(new RoleCategory());
        when(typedQuery.getResultList()).thenReturn(expectedRoleCategoryStates);

        // Act
        List<RoleCategory> actualRoleCategoryStates = roleCategoryStateDao.findListByRoleAndCategoryNames("test", "test", RoleCategoryType.LAB);

        // Assert
        assertEquals(expectedRoleCategoryStates, actualRoleCategoryStates);
    }

    @Test
    void testFindByStateIdAndType() {
        // Arrange
        List<RoleCategory> expectedRoleCategoryStates = Collections.singletonList(new RoleCategory());
        when(typedQuery.getResultList()).thenReturn(expectedRoleCategoryStates);

        // Act
        List<RoleCategory> actualRoleCategoryStates = roleCategoryStateDao.findByCategoryIdAndType(1L, RoleCategoryType.LAB);

        // Assert
        assertEquals(expectedRoleCategoryStates, actualRoleCategoryStates);
    }

}