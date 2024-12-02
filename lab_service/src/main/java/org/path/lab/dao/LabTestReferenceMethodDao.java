package org.path.lab.dao;

import org.path.lab.entity.LabTestReferenceMethod;
import org.path.lab.entity.LabTestType;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class LabTestReferenceMethodDao extends BaseDao<LabTestReferenceMethod>{
    private final EntityManager em;

    public LabTestReferenceMethodDao(EntityManager em) {
        super(em, LabTestReferenceMethod.class);
        this.em = em;
    }

    public List<LabTestReferenceMethod> findAllByTestTypeId(Long testTypeId, Integer pageNumber, Integer pageSize) {
        List<LabTestReferenceMethod> obj = null;
        TypedQuery<LabTestReferenceMethod> query = em
                .createQuery("from LabTestReferenceMethod l where l.labTestType.id = :testTypeId", LabTestReferenceMethod.class)
                .setParameter("testTypeId", testTypeId);
        if(pageSize != null && pageNumber != null) {
            query.setFirstResult(pageNumber * pageSize);
            query.setMaxResults(pageSize);
        }
        obj = query.getResultList();
        return obj;
    }
    public List<LabTestReferenceMethod> findAllByIds(List<Long>ids) {
        List<LabTestReferenceMethod> obj = null;
        TypedQuery<LabTestReferenceMethod> query = em
                .createQuery("from LabTestReferenceMethod l where l.id in (:ids)", LabTestReferenceMethod.class)
                .setParameter("ids", ids);
        obj = query.getResultList();
        return obj;
    }

    public List<LabTestReferenceMethod> findAllByCategoryId(Long categoryId, Integer pageNumber, Integer pageSize , LabTestType.Type type) {
        List<LabTestReferenceMethod> obj = null;
        TypedQuery<LabTestReferenceMethod> query = em
                .createQuery("from LabTestReferenceMethod l where l.labTestType.categoryId = :categoryId and l.labTestType.geoId is null and (:typeIsNull is true or l.labTestType.type = :type)", LabTestReferenceMethod.class)
                .setParameter("categoryId", categoryId)
                .setParameter("type",type);

        if(type == null) {
            query.setParameter("typeIsNull", true);
        } else{
            query.setParameter("typeIsNull",false);
        }

        if(pageSize != null && pageNumber != null) {
            query.setFirstResult(pageNumber * pageSize);
            query.setMaxResults(pageSize);
        }
        obj = query.getResultList();
        return obj;
    }

    public List<LabTestReferenceMethod> findAllByCategoryIdAndGeoId(Long categoryId, Integer pageNumber, Integer pageSize , LabTestType.Type type, String geoId) {
        List<LabTestReferenceMethod> obj = null;
        TypedQuery<LabTestReferenceMethod> query = em
                .createQuery("from LabTestReferenceMethod l where l.labTestType.categoryId = :categoryId and l.labTestType.geoId = :geoId and (:typeIsNull is true or l.labTestType.type = :type)", LabTestReferenceMethod.class)
                .setParameter("categoryId", categoryId)
                .setParameter("geoId", geoId)
                .setParameter("type",type);

        if(type == null) {
            query.setParameter("typeIsNull", true);
        } else{
            query.setParameter("typeIsNull",false);
        }

        if(pageSize != null && pageNumber != null) {
            query.setFirstResult(pageNumber * pageSize);
            query.setMaxResults(pageSize);
        }
        obj = query.getResultList();
        return obj;
    }

}
