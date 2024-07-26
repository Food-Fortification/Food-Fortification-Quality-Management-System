package com.beehyv.fortification.dao;


import com.beehyv.fortification.entity.UOM;
import com.beehyv.parent.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Component
public class UOMDao extends BaseDao<UOM>{
    private final EntityManager em;

    public UOMDao(EntityManager em) {
        super(em, UOM.class);
        this.em = em;
    }

    public List<UOM> findAllBIds(List<Long> uomIds) {
        try {
            return em
                    .createQuery("from UOM where id in :uomIds  order by id asc", UOM.class)
                    .setParameter("uomIds", uomIds).getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<UOM> findAll(Integer pageNumber, Integer pageSize) {
        List<UOM> obj = null;
        TypedQuery<UOM> query = em.createQuery("from UOM u order by u.conversionFactorKg asc", UOM.class);
        if(pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber-1) * pageSize);
            query.setMaxResults(pageSize);
        }
        obj = query.getResultList();
        return obj;
    }

    public UOM findByConversionFactor(Long conversionFactorToKg) {
        try {
            return em.createQuery("from UOM where conversionFactorKg = :factor", UOM.class)
                    .setParameter("factor", conversionFactorToKg)
                    .getSingleResult();
        } catch (NoResultException exception) {
            throw new ResourceNotFoundException("UOM", "conversionFactorToKg", conversionFactorToKg);
        }
    }

    public UOM findByName(String name){
        return em.createQuery("from UOM where name = :name", UOM.class)
            .setParameter("name", name)
            .getSingleResult();
    }
}