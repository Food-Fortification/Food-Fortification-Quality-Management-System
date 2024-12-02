package org.path.lab.dao;

import org.path.lab.entity.LabTest;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.*;

@Component
public class LabTestDao extends BaseDao<LabTest>{

    private final EntityManager em;

    public LabTestDao(EntityManager em) {
        super(em, LabTest.class);
        this.em = em;
    }

    public List<LabTest> findLabTestByBatchId(Long batchId,Integer pageNumber, Integer pageSize) {
        List<LabTest> obj;
        TypedQuery<LabTest> query = em
                .createQuery("from LabTest l where l.labSample.batchId = :batchId", LabTest.class)
                .setParameter("batchId", batchId);
        if(pageSize != null && pageNumber != null) {
            query.setFirstResult(pageNumber * pageSize);
            query.setMaxResults(pageSize);
        }
        obj = query.getResultList();
        return obj;
    }
}
