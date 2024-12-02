package org.path.lab.dao;

import org.path.lab.entity.SampleProperty;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class SamplePropertyDao extends BaseDao<SampleProperty>{
    public SamplePropertyDao(EntityManager em) {
        super(em, SampleProperty.class);
    }
}
