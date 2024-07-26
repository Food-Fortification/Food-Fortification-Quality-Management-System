package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.TransportQuantityDetailsDao;
import com.beehyv.fortification.entity.TransportQuantityDetails;

public class TransportQuantityDetailsManager extends BaseManager<TransportQuantityDetails, TransportQuantityDetailsDao>{

    private final TransportQuantityDetailsDao dao;

    public TransportQuantityDetailsManager(TransportQuantityDetailsDao dao) {
        super(dao);
        this.dao = dao;
    }
}
