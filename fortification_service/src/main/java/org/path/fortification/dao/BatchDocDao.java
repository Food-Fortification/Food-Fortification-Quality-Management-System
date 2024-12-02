package org.path.fortification.dao;

import org.path.fortification.entity.BatchDoc;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class BatchDocDao extends BaseDao<BatchDoc>{
    private final EntityManager em;
    public BatchDocDao(EntityManager em) {
        super(em, BatchDoc.class);
        this.em = em;
    }

    public List<BatchDoc> findAllByBatchId(Long batchId, Integer pageNumber, Integer pageSize) {
        List<BatchDoc> obj = null;
        TypedQuery<BatchDoc> query = em
                .createQuery("from BatchDoc b where b.batch.id = :batchId order by b.id desc", BatchDoc.class)
                .setParameter("batchId", batchId);
        if(pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber-1) * pageSize);
            query.setMaxResults(pageSize);
        }
        obj = query.getResultList();
        return obj;
    }
}