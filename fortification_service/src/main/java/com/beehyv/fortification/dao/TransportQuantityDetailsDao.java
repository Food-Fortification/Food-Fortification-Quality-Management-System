package com.beehyv.fortification.dao;

import com.beehyv.fortification.entity.TransportQuantityDetails;

import javax.persistence.EntityManager;

public class TransportQuantityDetailsDao extends BaseDao<TransportQuantityDetails>{

    private final EntityManager em;

    public TransportQuantityDetailsDao(EntityManager em) {
        super(em, TransportQuantityDetails.class);
        this.em = em;
    }
}
