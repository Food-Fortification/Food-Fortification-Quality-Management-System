package org.path.fortification.dao;

import org.path.fortification.entity.LabConfigCategory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;

@Component
public class LabConfigCategoryDao extends BaseDao<LabConfigCategory> {
    private final EntityManager em;

    public LabConfigCategoryDao(EntityManager em) {
        super(em, LabConfigCategory.class);
        this.em = em;
    }


    public LabConfigCategory findByCategoryId(Long categoryId) {
        try {
            return em.createQuery("from LabConfigCategory r where r.category.id=:categoryId", LabConfigCategory.class)
                    .setParameter("categoryId", categoryId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<LabConfigCategory> findByCategoryIds(Long sourceCategoryId, Long targetCategoryId) {
        try {
            return em.createQuery("from LabConfigCategory r where r.sourceCategory.id=:sourceCategoryId and " +
                            "r.targetCategory.id = :targetCategoryId", LabConfigCategory.class)
                    .setParameter("sourceCategoryId", sourceCategoryId)
                    .setParameter("targetCategoryId", targetCategoryId)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}