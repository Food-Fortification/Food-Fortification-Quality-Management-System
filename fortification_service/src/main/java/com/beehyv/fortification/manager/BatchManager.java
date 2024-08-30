package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.BatchDao;
import com.beehyv.fortification.dto.requestDto.SearchListRequest;
import com.beehyv.fortification.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class BatchManager extends BaseManager<Batch, BatchDao> {
    @Autowired
    private BatchDao batchDao;
    @Autowired
    private CategoryManager categoryManager;

    public BatchManager(BatchDao batchDao) {
        super(batchDao);
        this.batchDao = batchDao;
    }

    @Override
    public Batch create(Batch batch) {
        updateUUIDs(batch.getBatchProperties());
        updateUUIDs(batch.getBatchDocs());
        updateUUIDs(batch.getBatchLotMapping().stream().map(BatchLotMapping::getSourceLot).collect(Collectors.toSet()));
        updateUUIDs(batch.getSizeUnits());
        updateUUIDs(batch.getWastes());
        updateUUIDs(batch.getMixes());
        return batchDao.create(batch);
    }

    public Batch save(Batch batch) {
        return batchDao.update(batch);
    }

    private <T extends Base> void updateUUIDs(Set<T> set) {
        if (set != null) set.forEach(d -> d.setUuid(UUID.randomUUID().toString()));
    }

    public Long getCount(List<String> filterByState, Long categoryId, Long manufacturerId, Long stateId, SearchListRequest searchRequest, Boolean remQuantity, Date fromDate, Date toDate) {
        return batchDao.getCount(filterByState,categoryId, manufacturerId, stateId, searchRequest,remQuantity,fromDate,toDate);
    }

    public Long getCountForSuperAdmin(SearchListRequest searchRequest, List<Long> testManufacturerIds) {
        List<Long> categoryIds = categoryManager.getUserCategoryIds(searchRequest, RoleCategoryType.MODULE);
        return batchDao.getCountForSuperAdmin(searchRequest, categoryIds, testManufacturerIds);
    }

    public List<Batch> findAllBatches(List<String> filterByState, Date fromDate, Date toDate, Long categoryId, Long manufacturerId, Integer pageNumber, Integer pageSize, SearchListRequest searchRequest, Boolean remQuantity, Boolean isLabTested) {
        return batchDao.findAllBatches(filterByState,fromDate,toDate,categoryId, manufacturerId, pageNumber, pageSize, searchRequest, remQuantity, isLabTested);
    }

    public List<Batch> findAllBatches(Integer pageNumber, Integer pageSize, SearchListRequest searchRequest, Boolean remQuantity, List<Long> testManufacturerIds) {
        List<Long> categoryIds = categoryManager.getUserCategoryIds(searchRequest, RoleCategoryType.MODULE);
        return batchDao.findAllBatches(pageNumber, pageSize, searchRequest, categoryIds, remQuantity, testManufacturerIds);
    }

    public Batch findByIdAndManufacturerId(Long id, Long manufacturerId) {
        return batchDao.findByIdAndManufacturerId(id, manufacturerId);
    }

    public Batch findByUUID(String uuid) {
        return batchDao.findByUUID(uuid);
    }

    public List<BatchStateGeo> findAllAggregateByManufacturerIdAndCategoryId(int pageNumber, int pageSize, List<State> state) {
        return batchDao.findAllAggregateByManufacturerIdAndCategoryId(pageNumber, pageSize, state);
    }

    public List<Batch> findAllBatchesForInspection(Long categoryId, SearchListRequest searchListRequest, List<Long> testManufacturerIds, List<Long> batchIds, Integer pageNumber, Integer pageSize) {
        return batchDao.findAllBatchesForInspection(categoryId, searchListRequest, testManufacturerIds, batchIds, pageNumber, pageSize);
    }

    public Long getCountForInspection(Long categoryId, SearchListRequest searchListRequest, List<Long> testManufacturerIds) {
        return batchDao.gatCountForInspection(categoryId, searchListRequest, testManufacturerIds);
    }
}
