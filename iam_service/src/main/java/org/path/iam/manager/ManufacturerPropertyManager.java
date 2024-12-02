package org.path.iam.manager;

import org.path.iam.dao.ManufacturerPropertyDao;
import org.path.iam.model.ManufacturerProperty;
import org.springframework.stereotype.Component;

@Component
public class ManufacturerPropertyManager extends BaseManager<ManufacturerProperty, ManufacturerPropertyDao> {
    public ManufacturerPropertyManager(ManufacturerPropertyDao dao) {
        super(dao);
    }
}
