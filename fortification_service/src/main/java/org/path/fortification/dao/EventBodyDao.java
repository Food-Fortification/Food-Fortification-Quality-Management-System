package org.path.fortification.dao;

import org.path.fortification.entity.EventBody;
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
