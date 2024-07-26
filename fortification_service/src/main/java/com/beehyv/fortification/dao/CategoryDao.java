package com.beehyv.fortification.dao;

import com.beehyv.fortification.entity.Category;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CategoryDao extends BaseDao<Category>{
    private final EntityManager em;
    public CategoryDao(EntityManager em) {
        super(em, Category.class);
        this.em = em;
    }

    public List<Category> getCategoryBySourceCategoryId(Long categoryId, Long stateGeoId) {
        List<Category> targetCategories = new ArrayList<>();
        try{
            targetCategories = em.createQuery("SELECT c.targetCategory from SourceCategoryMapping  c where c.sourceCategory.id = :cid and c.stateGeoId = :geoId order by c.id desc", Category.class)
                    .setParameter("cid", categoryId)
                    .setParameter("geoId", stateGeoId)
                    .getResultList();
        } catch (NoResultException ignored) {

        }
        if (targetCategories.isEmpty()) {
            try {
                targetCategories = em.createQuery("SELECT c.targetCategory from SourceCategoryMapping  c where c.sourceCategory.id = :cid and c.stateGeoId is null order by c.id desc", Category.class)
                        .setParameter("cid", categoryId)
                        .getResultList();
            } catch (Exception ignored) {

            }
        }
        return targetCategories;
    }

    public List<Category> findAllByIds(List<Long> ids) {
        try {
            return em
                    .createQuery("from Category where id in :ids  order by id asc", Category.class)
                    .setParameter("ids", ids)
                    .getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    public List<Category> findAllByIndependentBatch(Boolean independentBatch, Integer pageNumber, Integer pageSize) {
        TypedQuery<Category> query = em.createQuery("from  Category c where (:independentBatch is null or c.independentBatch = :independentBatch) order by c.id desc", Category.class);
        query.setParameter("independentBatch", null);
        if (independentBatch != null) {
            query.setParameter("independentBatch", independentBatch);
        }
        if (pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber-1) * pageSize);
            query.setMaxResults(pageSize);
        }
        return query.getResultList();
    }

    public Long getCountByIndependentBatch(Boolean independentBatch) {
        return em.createQuery("SELECT count (c.id) from Category c " +
                        "where (:independentBatch is null or c.independentBatch = :independentBatch)",
                        Long.class)
                .setParameter("independentBatch", independentBatch)
                .getSingleResult();
    }

    public List<Category> findAllByNames(List<String> categoryNames) {
        try {
            return em
                    .createQuery("from Category where name in :categoryNames  order by id asc", Category.class)
                    .setParameter("categoryNames", categoryNames)
                    .getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    public List<Category> getSourceCategory(Long targetCategoryId, Long geoId) {
        List<Category> sourceCategory = new ArrayList<>();
        try {
            sourceCategory = em.createQuery("SELECT c.returnCategory from SourceCategoryMapping c  where c.targetCategory.id = :cid " +
                            "and c.stateGeoId = :geoId and c.sourceCategory.independentBatch is false order by c.id desc", Category.class)
                    .setParameter("cid", targetCategoryId)
                    .setParameter("geoId", geoId)
                    .getResultList();
        } catch (NoResultException ignored) {
        }
        if (sourceCategory.isEmpty()) {
            try {
                sourceCategory = em.createQuery("SELECT c.sourceCategory from SourceCategoryMapping c  where c.targetCategory.id = :cid " +
                                "and c.stateGeoId is null and c.sourceCategory.independentBatch is false order by c.id desc", Category.class)
                        .setParameter("cid", targetCategoryId)
                        .getResultList();
            } catch (NoResultException ignored) {

            }
        }
        return sourceCategory;
    }

    public Long findCategoryIdByName(String categoryName){
         return em.createQuery("SELECT c.id from Category  c where c.name = :name", Long.class)
            .setParameter("name", categoryName).getSingleResult();
    }

    public String findCategoryNameById(Long categoryId){
        return em.createQuery("SELECT c.name from Category  c where c.id = :id", String.class)
                .setParameter("id", categoryId).getSingleResult();
    }

    public Category findCategoryByName(String categoryName){
        return em.createQuery("SELECT c from Category  c where c.name = :name", Category.class)
                .setParameter("name", categoryName).getSingleResult();
    }

    public Map<Long, String> getCategoryAndActionBySourceCategoryId(Long categoryId, Long stateGeoId) {
        List<Object[]> targetCategories = new ArrayList<>();
        try {
            targetCategories = em.createQuery("SELECT c.targetCategory.id, c.categoryAction from SourceCategoryMapping c where c.sourceCategory.id = :cid and c.stateGeoId = :geoId order by c.id desc", Object[].class)
                    .setParameter("cid", categoryId)
                    .setParameter("geoId", stateGeoId)
                    .getResultList();
        } catch (NoResultException ignored) {
        }

        if (targetCategories.isEmpty()) {
            try {
                targetCategories = em.createQuery("SELECT c.targetCategory.id, c.categoryAction from SourceCategoryMapping c where c.sourceCategory.id = :cid and c.stateGeoId is null and is_deleted=0 order by c.id desc", Object[].class)
                        .setParameter("cid", categoryId)
                        .getResultList();
            } catch (Exception ignored) {
            }
        }
        Map<Long, String> result = new HashMap<>();
        for (Object[] entry : targetCategories) {
            Long targetCategoryId = (Long) entry[0];
            String categoryAction = entry[1] != null ? entry[1].toString() : null;
            result.put(targetCategoryId, categoryAction);
        }
        return result;
    }

    public List<Category> findCategoryListByName(String categoryName){
        return em.createQuery("SELECT c from Category  c where c.name = :name", Category.class)
                .setParameter("name", categoryName).getResultList();
    }

    public Integer findMaxSequence(){
        return em.createQuery("select max(c.sequence) FROM Category c", Integer.class)
                .getSingleResult();
    }


}