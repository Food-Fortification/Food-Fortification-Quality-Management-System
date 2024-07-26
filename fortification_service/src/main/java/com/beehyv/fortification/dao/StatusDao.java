package com.beehyv.fortification.dao;

import com.beehyv.fortification.entity.Status;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class StatusDao extends BaseDao<Status>{
    public StatusDao(EntityManager em) {
        super(em, Status.class);
    }
}
