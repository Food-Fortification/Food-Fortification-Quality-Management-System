package org.path.fortification.manager;

import org.path.fortification.dao.WastageDao;
import org.path.fortification.entity.Wastage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BatchWastageManager extends BaseManager<Wastage, WastageDao> {
    private final WastageDao dao;
    public BatchWastageManager(WastageDao dao) {
        super(dao);
        this.dao = dao;
    }

    public Long getCountByBatchId(int listSize, Long batchId, Integer pageNumber, Integer pageSize) {
        if(pageSize == null || pageNumber == null) {
            return ((Integer) listSize).longValue();
        }
        return dao.getCountByBatchId(batchId);
    }
    public Long getCountByLotId(int listSize, Long lotId, Integer pageNumber, Integer pageSize) {
        if(pageSize == null || pageNumber == null) {
            return ((Integer) listSize).longValue();
        }
        return dao.getCountByLotId(lotId);
    }

    public List<Wastage> findAllByBatchId(Long batchId, Integer pageNumber, Integer pageSize) {
        return dao.findAllByBatchId(batchId, pageNumber, pageSize);
    }
    public List<Wastage> findAllByLotId(Long lotId, Integer pageNumber, Integer pageSize) {
        return dao.findAllByLotId(lotId, pageNumber, pageSize);
    }
}
