package com.beehyv.iam.manager;

import com.beehyv.iam.dao.ManufacturerEmpanelDao;
import com.beehyv.iam.model.ManufacturerEmpanel;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ManufacturerEmpanelManager extends BaseManager<ManufacturerEmpanel, ManufacturerEmpanelDao>{

    private final ManufacturerEmpanelDao dao;
    public ManufacturerEmpanelManager(ManufacturerEmpanelDao dao) {
        super(dao);
        this.dao = dao;
    }

    public List<Long> getAllEmpanelledManufacturers(Long categoryId, String stateGeoId, Date fromDate, Date toDate) {
        return dao.getAllEmpanelledManufacturers(categoryId, stateGeoId, fromDate, toDate);
    }
}
