package org.path.fortification.dao;

import org.path.fortification.entity.Wastage;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class WastageDao extends BaseDao<Wastage> {
    private final EntityManager em;
    public WastageDao(EntityManager em) {
        super(em, Wastage.class);
        this.em = em;
    }
    public Long getCountByBatchId(Long batchId) {
        return (Long) em.createQuery("select count (b.id) from Wastage as b where b.batch.id = :batchId order by b.id desc ")
                .setParameter("batchId", batchId)
                .getSingleResult();
    }

    public Long getCountByLotId(Long lotId) {
        return (Long) em.createQuery("select count (b.id) from Wastage as b where b.lot.id = :lotId order by b.id desc ")
                .setParameter("lotId", lotId)
                .getSingleResult();
    }


    public List<Wastage> findAllByBatchId(Long batchId, Integer pageNumber, Integer pageSize) {
        List<Wastage> obj = null;
        TypedQuery<Wastage> query = em
                .createQuery("from Wastage b where b.batch.id = :batchId", Wastage.class)
                .setParameter("batchId", batchId);
        if(pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber-1) * pageSize);
            query.setMaxResults(pageSize);
        }
        obj = query.getResultList();
        return obj;
    }

    public List<Wastage> findAllByLotId(Long lotId, Integer pageNumber, Integer pageSize) {
        List<Wastage> obj = null;
        TypedQuery<Wastage> query = em
                .createQuery("from Wastage b where b.lot.id = :lotId", Wastage.class)
                .setParameter("lotId", lotId);
        if(pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber-1) * pageSize);
            query.setMaxResults(pageSize);
        }
        obj = query.getResultList();
        return obj;
    }
}
