package com.beehyv.iam.manager;

import com.beehyv.iam.dao.ManufacturerCategoryAttributesDao;
import com.beehyv.iam.model.ManufacturerCategoryAttributes;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ManufacturerCategoryAttributesManager extends BaseManager<ManufacturerCategoryAttributes, ManufacturerCategoryAttributesDao> {

  private final ManufacturerCategoryAttributesDao dao;
  public ManufacturerCategoryAttributesManager(ManufacturerCategoryAttributesDao dao) {
    super(dao);
    this.dao = dao;
  }

  public List<ManufacturerCategoryAttributes> findByManufacturerId(Long manufacturerId) {
    return dao.findByManufacturerId(manufacturerId);
  }

  public List<ManufacturerCategoryAttributes> findByManufacturerIdAndUserId(Long manufacturerId, Long userId) {
    return dao.findByManufacturerIdAndUserId(manufacturerId, userId);
  }
}
