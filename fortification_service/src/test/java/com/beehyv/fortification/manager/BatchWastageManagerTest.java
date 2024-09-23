package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.WastageDao;
import com.beehyv.fortification.entity.Wastage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BatchWastageManagerTest {

    @Mock
    private WastageDao wastageDao;

    private BatchWastageManager batchWastageManager;

    private Long batchId;
    private Long lotId;
    private Integer pageNumber;
    private Integer pageSize;
    private List<Wastage> wastageList;

    @BeforeEach
    public void setUp() {
        batchWastageManager = new BatchWastageManager(wastageDao);
        batchId = 1L;
        lotId = 2L;
        pageNumber = 1;
        pageSize = 10;
        wastageList = Arrays.asList(new Wastage(), new Wastage());

        when(wastageDao.getCountByBatchId(batchId)).thenReturn((long) wastageList.size());
        when(wastageDao.getCountByLotId(lotId)).thenReturn((long) wastageList.size());
        when(wastageDao.findAllByBatchId(batchId, pageNumber, pageSize)).thenReturn(wastageList);
        when(wastageDao.findAllByLotId(lotId, pageNumber, pageSize)).thenReturn(wastageList);
    }

    @Test
    public void testGetCountByBatchId() {
        Long result = batchWastageManager.getCountByBatchId(wastageList.size(), batchId, pageNumber, pageSize);
        assertEquals(wastageList.size(), result);
    }

    @Test
    public void testGetCountByLotId() {
        Long result = batchWastageManager.getCountByLotId(wastageList.size(), lotId, pageNumber, pageSize);
        assertEquals(wastageList.size(), result);
    }

    @Test
    public void testFindAllByBatchId() {
        List<Wastage> result = batchWastageManager.findAllByBatchId(batchId, pageNumber, pageSize);
        assertEquals(wastageList, result);
    }

    @Test
    public void testFindAllByLotId() {
        List<Wastage> result = batchWastageManager.findAllByLotId(lotId, pageNumber, pageSize);
        assertEquals(wastageList, result);
    }
}