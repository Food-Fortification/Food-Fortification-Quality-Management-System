package org.path.iam.manager;

import org.path.iam.dao.DistrictDao;
import org.path.iam.model.District;
import org.springframework.stereotype.Component;

import java.util.List;
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

    public List<District> findAllByStateGeoId(String stateGeoId){
        return dao.findAllByStateGeoId(stateGeoId);
    }

    public List<District> findAllByGeoIds(List<String> geoIds){
        return dao.findAllByGeoIds(geoIds);
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

    public District findByNameAndStateId(String districtName, Long stateId){
        return dao.findByNameAndStateId(districtName, stateId);
    }

    public District findByName(String districtName){
        return dao.findByName(districtName);
    }

}
