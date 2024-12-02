package org.path.broadcast.dao;

import org.path.broadcast.model.Event;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Component
public class EventDao extends BaseDao<Event> {

    private final EntityManager em;

    public EventDao(EntityManager em) {
        super(em, Event.class);
        this.em = em;
    }

    public Event findByName(String name) {
        try {
            return em.createQuery("from Event e where e.stateName = :name", Event.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
