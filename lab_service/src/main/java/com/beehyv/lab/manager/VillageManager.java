package com.beehyv.lab.manager;

import com.beehyv.lab.dao.VillageDao;
import com.beehyv.lab.entity.Village;
import java.util.List;
import org.springframework.stereotype.Component;

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

    public Long getCount(Long districtId){
        return dao.getCount(districtId);
    }

    public Village findByDistrictNameAndVillageName(String villageName,String districtName){

        return dao.findByDistrictNameAndVillageName(villageName,districtName);

    }
}
