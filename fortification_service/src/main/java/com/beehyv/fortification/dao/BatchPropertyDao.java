package com.beehyv.fortification.dao;

import com.beehyv.fortification.entity.Batch;
import com.beehyv.fortification.entity.BatchProperty;
import com.beehyv.parent.exceptions.CustomException;
import com.beehyv.parent.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class BatchPropertyDao extends BaseDao<BatchProperty> {
    @PersistenceContext
    private EntityManager em;
    public BatchPropertyDao(EntityManager em) {
        super(em, BatchProperty.class);
    }
    public List<Batch> searchBatches(String search){
        String hql = "SELECT Distinct(bp.batch) FROM BatchProperty as bp " +
                "WHERE ((bp.name = :property AND bp.value like :search) OR " +
                "bp.batch.batchNo like :search) " +
                "AND bp.batch.category.independentBatch = :independentBatch";
        TypedQuery<Batch> query = em.createQuery(hql, Batch.class)
                .setParameter("search", "%" + search + "%")
                .setParameter("independentBatch",false)
                .setParameter("property", "manufacture_batchNumber");
        List<Batch> result = null;
        try {
            result = query.getResultList();
        } catch (NoResultException exception) {
            throw new CustomException("", HttpStatus.BAD_REQUEST);
        }
        return result;
    }
    public Long getCountOfSearchedBatches(String search){
        String hql = "SELECT count(Distinct(bp.batch)) FROM BatchProperty as bp " +
                "WHERE (bp.name = :property AND bp.value like :search) OR " +
                "bp.batch.batchNo like :search";
        TypedQuery<Long> query = em.createQuery(hql, Long.class)
                .setParameter("search", "%" + search + "%")
                .setParameter("property", "manufacture_batchNumber");
        return query.getSingleResult();
    }
}