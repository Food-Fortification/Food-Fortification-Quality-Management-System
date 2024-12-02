package org.path.iam.manager;

import org.path.iam.dao.AttributeCategoryScoreDao;
import org.path.iam.model.AttributeCategoryScore;
import org.springframework.stereotype.Component;

@Component
public class AttributeCategoryScoreManager extends BaseManager<AttributeCategoryScore, AttributeCategoryScoreDao> {

  private final AttributeCategoryScoreDao dao;
  public AttributeCategoryScoreManager(AttributeCategoryScoreDao dao) {
    super(dao);
    this.dao = dao;
  }
}
