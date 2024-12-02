package org.path.fortification.manager;

import org.path.fortification.dao.SizeUnitDao;
import org.path.fortification.entity.SizeUnit;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SizeUnitManager extends BaseManager<SizeUnit, SizeUnitDao> {
    private final SizeUnitDao dao;
    public SizeUnitManager(SizeUnitDao dao) {
        super(dao);
        this.dao = dao;
    }

    public List<SizeUnit> findAllByBatchId(Long batchId, Integer pageNumber, Integer pageSize) {
        return dao.findAllByBatchId(batchId, pageNumber, pageSize);
    }

    public Long getCount(int listSize, Long batchId, Integer pageNumber, Integer pageSize) {
        if(pageSize == null || pageNumber == null) {
            return ((Integer) listSize).longValue();
        }
        return dao.getCount(batchId);
    }
}
