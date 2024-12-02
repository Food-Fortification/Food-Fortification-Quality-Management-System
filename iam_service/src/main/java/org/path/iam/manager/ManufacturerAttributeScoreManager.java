package org.path.iam.manager;

import org.path.iam.dao.ManufacturerAttributeScoreDao;
import org.path.iam.model.ManufacturerAttributeScore;
import org.springframework.stereotype.Component;

@Component
public class ManufacturerAttributeScoreManager extends BaseManager<ManufacturerAttributeScore, ManufacturerAttributeScoreDao> {

  private final ManufacturerAttributeScoreDao dao;
  public ManufacturerAttributeScoreManager(ManufacturerAttributeScoreDao dao) {
    super(dao);
    this.dao = dao;
  }
}
