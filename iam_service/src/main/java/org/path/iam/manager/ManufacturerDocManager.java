package org.path.iam.manager;

import org.path.iam.dao.ManufacturerDocDao;
import org.path.iam.model.ManufacturerDoc;
import org.springframework.stereotype.Component;

@Component
public class ManufacturerDocManager extends BaseManager<ManufacturerDoc, ManufacturerDocDao> {
    public ManufacturerDocManager(ManufacturerDocDao dao) {
        super(dao);
    }
}
