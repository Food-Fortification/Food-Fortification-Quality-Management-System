package com.beehyv.lab.entityTests;

import com.beehyv.lab.entity.LabCategory;
import com.beehyv.lab.entity.Lab;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class LabCategoryTest {

    @Mock
    private Lab lab;

    @InjectMocks
    private LabCategory labCategory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllFields() {
        Long idValue = 1L;
        Long categoryIdValue = 1L;
        Boolean isEnabledValue = true;

        when(lab.getId()).thenReturn(1L);

        labCategory.setId(idValue);
        labCategory.setCategoryId(categoryIdValue);
        labCategory.setIsEnabled(isEnabledValue);
        labCategory.setLab(lab);

        assertEquals(idValue, labCategory.getId());
        assertEquals(categoryIdValue, labCategory.getCategoryId());
        assertEquals(isEnabledValue, labCategory.getIsEnabled());
        assertEquals(1L, labCategory.getLab().getId());
    }
}