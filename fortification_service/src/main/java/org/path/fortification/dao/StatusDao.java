package org.path.fortification.dao;

import org.path.fortification.entity.Status;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class StatusDao extends BaseDao<Status>{
    public StatusDao(EntityManager em) {
        super(em, Status.class);
    }
}
