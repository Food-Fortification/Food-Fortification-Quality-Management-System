package org.path.iam.dao;

import org.path.iam.model.AttributeCategoryScore;
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
