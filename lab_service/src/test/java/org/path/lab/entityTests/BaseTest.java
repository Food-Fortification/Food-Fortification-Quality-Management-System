package org.path.lab.entityTests;

import org.path.lab.entity.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.path.lab.entity.Base;


import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;


class BaseTest {

    @Mock
    private Status status;

    @InjectMocks
    private test base;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        MockitoAnnotations.initMocks(this);
        when(status.getId()).thenReturn(1L);
    }

    @Test
    void testAllFields() {
        String createdByValue = "Test User";
        LocalDateTime createdDateValue = LocalDateTime.now();
        String modifiedByValue = "Test User";
        LocalDateTime modifiedDateValue = LocalDateTime.now();
        String uuidValue = "123e4567-e89b-12d3-a456-426614174000";
        Boolean isDeletedValue = false;


        base.setCreatedBy(createdByValue);
        base.setCreatedDate(createdDateValue);
        base.setModifiedBy(modifiedByValue);
        base.setModifiedDate(modifiedDateValue);
        base.setUuid(uuidValue);
        base.setIsDeleted(isDeletedValue);
        base.setStatus(status);


        assertEquals(createdByValue, base.getCreatedBy());
        assertEquals(createdDateValue, base.getCreatedDate());
        assertEquals(modifiedByValue, base.getModifiedBy());
        assertEquals(modifiedDateValue, base.getModifiedDate());
        assertEquals(uuidValue, base.getUuid());
        assertFalse(base.getIsDeleted());
        assertEquals(1L, base.getStatus().getId());
    }
}

class test extends Base {}