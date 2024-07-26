package com.beehyv.fortification.dao;

import com.beehyv.fortification.entity.DocType;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class DocTypeDao extends BaseDao<DocType>{
    public DocTypeDao(EntityManager em) {
        super(em, DocType.class);
    }
}