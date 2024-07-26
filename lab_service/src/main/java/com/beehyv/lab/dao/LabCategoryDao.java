package com.beehyv.lab.dao;

import com.beehyv.lab.entity.LabCategory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class LabCategoryDao extends BaseDao<LabCategory>{
    private final EntityManager em;

    public LabCategoryDao(EntityManager em) {
        super(em, LabCategory.class);
        this.em = em;
    }

    public List<LabCategory> findAllByLabId(Long labId) {
        List<LabCategory> obj = null;
        String hql = "FROM LabCategory b " +
                "WHERE b.lab.id = :labId and b.isEnabled is true and b.isDeleted is false";
        TypedQuery<LabCategory> query = em.createQuery(hql, LabCategory.class).setParameter("labId",labId);

        obj = query.getResultList();
        return obj;
    }
    public List<LabCategory> findByCountryAndState(String country, String state, Long categoryId){
        List<LabCategory> obj = null;
        String hql = "FROM LabCategory l " +
                "WHERE l.lab.address.village.district.state.name = :state " +
                "AND l.categoryId = :categoryId " +
                "AND l.lab.status.name = :status";
        TypedQuery<LabCategory> query = em.createQuery(hql, LabCategory.class)
                .setParameter("state",state)
                .setParameter("categoryId",categoryId)
                .setParameter("status","Active");
        obj = query.getResultList();
        if (obj.isEmpty()){
            hql = "FROM LabCategory l " +
                    "WHERE l.lab.address.village.district.state.country.name = :country " +
                    "AND l.categoryId = :categoryId " +
                    "And l.lab.status.name = :status";
            query = em.createQuery(hql, LabCategory.class)
                    .setParameter("country",country)
                    .setParameter("categoryId",categoryId)
                    .setParameter("status","Active");
            obj = query.getResultList();
            return obj;
        }
        return obj;
    }
}
