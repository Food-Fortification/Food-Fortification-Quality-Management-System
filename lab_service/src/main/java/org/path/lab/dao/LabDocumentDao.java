package org.path.lab.dao;

import org.path.lab.entity.LabDocument;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class LabDocumentDao extends BaseDao<LabDocument>{
    public LabDocumentDao(EntityManager em) {
        super(em, LabDocument.class);
    }
}
