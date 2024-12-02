package org.path.fortification.dao;

import org.path.fortification.entity.BatchLotMapping;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class BatchLotMappingDao extends BaseDao<BatchLotMapping> {
    private final EntityManager em;

    public BatchLotMappingDao(EntityManager em) {
        super(em, BatchLotMapping.class);
        this.em = em;
    }
}

