package com.beehyv.lab.dao;


import com.beehyv.lab.entity.Country;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;

@Component
public class CountryDao extends BaseDao<Country>{
    private final EntityManager em;

    public CountryDao(EntityManager em) {
        super(em, Country.class);
        this.em=em;
    }

    public List<Country> findAll(String search, Integer pageNumber, Integer pageSize) {
        String hql = "select c from Country c where (:search is null or c.name like :search)";
        TypedQuery<Country> query = em.createQuery(hql, Country.class);
        query.setParameter("search",null);
        if (search != null && !Objects.equals(search, "")) {
            query.setParameter("search", "%" + search + "%");
        }
        if (pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber-1)*pageSize);
            query.setMaxResults(pageSize);
        }
        return query.getResultList();
    }
    public Long getCount(String search){
        String hql = "select count(c.id) from Country c where (:search is null or c.name like :search)";
        TypedQuery<Long> query = em.createQuery(hql, Long.class);
        query.setParameter("search",null);
        if (search != null && !Objects.equals(search, "")) {
            query.setParameter("search", "%" + search + "%");
        }
        return query.getSingleResult();
    }
}
