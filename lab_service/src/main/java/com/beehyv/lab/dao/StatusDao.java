package com.beehyv.lab.dao;

import com.beehyv.lab.entity.Status;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import org.springframework.stereotype.Component;

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

