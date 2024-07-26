package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.TransportVehicleDetailsDao;
import com.beehyv.fortification.entity.TransportVehicleDetails;

public class TransportVehicleDetailsManager extends BaseManager<TransportVehicleDetails, TransportVehicleDetailsDao>{

    private final TransportVehicleDetailsDao dao;
    public TransportVehicleDetailsManager(TransportVehicleDetailsDao dao) {
        super(dao);
        this.dao = dao;
    }
}
