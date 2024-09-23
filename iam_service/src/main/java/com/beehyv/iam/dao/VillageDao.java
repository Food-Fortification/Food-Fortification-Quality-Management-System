package com.beehyv.iam.dao;

import com.beehyv.iam.model.State;
import com.beehyv.iam.model.Village;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

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

    public Village findByDistrictNameAndVillageName(String villageName,String districtName){

        List<Village> obj = null;

        TypedQuery<Village> query = em
                .createQuery("from Village d where d.district.name = :district_name and d.name =:village_name", Village.class)
                .setParameter("village_name", villageName)
                .setParameter("district_name", districtName);
        obj = query.getResultList();
        if(!obj.isEmpty())
            return obj.get(0);
        else {
            return null;
        }
    }

    public Village findByNameAndDistrictId(String villageName, Long districtId){
        try {
            return em.createQuery("from Village v where trim(lower(v.name)) = :villageName and v.district.id = :districtId", Village.class)
                    .setParameter("villageName",villageName)
                    .setParameter("districtId",districtId)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }
}
