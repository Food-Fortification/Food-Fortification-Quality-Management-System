package org.path.fortification.manager;

import org.path.fortification.dao.MixMappingDao;
import org.path.fortification.entity.MixMapping;
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
public class MixMappingManagerTest {

    @Mock
    private MixMappingDao mixMappingDao;

    private MixMappingManager mixMappingManager;

    private Long targetBatchId;
    private Integer pageNumber;
    private Integer pageSize;
    private Long sourceLotId;
    private List<Long> ids;
    private MixMapping mixMapping;

    @BeforeEach
    public void setUp() {
        mixMappingManager = new MixMappingManager(mixMappingDao);
        targetBatchId = 1L;
        pageNumber = 1;
        pageSize = 10;
        sourceLotId = 1L;
        ids = Arrays.asList(1L, 2L);
        mixMapping = new MixMapping();

        when(mixMappingDao.findAllByTargetBatchId(targetBatchId, pageNumber, pageSize)).thenReturn(Collections.singletonList(mixMapping));
        when(mixMappingDao.getCountByTargetBatchId(targetBatchId)).thenReturn(1L);
        when(mixMappingDao.getCountBySourceLotId(sourceLotId)).thenReturn(1L);
        when(mixMappingDao.findAllBySourceLotId(sourceLotId, pageNumber, pageSize)).thenReturn(Collections.singletonList(mixMapping));
        when(mixMappingDao.findAllByIds(ids)).thenReturn(Collections.singletonList(mixMapping));
    }

    @Test
    public void testFindAllByTargetBatchId() {
        List<MixMapping> result = mixMappingManager.findAllByTargetBatchId(targetBatchId, pageNumber, pageSize);
        assertEquals(Collections.singletonList(mixMapping), result);
    }

    @Test
    public void testGetCountByTargetBatchId() {
        Long result = mixMappingManager.getCountByTargetBatchId(1, targetBatchId, pageNumber, pageSize);
        assertEquals(1L, result);
    }

    @Test
    public void testGetCountBySourceLotId() {
        Long result = mixMappingManager.getCountBySourceLotId(1, sourceLotId, pageNumber, pageSize);
        assertEquals(1L, result);
    }

    @Test
    public void testFindAllBySourceLotId() {
        List<MixMapping> result = mixMappingManager.findAllBySourceLotId(sourceLotId, pageNumber, pageSize);
        assertEquals(Collections.singletonList(mixMapping), result);
    }

    @Test
    public void testFindAllByIds() {
        List<MixMapping> result = mixMappingManager.findAllByIds(ids);
        assertEquals(Collections.singletonList(mixMapping), result);
    }
}