package com.beehyv.fortification.dao;

import com.beehyv.fortification.dto.requestDto.DashboardRequestDto;
import com.beehyv.fortification.dto.requestDto.SearchListRequest;
import com.beehyv.fortification.dto.responseDto.DashboardTestingResponseDto;
import com.beehyv.fortification.dto.responseDto.DashboardWarehouseResponseDto;
import com.beehyv.fortification.dto.responseDto.EmpanelledAggregatesResponseDto;
import com.beehyv.fortification.entity.*;
import com.beehyv.fortification.enums.GeoManufacturerTestingResponseType;
import com.beehyv.fortification.enums.ManufacturerCategoryAction;
import com.beehyv.fortification.helper.DaoHelper;
import com.beehyv.parent.exceptions.CustomException;
import com.beehyv.parent.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class LotDao extends BaseDao<Lot> {
    private final EntityManager em;

    public LotDao(EntityManager em) {
        super(em, Lot.class);
        this.em = em;
    }

    public Lot findByIdAndManufacturerId(Long id, Long manufacturerId){
        try{
            Lot lot = em.createQuery("select l from Lot as l where l.id = :lotId", Lot.class)
                    .setParameter("lotId", id)
                    .getSingleResult();
            boolean access = Objects.equals(lot.getManufacturerId(), manufacturerId) || Objects.equals(lot.getTargetManufacturerId(), manufacturerId)
                    || lot.getTargetBatchMapping().stream()
                    .map(BatchLotMapping::getSourceLot)
                    .anyMatch(s -> (Objects.equals(s.getManufacturerId(), manufacturerId) || Objects.equals(s.getTargetManufacturerId(), manufacturerId)))
                    || lot.getSourceBatchMapping().stream().map(BatchLotMapping::getTargetLot).filter(Objects::nonNull)
                    .anyMatch(t -> (Objects.equals(t.getManufacturerId(), manufacturerId) || Objects.equals(t.getTargetManufacturerId(), manufacturerId)));
            if (access) return lot;
            throw new CustomException("Access Denied", HttpStatus.BAD_REQUEST);
        }catch (NoResultException exception) {
            throw new CustomException("Permission denied", HttpStatus.NO_CONTENT);
        }
    }

    public Long getCount(Long batchId) {
        return (Long) em.createQuery("select count (b.sourceLot.id) from BatchLotMapping as b where b.batch.id = :batchId")
                .setParameter("batchId", batchId)
                .getSingleResult();
    }

    public List<Lot> findAllByBatchId(Long batchId, Integer pageNumber, Integer pageSize) {
        List<Lot> obj = null;
        TypedQuery<Lot> query = em
                .createQuery("SELECT b.sourceLot from BatchLotMapping b where b.batch.id = :batchId  order by b.id desc", Lot.class)
                .setParameter("batchId", batchId);
        if (pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        obj = query.getResultList();
        return obj;
    }

    public List<Lot> findAllByCategoryId(Long manufacturerId, Long categoryId, SearchListRequest searchRequest) {
        String hql = "SELECT distinct b.sourceLot FROM BatchLotMapping as b " +
                "left join LotProperty lp on lp.lot.id = b.sourceLot.id " +
                "WHERE (:categoryId is 0L or b.sourceLot.category.id = :categoryId) AND " +
                "((b.sourceLot.targetManufacturerId = :manufacturerId) " +
                "OR (b.sourceLot.manufacturerId = :manufacturerId)) " +
                "AND ((:batchNo is null or b.sourceLot.lotNo like :batchNo) " +
                "or (:batchNo is null or (lp.name = 'manufacture_lotNumber' and lp.value like :batchNo)))" +
                "AND (:stateIdsNull is true or b.sourceLot.state.id in (:stateIds)) " +
                "AND (:receiveStart is null or b.sourceLot.dateOfReceiving >= :receiveStart) " +
                "AND (:receiveEnd is null or b.sourceLot.dateOfReceiving <= :receiveEnd) " +
                "AND (:dateOfManufactureStart is null or b.batch.dateOfManufacture >= :dateOfManufactureStart) " +
                "AND (:dateOfManufactureEnd is null or b.batch.dateOfManufacture <= :dateOfManufactureEnd) " +
                "AND (:expStart is null or b.batch.dateOfExpiry >= :expStart) " +
                "AND (:expEnd is null or b.batch.dateOfExpiry <= :expEnd) " +
                "order by b.sourceLot.id asc";
        TypedQuery<Lot> query = em.createQuery(hql, Lot.class)
                .setParameter("categoryId", categoryId)
                .setParameter("manufacturerId", manufacturerId);
        this.setSearchParams(query, searchRequest, true);
        return query.getResultList();
    }

    public List<Lot> findAllByCategoryIdForTargetLot(Long manufacturerId, Long categoryId, SearchListRequest searchRequest) {
        String hql = "SELECT DISTINCT b.targetLot FROM BatchLotMapping as b " +
                "left join LotProperty lp on lp.lot.id = b.targetLot.id " +
                "WHERE (:categoryId is 0L or b.targetLot.category.id = :categoryId) AND " +
                "((b.targetLot.targetManufacturerId = :manufacturerId) " +
                "OR (b.targetLot.manufacturerId = :manufacturerId)) " +
                "AND ((:batchNo is null or b.targetLot.lotNo like :batchNo) " +
                "or (:batchNo is null or (lp.name = 'manufacture_lotNumber' and lp.value like :batchNo)))" +
                "AND (:stateIdsNull is true or b.targetLot.state.id in (:stateIds)) " +
                "AND (:receiveStart is null or b.targetLot.dateOfReceiving >= :receiveStart) " +
                "AND (:receiveEnd is null or b.targetLot.dateOfReceiving <= :receiveEnd) " +
                "order by b.targetLot.id asc";
        TypedQuery<Lot> query = em.createQuery(hql, Lot.class)
                .setParameter("categoryId", categoryId)
                .setParameter("manufacturerId", manufacturerId);
        this.setSearchParams(query, searchRequest, false);
        return query.getResultList();
    }
    public List<Lot> getLotInventory(Long manufacturerId, Long categoryId, SearchListRequest searchRequest, Integer pageNumber, Integer pageSize, Boolean approvedSourceLots) {
        String hql = "SELECT distinct b.sourceLot FROM BatchLotMapping as b " +
                "WHERE b.sourceLot.category.id = :categoryId AND " +
                "(b.sourceLot.targetManufacturerId = :manufacturerId) " +
                "AND b.sourceLot.isReceivedAtTarget is true " +
                "AND b.sourceLot.remainingQuantity > 0 " +
                "AND (:stateIdsNull is true or b.sourceLot.state.id in (:stateIds)) " +
                "AND (:batchNo is null or b.sourceLot.lotNo like :batchNo) " +
                "AND (:receiveStart is null or b.sourceLot.dateOfReceiving >= :receiveStart) " +
                "AND (:receiveEnd is null or b.sourceLot.dateOfReceiving <= :receiveEnd) " +
                "AND (:dateOfManufactureStart is null or b.batch.dateOfManufacture >= :dateOfManufactureStart) " +
                "AND (:dateOfManufactureEnd is null or b.batch.dateOfManufacture <= :dateOfManufactureEnd) " +
                "AND (:expStart is null or b.batch.dateOfExpiry >= :expStart) " +
                "AND (:expEnd is null or b.batch.dateOfExpiry <= :expEnd) " +
                "AND (:lotStateNull is true or b.sourceLot.state.name = 'approved')" +
                " order by b.id asc";
        TypedQuery<Lot> query = em.createQuery(hql, Lot.class)
                .setParameter("categoryId", categoryId)
                .setParameter("manufacturerId", manufacturerId);
        this.setSearchParams(query, searchRequest, true);
        query.setParameter("lotStateNull", true);
        if (approvedSourceLots != null && approvedSourceLots.equals(true)) {
            query.setParameter("lotStateNull", false);
        }
        return query.getResultList();
    }


    public List<Lot> getTargetLotInventory(Long manufacturerId, Long categoryId, SearchListRequest searchRequest, Integer pageNumber, Integer pageSize, Boolean approvedSourceLots) {
        String hql = "SELECT distinct b.targetLot FROM BatchLotMapping as b " +
                "WHERE b.targetLot.category.id = :categoryId AND " +
                "(b.targetLot.targetManufacturerId = :manufacturerId) " +
                "AND b.targetLot.isReceivedAtTarget is true " +
                "AND b.targetLot.remainingQuantity > 0 " +
                "AND (:stateIdsNull is true or b.targetLot.state.id in (:stateIds)) " +
                "AND (:batchNo is null or b.targetLot.lotNo like :batchNo) " +
                "AND (:receiveStart is null or b.targetLot.dateOfReceiving >= :receiveStart) " +
                "AND (:receiveEnd is null or b.targetLot.dateOfReceiving <= :receiveEnd) " +
                "AND (:lotStateNull is true or b.targetLot.state.name = 'approved')" +
                " order by b.id desc";
        TypedQuery<Lot> query = em.createQuery(hql, Lot.class)
                .setParameter("categoryId", categoryId)
                .setParameter("manufacturerId", manufacturerId);
        this.setSearchParams(query, searchRequest, false);
        this.setSearchParams(query, searchRequest, false);
        query.setParameter("lotStateNull", true);
        if (approvedSourceLots != null && approvedSourceLots.equals(true)) {
            query.setParameter("lotStateNull", false);
        }
        return query.getResultList();
    }
    public Long getLotInventoryCount(Long manufacturerId, Long categoryId, SearchListRequest searchRequest, Boolean approvedSourceLots) {
        String hql = "SELECT count(distinct (b.sourceLot)) FROM BatchLotMapping as b " +
                "WHERE b.sourceLot.category.id = :categoryId AND " +
                "(b.sourceLot.targetManufacturerId = :manufacturerId) " +
                "AND b.sourceLot.isReceivedAtTarget is true " +
                "AND b.sourceLot.remainingQuantity > 0 " +
                "AND (:stateIdsNull is true or b.sourceLot.state.id in (:stateIds)) " +
                "AND (:batchNo is null or b.sourceLot.lotNo like :batchNo) " +
                "AND (:receiveStart is null or b.sourceLot.dateOfReceiving >= :receiveStart) " +
                "AND (:receiveEnd is null or b.sourceLot.dateOfReceiving <= :receiveEnd) " +
                "AND (:dateOfManufactureStart is null or b.batch.dateOfManufacture >= :dateOfManufactureStart) " +
                "AND (:dateOfManufactureEnd is null or b.batch.dateOfManufacture <= :dateOfManufactureEnd) " +
                "AND (:expStart is null or b.batch.dateOfExpiry >= :expStart) " +
                "AND (:expEnd is null or b.batch.dateOfExpiry <= :expEnd) " +
                "AND (:lotStateNull is true or b.sourceLot.state.name = 'approved')" +
                " order by b.id desc";
        TypedQuery<Long> query = em.createQuery(hql, Long.class)
                .setParameter("categoryId", categoryId)
                .setParameter("manufacturerId", manufacturerId);
        this.setSearchParams(query, searchRequest, true);
        query.setParameter("lotStateNull", true);
        if (approvedSourceLots != null && approvedSourceLots.equals(true)) {
            query.setParameter("lotStateNull", false);
        }
        return query.getSingleResult();
    }

    public Long getTargetLotInventoryCount(Long manufacturerId, Long categoryId, SearchListRequest searchRequest, Boolean approvedSourceLots) {
        String hql = "SELECT count(distinct (b.targetLot)) FROM BatchLotMapping as b " +
                "WHERE b.targetLot.category.id = :categoryId AND " +
                "(b.targetLot.targetManufacturerId = :manufacturerId) " +
                "AND b.targetLot.isReceivedAtTarget is true " +
                "AND b.targetLot.remainingQuantity > 0 " +
                "AND (:stateIdsNull is true or b.targetLot.state.id in (:stateIds)) " +
                "AND (:batchNo is null or b.targetLot.lotNo like :batchNo) " +
                "AND (:receiveStart is null or b.targetLot.dateOfReceiving >= :receiveStart) " +
                "AND (:receiveEnd is null or b.targetLot.dateOfReceiving <= :receiveEnd) " +
                "AND (:lotStateNull is true or b.targetLot.state.name = 'approved')" +
                " order by b.id desc";
        TypedQuery<Long> query = em.createQuery(hql, Long.class)
                .setParameter("categoryId", categoryId)
                .setParameter("manufacturerId", manufacturerId);
        this.setSearchParams(query, searchRequest, false);
        query.setParameter("lotStateNull", true);
        if (approvedSourceLots != null && approvedSourceLots.equals(true)) {
            query.setParameter("lotStateNull", false);
        }
        return query.getSingleResult();
    }

    public List<Object[]> findPremixDispatchedToFrk(Long categoryId, DashboardRequestDto dto, Long sourceManufacturerId, List<Long> targetManufacturerIds, List<String> lotApprovalPendingStates, List<Long> testManufacturerIds, List<String> lotRejectedStates){
        return em.createQuery(
                        "Select l.manufacturerId, " +
                                "sum(case when l.state.name = 'approved' then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name in :lotRejectedStates then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name in :lotApprovalPendingStates then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name = 'approved' then (l.remainingQuantity * l.uom.conversionFactorKg) else 0D end) " +
                                "from Lot l " +
                                "where l.category.id = :categoryId " +
                                "and l.targetManufacturerId = :sourceManufacturerId " +
                                "and (:targetManufacturerIdsNull is true or l.targetManufacturerId in :targetManufacturerIds) " +
                                "and (:targetDistrictGeoId is null or l.targetDistrictGeoId = :targetDistrictGeoId) " +
                                "and (:targetStateGeoId is null or l.targetStateGeoId = :targetStateGeoId) " +
                                "and (:testManufacturerIdsNull is true or l.manufacturerId not in :testManufacturerIds) " +
                                "and (l.dateOfDispatch >= :fromDate and l.dateOfDispatch <= :toDate) " +
                                "group by l.targetManufacturerId", Object[].class)
                .setParameter("categoryId", categoryId)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("targetDistrictGeoId", dto.getTargetDistrictGeoId())
                .setParameter("targetStateGeoId", dto.getTargetStateGeoId())
                .setParameter("sourceManufacturerId", sourceManufacturerId)
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("lotRejectedStates", lotRejectedStates)
                .setParameter("targetManufacturerIds", targetManufacturerIds)
                .setParameter("targetManufacturerIdsNull", targetManufacturerIds==null)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("toDate", dto.getToDate())
                .setParameter("lotApprovalPendingStates", lotApprovalPendingStates)
                .getResultList();
    }

    private void setSearchParams(TypedQuery<?> query, SearchListRequest searchRequest, Boolean isSourceLot) {
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
        DaoHelper.setQueryDateRange("receive", map.get("dateOfReceiving"), query);
        if (isSourceLot) {
            DaoHelper.setQueryDateRange("dateOfManufacture", map.get("dateOfManufacture"), query);
            DaoHelper.setQueryDateRange("exp", map.get("dateOfExpiry"), query);
        }
    }

    public List<Lot> findAllByCategoryIds(SearchListRequest searchRequest, List<Long> userCategoryIds, Integer pageNumber, Integer pageSize, List<Long> testManufacturerIds) {

        String hql = "SELECT b.sourceLot FROM BatchLotMapping as b " +
                "WHERE (:categoryIdsIsNull is true or b.sourceLot.category.id in (:categoryIds)) " +
                "AND (:testManufacturerIdsNull is true or b.sourceLot.manufacturerId not in :testManufacturerIds) " +
                "AND (:targetManufacturerIdsNull is true or b.sourceLot.targetManufacturerId in (:targetManufacturerIds)) " +
                "AND (:manufacturerIdsNull is true or b.sourceLot.manufacturerId in (:manufacturerIds)) " +
                "AND (:batchNo is null or b.sourceLot.lotNo like :batchNo) " +
                "AND (:stateIdsNull is true or b.sourceLot.state.id in (:stateIds)) " +
                "AND (:receiveStart is null or b.sourceLot.dateOfReceiving >= :receiveStart) " +
                "AND (:receiveEnd is null or b.sourceLot.dateOfReceiving <= :receiveEnd) " +
                "AND (:dateOfManufactureStart is null or b.batch.dateOfManufacture >= :dateOfManufactureStart) " +
                "AND (:dateOfManufactureEnd is null or b.batch.dateOfManufacture <= :dateOfManufactureEnd) " +
                "AND (:expStart is null or b.batch.dateOfExpiry >= :expStart) " +
                "AND (:expEnd is null or b.batch.dateOfExpiry <= :expEnd) " +
                " order by b.id asc";
        TypedQuery<Lot> query = em.createQuery(hql, Lot.class)
                .setParameter("categoryIds", userCategoryIds)
                .setParameter("categoryIdsIsNull",userCategoryIds.isEmpty());
        this.setSearchParams(query, searchRequest, testManufacturerIds, false);
        if (pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        return query.getResultList();
    }


    public Long getCount(Long manufacturerId, Long categoryId, SearchListRequest searchRequest) {
        String hql = "SELECT count(distinct b.sourceLot.id) FROM BatchLotMapping as b " +
                "left join LotProperty lp on lp.lot.id = b.sourceLot.id " +
                "WHERE (:categoryId is 0L or b.sourceLot.category.id = :categoryId) AND " +
                "((b.sourceLot.targetManufacturerId = :manufacturerId) " +
                "OR (b.sourceLot.manufacturerId = :manufacturerId)) " +
                "AND ((:batchNo is null or b.sourceLot.lotNo like :batchNo) " +
                "or (:batchNo is null or (lp.name = 'manufacture_lotNumber' and lp.value like :batchNo)))" +
                "AND (:stateIdsNull is true or b.sourceLot.state.id in (:stateIds)) " +
                "AND (:receiveStart is null or b.sourceLot.dateOfReceiving >= :receiveStart) " +
                "AND (:receiveEnd is null or b.sourceLot.dateOfReceiving <= :receiveEnd) " +
                "AND (:dateOfManufactureStart is null or b.batch.dateOfManufacture >= :dateOfManufactureStart) " +
                "AND (:dateOfManufactureEnd is null or b.batch.dateOfManufacture <= :dateOfManufactureEnd) " +
                "AND (:expStart is null or b.batch.dateOfExpiry >= :expStart) " +
                "AND (:expEnd is null or b.batch.dateOfExpiry <= :expEnd) ";
        TypedQuery<Long> query = em.createQuery(hql, Long.class)
                .setParameter("categoryId", categoryId)
                .setParameter("manufacturerId", manufacturerId);
        this.setSearchParams(query, searchRequest, true);
        return query.getSingleResult();
    }

    public Long getCountForTargetLot(Long manufacturerId, Long categoryId, SearchListRequest searchRequest) {
        String hql = "SELECT count( distinct b.targetLot.id) FROM BatchLotMapping as b " +
                "left join LotProperty lp on lp.lot.id = b.targetLot.id " +
                "WHERE (:categoryId is 0L or b.targetLot.category.id = :categoryId) AND " +
                "((b.targetLot.targetManufacturerId = :manufacturerId) " +
                "OR (b.targetLot.manufacturerId = :manufacturerId)) " +
                "AND ((:batchNo is null or b.targetLot.lotNo like :batchNo) " +
                "or (:batchNo is null or (lp.name = 'manufacture_lotNumber' and lp.value like :batchNo)))" +
                "AND (:stateIdsNull is true or b.targetLot.state.id in (:stateIds)) " +
                "AND (:receiveStart is null or b.targetLot.dateOfReceiving >= :receiveStart) " +
                "AND (:receiveEnd is null or b.targetLot.dateOfReceiving <= :receiveEnd) ";
        TypedQuery<Long> query = em.createQuery(hql, Long.class)
                .setParameter("categoryId", categoryId)
                .setParameter("manufacturerId", manufacturerId);
        this.setSearchParams(query, searchRequest, false);
        return query.getSingleResult();
    }

    public Long getCount(SearchListRequest searchRequest, List<Long> categoryIds, List<Long> testManufacturerIds) {
        String hql = "SELECT count(b.sourceLot.id) FROM BatchLotMapping as b " +
                "WHERE (b.sourceLot.category.id in (:categoryIds)) " +
                "AND (:testManufacturerIdsNull is true or b.sourceLot.manufacturerId not in :testManufacturerIds) " +
                "AND (:targetManufacturerIdsNull is true or b.sourceLot.targetManufacturerId in (:targetManufacturerIds)) " +
                "AND (:manufacturerIdsNull is true or b.sourceLot.manufacturerId in (:manufacturerIds)) " +
                "AND (:batchNo is null or b.sourceLot.lotNo like :batchNo) " +
                "AND (:stateIdsNull is true or b.sourceLot.state.id in (:stateIds)) " +
                "AND (:receiveStart is null or b.sourceLot.dateOfReceiving >= :receiveStart) " +
                "AND (:receiveEnd is null or b.sourceLot.dateOfReceiving <= :receiveEnd) " +
                "AND (:dateOfManufactureStart is null or b.batch.dateOfManufacture >= :dateOfManufactureStart) " +
                "AND (:dateOfManufactureEnd is null or b.batch.dateOfManufacture <= :dateOfManufactureEnd) " +
                "AND (:expStart is null or b.batch.dateOfExpiry >= :expStart) " +
                "AND (:expEnd is null or b.batch.dateOfExpiry <= :expEnd) ";
        TypedQuery<Long> query = em.createQuery(hql, Long.class)
                .setParameter("categoryIds", categoryIds);
        this.setSearchParams(query, searchRequest, testManufacturerIds, false);
        return query.getSingleResult();
    }

    public List<Lot> findAllBySourceCategoryId(Long manufacturerId, Long categoryId, String search, Integer pageNumber, Integer pageSize) {
        String hql = "SELECT distinct(l.sourceLot) FROM BatchLotMapping l " +
                "where l.sourceLot.category.id = :categoryId " +
                "and l.sourceLot.targetManufacturerId = :manufacturerId " +
                "and l.sourceLot.state.name = 'approved' " +
                "and l.sourceLot.remainingQuantity > 0L " +
                "and (:search is null or l.sourceLot.lotNo like :search)" +
                " order by l.id desc";
        TypedQuery<Lot> query = em.createQuery(hql, Lot.class)
                .setParameter("categoryId", categoryId)
                .setParameter("manufacturerId", manufacturerId);
        query.setParameter("search", null);
        if (search != null && !Objects.equals(search, "")) {
            query.setParameter("search", "%" + search + "%");
        }
        if (pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        return query.getResultList();
    }

    public List<Lot> findAllTargetLotsBySourceCategoryId(Long manufacturerId, Long categoryId, String search, Integer pageNumber, Integer pageSize) {
        String hql = "SELECT distinct(l.targetLot) FROM BatchLotMapping l " +
                "where l.targetLot.category.id = :categoryId " +
                "and l.targetLot.targetManufacturerId = :manufacturerId " +
                "and l.targetLot.state.name = 'approved' " +
                "and l.targetLot.remainingQuantity > 0L " +
                "and (:search is null or l.targetLot.lotNo like :search)" +
                " order by l.id desc";
        TypedQuery<Lot> query = em.createQuery(hql, Lot.class)
                .setParameter("categoryId", categoryId)
                .setParameter("manufacturerId", manufacturerId);
        query.setParameter("search", null);
        if (search != null && !Objects.equals(search, "")) {
            query.setParameter("search", "%" + search + "%");
        }
        if (pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        return query.getResultList();
    }

    public List<Lot> findAllLotsForInspection(Long categoryId, SearchListRequest searchListRequest, List<Long> testManufacturerIds, List<Long> lotIds, Integer pageNumber, Integer pageSize) {
        String hql = "SELECT l.sourceLot FROM BatchLotMapping as l " +
                "WHERE  l.sourceLot.category.id = :categoryId " +
                "AND (:lotIdsNull is true or l.sourceLot.id in :lotIds) " +
                "AND (:testManufacturerIdsNull is true or l.sourceLot.targetManufacturerId not in :testManufacturerIds) " +
                "AND (:targetManufacturerIdsNull is true or l.sourceLot.targetManufacturerId in (:targetManufacturerIds)) " +
                "AND (:manufacturerIdsNull is true or l.sourceLot.manufacturerId in (:manufacturerIds)) " +
                "AND (:isBlocked is null or l.sourceLot.isBlocked = :isBlocked) " +
                "AND (:stateIdsNull is true or l.sourceLot.state.id in (:stateIds)) " +
                "AND (:batchNo is null or l.sourceLot.lotNo like :batchNo) " +
                "AND (:receiveStart is null or l.sourceLot.dateOfReceiving >= :receiveStart) " +
                "AND (:receiveEnd is null or l.sourceLot.dateOfReceiving <= :receiveEnd) " +
                "AND (:dateOfManufactureStart is null or l.batch.dateOfManufacture >= :dateOfManufactureStart) " +
                "AND (:dateOfManufactureEnd is null or l.batch.dateOfManufacture <= :dateOfManufactureEnd) " +
                "AND (:expStart is null or l.batch.dateOfExpiry >= :expStart) " +
                "AND (:expEnd is null or l.batch.dateOfExpiry <= :expEnd) " +
                " order by l.id asc";
        TypedQuery<Lot> query = em.createQuery(hql, Lot.class)
                .setParameter("categoryId", categoryId)
                .setParameter("lotIds", lotIds);
        query.setParameter("lotIdsNull", lotIds.isEmpty());
        this.setSearchParams(query, searchListRequest, testManufacturerIds, true);
        if (pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        return query.getResultList();
    }

    public Long getCountForInspection(Long categoryId, SearchListRequest searchListRequest, List<Long> testManufacturerIds) {
        String hql = "SELECT count(l.sourceLot.id) FROM BatchLotMapping as l " +
                "WHERE  l.sourceLot.category.id = :categoryId " +
                "AND (:testManufacturerIdsNull is true or l.sourceLot.targetManufacturerId not in :testManufacturerIds) " +
                "AND (:targetManufacturerIdsNull is true or l.sourceLot.targetManufacturerId in (:targetManufacturerIds)) " +
                "AND (:manufacturerIdsNull is true or l.sourceLot.manufacturerId in (:manufacturerIds)) " +
                "AND (:isBlocked is null or l.sourceLot.isBlocked = :isBlocked) " +
                "AND (:stateIdsNull is true or l.sourceLot.state.id in (:stateIds)) " +
                "AND (:batchNo is null or l.sourceLot.lotNo like :batchNo) " +
                "AND (:receiveStart is null or l.sourceLot.dateOfReceiving >= :receiveStart) " +
                "AND (:receiveEnd is null or l.sourceLot.dateOfReceiving <= :receiveEnd) " +
                "AND (:dateOfManufactureStart is null or l.batch.dateOfManufacture >= :dateOfManufactureStart) " +
                "AND (:dateOfManufactureEnd is null or l.batch.dateOfManufacture <= :dateOfManufactureEnd) " +
                "AND (:expStart is null or l.batch.dateOfExpiry >= :expStart) " +
                "AND (:expEnd is null or l.batch.dateOfExpiry <= :expEnd) " +
                " order by l.id desc";
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
        try {
            List<Long> manufacturerIds = ((List<Integer>) map.get("targetManufacturerIds")).stream()
                    .map(Integer::longValue).collect(Collectors.toList());
            query.setParameter("targetManufacturerIds", manufacturerIds);
            query.setParameter("targetManufacturerIdsNull", false);
        } catch (Exception e) {
            query.setParameter("targetManufacturerIds", new ArrayList<>());
            query.setParameter("targetManufacturerIdsNull", true);
        }
        if (inspection) {
            query.setParameter("isBlocked", null);
            if (map.get("isBlocked") != null && !Objects.equals(map.get("isBlocked"), "")) {
                query.setParameter("isBlocked", map.get("isBlocked"));
            }
        }
        DaoHelper.setQueryDateRange("receive", map.get("dateOfReceiving"), query);
        DaoHelper.setQueryDateRange("dateOfManufacture", map.get("dateOfManufacture"), query);
        DaoHelper.setQueryDateRange("exp", map.get("dateOfExpiry"), query);
    }

    public List<Lot> findAllByIds(List<Long> ids) {
        return Optional.ofNullable(em.createQuery("FROM Lot l WHERE l.id IN (:ids)", Lot.class)
                .setParameter("ids", ids)
                .getResultList()).orElseThrow(() -> new ResourceNotFoundException("Lot", "ids", ids));
    }

    public Lot findByUUID(String uuid) {
        TypedQuery<Lot> query = em.createQuery("select b.sourceLot from BatchLotMapping b where b.sourceLot.uuid = :uuid", Lot.class)
                .setParameter("uuid", uuid);
        try {
            return query.getSingleResult();
        } catch (NoResultException exception) {
            throw new ResourceNotFoundException("Lot", "uuid", uuid);
        }
    }

    public Long getCount(Long manufacturerId, Long categoryId, String search) {
        String hql = "SELECT count(distinct(l.sourceLot.id)) FROM BatchLotMapping as l " +
                "where l.sourceLot.category.id = :categoryId " +
                "and l.sourceLot.targetManufacturerId = :manufacturerId " +
                "and l.sourceLot.state.name = 'approved' " +
                "and l.sourceLot.remainingQuantity > 0L " +
                "and (:search is null or l.sourceLot.lotNo like :search)" +
                " order by l.id desc";
        TypedQuery<Long> query = em.createQuery(hql, Long.class)
                .setParameter("categoryId", categoryId)
                .setParameter("manufacturerId", manufacturerId);
        query.setParameter("search", null);
        if (search != null && !Objects.equals(search, "")) {
            query.setParameter("search", "%" + search + "%");
        }
        return query.getSingleResult();
    }

    public Long getTargetLotsCount(Long manufacturerId, Long categoryId, String search) {
        String hql = "SELECT count(distinct(l.targetLot.id)) FROM BatchLotMapping as l " +
                "where l.targetLot.category.id = :categoryId " +
                "and l.targetLot.targetManufacturerId = :manufacturerId " +
                "and l.targetLot.state.name = 'approved' " +
                "and l.targetLot.remainingQuantity > 0L " +
                "and (:search is null or l.targetLot.lotNo like :search)" +
                " order by l.id desc";
        TypedQuery<Long> query = em.createQuery(hql, Long.class)
                .setParameter("categoryId", categoryId)
                .setParameter("manufacturerId", manufacturerId);
        query.setParameter("search", null);
        if (search != null && !Objects.equals(search, "")) {
            query.setParameter("search", "%" + search + "%");
        }
        return query.getSingleResult();
    }

    public Double[] getQuantitySum(Long manufacturerId, Long categoryId) {
        Object objects = em.createQuery("SELECT sum(l.sourceLot.totalQuantity) as totalQantity, sum(l.sourceLot.remainingQuantity) as remaningQuantity" +
                        " FROM BatchLotMapping l " +
                        "WHERE l.sourceLot.targetManufacturerId = :manufacturerId AND l.sourceLot.category.id = :categoryId")
                .setParameter("categoryId", categoryId)
                .setParameter("manufacturerId", manufacturerId)
                .getSingleResult();
        Double[] result = new Double[2];
        result[0] = (Double) (((Object[]) objects)[0]);
        result[1] = (Double) (((Object[]) objects)[1]);
        return result;
    }

    public List<LotStateGeo> findAllAggregateByManufacturerIdAndCategoryId(int pageNumber, int pageSize, List<State> states) {
        return em.createQuery(
                        "select " +
                                "l.sourceLot.category.id as categoryId, " +
                                "l.sourceLot.targetManufacturerId as manufacturerId, " +
                                "l.sourceLot.sourceDistrictGeoId as districtGeoId, " +
                                "l.sourceLot.dateOfDispatch as date, " +
                                "sum(case when l.sourceLot.state.name = 'approved' then (l.sourceLot.remainingQuantity * l.sourceLot.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.sourceLot.isLabTested is true then (l.sourceLot.totalQuantity * l.sourceLot.uom.conversionFactorKg) else 0D end), " +
                                states.stream()
                                        .map(d -> "sum(case when l.sourceLot.state.name = '" + d.getName() + "' then (l.sourceLot.totalQuantity * l.sourceLot.uom.conversionFactorKg) else 0D end)")
                                        .collect(Collectors.joining(", ")) +
                                " from BatchLotMapping l " +
                                "where l.sourceLot.category.independentBatch is false " +
                                "group by date, l.sourceLot.category.id, l.sourceLot.targetManufacturerId, l.sourceLot.sourceDistrictGeoId ",
                        Object[].class
                )
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList()
                .stream()
                .map(d -> {
                    LotStateGeo lotStateGeo = new LotStateGeo();
                    lotStateGeo.setGeoStateId(new GeoStateId((Long) d[0], (Long) d[1], (Date) d[3]));
                    double remainingQuantity = (d[3] == null) ? 0d : (Double) d[4];
                    double testedQuantity = (d[4] == null) ? 0d : (Double) d[5];
                    double receivedQuantity = 0d;
                    for (int i = 0; i < states.size(); i++) {
                        Double quantity = (d[6 + i] == null) ? 0d : (Double) d[6 + i];
                        switch (states.get(i).getName()) {
                            case "dispatched" -> lotStateGeo.setInTransitQuantity(quantity);
                            case "selfTestedLot", "lotSampleLabTestDone" ->
                                    lotStateGeo.addTestedQuantity(quantity);
                            case "lotSampleRejected" -> lotStateGeo.setSampleRejectedQuantity(quantity);
                            case "sentLotSampleToLabTest" ->
                                    lotStateGeo.setSampleInTransitQuantity(quantity);
                            case "lotSampleInLab" -> lotStateGeo.setTestInProgressQuantity(quantity);
                            case "approved" -> lotStateGeo.setApprovedQuantity(quantity);
                            case "toSendBackRejected" -> lotStateGeo.addRejectedQuantity(quantity);
                            case "sentBackRejected" -> {
                                lotStateGeo.setSentBackRejectedQuantity(quantity);
                                lotStateGeo.addRejectedQuantity(quantity);
                            }
                            case "receivedRejected" -> {
                                lotStateGeo.setReceivedRejectedBackQuantity(quantity);
                                lotStateGeo.addRejectedQuantity(quantity);
                            }
                        }
                        if (!states.get(i).getName().equals("dispatched")) receivedQuantity += quantity;
                    }
                    lotStateGeo.setDistrictGeoId((String) d[2]);
                    lotStateGeo.setTestedQuantity(testedQuantity);
                    lotStateGeo.setReceivedQuantity(receivedQuantity);
                    lotStateGeo.setUsedQuantity(lotStateGeo.getApprovedQuantity() - remainingQuantity);
                    return lotStateGeo;
                }).toList();
    }

    public List<Long[]> migrateData() {
        List<Object[]> objects = em.createNativeQuery("select id, batch_id from lot where batch_id is not null")
                .getResultList();
        return objects.stream().map(o -> {
            Long[] result = new Long[2];
            result[0] = ((BigInteger) o[0]).longValue();
            result[1] = ((BigInteger) o[1]).longValue();
            return result;
        }).toList();
    }

    public Double[] getWarehouseAggregateProductionQuantities(Long categoryId, List<Long> testManufacturerIds) {
        Object object = em.createQuery(
                        "select " +
                                "sum(l.totalQuantity * l.uom.conversionFactorKg), " +
                                "sum(l.remainingQuantity * l.uom.conversionFactorKg) " +
                                "from Lot l where l.isReceivedAtTarget is true " +
                                "and l.state.name = :state " +
                                "and l.category.id = :categoryId " +
                                "and (:testManufacturerIdsNull is true or (l.manufacturerId not in :testManufacturerIds " +
                                "and l.targetManufacturerId not in :testManufacturerIds))")
                .setParameter("categoryId", categoryId)
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("state", "approved")
                .getSingleResult();
        Double[] result = new Double[2];
        result[0] = (Double) (((Object[]) object)[0]);
        result[1] = (Double) (((Object[]) object)[1]);
        return result;
    }

    public Double[] getWarehouseAggregateTestingQuantities(Long categoryId, List<Long> testManufacturerIds) {
        Object object = em.createQuery(
                        "select " +
                                "sum(case when l.isLabTested is true then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name = :underTestingStates then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.isLabTested is true and (l.state.name = :approvedState) then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.isLabTested is true and (l.state.name in :rejectedStates) then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end) " +
                                "from Lot l where l.isReceivedAtTarget is true " +
                                "and l.category.id = :categoryId " +
                                "and (:testManufacturerIdsNull is true or (l.manufacturerId not in :testManufacturerIds " +
                                "and l.targetManufacturerId not in :testManufacturerIds))")
                .setParameter("categoryId", categoryId)
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("approvedState", "approved")
                .setParameter("rejectedStates", List.of("toSendBackRejected", "sentBackRejected", "receivedRejected", "lotSampleRejected"))
                .setParameter("underTestingStates", "lotSampleInLab")
                .getSingleResult();
        Double[] result = new Double[4];
        result[0] = (Double) (((Object[]) object)[0]);
        result[1] = (Double) (((Object[]) object)[1]);
        result[2] = (Double) (((Object[]) object)[2]);
        result[3] = (Double) (((Object[]) object)[3]);
        return result;
    }

    public List<Object[]> getWarehouseAggregateProductionManufacturerQuantities(Long categoryId, List<Long> testManufacturerIds) {
        return em.createQuery("select " +
                                "l.targetManufacturerId," +
                                "sum(l.totalQuantity * l.uom.conversionFactorKg), " +
                                "sum(l.remainingQuantity * l.uom.conversionFactorKg) " +
                                "from Lot l where l.isReceivedAtTarget is true " +
                                "and l.state.name = :state " +
                                "and l.category.id = :categoryId " +
                                "and (:testManufacturerIdsNull is true or (l.manufacturerId not in :testManufacturerIds " +
                                "and l.targetManufacturerId not in :testManufacturerIds)) " +
                                "group by year(l.dateOfDispatch), l.targetManufacturerId",
                        Object[].class)
                .setParameter("categoryId", categoryId)
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("state", "approved")
                .getResultList();
    }

    public List<Object[]> getWarehouseAggregateTestingManufacturerQuantities(Long categoryId, List<Long> testManufacturerIds) {
        return em.createQuery("select " +
                        "l.targetManufacturerId, " +
                        "sum(case when l.isLabTested is true then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                        "sum(case when l.state.name = :underTestingStates then (l.remainingQuantity * l.uom.conversionFactorKg) else 0D end), " +
                        "sum(case when l.isLabTested is true and (l.state.name = :approvedState) then (l.remainingQuantity * l.uom.conversionFactorKg) else 0D end), " +
                        "sum(case when l.isLabTested is true and (l.state.name in :rejectedStates) then (l.remainingQuantity * l.uom.conversionFactorKg) else 0D end) " +
                        "from Lot l where l.isReceivedAtTarget is true " +
                        "and l.category.id = :categoryId " +
                        "and (:testManufacturerIdsNull is true or (l.manufacturerId not in :testManufacturerIds " +
                        "and l.targetManufacturerId not in :testManufacturerIds))", Object[].class)
                .setParameter("categoryId", categoryId)
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("approvedState", "approved")
                .setParameter("rejectedStates", List.of("toSendBackRejected", "sentBackRejected", "receivedRejected", "lotSampleRejected"))
                .setParameter("underTestingStates", "lotSampleInLab")
                .getResultList();
    }

    public List<Lot> findByLotNo(String lotNo){
        return em.createQuery(
            "from Lot l " +
                "where l.lotNo = :lotNo", Lot.class)
            .setParameter("lotNo", lotNo)
            .getResultList();
    }

    public List<Lot> findTargetLotsBySourceLotNo(String sourceLotNo, Long manufacturerId){
        try {
            return em.createQuery(
                            "Select b.targetLot from BatchLotMapping b " +
                                    "where b.sourceLot.lotNo = :sourceLotNo " +
                                    "and b.targetLot.targetManufacturerId = :manufacturerId " +
                                    "Order by b.targetLot.id", Lot.class)
                    .setParameter("sourceLotNo", sourceLotNo)
                    .setParameter("manufacturerId", manufacturerId)
                    .getResultList();
        }catch (IndexOutOfBoundsException e){
            throw new CustomException("Invalid lot provided as source lot: " + sourceLotNo, HttpStatus.BAD_REQUEST);
        }
    }

    public List<Long> findAllTargetManufacturerIdsBySource(List<Long> manufacturerIds){
        return em.createQuery(
                        "Select l.targetManufacturerId from Lot l " +
                                "where l.manufacturerId in :manufacturerIds " , Long.class)
                .setParameter("manufacturerIds", manufacturerIds)
                .getResultList();
    }

    public List<Long> findAllSourceManufacturerIdsByDistrictGeoId(Long categoryId, List<String> districtGeoIds, DashboardRequestDto dto, List<Long> targetManufacturerIds) {
        return em.createQuery(
                        "Select distinct l.manufacturerId from Lot l " +
                                "where l.category.id = :categoryId " +
                                "and (:districtGeoIdsNull is true or l.targetDistrictGeoId in :districtGeoIds) " +
                                "and (:targetManufacturerIds is null or l.targetManufacturerId in :targetManufacturerIds)" +
                                "and (:dateNull is true or (l.dateOfDispatch >= :fromDate and l.dateOfDispatch <= :toDate))", Long.class)
                .setParameter("categoryId", categoryId)
                .setParameter("districtGeoIds", districtGeoIds)
                .setParameter("districtGeoIdsNull", districtGeoIds == null || districtGeoIds.isEmpty())
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("targetManufacturerIds", targetManufacturerIds)
                .setParameter("toDate", dto.getToDate()== null ? new Date() : dto.getToDate())
                .setParameter("dateNull", dto.getFromDate() == null)
                .getResultList();
    }

    public List<Object[]> findAllTargetManufacturerAggregates(Long categoryId, DashboardRequestDto dto, Long sourceManufacturerId, List<Long> targetManufacturerIds, List<String> lotApprovalPendingStates, List<Long> testManufacturerIds, List<String> lotRejectedStates){
        return em.createQuery(
                        "Select l.targetManufacturerId, " +
                                "sum(case when l.state.name = 'approved' then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name in :lotRejectedStates then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name in :lotApprovalPendingStates then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name = 'approved' then (l.remainingQuantity * l.uom.conversionFactorKg) else 0D end) " +
                                "from Lot l " +
                                "where l.category.id = :categoryId " +
                                "and l.manufacturerId = :sourceManufacturerId " +
                                "and (:targetManufacturerIdsNull is true or l.targetManufacturerId in :targetManufacturerIds) " +
                                "and (:targetDistrictGeoId is null or l.targetDistrictGeoId = :targetDistrictGeoId) " +
                                "and (:targetStateGeoId is null or l.targetStateGeoId = :targetStateGeoId) " +
                                "and (:testManufacturerIdsNull is true or l.manufacturerId not in :testManufacturerIds) " +
                                "and (l.dateOfDispatch >= :fromDate and l.dateOfDispatch <= :toDate) " +
                                "group by l.targetManufacturerId", Object[].class)
                .setParameter("categoryId", categoryId)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("targetDistrictGeoId", dto.getTargetDistrictGeoId())
                .setParameter("targetStateGeoId", dto.getTargetStateGeoId())
                .setParameter("sourceManufacturerId", sourceManufacturerId)
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("lotRejectedStates", lotRejectedStates)
                .setParameter("targetManufacturerIds", targetManufacturerIds)
                .setParameter("targetManufacturerIdsNull", targetManufacturerIds==null)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("toDate", dto.getToDate())
                .setParameter("lotApprovalPendingStates", lotApprovalPendingStates)
                .getResultList();
    }

    public List<Object[]> findAllEmpaneledTargetManufacturerAggregates(Long categoryId, DashboardRequestDto dto, Long sourceManufacturerId, List<Long> targetManufacturerIds, List<String> lotApprovalPendingStates, List<Long> testManufacturerIds, List<String> lotRejectedStates){
        return em.createQuery(
                        "Select l.targetManufacturerId, " +
                                "sum(case when l.state.name = 'approved' then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name in :lotRejectedStates then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name in :lotApprovalPendingStates then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name = 'approved' then (l.remainingQuantity * l.uom.conversionFactorKg) else 0D end) " +
                                "from Lot l " +
                                "where l.category.id = :categoryId " +
                                "and l.manufacturerId = :sourceManufacturerId " +
                                "and (:targetManufacturerIdsNull is true or l.targetManufacturerId in :targetManufacturerIds) " +
                                "and (:targetDistrictGeoId is null or l.targetDistrictGeoId = :targetDistrictGeoId) " +
                                "and (:targetStateGeoId is null or l.targetStateGeoId = :targetStateGeoId) " +
                                "and (:testManufacturerIdsNull is true or l.manufacturerId not in :testManufacturerIds) " +
                                "and (l.dateOfDispatch >= :fromDate and l.dateOfDispatch <= :toDate) " +
                                "group by l.targetManufacturerId", Object[].class)
                .setParameter("categoryId", categoryId)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("targetDistrictGeoId", dto.getTargetDistrictGeoId())
                .setParameter("targetStateGeoId", dto.getEmpanelledStateGeoId())
                .setParameter("sourceManufacturerId", sourceManufacturerId)
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("lotRejectedStates", lotRejectedStates)
                .setParameter("targetManufacturerIds", targetManufacturerIds)
                .setParameter("targetManufacturerIdsNull", targetManufacturerIds==null)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("toDate", dto.getToDate())
                .setParameter("lotApprovalPendingStates", lotApprovalPendingStates)
                .getResultList();
    }

    public List<Object[]> getAggregateByDistrictsGeoId(Long categoryId, Long sourceManufacturerId, DashboardRequestDto dto, List<String> lotStates, List<Long> testManufacturerIds, List<String> lotRejectedStates) {
        return em.createQuery(
                        "Select l.targetDistrictGeoId, " +
                                "sum(case when l.state.name = 'approved' then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name in :lotRejectedStates then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name in :lotStates then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name = 'approved' then (l.remainingQuantity * l.uom.conversionFactorKg) else 0D end) " +
                                "from Lot l " +
                                "where l.category.id = :categoryId " +
                                "and l.manufacturerId = :sourceManufacturerId " +
                                "and (:testManufacturerIdsNull is true or (l.manufacturerId not in :testManufacturerIds and l.targetManufacturerId not in :testManufacturerIds)) " +
                                "and (:dateNull is true or (l.dateOfDispatch >= :fromDate and l.dateOfDispatch <= :toDate)) " +
                                "group by l.targetDistrictGeoId", Object[].class)
                .setParameter("categoryId", categoryId)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("sourceManufacturerId", sourceManufacturerId)
                .setParameter("toDate", dto.getToDate()== null ? new Date() : dto.getToDate())
                .setParameter("dateNull", dto.getFromDate() == null)
                .setParameter("lotStates", lotStates)
                .setParameter("lotRejectedStates", lotRejectedStates)
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.size() <= 0)
                .getResultList();
    }

    public List<Lot> findAllLotsBySourceAndTargetManufacturer(Long categoryId, DashboardRequestDto dto, Long sourceManufacturerId, Long targetManufacturerId, List<Long> testManufacturerIds){
        return em.createQuery(
                        "Select l from Lot l " +
                                "where l.category.id = :categoryId " +
                                "and l.manufacturerId = :sourceManufacturerId " +
                                "and l.targetManufacturerId = :targetManufacturerId " +
                                "and (:testManufacturerIdsNull is true or (l.manufacturerId not in :testManufacturerIds and l.targetManufacturerId not in :testManufacturerIds)) " +
                                "and (:dateNull is true or (l.dateOfDispatch >= :fromDate and l.dateOfDispatch <= :toDate))", Lot.class)
                .setParameter("categoryId", categoryId)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("sourceManufacturerId", sourceManufacturerId)
                .setParameter("targetManufacturerId", targetManufacturerId)
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("toDate", dto.getToDate()== null ? new Date() : dto.getToDate())
                .setParameter("dateNull", dto.getFromDate() == null)
                .getResultList();
    }

    public List<Object[]> getSourceWarehouseAggregate(Long categoryId, String districtGeoId, DashboardRequestDto dto, List<String> lotStates, List<Long> testManufacturerIds, List<String> lotRejectedStates) {
        return em.createQuery(
                        "Select l.manufacturerId, " +
                                "sum(case when l.state.name = 'approved' then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name in :lotRejectedStates then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name in :lotStates then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name = 'approved' then (l.remainingQuantity * l.uom.conversionFactorKg) else 0D end) " +
                                "from Lot l " +
                                "where l.category.id = :categoryId " +
                                "and l.targetDistrictGeoId = :districtGeoId " +
                                "and (:testManufacturerIdsNull is true or (l.manufacturerId not in :testManufacturerIds and l.targetManufacturerId not in :testManufacturerIds)) " +
                                "and (:dateNull is true or (l.dateOfDispatch >= :fromDate and l.dateOfDispatch <= :toDate)) " +
                                "group by l.manufacturerId", Object[].class)
                .setParameter("categoryId", categoryId)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("districtGeoId", districtGeoId)
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("lotRejectedStates", lotRejectedStates)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("toDate", dto.getToDate()== null ? new Date() : dto.getToDate())
                .setParameter("dateNull", dto.getFromDate() == null)
                .setParameter("lotStates", lotStates)
                .getResultList();
    }

    public Double[] getWarehouseAggregateForDashboard(String districtGeoId, String stateGeoId, List<Long> testManufacturerIds, DashboardRequestDto dto, Long categoryId) {
        Object object;
            object = em.createQuery(
                            "select " +
                                    "sum(case when l.state.name = :approvedState then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                    "sum(case when l.state.name in :rejectedStates then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                    "sum(case when l.state.name = :approvedState then ((l.totalQuantity - l.remainingQuantity) * l.uom.conversionFactorKg) else 0D end), " +
                                    "sum(case when l.state.name = :approvedState then (l.remainingQuantity * l.uom.conversionFactorKg) else 0D end) " +
                                    "from Lot l where " +
                                    "l.category.id = :categoryId " +
                                    "and (:districtGeoId is null or l.targetDistrictGeoId = :districtGeoId) " +
                                    "and (:stateGeoId is null or l.targetStateGeoId = :stateGeoId) " +
                                    "and (l.dateOfDispatch >= :fromDate and l.dateOfDispatch <= :toDate) " +
                                    "and (:testManufacturerIdsNull is true or (l.manufacturerId not in :testManufacturerIds and l.targetManufacturerId not in :testManufacturerIds))")
                    .setParameter("categoryId", categoryId)
                    .setParameter("districtGeoId", districtGeoId)
                    .setParameter("stateGeoId", stateGeoId)
                    .setParameter("testManufacturerIds", testManufacturerIds)
                    .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                    .setParameter("approvedState", "approved")
                    .setParameter("rejectedStates", List.of("toSendBackRejected", "sentBackRejected", "receivedRejected", "lotSampleRejected"))
                    .setParameter("fromDate", dto.getFromDate())
                    .setParameter("toDate", dto.getToDate())
                    .getSingleResult();

        Double[] result = new Double[4];
        result[0] = (Double) (((Object[]) object)[0]);
        result[1] = (Double) (((Object[]) object)[1]);
        result[2] = (Double) (((Object[]) object)[2]);
        result[3] = (Double) (((Object[]) object)[3]);
        return result;
    }


    public Double[] getWarehouseAggregateForDashboardFRK(String districtGeoId, String stateGeoId, List<Long> testManufacturerIds, DashboardRequestDto dto, Long categoryId) {
        Object object;
            object = em.createQuery(
                            "select " +
                                    "sum(case when l.state.name = :approvedState and l.action = :dispatchAction then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                    "sum(case when l.state.name in :rejectedStates and l.action = :dispatchAction then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                    "sum(case when l.state.name = :approvedState and l.action = :dispatchAction then ((l.totalQuantity - l.remainingQuantity) * l.uom.conversionFactorKg) else 0D end), " +
                                    "sum(case when l.state.name = :approvedState and l.action = :dispatchAction then (l.remainingQuantity * l.uom.conversionFactorKg) else 0D end) " +
                                    "from Lot l where " +
                                    "l.category.id = :categoryId " +
                                    "and (:districtGeoId is null or l.targetDistrictGeoId = :districtGeoId) " +
                                    "and (:stateGeoId is null or l.targetStateGeoId = :stateGeoId) " +
                                    "and (l.dateOfDispatch >= :fromDate and l.dateOfDispatch <= :toDate) " +
                                    "and (:testManufacturerIdsNull is true or (l.manufacturerId not in :testManufacturerIds and l.targetManufacturerId not in :testManufacturerIds))")
                    .setParameter("dispatchAction", ManufacturerCategoryAction.LOT_TO_LOT_DISPATCH)
                    .setParameter("categoryId", categoryId)
                    .setParameter("districtGeoId", districtGeoId)
                    .setParameter("stateGeoId", stateGeoId)
                    .setParameter("testManufacturerIds", testManufacturerIds)
                    .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                    .setParameter("approvedState", "approved")
                    .setParameter("rejectedStates", List.of("toSendBackRejected", "sentBackRejected", "receivedRejected", "lotSampleRejected"))
                    .setParameter("fromDate", dto.getFromDate())
                    .setParameter("toDate", dto.getToDate())
                    .getSingleResult();

        Double[] result = new Double[4];
        result[0] = (Double) (((Object[]) object)[0]);
        result[1] = (Double) (((Object[]) object)[1]);
        result[2] = (Double) (((Object[]) object)[2]);
        result[3] = (Double) (((Object[]) object)[3]);
        return result;
    }

    public Double[] getEmpanelWarehouseAggregateForDashboardFRK(String districtGeoId, String geoIdType, String stateGeoId, List<Long> testManufacturerIds, DashboardRequestDto dto, Long categoryId, List<Long> targetManufacturerIds) {
        Object object;
        object = em.createQuery(
                        "select " +
                                "sum(case when l.state.name = :approvedState  then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name in :rejectedStates then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name = :approvedState  then ((l.totalQuantity - l.remainingQuantity) * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name = :approvedState  then (l.remainingQuantity * l.uom.conversionFactorKg) else 0D end) " +
                                "from Lot l where " +
                                "l.category.id = :categoryId " +
                                "and (:targetStateGeoId is null or l.targetStateGeoId = :targetStateGeoId) " +
                                "and l.targetManufacturerId in :targetManufacturerIds " +
                                "and (l.dateOfDispatch >= :fromDate and l.dateOfDispatch <= :toDate) " +
                                "and (:testManufacturerIdsNull is true or (l.manufacturerId not in :testManufacturerIds and l.targetManufacturerId not in :testManufacturerIds))")
                .setParameter("categoryId", categoryId)
                .setParameter("targetStateGeoId", dto.getTargetStateGeoId())
                .setParameter("targetManufacturerIds",targetManufacturerIds)
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("approvedState", "approved")
                .setParameter("rejectedStates", List.of("toSendBackRejected", "sentBackRejected", "receivedRejected", "lotSampleRejected"))
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate())
                .getSingleResult();

        Double[] result = new Double[4];
        result[0] = (Double) (((Object[]) object)[0]);
        result[1] = (Double) (((Object[]) object)[1]);
        result[2] = (Double) (((Object[]) object)[2]);
        result[3] = (Double) (((Object[]) object)[3]);
        return result;
    }

    public List<DashboardWarehouseResponseDto> getWarehouseQuantitiesForFRK(String groupBy, List<Long> testManufacturerIds, DashboardRequestDto dto) {
        List<Object[]> object;
            object = em.createQuery(
                        "select " +
                                "l." + groupBy + ", " +
                                "sum(case when l.state.name = :approvedState and l.action = :dispatchAction then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name in :rejectedStates and l.action = :dispatchAction then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name = :approvedState and l.action = :dispatchAction then ((l.totalQuantity - l.remainingQuantity) * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name = :approvedState and l.action = :dispatchAction then (l.remainingQuantity * l.uom.conversionFactorKg) else 0D end) " +
                                "from Lot l where " +
                                "l.category.id = :categoryId " +
                                "and (:targetStateGeoId is null or l.targetStateGeoId = :targetStateGeoId) " +
                                "and (:targetDistrictGeoId is null or l.targetDistrictGeoId = :targetDistrictGeoId) " +
                                "and (l.dateOfDispatch >= :fromDate and l.dateOfDispatch <= :toDate) " +
                                "and (:testManufacturerIdsNull is true or (l.manufacturerId not in :testManufacturerIds " +
                                "and l.targetManufacturerId not in :testManufacturerIds)) " +
                                "group by l." +groupBy, Object[].class)
                    .setParameter("dispatchAction",ManufacturerCategoryAction.LOT_TO_LOT_DISPATCH)
                .setParameter("categoryId", dto.getCategoryId())
                .setParameter("targetStateGeoId", dto.getTargetStateGeoId())
                .setParameter("targetDistrictGeoId", dto.getTargetDistrictGeoId())
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("approvedState", "approved")
                .setParameter("rejectedStates", List.of("toSendBackRejected", "sentBackRejected", "receivedRejected", "lotSampleRejected"))
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate())
                .getResultList();

        return object.stream().map(res -> {
            DashboardWarehouseResponseDto responseDto = new DashboardWarehouseResponseDto();
            responseDto.setId((String) res[0]);
            responseDto.setAccepted((Double) res[1]);
            responseDto.setRejected((Double) res[2]);
            responseDto.setDispatched((Double) res[3]);
            responseDto.setAvailable((Double) res[4]);
            return responseDto;
        }).toList();
    }

    public List<DashboardWarehouseResponseDto> getEmpanelWarehouseQuantities(String groupBy, List<Long> testManufacturerIds, DashboardRequestDto dto, List<Long> targetManufacturerIds) {
        List<Object[]> object;
        object = em.createQuery(
                        "select " +
                                "l." + groupBy + ", " +
                                "sum(case when l.state.name = :approvedState  then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name in :rejectedStates  then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name = :approvedState  then ((l.totalQuantity - l.remainingQuantity) * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name = :approvedState  then (l.remainingQuantity * l.uom.conversionFactorKg) else 0D end) " +
                                "from Lot l where " +
                                "l.category.id = :categoryId " +
                                "and l.targetManufacturerId in :targetManufacturerIds " +
                                "and (:targetStateGeoId is null or l.targetStateGeoId = :targetStateGeoId) " +
                                "and (:targetDistrictGeoId is null or l.targetDistrictGeoId = :targetDistrictGeoId) " +
                                "and (l.dateOfDispatch >= :fromDate and l.dateOfDispatch <= :toDate) " +
                                "and (:testManufacturerIdsNull is true or (l.manufacturerId not in :testManufacturerIds " +
                                "and l.targetManufacturerId not in :testManufacturerIds)) " +
                                "group by l." +groupBy, Object[].class)
                .setParameter("categoryId", dto.getCategoryId())
                .setParameter("targetManufacturerIds", targetManufacturerIds)
                .setParameter("targetStateGeoId", dto.getTargetStateGeoId())
                .setParameter("targetDistrictGeoId", dto.getTargetDistrictGeoId())
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("approvedState", "approved")
                .setParameter("rejectedStates", List.of("toSendBackRejected", "sentBackRejected", "receivedRejected", "lotSampleRejected"))
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate())
                .getResultList();

        return object.stream().map(res -> {
            DashboardWarehouseResponseDto responseDto = new DashboardWarehouseResponseDto();
            responseDto.setId((String) res[0]);
            responseDto.setAccepted((Double) res[1]);
            responseDto.setRejected((Double) res[2]);
            responseDto.setDispatched((Double) res[3]);
            responseDto.setAvailable((Double) res[4]);
            return responseDto;
        }).toList();
    }

public List<DashboardWarehouseResponseDto> getWarehouseQuantitiesForMiller(String groupBy, List<Long> testManufacturerIds, DashboardRequestDto dto) {
    List<Object[]> object;
    object = em.createQuery(
    "select " +
            "l." + groupBy + ", " +
            "sum(case when l.state.name = :approvedState then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
            "sum(case when l.state.name in :rejectedStates then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
            "sum(case when l.state.name = :approvedState then ((l.totalQuantity - l.remainingQuantity) * l.uom.conversionFactorKg) else 0D end), " +
            "sum(case when l.state.name = :approvedState then (l.remainingQuantity * l.uom.conversionFactorKg) else 0D end) " +
            "from Lot l where " +
            "l.category.id = :categoryId " +
            "and (:targetStateGeoId is null or l.targetStateGeoId = :targetStateGeoId) " +
            "and (:targetDistrictGeoId is null or l.targetDistrictGeoId = :targetDistrictGeoId) " +
            "and (l.dateOfDispatch >= :fromDate and l.dateOfDispatch <= :toDate) " +
            "and (:testManufacturerIdsNull is true or (l.manufacturerId not in :testManufacturerIds " +
            "and l.targetManufacturerId not in :testManufacturerIds)) " +
            "group by l." +groupBy, Object[].class)
                    .setParameter("categoryId", dto.getCategoryId())
            .setParameter("targetStateGeoId", dto.getTargetStateGeoId())
            .setParameter("targetDistrictGeoId", dto.getTargetDistrictGeoId())
            .setParameter("testManufacturerIds", testManufacturerIds)
            .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
            .setParameter("approvedState", "approved")
            .setParameter("rejectedStates", List.of("toSendBackRejected", "sentBackRejected", "receivedRejected", "lotSampleRejected"))
            .setParameter("fromDate", dto.getFromDate())
            .setParameter("toDate", dto.getToDate())
            .getResultList();

        return object.stream().map(res -> {
DashboardWarehouseResponseDto responseDto = new DashboardWarehouseResponseDto();
            responseDto.setId((String) res[0]);
        responseDto.setAccepted((Double) res[1]);
        responseDto.setRejected((Double) res[2]);
        responseDto.setDispatched((Double) res[3]);
        responseDto.setAvailable((Double) res[4]);
        return responseDto;
        }).toList();
    }

    public Double[] getAllEmpanelledManufacturersAggregate(Long categoryId, List<Long> sourceManufacturerIds, DashboardRequestDto dto, List<Long> testManufacturerIds, String empaneledStategeoId, String filterBy) {
        Object object = em.createQuery(
                        "select " +
                                "sum(case when b.sourceLot.state.name = :approvedState then (b.sourceLot.totalQuantity * b.sourceLot.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when b.sourceLot.state.name in :rejectedStates then (b.sourceLot.totalQuantity * b.sourceLot.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when b.sourceLot.state.name in :transitStates then (b.sourceLot.totalQuantity * b.sourceLot.uom.conversionFactorKg) else 0D end), " +
                                "sum(b.sourceLot.totalQuantity * b.sourceLot.uom.conversionFactorKg) " +
                                "from BatchLotMapping b where b.batch IS NOT NULL " +
                                "and b.sourceLot.category.id = :categoryId " +
                                "and b.sourceLot.manufacturerId in :sourceManufacturerIds " +
                                "and (:filterBy is null or b.sourceLot.sourceStateGeoId  = :geoId) " +
                                "and (:stateGeoId is null or b.sourceLot.targetStateGeoId = :stateGeoId) " +
                                "and (:districtGeoId is null or b.sourceLot.targetDistrictGeoId = :districtGeoId) " +
                                "and (:dateNull is true or (b.sourceLot.dateOfDispatch >= :fromDate and b.sourceLot.dateOfDispatch <= :toDate)) " +
                                "and (:testManufacturerIdsNull is true or (b.sourceLot.manufacturerId not in :testManufacturerIds " +
                                "and b.sourceLot.targetManufacturerId not in :testManufacturerIds)) "


                )
                .setParameter("categoryId", categoryId)
                .setParameter("sourceManufacturerIds", sourceManufacturerIds)
                .setParameter("stateGeoId", empaneledStategeoId)
                .setParameter("filterBy", filterBy)
                .setParameter("geoId", dto.getGeoId())
                .setParameter("districtGeoId", dto.getTargetDistrictGeoId())
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("approvedState", "approved")
                .setParameter("rejectedStates", List.of("toSendBackRejected", "sentBackRejected", "receivedRejected", "lotSampleRejected"))
                .setParameter("transitStates", List.of("dispatched", "lotReceived", "selfTestedLot", "sentLotSampleToLabTest", "lotSampleInLab", "lotSampleLabTestDone"))
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate())
                .setParameter("dateNull", dto.getFromDate()==null)
                .getSingleResult();
        Double[] result = new Double[4];
        result[0] = (Double) (((Object[]) object)[0]);
        result[1] = (Double) (((Object[]) object)[1]);
        result[2] = (Double) (((Object[]) object)[2]);
        result[3] = (Double) (((Object[]) object)[3]);
        return result;
    }

    public List<DashboardTestingResponseDto> getAllEmpanelledManufacturersAggregateSumForCategory(Long categoryId, String filterBy, String groupBy, List<Long> sourceManufacturerIds, DashboardRequestDto dto, List<Long> testManufacturerIds, String empaneledStategeoId) {
        TypedQuery<Object[]> query = em.createQuery(
                        "select " +
                                "b.sourceLot." + groupBy + ", " +
                                "sum(case when b.sourceLot.state.name = :approvedState then (b.sourceLot.totalQuantity * b.sourceLot.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when b.sourceLot.state.name in :rejectedStates then (b.sourceLot.totalQuantity * b.sourceLot.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when b.sourceLot.state.name in :transitStates then (b.sourceLot.totalQuantity * b.sourceLot.uom.conversionFactorKg) else 0D end), " +
                                "sum(b.sourceLot.totalQuantity * b.sourceLot.uom.conversionFactorKg) " +
                                "from BatchLotMapping b where b.batch IS NOT NULL " +
                                "and b.sourceLot.category.id = :categoryId " +
                                "and b.sourceLot.manufacturerId in :sourceManufacturerIds " +
                                "and (:stateGeoId is null or b.sourceLot.targetStateGeoId = :stateGeoId) " +
                                "and (:districtGeoId is null or b.sourceLot.targetDistrictGeoId = :districtGeoId) " +
                                "and (:dateNull is true or (b.sourceLot.dateOfDispatch >= :fromDate and b.sourceLot.dateOfDispatch <= :toDate)) " +
                                "and (:testManufacturerIdsNull is true or (b.sourceLot.manufacturerId not in :testManufacturerIds " +
                                "and b.sourceLot.targetManufacturerId not in :testManufacturerIds)) " +
                                "group by b.sourceLot." + groupBy, Object[].class)
                .setParameter("categoryId", categoryId)
                .setParameter("sourceManufacturerIds", sourceManufacturerIds)
                .setParameter("stateGeoId", empaneledStategeoId)
//                .setParameter("filterBy",filterBy)
                .setParameter("districtGeoId", dto.getTargetDistrictGeoId())
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("approvedState", "approved")
                .setParameter("rejectedStates", List.of("toSendBackRejected", "sentBackRejected", "receivedRejected", "lotSampleRejected"))
                .setParameter("transitStates", List.of("dispatched", "lotReceived", "selfTestedLot", "sentLotSampleToLabTest", "lotSampleInLab", "lotSampleLabTestDone"))
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate())
                .setParameter("dateNull", dto.getFromDate()==null);


      List<Object[]> objects  = query.getResultList();
      return objects.stream().map(res-> {
          DashboardTestingResponseDto responseDto = new DashboardTestingResponseDto();
          responseDto.setId((String) res[0]);
          responseDto.setLotApproved((Double) res[1]);
          responseDto.setLotRejected((Double) res[2]);
          responseDto.setLotInTransit((Double)res[3]);
          return responseDto;

      }).toList();
    }

    public List<Object[]> getEmpanelledGeoAggregate(Long categoryId, GeoManufacturerTestingResponseType responseType, List<Long> sourceManufacturerIds, DashboardRequestDto dto, List<Long> testManufacturerIds, String stateGeoId) {
        String column = "sum(b." + responseType + ")";
        if(responseType.equals(GeoManufacturerTestingResponseType.approvedQuantity)){
            column = "sum(case when b.sourceLot.state.name = :approvedState then (b.sourceLot.totalQuantity * b.sourceLot.uom.conversionFactorKg) else 0D end)";
        }
        if(responseType.equals(GeoManufacturerTestingResponseType.lotRejected)){
            column = "sum(case when b.sourceLot.state.name in :rejectedStates then (b.sourceLot.totalQuantity * b.sourceLot.uom.conversionFactorKg) else 0D end)";
        }
        if(responseType.equals(GeoManufacturerTestingResponseType.transitQuantity)){
            column = "sum(case when b.sourceLot.state.name in :transitStates then (b.sourceLot.totalQuantity * b.sourceLot.uom.conversionFactorKg) else 0D end)";
        }

        TypedQuery<Object[]> query = em.createQuery(
                        "select " +
                                "b.sourceLot.manufacturerId, " +
                                "b.sourceLot.sourceDistrictGeoId, " +
                                "b.sourceLot.sourceStateGeoId, " +
                                column +
                                "from BatchLotMapping b where b.batch IS NOT NULL " +
                                "and b.sourceLot.category.id = :categoryId " +
                                "and b.sourceLot.manufacturerId in :sourceManufacturerIds " +
                                "and (:stateGeoId is null or b.sourceLot.targetStateGeoId = :stateGeoId) " +
                                "and (:districtGeoId is null or b.sourceLot.targetDistrictGeoId = :districtGeoId) " +
                                "and (:dateNull is true or (b.sourceLot.dateOfDispatch >= :fromDate and b.sourceLot.dateOfDispatch <= :toDate)) " +
                                "and (:testManufacturerIdsNull is true or (b.sourceLot.manufacturerId not in :testManufacturerIds " +
                                "and b.sourceLot.targetManufacturerId not in :testManufacturerIds)) " +
                                "group by b.sourceLot.manufacturerId " +
                                "HAVING "+ column + " <> 0 ",Object[].class)
                .setParameter("categoryId", categoryId)
                .setParameter("sourceManufacturerIds", sourceManufacturerIds)
                .setParameter("stateGeoId", stateGeoId)
                .setParameter("districtGeoId", dto.getTargetDistrictGeoId())
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty());
        if(responseType.equals(GeoManufacturerTestingResponseType.approvedQuantity)){
            query.setParameter("approvedState", "approved");
        }
        if(responseType.equals(GeoManufacturerTestingResponseType.lotRejected)){
            query.setParameter("rejectedStates", List.of("toSendBackRejected", "sentBackRejected", "receivedRejected", "lotSampleRejected"));
        }
        if(responseType.equals(GeoManufacturerTestingResponseType.transitQuantity)){
            query.setParameter("transitStates", List.of("dispatched", "lotReceived", "selfTestedLot", "sentLotSampleToLabTest", "lotSampleInLab", "lotSampleLabTestDone"));

        }
                query.setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate())
                .setParameter("dateNull", dto.getFromDate()==null);


        return query.getResultList();
    }

    public Double[] getAllEmpanelledManufacturersAggregateForWarehouse(DashboardRequestDto dto, List<Long> testManufacturerIds) {
        Object object = em.createQuery(
                        "select " +
                                "sum(case when b.sourceLot.state.name = :approvedState then (b.sourceLot.totalQuantity * b.sourceLot.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when b.sourceLot.state.name in :rejectedStates then (b.sourceLot.totalQuantity * b.sourceLot.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when b.sourceLot.state.name = :approvedState then ((b.sourceLot.totalQuantity - b.sourceLot.remainingQuantity) * b.sourceLot.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when b.sourceLot.state.name = :approvedState then (b.sourceLot.remainingQuantity * b.sourceLot.uom.conversionFactorKg) else 0D end), " +
                                "sum(b.sourceLot.totalQuantity * b.sourceLot.uom.conversionFactorKg) " +
                                "from BatchLotMapping b where b.batch IS NULL " +
                                "and (b.sourceLot.dateOfDispatch >= :fromDate and b.sourceLot.dateOfDispatch <= :toDate) " +
                                "and (:testManufacturerIdsNull is true or (b.sourceLot.manufacturerId not in :testManufacturerIds " +
                                "and b.sourceLot.targetManufacturerId not in :testManufacturerIds))")
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("approvedState", "approved")
                .setParameter("rejectedStates", List.of("toSendBackRejected", "sentBackRejected", "receivedRejected", "lotSampleRejected"))
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate())
                .getSingleResult();
        Double[] result = new Double[5];
        result[0] = (Double) (((Object[]) object)[0]);
        result[1] = (Double) (((Object[]) object)[1]);
        result[2] = (Double) (((Object[]) object)[2]);
        result[3] = (Double) (((Object[]) object)[3]);
        result[4] = (Double) (((Object[]) object)[4]);
        return result;
    }

    public List<EmpanelledAggregatesResponseDto> getAllEmpanelledManufacturersQuantityForWarehouse(DashboardRequestDto dto, List<Long> testManufacturerIds) {
        List<Object[]> objects = em.createQuery(
                        "select " +
                                "b.sourceLot.targetDistrictGeoId, " +
                                "sum(case when b.sourceLot.state.name = :approvedState then (b.sourceLot.totalQuantity * b.sourceLot.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when b.sourceLot.state.name in :rejectedStates then (b.sourceLot.totalQuantity * b.sourceLot.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when b.sourceLot.state.name = :approvedState then ((b.sourceLot.totalQuantity - b.sourceLot.remainingQuantity) * b.sourceLot.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when b.sourceLot.state.name = :approvedState then (b.sourceLot.remainingQuantity * b.sourceLot.uom.conversionFactorKg) else 0D end) " +
                                "from BatchLotMapping b where b.batch IS NULL " +
                                "and b.sourceLot.category.id = :categoryId " +
                                "and (b.sourceLot.dateOfDispatch >= :fromDate and b.sourceLot.dateOfDispatch <= :toDate) " +
                                "and (:testManufacturerIdsNull is true or (b.sourceLot.manufacturerId not in :testManufacturerIds " +
                                "and b.sourceLot.targetManufacturerId not in :testManufacturerIds)) " +
                                "group by b.sourceLot.targetDistrictGeoId", Object[].class)
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("approvedState", "approved")
                .setParameter("rejectedStates", List.of("toSendBackRejected", "sentBackRejected", "receivedRejected", "lotSampleRejected"))
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate())
                .getResultList();

        return objects.stream().map(res -> {
             return EmpanelledAggregatesResponseDto
                    .builder()
                    .approved((Double) res[1])
                    .rejected((Double) res[2])
                    .dispatched((Double) res[3])
                    .available((Double) res[4])
                    .geoId((String) res[0])
                    .build();
        }).toList();
    }

    public List<Object[]> findAllSourceManufacturerAggregatesByTarget(Long categoryId, DashboardRequestDto dto, Long targetManufacturerId, List<String> lotApprovalPendingStates, List<Long> testManufacturerIds, List<String> lotRejectedStates, List<Long> sourceManufacturerIds){
        return em.createQuery(
                        "Select l.manufacturerId, " +
                                "sum(case when l.state.name = 'approved' then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name in :lotRejectedStates then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name in :lotApprovalPendingStates then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name = 'approved' then (l.remainingQuantity * l.uom.conversionFactorKg) else 0D end) " +
                                "from Lot l " +
                                "where l.category.id = :categoryId " +
                                "and (:sourceDistrictGeoId is null or l.sourceDistrictGeoId = :sourceDistrictGeoId) " +
                                "and (:sourceStateGeoId is null or l.sourceStateGeoId = :sourceStateGeoId) " +
                                "and (:sourceManufacturerIdsNull is true or l.manufacturerId in :sourceManufacturerIds) " +
                                "and l.targetManufacturerId = :targetManufacturerId " +
                                "and (:testManufacturerIdsNull is true or (l.manufacturerId not in :testManufacturerIds and l.targetManufacturerId not in :testManufacturerIds)) " +
                                "and (l.dateOfDispatch >= :fromDate and l.dateOfDispatch <= :toDate) " +
                                "group by l.manufacturerId", Object[].class)
                .setParameter("categoryId", categoryId)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("sourceDistrictGeoId", dto.getSourceDistrictGeoId())
                .setParameter("sourceStateGeoId", dto.getSourceStateGeoId())
                .setParameter("targetManufacturerId", targetManufacturerId)
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("sourceManufacturerIds", sourceManufacturerIds)
                .setParameter("sourceManufacturerIdsNull", sourceManufacturerIds == null)
                .setParameter("lotRejectedStates", lotRejectedStates)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("toDate", dto.getToDate())
                .setParameter("lotApprovalPendingStates", lotApprovalPendingStates)
                .getResultList();
    }

    public List<Object[]> findAllTargetManufacturerAggregates(Long categoryId, String districtGeoId, DashboardRequestDto dto, Long sourceManufacturerId, List<String> lotStates, List<Long> testManufacturerIds, List<String> lotRejectedStates){
        return em.createQuery(
                        "Select l.targetManufacturerId," +
                                "sum(case when l.state.name = 'approved' then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name in :lotRejectedStates then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name in :lotStates then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name = 'approved' then (l.remainingQuantity * l.uom.conversionFactorKg) else 0D end) " +
                                "from Lot l " +
                                "where l.category.id = :categoryId " +
                                "and l.districtGeoId = :districtGeoId " +
                                "and l.manufacturerId = :sourceManufacturerId " +
                                "and (:testManufacturerIdsNull is true or l.manufacturerId not in :testManufacturerIds) " +
                                "and (:dateNull is true or (l.dateOfDispatch >= :fromDate and l.dateOfDispatch <= :toDate)) " +
                                "group by l.targetManufacturerId", Object[].class)
                .setParameter("categoryId", categoryId)
                .setParameter("districtGeoId", districtGeoId)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("sourceManufacturerId", sourceManufacturerId)
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("lotRejectedStates", lotRejectedStates)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("toDate", dto.getToDate()== null ? new Date() : dto.getToDate())
                .setParameter("dateNull", dto.getFromDate() == null)
                .setParameter("lotStates", lotStates)
                .getResultList();
    }

    public List<Object[]> getAllWarehouseAggregateForMiller(Long categoryId, DashboardRequestDto dto, List<Long> testManufacturerIds) {
        TypedQuery<Object[]> query = em.createQuery(
                        "select l.targetManufacturerId, " +
                                "sum(case when l.state.name = :approvedState then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name in :rejectedStates then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name = :approvedState then ((l.totalQuantity - l.remainingQuantity) * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name = :approvedState then (l.remainingQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name in :pendingForApprovalSates then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end) " +
                                "from Lot l " +
//                                "left join LotStateGeo lg on lg.geoStateId.categoryId = l.category.id and lg.geoStateId.manufacturerId = l.targetManufacturerId " +
                                "where " +
                                "l.category.id = :categoryId " +
                                "and l.targetManufacturerId is not null " +
                                "and (:districtGeoId is null or l.targetDistrictGeoId = :districtGeoId) " +
                                "and (:stateGeoId is null or l.targetStateGeoId = :stateGeoId) " +
                                "and (l.dateOfDispatch >= :fromDate and l.dateOfDispatch <= :toDate) " +
                                "and (:testManufacturerIdsNull is true or (l.manufacturerId not in :testManufacturerIds and l.targetManufacturerId not in :testManufacturerIds))" +
                                "GROUP BY l.targetManufacturerId", Object[].class)
                .setParameter("categoryId", categoryId)
                .setParameter("approvedState", "approved")
                .setParameter("districtGeoId", dto.getSourceDistrictGeoId())
                .setParameter("stateGeoId", dto.getSourceStateGeoId())
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("rejectedStates", List.of("toSendBackRejected", "sentBackRejected", "receivedRejected", "lotSampleRejected"))
                .setParameter("pendingForApprovalSates", List.of("dispatched","lotReceived","selfTestedLot","sentLotSampleToLabTest","lotSampleInLab","lotSampleLabTestDone"))
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate());
        return  query.getResultList();
    }

    public List<Object[]> getAllWarehouseAggregateForFRK(Long categoryId, DashboardRequestDto dto, List<Long> testManufacturerIds) {
        TypedQuery<Object[]> query = em.createQuery(
                        "select l.targetManufacturerId, " +
                                "sum(case when l.state.name = :approvedState and l.action = :dispatchAction then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name in :rejectedStates and l.action = :dispatchAction then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name = :approvedState and l.action = :dispatchAction then ((l.totalQuantity - l.remainingQuantity) * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name = :approvedState and l.action = :dispatchAction then (l.remainingQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name in :pendingForApprovalSates  then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end) " +
                                "from Lot l " +
//                                "left join LotStateGeo lg on lg.geoStateId.categoryId = l.category.id and lg.geoStateId.manufacturerId = l.targetManufacturerId " +
                                "where (:districtGeoId is null or l.targetDistrictGeoId = :districtGeoId) " +
                                "and (:stateGeoId is null or l.targetStateGeoId = :stateGeoId) " +
                                "and (l.dateOfDispatch >= :fromDate and l.dateOfDispatch <= :toDate) " +
                                "and l.action = :dispatchAction " +
                                "and (:testManufacturerIdsNull is true or (l.manufacturerId not in :testManufacturerIds and l.targetManufacturerId not in :testManufacturerIds)) " +
                                "and l.category.id = :categoryId " +
                                "GROUP BY l.targetManufacturerId"
                        , Object[].class)
                .setParameter("dispatchAction", ManufacturerCategoryAction.LOT_TO_LOT_DISPATCH)
                .setParameter("categoryId", categoryId)
                .setParameter("approvedState", "approved")
                .setParameter("districtGeoId", dto.getSourceDistrictGeoId())
                .setParameter("stateGeoId", dto.getSourceStateGeoId())
                .setParameter("pendingForApprovalSates", List.of("dispatched","lotReceived","selfTestedLot","sentLotSampleToLabTest","lotSampleInLab","lotSampleLabTestDone"))
                .setParameter("rejectedStates", List.of("toSendBackRejected", "sentBackRejected", "receivedRejected", "lotSampleRejected"))
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate());
        return  query.getResultList();
    }

    public List<Object[]> getAllEmpanelWarehouseAggregate(Long categoryId, DashboardRequestDto dto, List<Long> testManufacturerIds,List<Long> targetManufacturerIds, String empanelStateGeoI) {
        TypedQuery<Object[]> query = em.createQuery(
                        "select l.targetManufacturerId, " +
                                "sum(case when l.state.name = :approvedState then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name in :rejectedStates then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name = :approvedState then ((l.totalQuantity - l.remainingQuantity) * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name = :approvedState then (l.remainingQuantity * l.uom.conversionFactorKg) else 0D end), " +
                                "sum(case when l.state.name in :pendingForApprovalSates  then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end) " +
                                "from Lot l " +
//                                "left join LotStateGeo lg on lg.geoStateId.categoryId = l.category.id and lg.geoStateId.manufacturerId = l.targetManufacturerId " +
                                "where (:districtGeoId is null or l.targetDistrictGeoId = :districtGeoId) " +
                                "and (l.dateOfDispatch >= :fromDate and l.dateOfDispatch <= :toDate) " +
                                "and l.targetManufacturerId in :targetManufacturerIds " +
                                "and (:testManufacturerIdsNull is true or (l.manufacturerId not in :testManufacturerIds and l.targetManufacturerId not in :testManufacturerIds)) " +
                                "and l.category.id = :categoryId " +
                                "GROUP BY l.targetManufacturerId"
                        , Object[].class)
                .setParameter("categoryId", categoryId)
                .setParameter("approvedState", "approved")
                .setParameter("targetManufacturerIds",targetManufacturerIds)
                .setParameter("districtGeoId", dto.getSourceDistrictGeoId())
                .setParameter("pendingForApprovalSates", List.of("dispatched","lotReceived","selfTestedLot","sentLotSampleToLabTest","lotSampleInLab","lotSampleLabTestDone"))
                .setParameter("rejectedStates", List.of("toSendBackRejected", "sentBackRejected", "receivedRejected", "lotSampleRejected"))
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate());
        return  query.getResultList();
    }

    public Long getTartgetManufacturerByLotId(Long id){
        Long targetManufacturerId = em.createQuery("select l.targetManufacturerId from Lot as l where l.id = :lotId", Long.class)
                .setParameter("lotId", id)
                .getSingleResult();
        return targetManufacturerId;
    }
}
