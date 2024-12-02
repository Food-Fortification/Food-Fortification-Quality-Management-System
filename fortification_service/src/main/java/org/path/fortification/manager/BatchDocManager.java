package org.path.fortification.manager;

import org.path.fortification.dao.BatchDocDao;
import org.path.fortification.entity.BatchDoc;
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
