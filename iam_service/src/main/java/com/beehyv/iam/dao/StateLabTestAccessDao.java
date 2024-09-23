package com.beehyv.iam.dao;
import com.beehyv.iam.dto.requestDto.SearchCriteria;
import com.beehyv.iam.dto.requestDto.SearchListRequest;
import com.beehyv.iam.enums.EntityType;
import com.beehyv.iam.enums.UserType;
import com.beehyv.iam.model.State;
import com.beehyv.iam.model.StateLabTestAccess;
import com.beehyv.iam.model.UserRoleCategory;
import com.beehyv.parent.exceptions.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.*;


@Component
public class StateLabTestAccessDao extends BaseDao<StateLabTestAccess> {
    private final EntityManager em;

    public StateLabTestAccessDao(EntityManager em) {
        super(em, StateLabTestAccess.class);
        this.em = em;
    }

    public StateLabTestAccess findByStateAndCategoryAndEntityType(Long categoryId,EntityType entityType, Long stateId) {
        try {
            return em.createQuery("from StateLabTestAccess s where  "
             + "(s.stateCategoryEntityTypeId.state.id = :stateId) " +
                    "and (s.stateCategoryEntityTypeId.categoryId =:categoryId) " +
                    " and (s.stateCategoryEntityTypeId.entityType= :entityType) ", StateLabTestAccess.class)
                    .setParameter("stateId", stateId)
                    .setParameter("categoryId", categoryId)
                    .setParameter("entityType", entityType)
                    .getSingleResult();
        }
        catch(NoResultException e){
            return null;
        }

    }


    public void deleteEntity(Long categoryId, EntityType entityType, Long stateId) {
        Query query =em.createQuery(" update StateLabTestAccess s set s.isDeleted= true where s.stateCategoryEntityTypeId.state.id = :stateId and s.stateCategoryEntityTypeId.categoryId =:categoryId " +
                        " and s.stateCategoryEntityTypeId.entityType= :entityType ");
                query.setParameter("stateId", stateId);
                query.setParameter("categoryId", categoryId);
                query.setParameter("entityType",entityType);
                query.executeUpdate();


    }

    public void deleteEntityByStateIds(List<Long> stateIds) {
        Query query =em.createQuery("update StateLabTestAccess s set s.isDeleted= true where s.stateCategoryEntityTypeId.state.id in :stateIds");
        query.setParameter("stateIds", stateIds);
        query.executeUpdate();


    }



    public List<StateLabTestAccess> findAllStateLabTestAccessBySearchAndFilter(SearchListRequest searchRequest,Integer pageNumber,Integer pageSize){
        List<StateLabTestAccess> stateLabTestAccessList = null;
        String hql = "select s " +
                "from StateLabTestAccess s where " +
                "(:search is null or s.stateCategoryEntityTypeId.state.name like :search) and " +
                "(:entityTypesNull is true or s.stateCategoryEntityTypeId.entityType in  :entityTypes) and " +
                "(:categoriesNull is true or s.stateCategoryEntityTypeId.categoryId in :categories) " +
                "order by s.stateCategoryEntityTypeId.state.name asc";
        TypedQuery<StateLabTestAccess> query = em.createQuery(hql, StateLabTestAccess.class);
        this.searchParams(query,searchRequest);
        if(pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber-1) * pageSize);
            query.setMaxResults(pageSize);
        }

        stateLabTestAccessList= query.getResultList();
        return stateLabTestAccessList;
    }


    public Long getCountForAllStateTestLabAccess(SearchListRequest searchRequest) {
        String hql = "select s " +
                "from StateLabTestAccess s where " +
                "(:search is null or s.stateCategoryEntityTypeId.state.name like :search) and " +
                "(:entityTypesNull is true or s.stateCategoryEntityTypeId.entityType in  :entityTypes) and " +
                "(:categoriesNull is true or s.stateCategoryEntityTypeId.categoryId in :categories) " +
                "order by s.stateCategoryEntityTypeId.state.name asc";
        TypedQuery<StateLabTestAccess> query = em.createQuery(hql, StateLabTestAccess.class);
        this.searchParams(query,searchRequest);
        return Long.valueOf(query.getResultList().size());
    }

    private void searchParams(TypedQuery<?> query, SearchListRequest searchRequest) {
        Map<String, Object> map = new HashMap<>();
        if(searchRequest != null && searchRequest.getSearchCriteriaList() != null) {
            searchRequest.getSearchCriteriaList()
                    .forEach(searchCriteria -> map.put(searchCriteria.getKey(), searchCriteria.getValue()));
        }
        try {
            List<Long> categories =((List<Integer>)map.get("categoryIds")).stream()
                    .map(Integer::longValue).filter(c->!Objects.equals(c,"null")).toList();

            query.setParameter("categories", categories);
            query.setParameter("categoriesNull", false);
        } catch (Exception e) {
            query.setParameter("categories", new ArrayList<>());
            query.setParameter("categoriesNull", true);
        }
        query.setParameter("search", null);
        if (map.get("search") != null && map.get("search") != ""){
            query.setParameter("search", "%" + map.get("search")+"%");
        }
        try {
            List<EntityType> entityTypes = new ArrayList<>();
            List<String> entityTypeSearchList = (List<String>) map.get("entityType");
            entityTypeSearchList.forEach(
                    entityType->{
                        if(entityType.equals("lot"))
                            entityTypes.add(EntityType.lot);
                        if(entityType.equals("batch"))
                            entityTypes.add(EntityType.batch);

                    }
            );
            query.setParameter("entityTypes", entityTypes);
            query.setParameter("entityTypesNull", false);

            }
         catch (Exception e) {
            query.setParameter("entityTypes", new ArrayList<>());
            query.setParameter("entityTypesNull", true);
        }

    }

}