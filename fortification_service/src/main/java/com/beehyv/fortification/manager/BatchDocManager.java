package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.BatchDocDao;
import com.beehyv.fortification.entity.BatchDoc;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BatchDocManager extends BaseManager<BatchDoc, BatchDocDao> {
    private final BatchDocDao dao;
    public BatchDocManager(BatchDocDao dao) {
        super(dao);
        this.dao = dao;
    }

    public List<BatchDoc> findAllByBatchId(Long batchId, Integer pageNumber, Integer pageSize) {
        return dao.findAllByBatchId(batchId, pageNumber, pageSize);
    }
}
