package org.path.lab.dao;

import org.path.lab.entity.CategoryDocumentRequirement;
import org.path.lab.enums.CategoryDocRequirementType;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class CategoryDocumentRequirementDao extends BaseDao<CategoryDocumentRequirement>{

    private final EntityManager em;
    public CategoryDocumentRequirementDao(EntityManager em) {
        super(em, CategoryDocumentRequirement.class);
        this.em = em;
    }

    public List<CategoryDocumentRequirement> findAllByCategoryIdAndType(Long categoryId, CategoryDocRequirementType docRequirementType, Integer pageNumber, Integer pageSize) {
        List<CategoryDocumentRequirement> obj = null;
        TypedQuery<CategoryDocumentRequirement> query = em
                .createQuery("from CategoryDocumentRequirement l where l.categoryId = :categoryId and l.categoryDocRequirementType = :docReqType and l.isEnabled is true", CategoryDocumentRequirement.class)
                .setParameter("categoryId", categoryId)
                .setParameter("docReqType", docRequirementType);
        if(pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        obj = query.getResultList();
        return obj;
    }

    public List<CategoryDocumentRequirement> findAll(CategoryDocRequirementType categoryDocRequirementType, Integer pageNumber, Integer pageSize) {
        List<CategoryDocumentRequirement> obj = null;
        TypedQuery<CategoryDocumentRequirement> query = em
                .createQuery("from CategoryDocumentRequirement l where l.categoryDocRequirementType = :categoryDocRequirementType", CategoryDocumentRequirement.class)
                .setParameter("categoryDocRequirementType", categoryDocRequirementType);
        if(pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        obj = query.getResultList();
        return obj;
    }

    public Long getCount(CategoryDocRequirementType categoryDocRequirementType) {
        return em.createQuery("SELECT count(c.id) from CategoryDocumentRequirement c where  c.categoryDocRequirementType = :categoryDocRequirementType", Long.class)
                .setParameter("categoryDocRequirementType", categoryDocRequirementType)
                .getSingleResult();
    }
}
