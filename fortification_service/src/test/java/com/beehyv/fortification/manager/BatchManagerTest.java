package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.BatchDao;
import com.beehyv.fortification.dto.requestDto.SearchListRequest;
import com.beehyv.fortification.entity.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BatchManagerTest {

    @Mock
    private BatchDao batchDao;

    @Mock
    private CategoryManager categoryManager;

    @InjectMocks
    private BatchManager batchManager;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testCreate() {
        Batch batch = new Batch();
        Set<BatchProperty> batchProperties = new HashSet<>();
        Set<BatchDoc> batchDocs = new HashSet<>();
        Set<BatchLotMapping> batchLotMappings = new HashSet<>();
        Set<SizeUnit> sizeUnits = new HashSet<>();
        Set<Wastage> wastes = new HashSet<>();
        Set<MixMapping> mixes = new HashSet<>();
        batch.setBatchProperties(batchProperties);
        batch.setBatchDocs(batchDocs);
        batch.setBatchLotMapping(batchLotMappings);
        batch.setSizeUnits(sizeUnits);
        batch.setWastes(wastes);
        batch.setMixes(mixes);

        when(batchDao.create(batch)).thenReturn(batch);
        batchManager.create(batch);

        verify(batchDao, times(1)).create(batch);
    }

    @Test
    public void testSave() {
        Batch batch = new Batch();

        batchManager.save(batch);

        verify(batchDao, times(1)).update(batch);
    }

    @Test
    public void testGetCount() {
        Long categoryId = 1L;
        Long manufacturerId = 2L;
        Long stateId = 3L;
        SearchListRequest searchRequest = new SearchListRequest();

        batchManager.getCount(categoryId, manufacturerId, stateId, searchRequest);

        verify(batchDao, times(1)).getCount(categoryId, manufacturerId, stateId, searchRequest);
    }


    @Test
    public void testFindAllBatches() {
        Long categoryId = 1L;
        Long manufacturerId = 2L;
        Integer pageNumber = 1;
        Integer pageSize = 10;
        SearchListRequest searchRequest = new SearchListRequest();
        Boolean remQuantity = true;

        batchManager.findAllBatches(categoryId, manufacturerId, pageNumber, pageSize, searchRequest, remQuantity);

        verify(batchDao, times(1)).findAllBatches(categoryId, manufacturerId, pageNumber, pageSize, searchRequest, remQuantity);
    }


    @Test
    public void testFindByIdAndManufacturerId() {
        Long id = 1L;
        Long manufacturerId = 2L;

        batchManager.findByIdAndManufacturerId(id, manufacturerId);

        verify(batchDao, times(1)).findByIdAndManufacturerId(id, manufacturerId);
    }

    @Test
    public void testFindByUUID() {
        String uuid = "uuid";

        batchManager.findByUUID(uuid);

        verify(batchDao, times(1)).findByUUID(uuid);
    }

    @Test
    public void testFindAllAggregateByManufacturerIdAndCategoryId() {
        int pageNumber = 1;
        int pageSize = 10;
        List<State> state = new ArrayList<>();

        batchManager.findAllAggregateByManufacturerIdAndCategoryId(pageNumber, pageSize, state);

        verify(batchDao, times(1)).findAllAggregateByManufacturerIdAndCategoryId(pageNumber, pageSize, state);
    }

    @Test
    public void testFindAllBatchesForInspection() {
        Long categoryId = 1L;
        SearchListRequest searchListRequest = new SearchListRequest();
        List<Long> testManufacturerIds = new ArrayList<>();
        List<Long> batchIds = new ArrayList<>();
        Integer pageNumber = 1;
        Integer pageSize = 10;

        batchManager.findAllBatchesForInspection(categoryId, searchListRequest, testManufacturerIds, batchIds, pageNumber, pageSize);

        verify(batchDao, times(1)).findAllBatchesForInspection(categoryId, searchListRequest, testManufacturerIds, batchIds, pageNumber, pageSize);
    }

    @Test
    public void testGetCountForInspection() {
        Long categoryId = 1L;
        SearchListRequest searchListRequest = new SearchListRequest();
        List<Long> testManufacturerIds = new ArrayList<>();

        batchManager.getCountForInspection(categoryId, searchListRequest, testManufacturerIds);

        verify(batchDao, times(1)).gatCountForInspection(categoryId, searchListRequest, testManufacturerIds);
    }
}