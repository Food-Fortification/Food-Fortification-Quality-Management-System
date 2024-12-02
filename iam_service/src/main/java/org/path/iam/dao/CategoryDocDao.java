package org.path.iam.dao;

import org.path.iam.model.CategoryDoc;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class CategoryDocDao extends BaseDao<CategoryDoc>{
    private final EntityManager em;
    public CategoryDocDao(EntityManager em) {
        super(em, CategoryDoc.class);
        this.em = em;
    }

    public Long getCount(Long categoryId) {
        return (Long) em.createQuery("select count (b.id) from CategoryDoc as b where b.categoryId = :categoryId and b.isEnabled is true")
                .setParameter("categoryId", categoryId)
                .getSingleResult();
    }

    public List<CategoryDoc> findAllByCategoryId(Long categoryId, Integer pageNumber, Integer pageSize) {
        List<CategoryDoc> obj = null;
        TypedQuery<CategoryDoc> query = em
                .createQuery("from CategoryDoc b where b.categoryId = :categoryId and b.isEnabled is true", CategoryDoc.class)
                .setParameter("categoryId", categoryId);
        if(pageSize != null && pageNumber != null) {
            query.setFirstResult(pageNumber*pageSize);
            query.setMaxResults(pageSize);
        }
        obj = query.getResultList();
        return obj;
    }
}
