package com.beehyv.fortification.dao;

import com.beehyv.fortification.dto.requestDto.DashboardRequestDto;
import com.beehyv.fortification.entity.GeoStateId;
import com.beehyv.fortification.entity.LotStateGeo;
import com.beehyv.fortification.enums.ManufacturerCategoryAction;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class LotStateGeoDao extends BaseDao<LotStateGeo>{
    private final EntityManager em;
    public LotStateGeoDao(EntityManager em) {
        super(em, LotStateGeo.class);
        this.em = em;
    }

    public LotStateGeo findByCategoryIdAndManufacturerId(GeoStateId geoStateId) {
        return em.createQuery("FROM LotStateGeo t WHERE t.geoStateId = :geoStateId", LotStateGeo.class)
                .setParameter("geoStateId", geoStateId)
                .getSingleResult();
    }

    public List<Object[]> findByCategoryIdsAndManufacturerId(Set<Long> categoryIds, Long manufacturerId) {
        return em.createQuery("Select t.geoStateId.categoryId, " +
                        "sum(t.inTransitQuantity), " +
                        "sum(t.receivedQuantity), " +
                        "sum(t.approvedQuantity), " +
                        "sum(t.rejectedQuantity), " +
                        "sum(t.usedQuantity), " +
                        "sum(t.sampleInTransitQuantity), " +
                        "sum(t.testInProgressQuantity), " +
                        "sum(t.testedQuantity) " +
                        "FROM LotStateGeo t " +
                        " where  t.geoStateId.categoryId in (:categoryIds) " +
                        "AND t.geoStateId.manufacturerId = :manufacturerId", Object[].class)
                .setParameter("categoryIds", categoryIds)
                .setParameter("manufacturerId", manufacturerId)
                .getResultList();
    }


    public List<Object[]> getAggregateByDistrictsGeoId(Long categoryId, List<String> districtGeoIds, DashboardRequestDto dto, List<Long> testManufacturerIds) {
         return em.createQuery(
                        "select" +
                                " l.districtGeoId," +
                                " sum(l.approvedQuantity), " +
                                " sum(l.rejectedQuantity), " +
                                " sum(l.inTransitQuantity + l.receivedQuantity + l.sampleInTransitQuantity + l.testInProgressQuantity), " +
                                " sum(l.usedQuantity), " +
                                "l.geoStateId.manufacturerId " +
                                "from LotStateGeo l " +
                                "where (:categoryId is null or l.geoStateId.categoryId = :categoryId) " +
                                "AND (:testManufacturerIdsNull is true or l.geoStateId.manufacturerId not in :testManufacturerIds) " +
                                "AND l.districtGeoId in :districtGeoIds " +
                                "AND (:dateNull is true or (l.geoStateId.producedDate >= :fromDate and l.geoStateId.producedDate <= :toDate)) " +
                                "group by l.districtGeoId", Object[].class)
                .setParameter("categoryId", categoryId)
                .setParameter("fromDate", dto.getFromDate())
                 .setParameter("testManufacturerIds", testManufacturerIds)
                 .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("toDate", dto.getToDate() == null ? new Date() : dto.getToDate())
                .setParameter("districtGeoIds", districtGeoIds)
                .setParameter("dateNull", dto.getFromDate()==null)
                .getResultList();
    }

    public List<Object[]> getAllWarehouseAggregateForMiller(Long categoryId, DashboardRequestDto dto, List<Long> testManufacturerIds) {
        TypedQuery<Object[]> query = em.createQuery(
                        "select" +
                                " l.geoStateId.manufacturerId," +
                                " sum(l.approvedQuantity), " +
                                " sum(l.rejectedQuantity), " +
                                " sum(l.inTransitQuantity + l.receivedQuantity + l.sampleInTransitQuantity + l.testInProgressQuantity), " +
                                " sum(l.usedQuantity)" +
                                "from LotStateGeo l " +
                                "where (:categoryId is null or l.geoStateId.categoryId = :categoryId) " +
                                "AND (:testManufacturerIdsNull is true or l.geoStateId.manufacturerId not in :testManufacturerIds) " +
                                "AND (l.geoStateId.producedDate >= :fromDate and l.geoStateId.producedDate <= :toDate) " +
                                "and (:districtGeoId is null or l.districtGeoId = :districtGeoId) " +
                                "and (:stateGeoId is null or l.stateGeoId = :stateGeoId) " +
                                "and (:countryGeoId is null or l.countryGeoId = :countryGeoId) " +
                                "group by l.geoStateId.manufacturerId", Object[].class)
                .setParameter("categoryId", categoryId)
                .setParameter("districtGeoId", dto.getSourceDistrictGeoId())
                .setParameter("stateGeoId", dto.getSourceStateGeoId())
                .setParameter("countryGeoId", dto.getSourceCountryGeoId())
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate());
        return  query.getResultList();
    }

    public List<Object[]> getAllWarehouseAggregateForFRK(Long categoryId, DashboardRequestDto dto, List<Long> testManufacturerIds) {
        TypedQuery<Object[]> query = em.createQuery(
                "SELECT " +
                        "   l.geoStateId.manufacturerId," +
                        "   SUM(l.approvedQuantity)/COUNT(*), " +
                        "   SUM(l.rejectedQuantity)/COUNT(*), " +
                        "   SUM(l.inTransitQuantity + l.receivedQuantity + l.sampleInTransitQuantity + l.testInProgressQuantity)/COUNT(*), " +
                        "   SUM(l.usedQuantity)/COUNT(*) " +
                        "FROM " +
                        "   LotStateGeo l " +
                        "JOIN Lot lot ON lot.targetManufacturerId = l.geoStateId.manufacturerId " +
                        "WHERE " +
                        "   (:categoryId IS NULL OR l.geoStateId.categoryId = :categoryId) " +
                        "   AND (:testManufacturerIdsNull IS TRUE OR l.geoStateId.manufacturerId NOT IN :testManufacturerIds) " +
                        "   AND (l.geoStateId.producedDate >= :fromDate AND l.geoStateId.producedDate <= :toDate) " +
                        "   AND (:districtGeoId IS NULL OR l.districtGeoId = :districtGeoId) " +
                        "   AND (:stateGeoId IS NULL OR l.stateGeoId = :stateGeoId) " +
                        "   AND lot.action = :dispatchAction " +
                        "   AND (:countryGeoId IS NULL OR l.countryGeoId = :countryGeoId) " +
                        "GROUP BY " +
                        "   l.geoStateId.manufacturerId", Object[].class)
                .setParameter("dispatchAction", ManufacturerCategoryAction.LOT_TO_LOT_DISPATCH)
                .setParameter("categoryId", categoryId)
                .setParameter("districtGeoId", dto.getSourceDistrictGeoId())
                .setParameter("stateGeoId", dto.getSourceStateGeoId())
                .setParameter("countryGeoId", dto.getSourceCountryGeoId())
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate());
        return  query.getResultList();
    }

}