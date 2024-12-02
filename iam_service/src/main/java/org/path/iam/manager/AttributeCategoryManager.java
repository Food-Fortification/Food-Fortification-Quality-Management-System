package org.path.iam.manager;

import org.path.iam.dao.AttributeCategoryDao;
import org.path.iam.model.AttributeCategory;
import org.springframework.stereotype.Component;

@Component
public class AttributeCategoryManager extends BaseManager<AttributeCategory, AttributeCategoryDao> {

  private final AttributeCategoryDao dao;
  public AttributeCategoryManager(AttributeCategoryDao dao) {
    super(dao);
    this.dao = dao;
  }
}
