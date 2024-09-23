package com.beehyv.iam.manager;

import com.beehyv.iam.dao.ManufacturerPropertyDao;
import com.beehyv.iam.model.ManufacturerProperty;
import org.springframework.stereotype.Component;

@Component
public class ManufacturerPropertyManager extends BaseManager<ManufacturerProperty, ManufacturerPropertyDao> {
    public ManufacturerPropertyManager(ManufacturerPropertyDao dao) {
        super(dao);
    }
}
