package org.path.fortification.dao;

import org.path.fortification.entity.MixMapping;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class MixMappingDao extends BaseDao<MixMapping>{
    private final EntityManager em;
    public MixMappingDao(EntityManager em) {
        super(em, MixMapping.class);
        this.em = em;
    }

    public Long getCountByTargetBatchId(Long targetBatchId) {
        return (Long) em.createQuery("select count (b.id) from MixMapping as b where b.targetBatch.id = :batchId")
                .setParameter("batchId", targetBatchId)
                .getSingleResult();
    }

    public Long getCountBySourceLotId(Long sourceLotId) {
        return (Long) em.createQuery("select count (b.id) from MixMapping as b where b.sourceLot.id = :sourceLotId")
                .setParameter("sourceLotId", sourceLotId)
                .getSingleResult();
    }

    public List<MixMapping> findAllByTargetBatchId(Long targetBatchId, Integer pageNumber, Integer pageSize) {
        List<MixMapping> obj;
        TypedQuery<MixMapping> query = em
                .createQuery("from MixMapping b where b.targetBatch.id = :batchId  order by b.id asc", MixMapping.class)
                .setParameter("batchId", targetBatchId);
        if(pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber-1) * pageSize);
            query.setMaxResults(pageSize);
        }
        obj = query.getResultList();
        return obj;
    }

    public List<MixMapping> findAllByTargetBatchIds(List<Long> targetBatchId) {
        List<MixMapping> obj;
        TypedQuery<MixMapping> query = em
                .createQuery("from MixMapping b where b.targetBatch.id in :batchId  order by b.id desc", MixMapping.class)
                .setParameter("batchId", targetBatchId);
        obj = query.getResultList();
        return obj;
    }

    public List<MixMapping> findAllBySourceLotId(Long sourceLotId, Integer pageNumber, Integer pageSize) {
        List<MixMapping> obj;
        TypedQuery<MixMapping> query = em
                .createQuery("from MixMapping b where b.sourceLot.id = :sourceLotId  order by b.id desc", MixMapping.class)
                .setParameter("sourceLotId", sourceLotId);
        if(pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber-1) * pageSize);
            query.setMaxResults(pageSize);
        }
        obj = query.getResultList();
        return obj;
    }
    public List<MixMapping> findAllByIds(List<Long> ids){
        return em.createQuery("from MixMapping m where m.id in :ids", MixMapping.class)
                .setParameter("ids",ids)
                .getResultList();
    }
}