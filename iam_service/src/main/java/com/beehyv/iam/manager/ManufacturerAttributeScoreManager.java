package com.beehyv.iam.manager;

import com.beehyv.iam.dao.ManufacturerAttributeScoreDao;
import com.beehyv.iam.model.ManufacturerAttributeScore;
import org.springframework.stereotype.Component;

@Component
public class ManufacturerAttributeScoreManager extends BaseManager<ManufacturerAttributeScore, ManufacturerAttributeScoreDao> {

  private final ManufacturerAttributeScoreDao dao;
  public ManufacturerAttributeScoreManager(ManufacturerAttributeScoreDao dao) {
    super(dao);
    this.dao = dao;
  }
}
