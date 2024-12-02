package org.path.iam.dao;

import org.path.iam.model.ManufacturerDoc;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;

@Component
public class ManufacturerDocDao extends BaseDao<ManufacturerDoc> {
    public ManufacturerDocDao(EntityManager em) {
        super(em, ManufacturerDoc.class);
    }
}
