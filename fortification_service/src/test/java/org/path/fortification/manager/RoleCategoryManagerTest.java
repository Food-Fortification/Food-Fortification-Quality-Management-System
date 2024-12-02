package org.path.fortification.manager;

import org.path.fortification.dao.RoleCategoryDao;
import org.path.fortification.entity.RoleCategory;
import org.path.fortification.entity.RoleCategoryType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RoleCategoryManagerTest {

    @Mock
    private RoleCategoryDao roleCategoryDao;
    private RoleCategoryManager roleCategoryManager;
    private Set<Long> roleCategoryIds;
    private RoleCategoryType roleCategoryType;
    private Long categoryId;
    private Long stateId;
    private List<String[]> roleCategoryList;
    private List<Long> ids;
    private String category;
    private String role;
    private Map<String, List<Long>> roleTypeList;
    private RoleCategory roleCategory;

    @BeforeEach
    public void setUp() {
        roleCategoryManager = new RoleCategoryManager(roleCategoryDao);
        roleCategoryIds = new HashSet<>(Arrays.asList(1L, 2L));
        roleCategoryType = RoleCategoryType.LAB; // replace with actual RoleCategoryType
        categoryId = 1L;
        stateId = 1L;
        roleCategoryList = Arrays.asList(new String[]{"category1", "role1", "LAB"}, new String[]{"category2", "role2", "TYPE2"});
        ids = Arrays.asList(1L, 2L);
        category = "category1";
        role = "role1";
        roleTypeList = new HashMap<>();
        roleTypeList.put("LAB", Arrays.asList(1L, 2L));
        roleCategory = new RoleCategory();

        when(roleCategoryDao.findAllByIdAndRoleCategoryType(roleCategoryIds, roleCategoryType)).thenReturn(Collections.singletonList(roleCategory));
        when(roleCategoryDao.findByCategoryAndState(categoryId, stateId)).thenReturn(Collections.singletonList(roleCategory));
        when(roleCategoryDao.findByCategoryName("category1", RoleCategoryType.valueOf("LAB"))).thenReturn(Collections.singletonList(roleCategory));
        when(roleCategoryDao.findAllByIds(ids)).thenReturn(Collections.singletonList(roleCategory));
        when(roleCategoryDao.findByRoleAndCategoryNames("role1", "category1", RoleCategoryType.valueOf("LAB"))).thenReturn(roleCategory);
        when(roleCategoryDao.findListByRoleAndCategoryNames(role, category, roleCategoryType)).thenReturn(Collections.singletonList(roleCategory));
        when(roleCategoryDao.findByCategoryIdAndType(categoryId, roleCategoryType)).thenReturn(Collections.singletonList(roleCategory));
    }

    @Test
    public void testFindAllByIdAndRoleCategoryType() {
        List<RoleCategory> result = roleCategoryManager.findAllByIdAndRoleCategoryType(roleCategoryIds, roleCategoryType);
        assertEquals(Collections.singletonList(roleCategory), result);
    }

    @Test
    public void testFindAllByCategoryIdAndState() {
        List<RoleCategory> result = roleCategoryManager.findAllByCategoryIdAndState(categoryId, stateId);
        assertEquals(Collections.singletonList(roleCategory), result);
    }

    @Test
    public void testFindAllByIds() {
        List<RoleCategory> result = roleCategoryManager.findAllByIds(ids);
        assertEquals(Collections.singletonList(roleCategory), result);
    }

    @Test
    public void testFindByCategoryAndRoleNames() {
        RoleCategory result = roleCategoryManager.findByCategoryAndRoleNames(category, role, roleCategoryType);
        assertEquals(roleCategory, result);
    }

    @Test
    public void testFindAllByCategoryIdAndType() {
        List<List<RoleCategory>> result = roleCategoryManager.findAllByCategoryIdAndType(roleTypeList);
        assertEquals(List.of(Collections.singletonList(roleCategory)), result);
    }

    @Test
    void testFindAllByCategoryName() {
        // Arrange
        List<String[]> roleCategoryList = Arrays.asList(new String[]{"category1", "role1", "LAB"}, new String[]{"category2", "role2", "LAB"});
        List<RoleCategory> roleCategories = List.of(new RoleCategory());
        when(roleCategoryDao.findByCategoryName("category1", RoleCategoryType.valueOf("LAB"))).thenReturn(roleCategories);

        // Act
        List<List<RoleCategory>> actualRoleCategoriesList = roleCategoryManager.findAllByCategoryName(roleCategoryList);

        // Assert
        assertNotNull(actualRoleCategoriesList);
        assertEquals(1, actualRoleCategoriesList.size());
        assertEquals(roleCategories, actualRoleCategoriesList.get(0));
    }

    @Test
    void testFindAllByCategoryAndRoleNames() {

        List<String[]> roleCategoryList = Arrays.asList(new String[]{"category1", "role1", "LAB"}, new String[]{"category2", "role2", "LAB"});
        RoleCategory roleCategory = new RoleCategory();
        when(roleCategoryDao.findByRoleAndCategoryNames("role1", "category1", RoleCategoryType.valueOf("LAB"))).thenReturn(roleCategory);

        // Act
        List<RoleCategory> actualRoleCategories = roleCategoryManager.findAllByCategoryAndRoleNames(roleCategoryList);

        // Assert
        assertNotNull(actualRoleCategories);
        assertEquals(1, actualRoleCategories.size());
        assertEquals(roleCategory, actualRoleCategories.get(0));
    }

}