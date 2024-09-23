package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.BatchLotMappingDao;
import com.beehyv.fortification.entity.BatchLotMapping;
import org.springframework.stereotype.Component;

@Component
public class BatchLotMappingManager extends BaseManager<BatchLotMapping, BatchLotMappingDao> {

    private final BatchLotMappingDao dao;

    public BatchLotMappingManager(BatchLotMappingDao dao) {
        super(dao);
        this.dao = dao;
    }
}

