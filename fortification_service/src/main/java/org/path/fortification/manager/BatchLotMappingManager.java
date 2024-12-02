package org.path.fortification.manager;

import org.path.fortification.dao.BatchLotMappingDao;
import org.path.fortification.entity.BatchLotMapping;
import org.springframework.stereotype.Component;

@Component
public class BatchLotMappingManager extends BaseManager<BatchLotMapping, BatchLotMappingDao> {

    private final BatchLotMappingDao dao;

    public BatchLotMappingManager(BatchLotMappingDao dao) {
        super(dao);
        this.dao = dao;
    }
}

