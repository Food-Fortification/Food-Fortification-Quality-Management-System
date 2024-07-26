package com.beehyv.fortification.dao;

import com.beehyv.fortification.entity.TransportVehicleDetails;

import javax.persistence.EntityManager;

public class TransportVehicleDetailsDao extends BaseDao<TransportVehicleDetails>{

    private final EntityManager em;

    public TransportVehicleDetailsDao(EntityManager em) {
        super(em, TransportVehicleDetails.class);
        this.em = em;
    }
}
