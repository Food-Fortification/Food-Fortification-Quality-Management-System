package org.path.fortification.dao;

import org.path.fortification.entity.DocType;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class DocTypeDao extends BaseDao<DocType>{
    public DocTypeDao(EntityManager em) {
        super(em, DocType.class);
    }
}