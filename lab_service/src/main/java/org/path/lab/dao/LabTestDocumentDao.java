package org.path.lab.dao;

import org.path.lab.entity.LabTestDocument;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class LabTestDocumentDao extends BaseDao<LabTestDocument>{
    public LabTestDocumentDao(EntityManager em) {
        super(em, LabTestDocument.class);
    }
}
