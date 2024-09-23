package com.beehyv.lab.dao;

import com.beehyv.lab.entity.LabDocument;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class LabDocumentDao extends BaseDao<LabDocument>{
    public LabDocumentDao(EntityManager em) {
        super(em, LabDocument.class);
    }
}
