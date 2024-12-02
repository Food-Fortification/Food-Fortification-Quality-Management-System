package org.path.fortification.dao;

import org.path.fortification.dto.requestDto.DashboardRequestDto;
import org.path.fortification.entity.GeoStateId;
import org.path.fortification.entity.LotStateGeo;
import org.path.fortification.enums.GeoManufacturerProductionResponseType;
import org.path.fortification.enums.ManufacturerCategoryAction;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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

    public Double getUsedQuantityProductionSum(String filterBy, List<Long>testManufacturerIds, DashboardRequestDto dto, List<Long> manufacturerIds){
        TypedQuery<Double>query =em.createQuery("select sum(quantityUsed) as usedQuantity from MixMapping mm where mm.sourceLot.category.id = :categoryId " +
                        "AND ((:manufacturerIdsNull is true and :filterByCountry is true ) OR mm.sourceLot.manufacturerId in :manufacturerIds) " +
                        "AND (:isFromDateNull is true OR (mm.targetBatch.dateOfManufacture >= :fromDate and mm.targetBatch.dateOfManufacture <= :toDate)) " +
                        "AND (:testManufacturerIdsNull is true or mm.sourceLot.manufacturerId not in :testManufacturerIds) " +
                        "AND mm.sourceLot.action = :action",Double.class )
                .setParameter("categoryId", dto.getCategoryId())
                .setParameter("manufacturerIds", manufacturerIds)
                .setParameter("manufacturerIdsNull", manufacturerIds.isEmpty())
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("filterByCountry", Objects.equals(filterBy,"countryGeoId"))
                .setParameter("isFromDateNull", dto.getFromDate()==null)
                .setParameter("action", ManufacturerCategoryAction.CREATION)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.size() <= 0)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate());
        Double object = query.getSingleResult();
        return object;
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
                        "AND t.geoStateId.manufacturerId = :manufacturerId group by t.geoStateId.categoryId", Object[].class)
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

    public List<Object[]> getGeoAggregatedUsedQuantity(GeoManufacturerProductionResponseType responseType, List<Long> testManufacturerIds, DashboardRequestDto dto) {
        String column = "sum(l.totalQuantity-l.remainingQuantity-" +
                "(COALESCE(w.wastageQuantity, 0) * COALESCE(u.conversionFactorKg, 1)))";

        TypedQuery<Object[]> query = em.createQuery(
                        "select " +
                                "l.targetManufacturerId, " +
                                "l.targetDistrictGeoId, " +
                                "l.targetStateGeoId, " +
                                column +
                                " from Lot as l " +
                                "left join Wastage w on l.id=w.lot.id " +
                                "left join UOM u on w.uom.id = u.id " +
                                "where (l.category.id = :categoryId) " +
                                "AND l.action = :action " +
                                "AND (l.dateOfDispatch >= :fromDate and l.dateOfDispatch <= :toDate) " +
                                "and (:testManufacturerIdsNull is true or l.manufacturerId NOT IN (:testManufacturerIds)) " +
                                "group by l.targetManufacturerId " +
                                "HAVING " + column+ "<>0", Object[].class)
                .setParameter("categoryId", dto.getCategoryId())
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.size() <= 0)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("action",ManufacturerCategoryAction.CREATION)
                .setParameter("toDate", dto.getToDate());

        return query.getResultList();
    }

    public  List<Object[]> getUsedQuantityProductionSumByCategory(String filterByUsed,String groupByUsed,List<Long> testManufacturerIds, DashboardRequestDto dto, List<Long> manufacturerIds){
        TypedQuery<Object[]>query = em.createQuery
                        ("SELECT l." + groupByUsed + ", " +
                                "sum(l.totalQuantity-l.remainingQuantity-" +
                                "(COALESCE(w.wastageQuantity, 0) * COALESCE(u.conversionFactorKg, 1))" +
                                ")" +
                                " as usedQuantity " +
                                "from Lot l " +
                                "left join Wastage w on l.id=w.lot.id " +
                                "left join UOM u on w.uom.id = u.id " +
                                "WHERE l.category.id = :categoryId " +
                                "AND (:manufacturerIdsNull IS TRUE OR l.manufacturerId IN :manufacturerIds) " +
                                "AND l.dateOfDispatch >= :fromDate AND l.dateOfDispatch <= :toDate " +
                                "AND (:testManufacturerIdsNull IS TRUE OR l.manufacturerId NOT IN :testManufacturerIds) " +
                                "AND l.action = :action " +
                                "AND (:filterBy IS NULL OR l.sourceStateGeoId = :sourceStateGeoId) " +
                                "GROUP BY l." + groupByUsed + " " +
                                "ORDER BY l." + groupByUsed ,Object[].class)
                .setParameter("categoryId", dto.getCategoryId())
                .setParameter("manufacturerIds", manufacturerIds)
                .setParameter("manufacturerIdsNull", manufacturerIds==null)
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("filterBy",filterByUsed)
                .setParameter("sourceStateGeoId",dto.getGeoId())
                .setParameter("action", ManufacturerCategoryAction.CREATION)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.size() <= 0)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate());
        List<Object[]> object = query.getResultList();
        return object;
    }
}