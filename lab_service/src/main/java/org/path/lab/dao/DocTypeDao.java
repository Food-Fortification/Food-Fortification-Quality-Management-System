package org.path.lab.dao;

import org.path.lab.entity.DocType;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class DocTypeDao extends BaseDao<DocType>{
    public DocTypeDao(EntityManager em) {
        super(em, DocType.class);
    }
}
