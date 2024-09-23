package com.beehyv.iam.dao;

import com.beehyv.iam.model.AttributeCategory;
import com.beehyv.iam.model.Country;
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
