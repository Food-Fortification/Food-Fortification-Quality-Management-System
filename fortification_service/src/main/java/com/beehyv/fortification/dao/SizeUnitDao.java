package com.beehyv.fortification.dao;

import com.beehyv.fortification.entity.SizeUnit;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class SizeUnitDao extends BaseDao<SizeUnit>{
    private final EntityManager em;
    public SizeUnitDao(EntityManager em) {
        super(em, SizeUnit.class);
        this.em = em;
    }

    public Long getCount(Long batchId) {
        return (Long) em.createQuery("select count (b.id) from SizeUnit as b where b.batch.id = :batchId")
                .setParameter("batchId", batchId)
                .getSingleResult();
    }

    public List<SizeUnit> findAllByBatchId(Long batchId, Integer pageNumber, Integer pageSize) {
        List<SizeUnit> obj = null;
        TypedQuery<SizeUnit> query = em
                .createQuery("from SizeUnit b where b.batch.id = :batchId  order by b.id asc", SizeUnit.class)
                .setParameter("batchId", batchId);
        if(pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber-1) * pageSize);
            query.setMaxResults(pageSize);
        }
        obj = query.getResultList();
        return obj;
    }
}