package com.beehyv.iam.manager;

import com.beehyv.iam.dao.ManufacturerCategoryDao;
import com.beehyv.iam.enums.ManufacturerCategoryAction;
import com.beehyv.iam.model.ManufacturerCategory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ManufacturerCategoryManager extends BaseManager<ManufacturerCategory, ManufacturerCategoryDao> {
    private final ManufacturerCategoryDao dao;
    public ManufacturerCategoryManager(ManufacturerCategoryDao dao) {
        super(dao);
        this.dao = dao;
    }
    public List<ManufacturerCategory> getByCategoryId(Long categoryId){
        return dao.getByCategoryId(categoryId);
    }
    public List<ManufacturerCategory> findAllByManufacturerId(Long manufacturerId){
        return dao.findAllByManufacturerId(manufacturerId);
    }

    public Boolean getCanSkipRawMaterialsForManufacturerAndCategory(Long manufacturerId, Long categoryId) {
        return dao.getCanSkipRawMaterialsForManufacturerAndCategory(manufacturerId, categoryId);
    }

    public String getActionNameByManufacturerIdAndCategoryId(Long manufacturerId, Long categoryId) {
        ManufacturerCategoryAction action = dao.getActionNameByManufacturerIdAndCategoryId(manufacturerId, categoryId);
        return (action != null) ? action.toString() : null;
    }


    public List<Long> filterManufacturersByCategory(Long categoryId, List<Long> manufacturerIds){
        return dao.filterManufacturersByCategory(categoryId, manufacturerIds);
    }

}
