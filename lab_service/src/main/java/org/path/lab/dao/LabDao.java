package org.path.lab.dao;

import org.path.lab.dto.requestDto.DashboardRequestDto;
import org.path.lab.dto.requestDto.SearchListRequest;
import org.path.lab.dto.responseDto.DashboardCountResponseDto;
import org.path.lab.entity.Lab;
import org.path.lab.entity.LabSample;

import java.util.*;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class LabDao extends BaseDao<Lab> {

    private final EntityManager em;

    public LabDao(EntityManager em) {
        super(em, Lab.class);
        this.em = em;
    }

    public Lab findByAddressId(Long addressId) {
        Lab obj = null;
        String hql = "FROM Lab l " +
                "WHERE l.address.id = :addressId";
        TypedQuery<LabSample> query = em.createQuery(hql, LabSample.class).setParameter("addressId", addressId);
        obj = query.getResultList().get(0).getLab();
        return obj;
    }

    public List<Lab> findByPinCode(String pinCode) {
        List<Lab> obj = null;
        String hql = "FROM Lab l " +
                "WHERE l.address.pinCode = :pinCode";
        TypedQuery<Lab> query = em.createQuery(hql, Lab.class)
                .setParameter("pinCode", pinCode);
        obj = query.getResultList();
        return obj;
    }


    public List<Lab> findByCountryAndState(String country, String state) {
        List<Lab> obj = null;
        String hql = "FROM Lab l " +
                "WHERE l.address.village.district.state.country.name = :country" +
                " AND l.address.village.district.state.name = :state";
        TypedQuery<Lab> query = em.createQuery(hql, Lab.class)
                .setParameter("country", country)
                .setParameter("state", state);
        obj = query.getResultList();
        return obj;
    }

    public List<Lab> findAllByIds(List<Long> ids) {
        return em.createQuery("FROM Lab l where l.id in (:ids)", Lab.class)
                .setParameter("ids", ids)
                .getResultList();
    }

    public List<Lab> findAllByCategoryIdAndGeoId(Long categoryId, String filterBy, String geoId, Integer pageNumber, Integer pageSize) {
        TypedQuery<Lab> query = em.createQuery(
                        "FROM Lab l " +
                                "where (:categoryId is null " +
                                "or :categoryId in (select lb.categoryId from LabCategory lb where lb.categoryId = :categoryId)) " +
                                "and l.status.name = :status " +
                                "and (:filterBy != 'districtGeoId' or l.address.village.district.geoId = :geoId) " +
                                "and (:filterBy != 'stateGeoId' or l.address.village.district.state.geoId = :geoId) " +
                                "and (:filterBy != 'countryGeoId' or l.address.village.district.state.country.geoId = :geoId) ",
                        Lab.class
                )
                .setParameter("categoryId", categoryId)
                .setParameter("filterBy", filterBy)
                .setParameter("geoId", geoId)
                .setParameter("status", "Active");
        if (pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        return query.getResultList();
    }

    public List<Lab> findAllLabs(SearchListRequest searchListRequest, String search, Integer pageNumber, Integer pageSize) {
        TypedQuery<Lab> query = em.createQuery(
                "select distinct(l) from Lab l join l.labCategoryMapping lc "
                        +"where (:search is null or l.name like :search)" +
                        "AND (:categoryIdsNull is true or lc.categoryId in (:categoryIds)) "
                        +"AND  lc.isEnabled is true " +
                        "and l.status.name = :status " +
                        " AND l.isDeleted is false "
                        +"AND lc.isDeleted is false "
                        +"AND (:stateIdsNull is true or l.address.village.district.state.id in (:stateIds)) "
                        +"AND (:districtIdsNull is true or l.address.village.district.id in (:districtIds)) "
                , Lab.class);
        this.setSearchParams(query,searchListRequest);
        query.setParameter("search", null);
        query.setParameter("status","Active");
        if (search != null && !Objects.equals(search, "")) {
            query.setParameter("search", "%" + search + "%");
        }
        if (pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        return query.getResultList();
    }
    public Long getCountForAllLabs(SearchListRequest searchListRequest, String search) {
        TypedQuery<Long> query = em.createQuery(
                "select count(Distinct(l.id)) from Lab l join l.labCategoryMapping lc where (:search is null or l.name like :search) "+
                "AND (:categoryIdsNull is true or lc.categoryId in (:categoryIds)) "
                        +"AND  lc.isEnabled is true " +
                        " AND l.isDeleted is false " +
                        "and l.status.name = :status "
                        +"AND lc.isDeleted is false "
                        +"AND (:stateIdsNull is true or l.address.village.district.state.id in (:stateIds)) "
                        +"AND (:districtIdsNull is true or l.address.village.district.id in (:districtIds)) ",
                Long.class);
        this.setSearchParams(query,searchListRequest);
        query.setParameter("search", null);
        query.setParameter("status", "Active");
        if (search != null && !Objects.equals(search, "")) {
            query.setParameter("search", "%" + search + "%");
        }
        return query.getSingleResult();
    }

    public List<Long> getAllActiveLabIds() {
        return em.createQuery(
                        "select l.id from Lab l where l.status.name = :status",
                        Long.class)
                .setParameter("status", "Active")
                .getResultList();
    }

    public List<Lab> findAllActiveLabsForCategory(String search, Long categoryId, Integer pageNumber, Integer pageSize) {
        TypedQuery<Lab> query = em.createQuery(
                        "Select distinct(l) from Lab l left join LabCategory lc on l.id = lc.lab.id " +
                                "where (:search is null or l.name like :search) " +
                                "and lc.categoryId = :categoryId " +
                                "and lc.isEnabled is true " +
                                "and lc.isDeleted is false " +
                                "and l.status.name = :status", Lab.class)
                .setParameter("search", null)
                .setParameter("status", "Active")
                .setParameter("categoryId", categoryId);
        if (search != null && !Objects.equals(search, "")) {
            query.setParameter("search", "%" + search + "%");
        }
        if (pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        return query.getResultList();
    }

    public Long getCountForAllActiveLabsCategory(String search, Long categoryId) {
        TypedQuery<Long> query = em.createQuery(
                        "Select count(l.id) from Lab l left join LabCategory lc on l.id = lc.lab.id " +
                                "where (:search is null or l.name like :search) " +
                                "and lc.categoryId = :categoryId " +
                                "and lc.isEnabled is true " +
                                "and lc.isDeleted is false " +
                                "and l.status.name = :status", Long.class)
                .setParameter("search", null)
                .setParameter("status", "Active")
                .setParameter("categoryId", categoryId);
        if (search != null && !Objects.equals(search, "")) {
            query.setParameter("search", "%" + search + "%");
        }
        return query.getSingleResult();
    }

    public List<DashboardCountResponseDto> getDistrictCount(Long categoryId, String geoId){
        List<DashboardCountResponseDto> result = new ArrayList<>();
        boolean categoryIdNull = categoryId == null;
        String hql = "select count(*), l.address.village.district.geoId, count(distinct(l.id)) " +
                "from Lab l left join LabCategory lc on l.id = lc.lab.id where " +
                "l.address.village.district.state.geoId = :geoId " +
                "and (:categoryIdNull is true or lc.categoryId = :categoryId) " +
                "and l.status.name = :status group by l.address.village.district.geoId";

        TypedQuery<Object[]> results = em.createQuery(hql, Object[].class)
                .setParameter("categoryIdNull", categoryIdNull)
                .setParameter("categoryId", categoryId)
                .setParameter("status","Active")
                .setParameter("geoId", geoId);
        List<Object[]> objects = results.getResultList();
        for (Object[] item : objects){
            DashboardCountResponseDto dto = new DashboardCountResponseDto();
            dto.setId(String.valueOf(item[1]));
            dto.setTotalLabCategories(Long.valueOf(item[0]== null ? "0" : item[0].toString()));
            dto.setTotal(Long.valueOf(item[2]== null ? "0" : item[2].toString()));
            result.add(dto);
        }
        return result;
    }

    public List<DashboardCountResponseDto> getStateCount(Long categoryId, String geoId) {
        List<DashboardCountResponseDto> result = new ArrayList<>();
        boolean categoryIdNull = categoryId == null;
        String hql = "select count(*), l.address.village.district.state.geoId, count(distinct(l.id)) " +
                "from Lab l left join LabCategory lc on l.id = lc.lab.id where " +
                "l.address.village.district.state.country.geoId = :geoId " +
                "and (:categoryIdNull is true or lc.categoryId = :categoryId) " +
                "and l.status.name = :status group by l.address.village.district.state.geoId";
        TypedQuery<Object[]> results = em.createQuery(hql, Object[].class)
                .setParameter("categoryIdNull", categoryIdNull)
                .setParameter("categoryId", categoryId)
                .setParameter("status","Active")
                .setParameter("geoId", geoId);
        List<Object[]> objects = results.getResultList();
        for (Object[] item : objects){
            DashboardCountResponseDto dto = new DashboardCountResponseDto();
            dto.setId(String.valueOf(item[1]));
            dto.setTotalLabCategories(Long.valueOf(item[0]== null ? "0" : item[0].toString()));
            dto.setTotal(Long.valueOf(item[2]== null ? "0" : item[2].toString()));
            result.add(dto);
        }
        return result;
    }


    public List<Object[]> getLabsAggregateByCategoryId(DashboardRequestDto dto) {
        return em.createQuery(
                        "SELECT l.id, l.name, " +
                                "l.address.village.district.name, " +
                                "l.address.village.district.state.name, " +
                                "COALESCE(COUNT(DISTINCT CASE WHEN ls.state.id IN ( " +
                                "   (SELECT id FROM SampleState WHERE name IN ('inProgress', 'done', 'rejected')) " +
                                ") THEN ls.id END), 0) AS samplesReceived, " +
                                "COALESCE(COUNT(DISTINCT CASE WHEN ls.state.id = (SELECT id FROM SampleState WHERE name = 'rejected') THEN ls.id END), 0) AS samplesRejected, " +
                                "COALESCE(COUNT(DISTINCT CASE WHEN ls.state.id = (SELECT id FROM SampleState WHERE name = 'inProgress') THEN ls.id END), 0) AS samplesInLab, " +
                                "COALESCE(COUNT(DISTINCT CASE WHEN ls.state.id = (SELECT id FROM SampleState WHERE name = 'done') THEN ls.id END), 0) AS testsDone, " +
                                "l.certificateNo, " +
                                "COALESCE(COUNT(DISTINCT CASE WHEN ls.state.id = (SELECT id FROM SampleState WHERE name = 'toReceive') THEN ls.id END), 0) AS samplesToReceive " +
                                "FROM Lab l " +
                                "LEFT JOIN l.labCategoryMapping lc " +
                                "LEFT JOIN LabSample ls ON (ls.lab.id = l.id AND ls.categoryId = :categoryId AND " +
                                "((:dateNull IS TRUE) OR (ls.sampleSentDate >= :fromDate AND ls.sampleSentDate <= :toDate))) " +
                                "WHERE lc.categoryId = :categoryId " +
                                "AND lc.isEnabled is true "+
                                "AND lc.isDeleted is false "+
                                "AND l.isDeleted is false "+
                                "and l.status.name = :status " +
                                "AND (:countryGeoId IS NULL OR l.address.village.district.state.country.geoId = :countryGeoId) " +
                                "AND (:stateGeoId IS NULL OR l.address.village.district.state.geoId = :stateGeoId) " +
                                "AND (:districtGeoId IS NULL OR l.address.village.district.geoId = :districtGeoId) " +
                                "GROUP BY l.id, l.name, l.address.village.district.name, l.address.village.district.state.name, l.certificateNo",
                        Object[].class)
                .setParameter("categoryId", dto.getCategoryId())
                .setParameter("countryGeoId", dto.getSourceCountryGeoId())
                .setParameter("stateGeoId", dto.getSourceStateGeoId())
                .setParameter("districtGeoId", dto.getSourceDistrictGeoId())
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("status", "Active")
                .setParameter("toDate", dto.getToDate() == null ? new Date() : dto.getToDate())
                .setParameter("dateNull", dto.getFromDate() == null)
                .getResultList();
    }

    public List<LabSample> getLabSampleDetails(DashboardRequestDto dto, Long labId, List stateNames) {
        return em.createQuery(
                        "SELECT DISTINCT(ls) " +
                                "FROM LabSample ls " +
                                "left JOIN ls.lab l " +
                                "left JOIN ls.state ss " +
                                "left JOIN l.labCategoryMapping lc " +
                                "WHERE ls.lab.id = :labId " +
                                "AND ls.categoryId = :categoryId " +
                                "AND ss.name IN (:stateNames) " +
                                "AND (:countryGeoId IS NULL OR l.address.village.district.state.country.geoId = :countryGeoId) " +
                                "AND (:stateGeoId IS NULL OR l.address.village.district.state.geoId = :stateGeoId) " +
                                "AND (:districtGeoId IS NULL OR l.address.village.district.geoId = :districtGeoId) " +
                                "AND (:fromDate IS NULL OR ls.sampleSentDate >= :fromDate) " +
                                "AND (:toDate IS NULL OR ls.sampleSentDate <= :toDate) "
                                , LabSample.class)
                .setParameter("labId", labId)
                .setParameter("categoryId", dto.getCategoryId())
                .setParameter("stateNames", stateNames)
                .setParameter("countryGeoId", dto.getSourceCountryGeoId())
                .setParameter("stateGeoId", dto.getSourceStateGeoId())
                .setParameter("districtGeoId", dto.getSourceDistrictGeoId())
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate() == null ? new Date() : dto.getToDate())
                .getResultList();    }
    private void setSearchParams(TypedQuery<?> query, SearchListRequest searchRequest) {
        Map<String, Object> map = new HashMap<>();
        if (searchRequest != null && searchRequest.getSearchCriteriaList() != null) {
            searchRequest.getSearchCriteriaList()
                    .forEach(searchCriteria -> map.put(searchCriteria.getKey(), searchCriteria.getValue()));
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
        try {
            List<Long> stateIds = ((List<Integer>) map.get("states")).stream()
                    .map(Integer::longValue).collect(Collectors.toList());
            if (stateIds.isEmpty()) {
                query.setParameter("stateIds", new ArrayList<>());
                query.setParameter("stateIdsNull", true);
            } else {
                query.setParameter("stateIds", stateIds);
                query.setParameter("stateIdsNull", false);
            }
        } catch (Exception e) {
            query.setParameter("stateIds", new ArrayList<>());
            query.setParameter("stateIdsNull", true);
        }
        try {
            List<Long> districtIds = ((List<Integer>) map.get("districts")).stream()
                    .map(Integer::longValue).collect(Collectors.toList());
            if (districtIds.isEmpty()) {
                query.setParameter("districtIds", new ArrayList<>());
                query.setParameter("districtIdsNull", true);
            } else {
                query.setParameter("districtIds", districtIds);
                query.setParameter("districtIdsNull", false);
            }
        } catch (Exception e) {
            query.setParameter("districtIds", new ArrayList<>());
            query.setParameter("districtIdsNull", true);
        }
    }

    public String findCertificateByName(String name) {
        try {
            return em.createQuery(
                            "SELECT l.certificateNo from Lab l " +
                                    "where l.name = :name", String.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
