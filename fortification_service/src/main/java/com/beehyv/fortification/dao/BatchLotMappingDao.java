package com.beehyv.fortification.dao;

import com.beehyv.fortification.entity.BatchLotMapping;
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

