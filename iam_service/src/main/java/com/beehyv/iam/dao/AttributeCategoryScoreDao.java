package com.beehyv.iam.dao;

import com.beehyv.iam.model.AttributeCategoryScore;
import com.beehyv.iam.model.Country;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class AttributeCategoryScoreDao extends BaseDao<AttributeCategoryScore> {

  private final EntityManager em;

  public AttributeCategoryScoreDao(EntityManager em) {
    super(em, AttributeCategoryScore.class);
    this.em=em;
  }
}
