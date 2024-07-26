package com.beehyv.iam.manager;

import com.beehyv.iam.dao.FssaiManufacturerErrorLogDao;
import com.beehyv.iam.model.FssaiManufacturerErrorLog;
import org.springframework.stereotype.Component;

@Component
public class FssaiManufacturerErrorLogManager extends BaseManager<FssaiManufacturerErrorLog, FssaiManufacturerErrorLogDao>{

    private final FssaiManufacturerErrorLogDao dao;


    public FssaiManufacturerErrorLogManager(FssaiManufacturerErrorLogDao dao) {
        super(dao);
        this.dao = dao;
    }
}
