package com.beehyv.iam.manager;

import com.beehyv.iam.dao.ManufacturerDocDao;
import com.beehyv.iam.model.ManufacturerDoc;
import org.springframework.stereotype.Component;

@Component
public class ManufacturerDocManager extends BaseManager<ManufacturerDoc, ManufacturerDocDao> {
    public ManufacturerDocManager(ManufacturerDocDao dao) {
        super(dao);
    }
}
