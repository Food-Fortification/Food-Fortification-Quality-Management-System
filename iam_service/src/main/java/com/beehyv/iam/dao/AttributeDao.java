package com.beehyv.iam.dao;

import com.beehyv.iam.model.Attribute;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class AttributeDao extends BaseDao<Attribute> {

  private final EntityManager em;

  public AttributeDao(EntityManager em) {
    super(em, Attribute.class);
    this.em=em;
  }

    public List<Attribute> findByIds(List<Long> ids) {
     return em.createQuery("from Attribute where id in :ids", Attribute.class)
             .setParameter("ids", ids)
             .getResultList();
    }
}
