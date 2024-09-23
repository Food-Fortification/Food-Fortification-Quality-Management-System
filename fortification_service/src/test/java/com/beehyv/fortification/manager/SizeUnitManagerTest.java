package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.SizeUnitDao;
import com.beehyv.fortification.entity.SizeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SizeUnitManagerTest {

    @Mock
    private SizeUnitDao sizeUnitDao;

    private SizeUnitManager sizeUnitManager;

    private Long batchId;
    private Integer pageNumber;
    private Integer pageSize;
    private SizeUnit sizeUnit;

    @BeforeEach
    public void setUp() {
        sizeUnitManager = new SizeUnitManager(sizeUnitDao);
        batchId = 1L;
        pageNumber = 1;
        pageSize = 10;
        sizeUnit = new SizeUnit();

        when(sizeUnitDao.findAllByBatchId(batchId, pageNumber, pageSize)).thenReturn(Collections.singletonList(sizeUnit));
        when(sizeUnitDao.getCount(batchId)).thenReturn(1L);
    }

    @Test
    public void testFindAllByBatchId() {
        List<SizeUnit> result = sizeUnitManager.findAllByBatchId(batchId, pageNumber, pageSize);
        assertEquals(Collections.singletonList(sizeUnit), result);
    }

    @Test
    public void testGetCount() {
        Long result = sizeUnitManager.getCount(1, batchId, pageNumber, pageSize);
        assertEquals(1L, result);
    }
}