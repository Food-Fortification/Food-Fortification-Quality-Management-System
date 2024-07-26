package com.beehyv.fortification.dao;

import com.beehyv.fortification.dto.requestDto.SearchListRequest;
import com.beehyv.fortification.entity.*;
import com.beehyv.fortification.helper.DaoHelper;
import com.beehyv.parent.exceptions.CustomException;
import com.beehyv.parent.exceptions.ResourceNotFoundException;
import org.hibernate.Session;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class BatchDao extends BaseDao<Batch> {
    @PersistenceContext
    private EntityManager em;

    public BatchDao(EntityManager em) {
        super(em, Batch.class);
        this.em = em;
    }

    public List<Batch> findAllBatches(Long categoryId, Long manufacturerId, Integer pageNumber, Integer pageSize, SearchListRequest searchRequest,Boolean remQuantity) {
        List<Batch> obj = null;
        String hql = "SELECT distinct(b) FROM Batch as b " +
                "left join BatchProperty bp on bp.batch.id = b.id " +
                "WHERE (:categoryId is 0L or b.category.id = :categoryId) AND " +
                "b.manufacturerId = :manufacturerId " +
                "AND (:remQuantity is null or b.remainingQuantity > :remQuantity) " +
                "AND (:stateIdsNull is true or b.state.id in (:stateIds)) " +
                "AND ((:batchNo is null or b.batchNo like :batchNo) or " +
                "(:manufacture_batchNumber is null or (bp.name = :batchPropertyName and bp.value like :manufacture_batchNumber))) " +
                "AND (:mfdStart is null or b.dateOfManufacture >= :mfdStart) " +
                "AND (:mfdEnd is null or b.dateOfManufacture <= :mfdEnd) " +
                "AND (:expStart is null or b.dateOfExpiry >= :expStart) " +
                "AND (:expEnd is null or b.dateOfExpiry <= :expEnd) "
                + " order by b.id asc";
        TypedQuery<Batch> query = em.createQuery(hql, Batch.class)
                .setParameter("categoryId", categoryId)
                .setParameter("manufacturerId", manufacturerId);
        if (remQuantity){
            query.setParameter("remQuantity",0d);
        }else {
            query.setParameter("remQuantity",null);
        }
        this.setSearchParams(query, searchRequest);
        if(pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber-1) * pageSize);
            query.setMaxResults(pageSize);
        }
        obj = query.getResultList();
        return obj;
    }

    public Long getCount(Long categoryId, Long manufacturerId, Long stateId, SearchListRequest searchRequest) {
        String hql = "select count (distinct(b.id)) from Batch as b " +
                "left join BatchProperty bp on bp.batch.id = b.id " +
                "WHERE (:categoryId is 0L or b.category.id = :categoryId) AND " +
                "b.manufacturerId = :manufacturerId " +
                "AND (:stateId is null or b.state.id = :stateId) " +
                "AND (:stateIdsNull is true or b.state.id in (:stateIds)) " +
                "AND ((:batchNo is null or b.batchNo like :batchNo) or " +
                "(:manufacture_batchNumber is null or (bp.name = :batchPropertyName and bp.value like :manufacture_batchNumber))) " +
                "AND (:mfdStart is null or b.dateOfManufacture >= :mfdStart) " +
                "AND (:mfdEnd is null or b.dateOfManufacture <= :mfdEnd) " +
                "AND (:expStart is null or b.dateOfExpiry >= :expStart) " +
                "AND (:expEnd is null or b.dateOfExpiry <= :expEnd) ";
        TypedQuery<Long> query = em.createQuery(hql, Long.class)
                .setParameter("stateId", stateId)
                .setParameter("manufacturerId", manufacturerId)
                .setParameter("categoryId",categoryId);
        this.setSearchParams(query, searchRequest);
        return query.getSingleResult();
    }

    public List<Batch> findAllBatches(Integer pageNumber, Integer pageSize, SearchListRequest searchRequest, List<Long> categoryIds, Boolean remQuantity, List<Long> testManufacturerIds) {
        List<Batch> obj = null;
        String hql = "SELECT b FROM Batch as b " +
                "WHERE ( b.category.id in (:categoryIds)) " +
                "AND (:testManufacturerIdsNull is true or b.manufacturerId not in :testManufacturerIds) " +
                "AND (:remQuantity is null or b.remainingQuantity > :remQuantity) " +
                "AND (:stateIdsNull is true or b.state.id in (:stateIds)) " +
                "AND (:batchNo is null or b.batchNo like :batchNo) " +
                "AND (:manufacturerIdsNull is true or b.manufacturerId in (:manufacturerIds)) " +
                "AND (:mfdStart is null or b.dateOfManufacture >= :mfdStart) " +
                "AND (:mfdEnd is null or b.dateOfManufacture <= :mfdEnd) " +
                "AND (:expStart is null or b.dateOfExpiry >= :expStart) " +
                "AND (:expEnd is null or b.dateOfExpiry <= :expEnd) "
                + " order by b.id asc";
        TypedQuery<Batch> query = em.createQuery(hql, Batch.class)
                .setParameter("categoryIds", categoryIds);
        if (remQuantity) {
            query.setParameter("remQuantity", 0d);
        } else {
            query.setParameter("remQuantity", null);
        }
        this.setSearchParams(query, searchRequest, testManufacturerIds, false);
        if (pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        obj = query.getResultList();
        return obj;
    }

    public Long getCountForSuperAdmin(SearchListRequest searchRequest, List<Long> categoryIds, List<Long> testManufacturerIds) {
        String hql = "select count (b.id) from Batch as b " +
                "WHERE (b.category.id in (:categoryIds)) " +
                "AND (:testManufacturerIdsNull is true or b.manufacturerId not in :testManufacturerIds) " +
                "AND (:stateIdsNull is true or b.state.id in (:stateIds)) " +
                "AND (:batchNo is null or b.batchNo like :batchNo) " +
                "AND (:manufacturerIdsNull is true or b.manufacturerId in (:manufacturerIds)) " +
                "AND (:mfdStart is null or b.dateOfManufacture >= :mfdStart) " +
                "AND (:mfdEnd is null or b.dateOfManufacture <= :mfdEnd) " +
                "AND (:expStart is null or b.dateOfExpiry >= :expStart) " +
                "AND (:expEnd is null or b.dateOfExpiry <= :expEnd) ";
        TypedQuery<Long> query = em.createQuery(hql, Long.class)
                .setParameter("categoryIds", categoryIds);
        this.setSearchParams(query, searchRequest, testManufacturerIds, false);
        return query.getSingleResult();
    }

    private void setSearchParams(TypedQuery<?> query, SearchListRequest searchRequest) {
        Map<String, Object> map = new HashMap<>();
        if(searchRequest != null && searchRequest.getSearchCriteriaList() != null) {
            searchRequest.getSearchCriteriaList()
                    .forEach(searchCriteria -> map.put(searchCriteria.getKey(), searchCriteria.getValue()));
        }
        query.setParameter("batchNo", null);
        if (map.get("batchNo") != null && !Objects.equals(map.get("batchNo"), "")) {
            query.setParameter("batchNo", "%" + map.get("batchNo") + "%");
        }
        query.setParameter("manufacture_batchNumber", null);
        query.setParameter("batchPropertyName","manufacture_batchNumber");
        if (map.get("batchNo") != null && !Objects.equals(map.get("batchNo"), "")) {
            query.setParameter("manufacture_batchNumber", "%" + map.get("batchNo") + "%");
        }
        try {
            List<Long> stateIds = ((List<Integer>) map.get("stateIds")).stream()
                    .map(Integer::longValue).collect(Collectors.toList());
            query.setParameter("stateIds", stateIds);
            query.setParameter("stateIdsNull", false);
        } catch (Exception e) {
            query.setParameter("stateIds", new ArrayList<>());
            query.setParameter("stateIdsNull", true);
        }
        DaoHelper.setQueryDateRange("mfd", map.get("dateOfManufacture"), query);
        DaoHelper.setQueryDateRange("exp", map.get("dateOfExpiry"), query);
    }

    public List<Batch> findAllBatchesForInspection(Long categoryId, SearchListRequest searchListRequest, List<Long> testManufacturerIds, List<Long> batchIds, Integer pageNumber, Integer pageSize) {
        String hql = "SELECT b FROM Batch as b " +
                "WHERE  b.category.id = :categoryId " +
                "AND (:batchIdsNull is true or b.id in :batchIds) " +
                "AND (:testManufacturerIdsNull is true or b.manufacturerId not in :testManufacturerIds) " +
                "AND (:manufacturerIdsNull is true or b.manufacturerId in (:manufacturerIds)) " +
                "AND (:isBlocked is null or b.isBlocked = :isBlocked) " +
                "AND (:stateIdsNull is true or b.state.id in (:stateIds)) " +
                "AND (:batchNo is null or b.batchNo like :batchNo) " +
                "AND (:mfdStart is null or b.dateOfManufacture >= :mfdStart) " +
                "AND (:mfdEnd is null or b.dateOfManufacture <= :mfdEnd) " +
                "AND (:expStart is null or b.dateOfExpiry >= :expStart) " +
                "AND (:expEnd is null or b.dateOfExpiry <= :expEnd) "
                + " order by b.id asc";
        TypedQuery<Batch> query = em.createQuery(hql, Batch.class)
                .setParameter("categoryId", categoryId)
                .setParameter("batchIds", batchIds);
        query.setParameter("batchIdsNull", batchIds.isEmpty());
        this.setSearchParams(query, searchListRequest, testManufacturerIds, true);
        if (pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        return query.getResultList();
    }

    public Long gatCountForInspection(Long categoryId, SearchListRequest searchListRequest, List<Long> testManufacturerIds) {
        String hql = "SELECT count(b.id) FROM Batch as b " +
                "WHERE  b.category.id = :categoryId " +
                "AND (:testManufacturerIdsNull is true or b.manufacturerId not in :testManufacturerIds) " +
                "AND (:manufacturerIdsNull is true or b.manufacturerId in (:manufacturerIds)) " +
                "AND (:isBlocked is null or b.isBlocked = :isBlocked) " +
                "AND (:stateIdsNull is true or b.state.id in (:stateIds)) " +
                "AND (:batchNo is null or b.batchNo like :batchNo) " +
                "AND (:mfdStart is null or b.dateOfManufacture >= :mfdStart) " +
                "AND (:mfdEnd is null or b.dateOfManufacture <= :mfdEnd) " +
                "AND (:expStart is null or b.dateOfExpiry >= :expStart) " +
                "AND (:expEnd is null or b.dateOfExpiry <= :expEnd) "
                + " order by b.id desc";
        TypedQuery<Long> query = em.createQuery(hql, Long.class)
                .setParameter("categoryId", categoryId);
        this.setSearchParams(query, searchListRequest, testManufacturerIds, true);
        return query.getSingleResult();
    }

    private void setSearchParams(TypedQuery<?> query, SearchListRequest searchRequest, List<Long> testManufacturerIds, Boolean inspection) {
        Map<String, Object> map = new HashMap<>();
        if (searchRequest != null && searchRequest.getSearchCriteriaList() != null) {
            searchRequest.getSearchCriteriaList()
                    .forEach(searchCriteria -> map.put(searchCriteria.getKey(), searchCriteria.getValue()));
        }
        query.setParameter("batchNo", null);
        if (map.get("batchNo") != null && !Objects.equals(map.get("batchNo"), "")) {
            query.setParameter("batchNo", "%" + map.get("batchNo") + "%");
        }
        try {
            List<Long> stateIds = ((List<Integer>) map.get("stateIds")).stream()
                    .map(Integer::longValue).collect(Collectors.toList());
            query.setParameter("stateIds", stateIds);
            query.setParameter("stateIdsNull", false);
        } catch (Exception e) {
            query.setParameter("stateIds", new ArrayList<>());
            query.setParameter("stateIdsNull", true);
        }
        if (testManufacturerIds.isEmpty()) {
            query.setParameter("testManufacturerIdsNull", true);
            query.setParameter("testManufacturerIds", new ArrayList<Long>());
        } else {
            query.setParameter("testManufacturerIdsNull", false);
            query.setParameter("testManufacturerIds", testManufacturerIds);
        }
        try {
            List<Long> manufacturerIds = ((List<Integer>) map.get("manufacturerIds")).stream()
                    .map(Integer::longValue).collect(Collectors.toList());
            query.setParameter("manufacturerIds", manufacturerIds);
            query.setParameter("manufacturerIdsNull", false);
        } catch (Exception e) {
            query.setParameter("manufacturerIds", new ArrayList<>());
            query.setParameter("manufacturerIdsNull", true);
        }
        if (inspection) {
            query.setParameter("isBlocked", null);
            if (map.get("isBlocked") != null && !Objects.equals(map.get("isBlocked"), "")) {
                query.setParameter("isBlocked", map.get("isBlocked"));
            }
        }
        DaoHelper.setQueryDateRange("mfd", map.get("dateOfManufacture"), query);
        DaoHelper.setQueryDateRange("exp", map.get("dateOfExpiry"), query);
    }

    public void delete(Long id) {
        Session currentSession = em.unwrap(Session.class);
        Batch obj = currentSession.get(Batch.class, id);
        currentSession.delete(obj);
    }

    public Batch findByIdAndManufacturerId(Long id, Long manufacturerId) {
        String hql = "SELECT b FROM Batch as b " +
                "WHERE b.id = :id AND " +
                "b.manufacturerId = :manufacturerId ";
        TypedQuery<Batch> query = em.createQuery(hql, Batch.class)
                .setParameter("id", id)
                .setParameter("manufacturerId", manufacturerId);
        Batch result = null;
        try {
            result = query.getSingleResult();
        } catch (NoResultException exception) {
            throw new CustomException("Permission denied", HttpStatus.NO_CONTENT);
        }
        return result;
    }
    public Batch findByUUID(String uuid){
        TypedQuery<Batch> query = em.createQuery("from Batch b where b.uuid = :uuid", Batch.class)
                .setParameter("uuid", uuid);
        try {
            return query.getSingleResult();
        } catch (NoResultException exception) {
            throw new ResourceNotFoundException("Batch", "uuid", uuid);
        }
    }

    public List<BatchStateGeo> findAllAggregateByManufacturerIdAndCategoryId(int pageNumber, int pageSize, List<State> states) {
        String hql = "select " +
                "b.category.id as categoryId, " +
                "b.manufacturerId as manufacturerId," +
                "b.dateOfManufacture as date, " +
                "sum(case when b.isLabTested is true then (b.totalQuantity * b.uom.conversionFactorKg) else 0D end), " +
                "sum(case when b.isLabTested is true and (b.state.name in :testApprovedList ) then (b.totalQuantity * b.uom.conversionFactorKg) else 0D end), " +
                "sum(case when b.isLabTested is true and b.state.name = 'rejected' then (b.totalQuantity * b.uom.conversionFactorKg) else 0D end), " +
                "sum((select sum(case when l.sourceLot.isLabTested is true  then (l.sourceLot.totalQuantity * l.sourceLot.uom.conversionFactorKg) else 0D end) from b.batchLotMapping as l)), " +
                "sum((select sum(case when l.sourceLot.isLabTested is true  and (l.sourceLot.state.name = 'approved' ) then (l.sourceLot.totalQuantity * l.sourceLot.uom.conversionFactorKg) else 0D end) from b.batchLotMapping as l)), " +
                "sum((select sum(case when l.sourceLot.isLabTested is true and (l.sourceLot.state.name in :rejectedLotStates) then (l.sourceLot.totalQuantity * l.sourceLot.uom.conversionFactorKg) else 0D end) from b.batchLotMapping as l)), " +
                "sum(case when b.isLabTested is true and (b.state.name = 'partiallyDispatched' or b.state.name = 'batchToDispatch') then (b.remainingQuantity * b.uom.conversionFactorKg) else 0D end), " +
                "sum(case when b.isLabTested is false and (b.state.name = 'partiallyDispatched' or b.state.name = 'batchToDispatch') then (b.remainingQuantity * b.uom.conversionFactorKg) else 0D end), " +
                "sum(case when b.isLabTested is true and (b.state.name = 'partiallyDispatched' or b.state.name = 'fullyDispatched') then ((b.totalQuantity - b.remainingQuantity) * b.uom.conversionFactorKg) else 0D end), " +
                "sum(case when b.isLabTested is false and (b.state.name = 'partiallyDispatched' or b.state.name = 'fullyDispatched') then ((b.totalQuantity - b.remainingQuantity) * b.uom.conversionFactorKg) else 0D end), " +
                states.stream().map(d -> switch (d.getType()) {
                    case BATCH -> "sum(case when b.state.name = '" + d.getName() + "' then" +
                            " (b.totalQuantity * b.uom.conversionFactorKg)" +
                            " else 0D end)";
                    case LOT ->
                            "sum((select sum(l.sourceLot.totalQuantity * l.sourceLot.uom.conversionFactorKg) from b.batchLotMapping as l where l.sourceLot.state.name = '" + d.getName() + "'))";
                }).collect(Collectors.joining(", ")) +
                " from Batch b " +
                "where b.category.independentBatch is false " +
                "group by date, b.manufacturerId, b.category.id";
        TypedQuery<Object[]> query = em.createQuery(
                hql,
                Object[].class
        );

        return query
                .setParameter("testApprovedList", List.of("partiallyDispatched", "fullyDispatched", "batchToDispatch"))
                .setParameter("rejectedLotStates", List.of("toSendBackRejected", "sentBackRejected", "receivedRejected", "lotSampleRejected"))
                .setFirstResult((pageNumber-1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList()
                .stream()
                .map(d -> {
                    BatchStateGeo batchStateGeo = new BatchStateGeo();
                    batchStateGeo.setGeoStateId(new GeoStateId((Long) d[0], (Long) d[1], (Date) d[2]));
                    double receivedQuantity = 0d;
                    double batchTestedQuantity = d[3] == null ? 0d : (Double) d[3];
                    double batchTestPassedQuantity = d[4] == null ? 0d : (Double) d[4];
                    double batchTestRejectedQuantity = d[5] == null ? 0d : (Double) d[5];
                    double lotTestedQuantity = d[6] == null ? 0d : (Double) d[6];
                    double lotTestPassedQuantity = d[7] == null ? 0d : (Double) d[7];
                    double lotTestRejectedQuantity = d[8] == null ? 0d : (Double) d[8];
                    double availableTested = d[9] == null ? 0d : (Double) d[9];
                    double availableNotTested = d[10] == null ? 0d : (Double) d[10];
                    double dispatchedTested = d[11] == null ? 0d : (Double) d[11];
                    double dispatchedNotTested = d[12] == null ? 0d : (Double) d[12];

                    double totalProductionQuantity = 0d;
                    for (int i = 0; i < states.size(); i++) {
                        Double quantity = d[13 + i] == null ? 0d : (Double) d[13 + i];
                        if (quantity == null) quantity = 0D;
                        if (states.get(i).getType() == StateType.BATCH) {
                            switch (states.get(i).getName()) {
                                case "sentBatchSampleToLabTest" ->
                                        batchStateGeo.setBatchSampleInTransitQuantity(quantity);
                                case "batchSampleInLab" -> batchStateGeo.setBatchSampleTestInProgressQuantity(quantity);
                                case "rejected", "batchSampleRejected" -> batchStateGeo.addRejectedQuantity(quantity);
                                case "batchToDispatch", "partiallyDispatched", "fullyDispatched" ->
                                        batchStateGeo.setProducedQuantity(quantity + batchStateGeo.getProducedQuantity());
                            }
                            totalProductionQuantity += quantity;
                        } else {
                            switch (states.get(i).getName()) {
                                case "dispatched" -> batchStateGeo.setInTransitQuantity(quantity);
                                case "lotSampleRejected" -> batchStateGeo.setLotSampleRejectedQuantity(quantity);
                                case "sentLotSampleToLabTest" -> batchStateGeo.setLotSampleInTransitQuantity(quantity);
                                case "lotSampleInLab" -> batchStateGeo.setLotSampleTestInProgressQuantity(quantity);
                                case "approved" -> batchStateGeo.setApprovedQuantity(quantity);
                                case "toSendBackRejected" -> {
                                    batchStateGeo.addLotRejected(quantity);
                                    batchStateGeo.addRejectedQuantity(quantity);
                                }
                                case "sentBackRejected" -> {
                                    batchStateGeo.addLotRejected(quantity);
                                    batchStateGeo.setRejectedInTransitQuantity(quantity);
                                    batchStateGeo.addRejectedQuantity(quantity);
                                }
                                case "receivedRejected" -> {
                                    batchStateGeo.addLotRejected(quantity);
                                    batchStateGeo.setReceivedRejectedQuantity(quantity);
                                    batchStateGeo.addRejectedQuantity(quantity);
                                }
                            }
                            if(!states.get(i).getName().equals("dispatched")) receivedQuantity += quantity;
                        }
                    }
                    batchStateGeo.setBatchTestedQuantity(batchTestedQuantity);
                    batchStateGeo.setBatchTestApprovedQuantity(batchTestPassedQuantity);
                    batchStateGeo.setBatchTestRejectedQuantity(batchTestRejectedQuantity);
                    batchStateGeo.setLotTestedQuantity(lotTestedQuantity);
                    batchStateGeo.setTotalQuantity(totalProductionQuantity);
                    batchStateGeo.setInProductionQuantity(totalProductionQuantity - batchStateGeo.getProducedQuantity());
                    batchStateGeo.setReceivedQuantity(receivedQuantity);
                    batchStateGeo.setLotTestApprovedQuantity(lotTestPassedQuantity);
                    batchStateGeo.setLotTestRejectedQuantity(lotTestRejectedQuantity);
                    batchStateGeo.setAvailableTested(availableTested);
                    batchStateGeo.setAvailableNotTested(availableNotTested);
                    batchStateGeo.setDispatchedTested(dispatchedTested);
                    batchStateGeo.setDispatchedNotTested(dispatchedNotTested);
                    return batchStateGeo;
                })
                .toList();
    }
}