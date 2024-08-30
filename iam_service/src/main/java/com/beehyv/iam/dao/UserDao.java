package com.beehyv.iam.dao;

import com.beehyv.iam.dto.requestDto.SearchListRequest;
import com.beehyv.iam.model.User;
import com.beehyv.parent.exceptions.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;

@Component
public class UserDao extends BaseDao<User> {
    private final EntityManager em;
    public UserDao(EntityManager em) {
        super(em, User.class);
        this.em=em;
    }
    public User findByName(String userName) {
        try {
            return em.createQuery("FROM User u where u.userName = :userName", User.class)
                    .setParameter("userName", userName)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    public User findByLabUserId(Long labUserId){
        try{
            return em.createQuery("FROM User u where u.labUsers.id = :labUserId",User.class)
                    .setParameter("labUserId", labUserId)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    public List<User> findAllByManufacturerId(Long manufacturerId) {
        return em.createQuery("from User where manufacturer.id = :manufacturerId", User.class)
                .setParameter("manufacturerId",manufacturerId)
                .getResultList();
    }

    public User findByEmail(String emailId) {
        try {
            return em.createQuery("FROM User u where u.email = :emailId", User.class)
                    .setParameter("emailId", emailId)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    public List<User> findAllBySearchAndFilter(SearchListRequest searchRequest, Integer pageNumber, Integer pageSize, Long manufacturerId) {
        List<User> userRoleCategories ;
        String hql = "select distinct u " +
                "from User u  inner join UserRoleCategory urc on u.id = urc.user.id where " +
                "u.manufacturer.id = :manufacturerId and" +
                "((:search is null or u.userName like :search) " +
                "or (:search is null or u.email like :search) " +
                "or (:search is null or u.firstName like :search) " +
                "or (:search is null or u.lastName like :search)) " +
                "and (:categoriesNull is true or urc.category in :categories) " +
                "and (:rolesNull is true or urc.role.name in :roles) " +
                "order by urc.user.id desc";
        TypedQuery<User> query = em.createQuery(hql, User.class)
                .setParameter("manufacturerId",manufacturerId);
        this.searchParams(query, searchRequest);
        if(pageSize != null && pageNumber != null) {
            query.setFirstResult(pageNumber * pageSize);
            query.setMaxResults(pageSize);
        }
        userRoleCategories = query.getResultList();
        return userRoleCategories;
    }
    public Long getCountBySearchAndFilter(SearchListRequest searchRequest, Long manufacturerId) {
        String hql = "select count(distinct u.id) " +
                "from User u  inner join UserRoleCategory urc on u.id = urc.user.id where " +
                "u.manufacturer.id = :manufacturerId and" +
                "((:search is null or u.userName like :search) " +
                "or (:search is null or u.email like :search) " +
                "or (:search is null or u.firstName like :search) " +
                "or (:search is null or u.lastName like :search)) " +
                "and (:categoriesNull is true or urc.category in :categories) " +
                "and (:rolesNull is true or urc.role.name in :roles) " +
                "order by urc.user.id desc";
        TypedQuery<Long> query = em.createQuery(hql, Long.class)
                .setParameter("manufacturerId",manufacturerId);
        this.searchParams(query, searchRequest);
        return query.getSingleResult();
    }
    private void searchParams(TypedQuery<?> query, SearchListRequest searchRequest) {
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
    }

    public List<User> getRegistrationTokens(Long manufacturerId, Long labId, Long targetManufacturerId, List<String> labUserRoles){
        String hql = "select distinct(urc.user) " +
            "from UserRoleCategory urc left join LabUsers lu on urc.user.labUsers.id = lu.id " +
            "where (urc.user.manufacturer.id = :manufacturerId ) " +
            "or (urc.user.manufacturer.id = :targetManufacturerId ) " +
            "or (:labId is not null and (lu.labId = :labId AND urc.role.name in :labUserRoles))" ;
        TypedQuery<User> query = em.createQuery(hql, User.class)
            .setParameter("manufacturerId",manufacturerId)
            .setParameter("targetManufacturerId",targetManufacturerId)
            .setParameter("labId",labId)
            .setParameter("labUserRoles",labUserRoles);
        return query.getResultList();
    }

    public void deleteRegistrationToken(String registrationToken){
        Query query =em.createQuery(" delete from NotificationUserToken t where t.registrationToken = :registrationToken");
        query.setParameter("registrationToken", registrationToken);
        query.executeUpdate();

    }

    public List<User> findallByManufacturerIds(List<Long> manufacturerIds) {
        String hql  = "Select distinct(u) from User u " +
                "left join UserRoleCategory urc on urc.user.id = u.id " +
                "where u.manufacturer.id in :manufacturerIds " +
                "AND urc.isDeleted is false";

        TypedQuery<User> query = em.createQuery(hql, User.class)
                .setParameter("manufacturerIds", manufacturerIds);
        return query.getResultList();
    }
}
