package org.path.fortification.manager;

import org.path.fortification.dao.UOMDao;
import org.path.fortification.entity.UOM;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UOMManager extends BaseManager<UOM, UOMDao> {
    private final UOMDao uomDao;
    public UOMManager(UOMDao dao) {
        super(dao);
        this.uomDao = dao;
    }

    public List<UOM> findAllByIds(List<Long> ids) {
        return uomDao.findAllBIds(ids);
    }

    public UOM findByConversionFactor(Long conversionFactorToKg) {
        return uomDao.findByConversionFactor(conversionFactorToKg);
    }

    public UOM findByName(String name){
        return uomDao.findByName(name);
    }
}
