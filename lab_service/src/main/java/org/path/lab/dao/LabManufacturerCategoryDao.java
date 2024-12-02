package org.path.lab.dao;

import org.path.lab.entity.LabManufacturerCategoryMapping;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class LabManufacturerCategoryDao extends BaseDao<LabManufacturerCategoryMapping>{

    private final EntityManager em;

    public LabManufacturerCategoryDao(EntityManager em) {
        super(em, LabManufacturerCategoryMapping.class);
        this.em = em;
    }





    public List<LabManufacturerCategoryMapping> findAllLabsByManufacturerIdAndCategoryId(String search, Long manufacturerId, Long categoryId, Integer pageNumber, Integer pageSize) {
        List<LabManufacturerCategoryMapping> result = new ArrayList<>();
        try {
            TypedQuery<LabManufacturerCategoryMapping> query = em.createQuery(
                            "FROM LabManufacturerCategoryMapping l " +
                                    "where l.manufacturerId = :manufacturerId AND (:search is null or l.lab.name like :search) " +
                                    "AND (:categoryId is null or l.categoryId = :categoryId)",
                            LabManufacturerCategoryMapping.class
                    )
                    .setParameter("manufacturerId", manufacturerId)
                    .setParameter("categoryId",categoryId);
            query.setParameter("search",null);
            if (search != null && !Objects.equals(search, "")) {
                query.setParameter("search", "%" + search + "%");
            }
            if(pageSize != null && pageNumber != null) {
                query.setFirstResult((pageNumber - 1) * pageSize);
                query.setMaxResults(pageSize);
            }
            result = query.getResultList();
            return result;
        }catch (NoResultException e){
            return result;
        }
    }

    public Long getCountForLabsByManufacturerIdAndCategoryId(String search,Long manufacturerId, Long categoryId) {
        String hql = "select count(l.id) FROM LabManufacturerCategoryMapping l " +
                "where l.manufacturerId = :manufacturerId  AND (:search is null or l.lab.name like :search) " +
                "AND (:categoryId is null or l.categoryId = :categoryId)";
        TypedQuery<Long> query = em.createQuery(
                        hql ,
                        Long.class
                )
                .setParameter("manufacturerId", manufacturerId)
                .setParameter("categoryId", categoryId);
        query.setParameter("search",null);
        if (search != null && !Objects.equals(search, "")) {
            query.setParameter("search", "%" + search + "%");
        }
        return query.getSingleResult();
    }
}
