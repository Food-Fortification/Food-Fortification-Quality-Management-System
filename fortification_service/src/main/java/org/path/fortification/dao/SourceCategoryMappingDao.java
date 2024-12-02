package org.path.fortification.dao;

import org.path.fortification.entity.SourceCategoryMapping;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class SourceCategoryMappingDao extends BaseDao<SourceCategoryMapping>{
    private final EntityManager em;
    public SourceCategoryMappingDao(EntityManager em) {
        super(em, SourceCategoryMapping.class);
        this.em = em;
    }

    public List<SourceCategoryMapping> findByIds(Long returnId, Long sourceId, Long targetId) {
        return  em.createQuery("from SourceCategoryMapping s where s.targetCategory.id = :targetId and " +
                        "s.sourceCategory.id = :sourceId and s.returnCategory.id = :returnId ", SourceCategoryMapping.class)
                .setParameter("returnId", returnId)
                .setParameter("sourceId", sourceId)
                .setParameter("targetId", targetId)
                .getResultList();
    }

    public List<SourceCategoryMapping> findBySourceId(Long sourceId) {
        return  em.createQuery("from SourceCategoryMapping s where  " +
                        "s.sourceCategory.id = :sourceId  ", SourceCategoryMapping.class)
                .setParameter("sourceId", sourceId)
                .getResultList();
    }
}