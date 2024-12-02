package org.path.fortification.dao;

import org.path.fortification.entity.BatchNoId;
import org.path.fortification.entity.BatchNoSequence;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

@Component
public class BatchNoSequenceDao extends BaseDao<BatchNoSequence>{
    private final EntityManager em;
    public BatchNoSequenceDao(EntityManager em) {
        super(em, BatchNoSequence.class);
        this.em = em;
    }

    @Transactional
    public BatchNoSequence findById(BatchNoId batchNoId) {
        BatchNoSequence batchNoSequence;
        try {
            batchNoSequence = em.createQuery("FROM BatchNoSequence bns where bns.batchNoId =: batchNoId", BatchNoSequence.class)
                    .setParameter("batchNoId", batchNoId).getSingleResult();
        } catch (NoResultException noResultException) {
            batchNoSequence = new BatchNoSequence(batchNoId, 1);
            em.persist(batchNoSequence);
        }
        return batchNoSequence;
    }
}