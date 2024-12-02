package org.path.fortification.manager;

import org.path.fortification.dao.LabConfigCategoryDao;
import org.path.fortification.entity.Category;
import org.path.fortification.entity.LabConfigCategory;
import org.path.parent.keycloakSecurity.KeycloakInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class LabConfigCategoryManagerTest {

    @Mock
    private LabConfigCategoryDao labConfigCategoryDao;

    @Mock
    private KeycloakInfo keycloakInfo;

    private LabConfigCategoryManager labConfigCategoryManager;

    private Category category;
    private Long categoryId;
    private Long targetCategoryId;
    private LabConfigCategory labConfigCategory;

    @BeforeEach
    public void setUp() {
        labConfigCategoryManager = new LabConfigCategoryManager(labConfigCategoryDao, keycloakInfo);
        category = new Category();
        categoryId = 1L;
        targetCategoryId = 2L;
        labConfigCategory = new LabConfigCategory();

        when(labConfigCategoryDao.findByCategoryId(category.getId())).thenReturn(labConfigCategory);
        when(labConfigCategoryDao.findByCategoryIds(categoryId, targetCategoryId)).thenReturn(Collections.singletonList(labConfigCategory));
    }

    @Test
    public void testFindByCategory() {
        LabConfigCategory result = labConfigCategoryManager.findByCategory(category);
        assertEquals(labConfigCategory, result);
    }

    @Test
    public void testFindByCategoryIds() {
        LabConfigCategory result = labConfigCategoryManager.findByCategoryIds(categoryId, targetCategoryId);
        assertEquals(labConfigCategory, result);
    }
}