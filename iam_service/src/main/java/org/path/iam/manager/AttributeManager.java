package org.path.iam.manager;

import org.path.iam.dao.AttributeDao;
import org.path.iam.model.Attribute;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AttributeManager extends BaseManager<Attribute, AttributeDao> {

  private final AttributeDao dao;
  public AttributeManager(AttributeDao dao) {
    super(dao);
    this.dao = dao;
  }
  public List<Attribute> findByIds(List<Long> ids){
    return dao.findByIds(ids);
  }
}
