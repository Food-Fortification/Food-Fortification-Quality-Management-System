package org.path.iam.dao;

import org.path.iam.model.AttributeCategory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class AttributeCategoryDao extends BaseDao<AttributeCategory> {

  private final EntityManager em;

  public AttributeCategoryDao(EntityManager em) {
    super(em, AttributeCategory.class);
    this.em=em;
  }
}
