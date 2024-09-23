package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.RoleCategoryStateDao;
import com.beehyv.fortification.entity.RoleCategoryState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RoleCategoryStateManagerTest {

    @Mock
    private RoleCategoryStateDao roleCategoryStateDao;

    private RoleCategoryStateManager roleCategoryStateManager;

    private Long categoryId;
    private Long roleCategoryId;
    private Long stateId;
    private RoleCategoryState roleCategoryState;

    @BeforeEach
    public void setUp() {
        roleCategoryStateManager = new RoleCategoryStateManager(roleCategoryStateDao);
        categoryId = 1L;
        roleCategoryId = 1L;
        stateId = 1L;
        roleCategoryState = new RoleCategoryState();

        when(roleCategoryStateDao.findByIds(categoryId, roleCategoryId, stateId)).thenReturn(Collections.singletonList(roleCategoryState));
    }

    @Test
    public void testFindByIdState() {
        RoleCategoryState result = roleCategoryStateManager.findByIdState(categoryId, roleCategoryId, stateId);
        assertEquals(roleCategoryState, result);
    }
}