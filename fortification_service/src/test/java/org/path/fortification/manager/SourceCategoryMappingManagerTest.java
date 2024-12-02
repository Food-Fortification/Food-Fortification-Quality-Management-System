package org.path.fortification.manager;

import org.path.fortification.dao.SourceCategoryMappingDao;
import org.path.fortification.entity.Category;
import org.path.fortification.entity.SourceCategoryMapping;
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
public class SourceCategoryMappingManagerTest {

    @Mock
    private SourceCategoryMappingDao sourceCategoryMappingDao;

    private SourceCategoryMappingManager sourceCategoryMappingManager;

    private Long returnId;
    private Long sourceId;
    private Long targetId;
    private SourceCategoryMapping sourceCategoryMapping;
    private Category category;

    @BeforeEach
    public void setUp() {
        sourceCategoryMappingManager = new SourceCategoryMappingManager(sourceCategoryMappingDao);
        returnId = 1L;
        sourceId = 1L;
        targetId = 1L;
        sourceCategoryMapping = new SourceCategoryMapping();
        category = new Category();

        when(sourceCategoryMappingDao.findByIds(returnId, sourceId, targetId)).thenReturn(Collections.singletonList(sourceCategoryMapping));
        when(sourceCategoryMappingDao.findBySourceId(sourceId)).thenReturn(Collections.singletonList(sourceCategoryMapping));
    }

    @Test
    public void testFindByIds() {
        SourceCategoryMapping result = sourceCategoryMappingManager.findByIds(returnId, sourceId, targetId);
        assertEquals(sourceCategoryMapping, result);
    }

//    @Test
//    public void testGetSource() {
//        List<Category> result = sourceCategoryMappingManager.getSource(sourceId);
//        assertEquals(Arrays.asList(category), result);
//    }
}