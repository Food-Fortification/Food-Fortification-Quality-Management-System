package com.beehyv.lab.dao;

import com.beehyv.lab.entity.Village;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class VillageDao extends BaseDao<Village>{

    private final EntityManager em;
    public VillageDao(EntityManager em) {
        super(em, Village.class);
        this.em = em;
    }
    public List<Village> findAllByDistrictId(Long districtId, Integer pageNumber, Integer pageSize) {
        List<Village> obj = null;
        TypedQuery<Village> query = em
            .createQuery("from Village d where d.district.id = :districtId", Village.class)
            .setParameter("districtId", districtId);
        if(pageSize != null && pageNumber != null) {
            query.setFirstResult(pageNumber*pageSize);
            query.setMaxResults(pageSize);
        }
        obj = query.getResultList();
        return obj;
    }

    public Long getCount(Long districtId) {
        List<Village> obj = null;
        TypedQuery<Long> query = em
            .createQuery("select count (d.id) from Village d where d.district.id = :districtId",
                Long.class)
            .setParameter("districtId", districtId);
        return query.getSingleResult();
    }

    public Village findByDistrictNameAndVillageName(String villageName,String districtName){

        Village obj = null;
        try {
            TypedQuery<Village> query = em
                .createQuery("from Village d where d.district.name = :district_name and d.name =:village_name", Village.class)
                .setParameter("village_name", villageName)
                .setParameter("district_name", districtName);
            obj = query.getSingleResult();
            return obj;
        }
        catch (NoResultException e) {
            return null;
        }
    }
}
