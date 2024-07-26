package com.beehyv.lab.manager;

import com.beehyv.lab.dao.DistrictDao;
import com.beehyv.lab.entity.District;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DistrictManager extends BaseManager<District, DistrictDao>{
    private final DistrictDao dao;
    public DistrictManager(DistrictDao dao) {
        super(dao);
        this.dao=dao;
    }
    public List<District> findAllByStateId(Long stateId, String search, Integer pageNumber, Integer pageSize){
        return dao.findAllByStateId(stateId, search, pageNumber, pageSize);
    }

    public Long getCountByStateId(Long stateId, String search) {
        return dao.getCountByStateId(stateId, search);
    }

    public List<District> findByStateGeoId(String geoId) {
        return dao.findByStateGeoId(geoId);
    }

    public District findByStateNameAndDistrictName(String districtName,String stateName){
        return dao.findByStateNameAndDistrictName(districtName,stateName);
    }
}
