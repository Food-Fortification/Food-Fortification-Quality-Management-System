package org.path.iam.dao;

import org.path.iam.model.Status;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Component
public class StatusDao extends BaseDao<Status> {
    private final EntityManager em;
    public StatusDao(EntityManager em) {
        super(em, Status.class);
        this.em = em;
    }
    public Status findByName(String name){
        try {
            return em
                    .createQuery("from Status where name = :name", Status.class)
                    .setParameter("name", name).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
