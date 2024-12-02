package org.path.lab.dao;

import org.path.lab.dto.requestDto.SearchListRequest;
import org.path.lab.entity.LabTestType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class LabTestTypeDao extends BaseDao<LabTestType>{
    private final EntityManager em;

    public LabTestTypeDao(EntityManager em) {
        super(em, LabTestType.class);
        this.em = em;
    }

    public List<LabTestType> findAllByCategoryId(Long categoryId, String geoId, Integer pageNumber, Integer pageSize) {
        List<LabTestType> obj = null;
        TypedQuery<LabTestType> query = em
                .createQuery("from LabTestType l where l.categoryId = :categoryId " +
                        "and ((:geoIdNull is true and l.geoId is NULL) or (:geoIdNull is false and l.geoId = :geoId))", LabTestType.class)
                .setParameter("categoryId", categoryId)
                .setParameter("geoId", geoId);
        if(pageSize != null && pageNumber != null) {
            query.setFirstResult(pageNumber * pageSize);
            query.setMaxResults(pageSize);
        }
        if(geoId==null){
            query.setParameter("geoIdNull", true);
        }else {
            query.setParameter("geoIdNull", false);
        }
        obj = query.getResultList();
        return obj;
    }

    public List<LabTestType> findAllByType(LabTestType.Type type, Long categoryId,Integer pageNumber, Integer pageSize) {
        List<LabTestType> obj = null;
        TypedQuery<LabTestType> query = em
                .createQuery("from LabTestType l where l.type = :type and l.categoryId = :categoryId", LabTestType.class)
                .setParameter("type", type)
                .setParameter("categoryId",categoryId);
        if(pageSize != null && pageNumber != null) {
            query.setFirstResult(pageNumber * pageSize);
            query.setMaxResults(pageSize);
        }
        obj = query.getResultList();
        return obj;
    }

    public List<LabTestType> findAllLabTestType(SearchListRequest searchListRequest,
        Integer pageNumber, Integer pageSize) {
        List<LabTestType> labTestTypes = null;
        String hql = "select l from LabTestType l where (:type is null or l.type= :type)"
            + " AND (:categoryIdsNull is true or l.categoryId in (:categoryIds))"
            + " AND (:search is null or l.name like :search)";
        TypedQuery<LabTestType> query = em.createQuery(hql, LabTestType.class);
        this.setSearchParams(query, searchListRequest);
        if (pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber-1) * pageSize);
            query.setMaxResults(pageSize);
        }
        labTestTypes = query.getResultList();
        return labTestTypes;
    }

    public Long getCountForAllLabTestType(SearchListRequest searchListRequest) {
        List<LabTestType> labTestTypes = null;
        String hql = "select count(l.id) from LabTestType l where (:type is null or l.type= :type)"
            + " AND (:categoryIdsNull is true or l.categoryId in (:categoryIds))"
            + " AND (:search is null or l.name like :search)";
        TypedQuery<Long> query = em.createQuery(hql, Long.class);
        this.setSearchParams(query, searchListRequest);
        return query.getSingleResult();
    }

    private void setSearchParams(TypedQuery<?> query, SearchListRequest searchRequest) {
        Map<String, Object> map = new HashMap<>();
        if (searchRequest != null && searchRequest.getSearchCriteriaList() != null) {
            searchRequest.getSearchCriteriaList()
                .forEach(
                    searchCriteria -> map.put(searchCriteria.getKey(), searchCriteria.getValue()));
        }
        try {
            List<Long> categoryIds = ((List<Integer>) map.get("categoryIds")).stream()
                .map(Integer::longValue).toList();
            query.setParameter("categoryIds",
                categoryIds.size() > 0 ? categoryIds : new ArrayList<>());
            query.setParameter("categoryIdsNull", categoryIds.size() <= 0);

        } catch (Exception e) {
            query.setParameter("categoryIds", new ArrayList<>());
            query.setParameter("categoryIdsNull", true);
        }
        query.setParameter("search", null);
        if (map.get("search") != null && !Objects.equals(map.get("search"), "")) {
            query.setParameter("search", "%" + map.get("search") + "%");
        }
        query.setParameter("type",
            map.get("type") != null ? LabTestType.Type.valueOf(map.get("type").toString()) : null);
    }
}
