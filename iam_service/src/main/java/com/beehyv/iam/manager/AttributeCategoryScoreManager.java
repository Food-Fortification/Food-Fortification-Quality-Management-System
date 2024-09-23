package com.beehyv.iam.manager;

import com.beehyv.iam.dao.AttributeCategoryScoreDao;
import com.beehyv.iam.model.AttributeCategoryScore;
import org.springframework.stereotype.Component;

@Component
public class AttributeCategoryScoreManager extends BaseManager<AttributeCategoryScore, AttributeCategoryScoreDao> {

  private final AttributeCategoryScoreDao dao;
  public AttributeCategoryScoreManager(AttributeCategoryScoreDao dao) {
    super(dao);
    this.dao = dao;
  }
}
