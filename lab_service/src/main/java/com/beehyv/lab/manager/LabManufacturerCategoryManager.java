package com.beehyv.lab.manager;

import com.beehyv.lab.dao.LabManufacturerCategoryDao;
import com.beehyv.lab.entity.LabManufacturerCategoryMapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LabManufacturerCategoryManager extends BaseManager<LabManufacturerCategoryMapping, LabManufacturerCategoryDao>{
    public LabManufacturerCategoryManager(LabManufacturerCategoryDao dao) {
        super(dao);
        this.labManufacturerCategoryDao = dao;
    }
    private final LabManufacturerCategoryDao labManufacturerCategoryDao;

    public List<LabManufacturerCategoryMapping> findAllLabsByManufacturerIdAndCategoryId(String search, Long manufacturerId, Long categoryId, Integer pageNumber,Integer pageSize){
        return  labManufacturerCategoryDao.findAllLabsByManufacturerIdAndCategoryId(search, manufacturerId, categoryId, pageNumber,pageSize);
    }

    public Long getCountForLabsByManufacturerIdAndCategoryId(String search,Long manufacturerId, Long categoryId){
        return  labManufacturerCategoryDao.getCountForLabsByManufacturerIdAndCategoryId(search,manufacturerId, categoryId);
    }


}
