package com.beehyv.fortification.dao;

import com.beehyv.fortification.entity.RoleCategory;
import com.beehyv.fortification.entity.RoleCategoryType;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component
public class RoleCategoryDao extends BaseDao<RoleCategory>{
    private final EntityManager em;
    public RoleCategoryDao(EntityManager em) {
        super(em, RoleCategory.class);
        this.em = em;
    }

    public List<RoleCategory> findAllByIdAndRoleCategoryType(Set<Long> roleCategoryIds, RoleCategoryType roleCategoryType) {
        return  em.createQuery("from RoleCategory r where r.id in :ids " +
                        " and r.roleCategoryType = :type  order by r.id desc", RoleCategory.class)
                .setParameter("ids", roleCategoryIds)
                .setParameter("type", roleCategoryType)
                .getResultList();
    }

    public List<RoleCategory> findByCategoryAndState(Long categoryId, Long stateId) {
        return em.createQuery("select r from RoleCategory r join r.roleCategoryStates s where s.category.id = :categoryId" +
                        " and s.state.id = :stateId  order by r.id desc", RoleCategory.class)
                .setParameter("categoryId", categoryId)
                .setParameter("stateId", stateId)
                .getResultList();
    }

    public List<RoleCategory> findByCategoryName(String categoryName, RoleCategoryType roleCategoryType) {
        try {
            return em.createQuery("from RoleCategory r  where r.category.name = :categoryName" +
                            " and r.roleCategoryType = :type order by r.id desc", RoleCategory.class)
                    .setParameter("categoryName", categoryName)
                    .setParameter("type", roleCategoryType)
                    .getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    public List<RoleCategory> findAllByIds(List<Long> ids) {
        return em.createQuery("from RoleCategory r where r.id in :ids  order by r.id desc")
                .setParameter("ids", ids)
                .getResultList();
    }

    public RoleCategory findByRoleAndCategoryNames(String roleName, String categoryName, RoleCategoryType type) {
        try {
            return em.createQuery("from RoleCategory r where r.category.name=:categoryName and r.roleName=:roleName and r.roleCategoryType =:type", RoleCategory.class)
                    .setParameter("roleName", roleName)
                    .setParameter("categoryName", categoryName)
                    .setParameter("type", type)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }
    public List<RoleCategory> findListByRoleAndCategoryNames(String roleName, String categoryName, RoleCategoryType type) {
        try {
            return em.createQuery("from RoleCategory r where r.category.name=:categoryName and r.roleName=:roleName and r.roleCategoryType =:type", RoleCategory.class)
                    .setParameter("roleName", roleName)
                    .setParameter("categoryName", categoryName)
                    .setParameter("type", type)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }

    }

    public List<RoleCategory> findByCategoryIdAndType(Long categoryId, RoleCategoryType roleCategoryType) {
        try {
            return em.createQuery("from RoleCategory r  where r.category.id = :categoryId" +
                            " and r.roleCategoryType = :type order by r.id desc", RoleCategory.class)
                    .setParameter("categoryId", categoryId)
                    .setParameter("type", roleCategoryType)
                    .getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }
}