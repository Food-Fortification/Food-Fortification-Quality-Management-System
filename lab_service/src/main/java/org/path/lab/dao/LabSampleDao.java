package org.path.lab.dao;

import org.path.lab.dto.requestDto.SearchListRequest;
import org.path.lab.entity.LabSample;
import org.path.lab.enums.SampleType;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class LabSampleDao extends BaseDao<LabSample> {
    private final EntityManager em;

    public LabSampleDao(EntityManager em) {
        super(em, LabSample.class);
        this.em = em;
    }

    public List<LabSample> findAll(Long labId, Integer pageNumber, Integer pageSize, SearchListRequest searchRequest) {
        List<LabSample> obj = null;
        String hql = "FROM LabSample b " +
                "WHERE b.lab.id = :labId " +
                "AND (:batchNo is null or b.batchNo like :batchNo or b.lotNo like :batchNo ) " +
                "AND (:batchId is null or b.batchId = :batchId or b.lotId = :batchId) " +
                "AND (:displayName is null or b.state.displayName = :displayName ) " +
                "AND (:categoryId is null or b.categoryId = :categoryId ) " +
                "AND (:stateIdsNull is true or b.state.id in (:stateIds)) " +
                "AND (:testDateStart is null or b.testDate >= :testDateStart) " +
                "AND (:testDateEnd is null or b.testDate <= :testDateEnd) " +
                "AND (:sentDateStart is null or b.sampleSentDate >= :sentDateStart) " +
                "AND (:sentDateEnd is null or b.sampleSentDate <= :sentDateEnd) " +
                "AND (:receivedStart is null or b.receivedDate >= :receivedStart) " +
                "AND (:receivedEnd is null or b.receivedDate <= :receivedEnd) " +
                "order by id desc";
        TypedQuery<LabSample> query = em.createQuery(hql, LabSample.class)
                .setParameter("labId", labId);
        this.setSearchParams(query, searchRequest);
        if (pageSize != null && pageNumber != null) {
            query.setFirstResult(pageNumber * pageSize);
            query.setMaxResults(pageSize);
        }
        obj = query.getResultList();
        return obj;
    }

    public List<LabSample> findAllSamplesForSuperAdmin(Integer pageNumber, Integer pageSize, SearchListRequest searchRequest, List<Long> testManufacturerIds) {
        List<LabSample> obj = null;
        String hql = "FROM LabSample b " +
                "WHERE (:batchNo is null or b.batchNo like :batchNo or b.lotNo like :batchNo ) " +
                "AND (:testManufacturerIdsNull is true or b.manufacturerId not in :testManufacturerIds) " +
                "AND (:batchId is null or b.batchId = :batchId or b.lotId = :batchId) " +
                "AND (:displayName is null or b.state.displayName = :displayName ) " +
                "AND (:categoryId is null or b.categoryId = :categoryId ) " +
                "AND (:stateIdsNull is true or b.state.id in (:stateIds)) " +
                "AND (:testDateStart is null or b.testDate >= :testDateStart) " +
                "AND (:testDateEnd is null or b.testDate <= :testDateEnd) " +
                "AND (:sentDateStart is null or b.sampleSentDate >= :sentDateStart) " +
                "AND (:sentDateEnd is null or b.sampleSentDate <= :sentDateEnd) " +
                "AND (:receivedStart is null or b.receivedDate >= :receivedStart) " +
                "AND (:receivedEnd is null or b.receivedDate <= :receivedEnd) " +
                "order by id desc";
        TypedQuery<LabSample> query = em.createQuery(hql, LabSample.class);
        this.setSearchParams(query, searchRequest);
        if (pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        if (testManufacturerIds.isEmpty()) {
            query.setParameter("testManufacturerIdsNull", true);
            query.setParameter("testManufacturerIds", new ArrayList<Long>());
        } else {
            query.setParameter("testManufacturerIdsNull", false);
            query.setParameter("testManufacturerIds", testManufacturerIds);
        }
        obj = query.getResultList();
        return obj;
    }

    public Long getCount(Long labId, SearchListRequest searchRequest) {
        String hql = "SELECT count(b.id) FROM LabSample b " +
                "WHERE b.lab.id = :labId " +
                "AND (:batchNo is null or b.batchNo like :batchNo or b.lotNo like :batchNo ) " +
                "AND (:batchId is null or b.batchId = :batchId or b.lotId = :batchId) " +
                "AND (:displayName is null or b.state.displayName = :displayName ) " +
                "AND (:categoryId is null or b.categoryId = :categoryId ) " +
                "AND (:stateIdsNull is true or b.state.id in (:stateIds)) " +
                "AND (:testDateStart is null or b.testDate >= :testDateStart) " +
                "AND (:testDateEnd is null or b.testDate <= :testDateEnd) " +
                "AND (:sentDateStart is null or b.sampleSentDate >= :sentDateStart) " +
                "AND (:sentDateEnd is null or b.sampleSentDate <= :sentDateEnd) " +
                "AND (:receivedStart is null or b.receivedDate >= :receivedStart) " +
                "AND (:receivedEnd is null or b.receivedDate <= :receivedEnd) ";
        TypedQuery<Long> query = em.createQuery(hql, Long.class)
                .setParameter("labId", labId);
        this.setSearchParams(query, searchRequest);
        return query.getSingleResult();
    }

    public Long getCountSamplesForSuperAdmin(SearchListRequest searchRequest, List<Long> testManufacturerIds) {
        String hql = "SELECT count(b.id) FROM LabSample b " +
                "WHERE (:batchNo is null or b.batchNo like :batchNo or b.lotNo like :batchNo ) " +
                "AND (:testManufacturerIdsNull is true or b.manufacturerId not in :testManufacturerIds) " +
                "AND (:batchId is null or b.batchId = :batchId or b.lotId = :batchId) " +
                "AND (:displayName is null or b.state.displayName = :displayName ) " +
                "AND (:categoryId is null or b.categoryId = :categoryId ) " +
                "AND (:stateIdsNull is true or b.state.id in (:stateIds)) " +
                "AND (:testDateStart is null or b.testDate >= :testDateStart) " +
                "AND (:testDateEnd is null or b.testDate <= :testDateEnd) " +
                "AND (:sentDateStart is null or b.sampleSentDate >= :sentDateStart) " +
                "AND (:sentDateEnd is null or b.sampleSentDate <= :sentDateEnd) " +
                "AND (:receivedStart is null or b.receivedDate >= :receivedStart) " +
                "AND (:receivedEnd is null or b.receivedDate <= :receivedEnd) ";
        TypedQuery<Long> query = em.createQuery(hql, Long.class);
        this.setSearchParams(query, searchRequest);
        if (testManufacturerIds.isEmpty()) {
            query.setParameter("testManufacturerIdsNull", true);
            query.setParameter("testManufacturerIds", new ArrayList<Long>());
        } else {
            query.setParameter("testManufacturerIdsNull", false);
            query.setParameter("testManufacturerIds", testManufacturerIds);
        }
        return query.getSingleResult();
    }

    public List<LabSample> findAllSamplesByBatchId(Long batchId) {
        List<LabSample> obj = new ArrayList<>();
        String hql = "FROM LabSample b " +
                "WHERE b.batchId = :batchId " +
                "and (b.state.name != 'rejected')";
        TypedQuery<LabSample> query = em.createQuery(hql, LabSample.class).setParameter("batchId", batchId);
        obj = query.getResultList();
        return obj.stream().filter(labSample -> labSample.getInspection() == null).toList();
    }

    public List<LabSample> findAllSamplesByBatchIds(List<Long> batchIds) {
        List<LabSample> obj = new ArrayList<>();
        String hql = "FROM LabSample b " +
                "WHERE b.batchId in :batchIds " +
                "and (b.state.name != 'selfCertified') " +
                "group by b.batchId " +
                "order by b.createdDate ";
        TypedQuery<LabSample> query = em.createQuery(hql, LabSample.class).setParameter("batchIds", batchIds);
        obj = query.getResultList();
        return obj.stream().filter(labSample -> labSample.getInspection() == null).toList();
    }

    public List<LabSample> findAllSamplesByBatchIdForInspection(Long batchId) {
        List<LabSample> obj = new ArrayList<>();
        String hql = "FROM LabSample b " +
            "WHERE b.batchId = :batchId " +
            "and (b.state.name != 'rejected')";
        TypedQuery<LabSample> query = em.createQuery(hql, LabSample.class).setParameter("batchId",batchId);
        obj = query.getResultList();
        return obj;
    }

    public List<LabSample> findAllSamplesByBatchIdsForInspection(List<Long> batchIds) {
        List<LabSample> obj = new ArrayList<>();
        String hql = "FROM LabSample b " +
                "WHERE b.batchId in :batchIds " +
                "and (b.state.name != 'selfCertified') " +
                "group by b.batchId " +
                "order by b.createdDate ";
        TypedQuery<LabSample> query = em.createQuery(hql, LabSample.class).setParameter("batchIds", batchIds);
        obj = query.getResultList();
        return obj;
    }

    public List<LabSample> findAllSamplesByLotId(Long lotId) {
        List<LabSample> obj = null;
        String hql = "FROM LabSample b " +
                "WHERE b.lotId = :lotId " +
                "and (b.state.name != 'rejected')";
        TypedQuery<LabSample> query = em.createQuery(hql, LabSample.class).setParameter("lotId", lotId);
        obj = query.getResultList();
        return obj.stream().filter(labSample -> labSample.getInspection() == null).toList();
    }

    public List<LabSample> findAllSamplesByLotIds(List<Long> lotIds) {
        List<LabSample> obj = null;
        String hql = "FROM LabSample b " +
                "WHERE b.lotId in :lotIds " +
                "and (b.state.name != 'selfCertified') " +
                "group by b.lotId " +
                "order by b.createdDate ";
        TypedQuery<LabSample> query = em.createQuery(hql, LabSample.class).setParameter("lotIds", lotIds);
        obj = query.getResultList();
        return obj.stream().filter(labSample -> labSample.getInspection() == null).toList();
    }

    public List<LabSample> findAllSamplesByLotIdForInspection(Long lotId) {
        List<LabSample> obj = null;
        String hql = "FROM LabSample b " +
                "WHERE b.lotId = :lotId " +
                "and (b.state.name != 'rejected')";
        TypedQuery<LabSample> query = em.createQuery(hql, LabSample.class).setParameter("lotId", lotId);
        obj = query.getResultList();
        return obj;
    }

    public List<LabSample> findAllSamplesByLotIdsForInspection(List<Long> lotIds) {
        List<LabSample> obj = null;
        String hql = "FROM LabSample b " +
                "WHERE b.lotId in :lotIds " +
                "and (b.state.name != 'selfCertified') " +
                "group by b.lotId " +
                "order by b.createdDate ";
        TypedQuery<LabSample> query = em.createQuery(hql, LabSample.class).setParameter("lotIds", lotIds);
        obj = query.getResultList();
        return obj;
    }

    private void setSearchParams(TypedQuery<?> query, SearchListRequest searchRequest) {
        Map<String, Object> map = new HashMap<>();
        if (searchRequest != null && searchRequest.getSearchCriteriaList() != null) {
            searchRequest.getSearchCriteriaList()
                    .forEach(searchCriteria -> map.put(searchCriteria.getKey(), searchCriteria.getValue()));
        }
        if (map.get("batchNo") != null && !Objects.equals(map.get("batchNo"), "")) {
            query.setParameter("batchNo", "%" + map.get("batchNo") + "%");
        } else {
            query.setParameter("batchNo", null);
        }
        try {
            List<Long> stateIds = ((List<Integer>) map.get("stateIds")).stream()
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
        this.setQueryLong("batchId", map.get("batchId"), query);
        this.setQueryString("displayName", map.get("displayName"), query);
        this.setQueryLong("categoryId", map.get("categoryId"), query);
        this.setQueryDateRange("received", map.get("receivedDate"), query);
        this.setQueryDateRange("sentDate", map.get("sampleSentDate"), query);
        this.setQueryDateRange("testDate", map.get("testDate"), query);
    }

    public void setQueryLong(String key, Object value, TypedQuery<?> query) {
        try {
            Long val = Long.parseLong((String) value, 10);
            query.setParameter(key, val);
        } catch (Exception e) {
            query.setParameter(key, null);
        }
    }

    public void setQueryString(String key, Object value, TypedQuery<?> query) {
        try {
            String val = ((String) value);
            query.setParameter(key, val);
        } catch (Exception e) {
            query.setParameter(key, null);
        }
    }

    public List<LabSample> findAllByCreateDate(List<Long> categoryList, String createStartDate, String createEndDate, Long labId) {

        LocalDateTime startDate = LocalDateTime.parse(createStartDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endDate = LocalDateTime.parse(createEndDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String hql = "SELECT b FROM LabSample as b where" +
                "(:createStart is null or b.createdDate >= :createStart) " +
                "AND (:createEnd is null or b.createdDate <= :createEnd) " +
                "and b.lab.id = :labId " +
                "and categoryId in (:categoryIds) ";
        TypedQuery<LabSample> query = em.createQuery(hql, LabSample.class)
                .setParameter("categoryIds", categoryList)
                .setParameter("createStart", startDate)
                .setParameter("labId", labId)
                .setParameter("createEnd", endDate);
        return query.getResultList();
    }

    public void setQueryDateRange(String prefix, Object value, TypedQuery<?> query) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        try {
            String exp = (String) value;
            String[] dateList = exp.split("-");
            try {
                query.setParameter(prefix + "Start", formatter.parse(dateList[0]));
            } catch (Exception e) {
                query.setParameter(prefix + "Start", null);
            }
            try {
                query.setParameter(prefix + "End", formatter.parse(dateList[1]));
            } catch (Exception e) {
                query.setParameter(prefix + "End", null);
            }
        } catch (Exception e) {
            query.setParameter(prefix + "Start", null);
            query.setParameter(prefix + "End", null);
        }
    }

    public List<Long[]> findAllAggregateByManufacturerIdAndLabIdAndCategoryId(Integer pageNumber, Integer pageSize) {
        return em.createQuery(
                        "select " +
                                "s.manufacturerId as manufacturerId, " +
                                "s.lab.id as labId, " +
                                "s.categoryId as categoryId, " +
                                "year(s.sampleSentDate) as sampleSentYear, " +
                                "sum(case when s.state.name = 'toReceive' then 1L else 0L end) as inTransitCount, " +
                                "sum(case when s.state.name = 'inProgress' then 1L else 0L end) as underTestingCount, " +
                                "sum(case when s.state.name = 'done' then 1L else 0L end) as testedCount, " +
                                "sum(case when s.state.name = 'rejected' then 1L else 0L end) as rejectedCount " +
                                "from LabSample s " +
                                "where s.lab is not null " +
                                "group by sampleSentYear, s.manufacturerId, s.lab.id, s.categoryId",
                        Object[].class
                )
                .setFirstResult(pageNumber * pageSize)
                .setMaxResults(pageSize)
                .getResultList()
                .stream().map(d -> {
                    Long[] array = new Long[8];
                    array[0] = (Long) d[0];
                    array[1] = (Long) d[1];
                    array[2] = (Long) d[2];
                    array[3] = ((Integer) d[3]).longValue();
                    array[4] = (Long) d[4];
                    array[5] = (Long) d[5];
                    array[6] = (Long) d[6];
                    array[7] = (Long) d[7];
                    return array;
                }).toList();
    }

    public LabSample findByCategoryIdAndId(Long categoryId, Long sampleId) {
        return em.createQuery("FROM LabSample ls where " +
                        "ls.id = :sampleId and ls.categoryId = :categoryId", LabSample.class)
                .setParameter("sampleId", sampleId)
                .setParameter("categoryId", categoryId)
                .getSingleResult();
    }

    public LabSample getLabNameAddressByEntityId(SampleType entityType, Long entityId) {
        String entityTypeField = entityType.equals(SampleType.batch) ? "batchId" : "lotId";
        return em.createQuery("FROM LabSample ls where " +
                        "ls." + entityTypeField + " = :entityId " +
                        "order by ls.id desc ", LabSample.class)
                .setParameter("entityId", entityId)
                .setMaxResults(1)
                .getSingleResult();
    }
}
