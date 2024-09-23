package com.beehyv.iam.dao;

import com.beehyv.iam.dto.requestDto.SearchListRequest;
import com.beehyv.iam.enums.UserType;
import com.beehyv.iam.model.UserRoleCategory;
import com.beehyv.parent.exceptions.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.*;

@Component
public class UserRoleCategoryDao extends BaseDao<UserRoleCategory> {
    private final EntityManager em;
    public UserRoleCategoryDao(EntityManager em) {
        super(em, UserRoleCategory.class);
        this.em =em;
    }
    public List<UserRoleCategory> findAllUsersBySearchAndFilter(SearchListRequest searchRequest, Integer pageNumber, Integer pageSize, Long manufacturerId) {
        List<UserRoleCategory> userRoleCategories = null;
        String hql = "select urc " +
                "from UserRoleCategory urc where " +
                "((:manufacturerIdNull is true and urc.user.manufacturer is not null) " +
                "or urc.user.manufacturer.id = :manufacturerId) and " +
                "(:search is null or urc.user.userName like :search " +
                "or urc.user.email like :search " +
                "or urc.user.firstName like :search " +
                "or urc.user.lastName like :search) " +
                "and (:categoriesNull is true or urc.category in :categories) " +
                "and (:statusNull is true or urc.user.status.name in :status) " +
                "and (:rolesNull is true or urc.role.name in :roles) " +
                "order by urc.user.id desc";
        TypedQuery<UserRoleCategory> query = em.createQuery(hql, UserRoleCategory.class)
                .setParameter("manufacturerId",manufacturerId);
        this.searchParams(query, searchRequest, UserType.MANUFACTURER, manufacturerId);
        if(pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber-1) * pageSize);
            query.setMaxResults(pageSize);
        }
        userRoleCategories = query.getResultList();
        return userRoleCategories;
    }
    public Long getCountForALlUsersBySearchAndFilter(SearchListRequest searchRequest, Long manufacturerId) {
        String hql = "select count(urc.id) " +
                "from UserRoleCategory urc where " +
                "((:manufacturerIdNull is true and urc.user.manufacturer is not null) " +
                "or urc.user.manufacturer.id = :manufacturerId) and " +
                "(:search is null or urc.user.userName like :search " +
                "or urc.user.email like :search " +
                "or urc.user.firstName like :search " +
                "or urc.user.lastName like :search) " +
                "and (:categoriesNull is true or urc.category in :categories) " +
                "and (:statusNull is true or urc.user.status.name in :status) " +
                "and (:rolesNull is true or urc.role.name in :roles) " +
                "order by urc.user.id desc";
        TypedQuery<Long> query = em.createQuery(hql, Long.class)
                .setParameter("manufacturerId",manufacturerId);
        this.searchParams(query, searchRequest, UserType.MANUFACTURER, manufacturerId);
        return query.getSingleResult();
    }

    public List<UserRoleCategory> findAllLabUsersBySearchAndFilter(SearchListRequest searchRequest, Integer pageNumber, Integer pageSize) {
        List<UserRoleCategory> userRoleCategories = null;
        String hql = "select urc " +
                "from UserRoleCategory urc where " +
                "urc.user.labUsers is not null and " +
                "(:search is null or urc.user.userName like :search " +
                "or urc.user.email like :search " +
                "or urc.user.firstName like :search " +
                "or urc.user.lastName like :search) " +
                "and (:categoriesNull is true or urc.category in :categories) " +
                "and (:statusNull is true or urc.user.status.name in :status) " +
                "and (:rolesNull is true or urc.role.name in :roles) " +
                "order by urc.user.id desc";
        TypedQuery<UserRoleCategory> query = em.createQuery(hql, UserRoleCategory.class);
        this.searchParams(query, searchRequest, UserType.LAB, null);
        if(pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber-1) * pageSize);
            query.setMaxResults(pageSize);
        }
        userRoleCategories = query.getResultList();
        return userRoleCategories;
    }

    public Long getCountForAllLabUsersBySearchAndFilter(SearchListRequest searchRequest) {
        String hql = "select count(urc.id) " +
                "from UserRoleCategory urc where " +
                "urc.user.labUsers is not null and " +
                "(:search is null or urc.user.userName like :search " +
                "or urc.user.email like :search " +
                "or urc.user.firstName like :search " +
                "or urc.user.lastName like :search) " +
                "and (:categoriesNull is true or urc.category in :categories) " +
                "and (:statusNull is true or urc.user.status.name in :status) " +
                "and (:rolesNull is true or urc.role.name in :roles) " +
                "order by urc.user.id desc";
        TypedQuery<Long> query = em.createQuery(hql, Long.class);
        this.searchParams(query, searchRequest, UserType.LAB, null);
        return query.getSingleResult();
    }

    public List<UserRoleCategory> findAllInspectionUsers(SearchListRequest searchRequest, Integer pageNumber, Integer pageSize) {
        List<UserRoleCategory> userRoleCategories = null;
        String hql = "select urc " +
                "from UserRoleCategory urc where " +
                "(urc.user.labUsers is null and urc.user.manufacturer is null) and " +
                "(:search is null or urc.user.userName like :search " +
                "or urc.user.email like :search " +
                "or urc.user.firstName like :search " +
                "or urc.user.lastName like :search) " +
                "and (:categoriesNull is true or urc.category in :categories) " +
                "and (:statusNull is true or urc.user.status.name in :status) " +
                "and (:rolesNull is true or urc.role.name in :roles) " +
                "order by urc.user.id desc";
        TypedQuery<UserRoleCategory> query = em.createQuery(hql, UserRoleCategory.class);
        this.searchParams(query, searchRequest, UserType.INSPECTION, null);
        if(pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber-1) * pageSize);
            query.setMaxResults(pageSize);
        }
        userRoleCategories = query.getResultList();
        return userRoleCategories;
    }

    public Long getCountForAllInspectionUsers(SearchListRequest searchRequest) {
        String hql = "select count(urc.id) " +
                "from UserRoleCategory urc where " +
                "(urc.user.labUsers is null and urc.user.manufacturer is null) and " +
                "(:search is null or urc.user.userName like :search " +
                "or urc.user.email like :search " +
                "or urc.user.firstName like :search " +
                "or urc.user.lastName like :search) " +
                "and (:categoriesNull is true or urc.category in :categories) " +
                "and (:statusNull is true or urc.user.status.name in :status) " +
                "and (:rolesNull is true or urc.role.name in :roles) " +
                "order by urc.user.id desc";
        TypedQuery<Long> query = em.createQuery(hql, Long.class);
        this.searchParams(query, searchRequest, UserType.INSPECTION, null);
        return query.getSingleResult();
    }


    private void searchParams(TypedQuery<?> query, SearchListRequest searchRequest, UserType type, Long manufacturerId) {
        Map<String, Object> map = new HashMap<>();
        if(searchRequest != null && searchRequest.getSearchCriteriaList() != null) {
            searchRequest.getSearchCriteriaList()
                    .forEach(searchCriteria -> map.put(searchCriteria.getKey(), searchCriteria.getValue()));
        }
        query.setParameter("search",null);
        if (map.get("search") != null && !Objects.equals(map.get("search"), "")) {
            query.setParameter("search", "%" + map.get("search") + "%");
        }
        try {
            List<String> categories = ((List<String>) map.get("categories")).stream()
                    .map(String::valueOf).filter(c->!Objects.equals(c,"null")).toList();
            if (categories.isEmpty())throw new CustomException("Categories not found", HttpStatus.BAD_REQUEST);
            query.setParameter("categories", categories);
            query.setParameter("categoriesNull", false);
        } catch (Exception e) {
            query.setParameter("categories", new ArrayList<>());
            query.setParameter("categoriesNull", true);
        }
        try {
            List<String> roles = ((List<String>) map.get("roles")).stream()
                    .map(String::valueOf).filter(r->!Objects.equals(r,"null")).toList();
            if (roles.isEmpty())throw new CustomException("roles not found", HttpStatus.BAD_REQUEST);
            query.setParameter("roles", roles);
            query.setParameter("rolesNull", false);
        } catch (Exception e) {
            query.setParameter("roles", new ArrayList<>());
            query.setParameter("rolesNull", true);
        }
        try {
            List<String> status = ((List<String>) map.get("status")).stream()
                    .map(String::valueOf).filter(r->!Objects.equals(r,"null")).toList();
            if (status.isEmpty())throw new CustomException("status not found", HttpStatus.BAD_REQUEST);
            query.setParameter("status", status);
            query.setParameter("statusNull", false);
        } catch (Exception e) {
            query.setParameter("status", new ArrayList<>());
            query.setParameter("statusNull", true);
        }
        if (type.equals(UserType.MANUFACTURER)){
            query.setParameter("manufacturerIdNull",false);
            if (manufacturerId==0L){
                query.setParameter("manufacturerIdNull",true);
            }
        }
    }

    public List<UserRoleCategory> findByUserId(Long userId){
        try {
            return em.createQuery("from UserRoleCategory urc where urc.user.id = :userId and (urc.user.status is null or urc.user.status.name in :status)", UserRoleCategory.class)
                    .setParameter("userId",userId)
                    .setParameter("status", "Active")
                    .getResultList();
        } catch (NoResultException e){
            return new ArrayList<>();
        }
    }


}
