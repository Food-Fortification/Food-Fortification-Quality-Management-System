package com.beehyv.fortification.dao;

import com.beehyv.fortification.entity.EventBody;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class EventBodyDao extends BaseDao<EventBody> {

    private final EntityManager em;

    public EventBodyDao(EntityManager em) {
        super(em, EventBody.class);
        this.em = em;
    }
}
