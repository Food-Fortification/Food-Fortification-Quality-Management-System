package org.path.lab.dao;

import org.path.lab.entity.District;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class DistrictDao extends BaseDao<District>{

    private final EntityManager em;
    public DistrictDao(EntityManager em) {
        super(em, District.class);
        this.em = em;
    }
    public List<District> findAllByStateId(Long stateId, String search, Integer pageNumber, Integer pageSize) {
        List<District> obj = null;
        TypedQuery<District> query = em
            .createQuery("from District d where d.state.id = :stateId AND (:search is null or d.name like :search)", District.class)
            .setParameter("stateId", stateId);
        query.setParameter("search",null);
        if (search != null && !Objects.equals(search, "")) {
            query.setParameter("search", "%" + search + "%");
        }
        if(pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber-1)*pageSize);
            query.setMaxResults(pageSize);
        }
        obj = query.getResultList();
        return obj;
    }

    public Long getCountByStateId(Long stateId, String search) {
        TypedQuery<Long> query = em
            .createQuery("select count(d.id) from District d where d.state.id = :stateId AND (:search is null or d.name like :search)", Long.class)
            .setParameter("stateId", stateId);
        query.setParameter("search",null);
        if (search != null && !Objects.equals(search, "")) {
            query.setParameter("search", "%" + search + "%");
        }
        return  query.getSingleResult();
    }

    public List<District> findByStateGeoId(String geoId) {
        try {
            return em.createQuery("from District d where d.state.geoId = :geoId", District.class)
                .setParameter("geoId",geoId)
                .getResultList();
        } catch (NoResultException e){
            return new ArrayList<>();
        }
    }

    public District findByStateNameAndDistrictName(String districtName,String stateName) {
        return em.createQuery("from District d where d.state.name = :stateName and d.name =:districtName", District.class)
            .setParameter("districtName",districtName)
            .setParameter("stateName",stateName)
            .getSingleResult();
    }
}