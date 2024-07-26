package com.beehyv.iam.dao;

import com.beehyv.iam.model.FssaiManufacturerErrorLog;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class FssaiManufacturerErrorLogDao extends BaseDao<FssaiManufacturerErrorLog> {

    private final EntityManager em;
    public FssaiManufacturerErrorLogDao(EntityManager em) {
        super(em, FssaiManufacturerErrorLog.class);
        this.em = em;
    }
}
