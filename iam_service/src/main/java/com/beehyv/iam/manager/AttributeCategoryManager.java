package com.beehyv.iam.manager;

import com.beehyv.iam.dao.AttributeCategoryDao;
import com.beehyv.iam.model.AttributeCategory;
import org.springframework.stereotype.Component;

@Component
public class AttributeCategoryManager extends BaseManager<AttributeCategory, AttributeCategoryDao> {

  private final AttributeCategoryDao dao;
  public AttributeCategoryManager(AttributeCategoryDao dao) {
    super(dao);
    this.dao = dao;
  }
}
