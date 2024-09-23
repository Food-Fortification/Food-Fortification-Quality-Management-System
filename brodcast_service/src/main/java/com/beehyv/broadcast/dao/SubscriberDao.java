package com.beehyv.broadcast.dao;


import com.beehyv.broadcast.model.Subscriber;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class SubscriberDao extends BaseDao<Subscriber> {

    private final EntityManager em;

    public SubscriberDao(EntityManager em) {
        super(em, Subscriber.class);
        this.em = em;
    }

    public List<Subscriber> findByGeoId(Integer stateGeoId) {
        return em.createQuery("from Subscriber s where s.stateGeoId = : stateGeoId", Subscriber.class)
                .setParameter("stateGeoId", stateGeoId)
                .getResultList();
    }
}
