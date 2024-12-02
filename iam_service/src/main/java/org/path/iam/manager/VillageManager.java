package org.path.iam.manager;

import org.path.iam.dao.VillageDao;
import org.path.iam.model.Village;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VillageManager extends BaseManager<Village, VillageDao>{
    private final VillageDao dao;
    public VillageManager(VillageDao dao) {
        super(dao);
        this.dao=dao;
    }
    public List<Village> findAllByDistrictId(Long districtId, Integer pageNumber, Integer pageSize){
        return dao.findAllByDistrictId(districtId, pageNumber, pageSize);
    }

    public Village findByDistrictNameAndVillageName(String villageName,String districtName){

        return dao.findByDistrictNameAndVillageName(villageName,districtName);

    }
    public Village findByNameAndDistrictId(String villageName, Long districtId){
        return dao.findByNameAndDistrictId(villageName, districtId);
    }

}
