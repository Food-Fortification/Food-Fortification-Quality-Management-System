package com.beehyv.iam.dao;

import com.beehyv.iam.model.AttributeCategoryScore;
import com.beehyv.iam.model.ManufacturerAttributeScore;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class ManufacturerAttributeScoreDao extends BaseDao<ManufacturerAttributeScore> {
  private final EntityManager em;

  public ManufacturerAttributeScoreDao(EntityManager em) {
    super(em, ManufacturerAttributeScore.class);
    this.em=em;
  }
}
