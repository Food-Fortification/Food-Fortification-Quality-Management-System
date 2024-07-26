package com.beehyv.fortification.dao;

import com.beehyv.fortification.dto.requestDto.DashboardRequestDto;
import com.beehyv.fortification.dto.responseDto.DashboardProductionResponseDto;
import com.beehyv.fortification.dto.responseDto.DashboardTestingResponseDto;
import com.beehyv.fortification.dto.responseDto.ProductionResponseDto;
import com.beehyv.fortification.dto.responseDto.TestingResponseDto;
import com.beehyv.fortification.entity.BatchStateGeo;
import com.beehyv.fortification.entity.GeoStateId;
import com.beehyv.fortification.enums.GeoManufacturerProductionResponseType;
import com.beehyv.fortification.enums.GeoManufacturerTestingResponseType;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;

@Component
public class BatchStateGeoDao extends BaseDao<BatchStateGeo> {
    private final EntityManager em;

    public BatchStateGeoDao(EntityManager em) {
        super(em, BatchStateGeo.class);
        this.em = em;
    }

    public BatchStateGeo findByCategoryIdAndManufacturerIdAndYear(GeoStateId geoStateId) {
        return em.createQuery("FROM BatchStateGeo t WHERE t.geoStateId = :geoStateId", BatchStateGeo.class)
                .setParameter("geoStateId", geoStateId)
                .getSingleResult();
    }

    public List<Object[]> findByCategoryIdsAndManufacturerId(List<Long> categoryIds, Long manufacturerId, Integer year, DashboardRequestDto dto) {
        TypedQuery<Object[]> query = em.createQuery("SELECT t.geoStateId.categoryId, " +
                        "sum(t.totalQuantity), " +
                        "sum(t.inProductionQuantity), " +
                        "sum(t.producedQuantity), " +
                        "sum(t.inTransitQuantity), " +
                        "sum(t.receivedQuantity), " +
                        "sum(t.approvedQuantity), " +
                        "sum(t.rejectedQuantity), " +
                        "sum(t.batchSampleInTransitQuantity), " +
                        "sum(t.batchSampleTestInProgressQuantity), " +
                        "sum(t.batchTestedQuantity), " +
                        "sum(t.lotSampleInTransitQuantity), " +
                        "sum(t.lotSampleTestInProgressQuantity), " +
                        "sum(t.lotTestedQuantity), " +
                        "sum(t.lotRejected), " +
                        "sum(t.rejectedInTransitQuantity), " +
                        "sum(t.receivedRejectedQuantity) " +
                        " FROM BatchStateGeo t " +
                        " where t.geoStateId.categoryId in (:categoryIds) " +
                        "AND t.geoStateId.manufacturerId = :manufacturerId " +
                        "AND (:yearNull is true OR YEAR(t.geoStateId.producedDate) = :year) " +
                        "AND (:dateNull is true or (t.geoStateId.producedDate >= :fromDate and t.geoStateId.producedDate <= :toDate)) " +
                        "group by t.geoStateId.categoryId ", Object[].class)
                .setParameter("categoryIds", categoryIds)
                .setParameter("manufacturerId", manufacturerId)
                .setParameter("year", year)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate() == null ? new Date() : dto.getToDate());

        if (dto.getFromDate() == null) {
            query.setParameter("yearNull", false);
            query.setParameter("dateNull", true);
        } else {
            query.setParameter("yearNull", true);
            query.setParameter("dateNull", false);
        }
        return query.getResultList();
    }

    public List<BatchStateGeo> findManufacturerQuantityByCategoryIdAndState(Long categoryId, String filterBy, String geoId,
                                                                            String orderBy, Set<Long> mids, Integer year,
                                                                            Integer pageNumber, Integer pageSize, DashboardRequestDto dto) {
        TypedQuery<BatchStateGeo> query = em.createQuery(
                        "FROM BatchStateGeo b " +
                                "WHERE (:categoryId is null or b.geoStateId.categoryId = :categoryId) " +
                                "and b.geoStateId.manufacturerId in (:mids) " +
                                "AND (:yearNull is true OR YEAR(b.geoStateId.producedDate) = :year) " +
                                "AND (:dateNull is true or (b.geoStateId.producedDate >= :fromDate and b.geoStateId.producedDate <= :toDate)) " +
                                "and (:filterBy is null or b." +
                                Optional.ofNullable(filterBy).orElse("countryGeoId") + " = :geoId) " +
                                "order by b." + orderBy
                        ,
                        BatchStateGeo.class
                )
                .setParameter("categoryId", categoryId)
                .setParameter("filterBy", filterBy)
                .setParameter("mids", mids)
                .setParameter("year", year)
                .setParameter("geoId", geoId)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate() == null ? new Date() : dto.getToDate());
        if (pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        if (dto.getFromDate() == null) {
            query.setParameter("yearNull", false);
            query.setParameter("dateNull", true);
        } else {
            query.setParameter("yearNull", true);
            query.setParameter("dateNull", false);
        }
        return query.getResultList();
    }

    public Long findManufacturerQuantityByCategoryIdAndStateCount(Long categoryId, String filterBy, String geoId, Integer year, DashboardRequestDto dto) {
        TypedQuery<Long> query = em.createQuery(
                        "SELECT count(b) FROM BatchStateGeo b " +
                                "WHERE (:categoryId is null or b.geoStateId.categoryId = :categoryId) " +
                                "AND (:yearNull is true OR YEAR(b.geoStateId.producedDate) = :year) " +
                                "AND (:dateNull is true or (b.geoStateId.producedDate >= :fromDate and b.geoStateId.producedDate <= :toDate)) " +
                                "and (:filterBy is null or b."
                                + Optional.ofNullable(filterBy).orElse("countryGeoId") + " = :geoId) "
                        ,
                        Long.class)
                .setParameter("categoryId", categoryId)
                .setParameter("filterBy", filterBy)
                .setParameter("geoId", geoId)
                .setParameter("year", year)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate() == null ? new Date() : dto.getToDate());

        if (dto.getFromDate() == null) {
            query.setParameter("yearNull", false);
            query.setParameter("dateNull", true);
        } else {
            query.setParameter("yearNull", true);
            query.setParameter("dateNull", false);
        }
        return query.getSingleResult();
    }

    public Double[] getGeoAggregatedProductionSum(Long categoryId, String filterBy, String geoId, List<Long> testManufacturerIds, Integer year, DashboardRequestDto dto) {
        TypedQuery<Object[]> query = em.createQuery(
                        "select " +
                                " sum(b.producedQuantity) as producedQuantity," +
                                " sum(b.inProductionQuantity) as inProductionQuantity " +
                                "from BatchStateGeo b " +
                                "where (:categoryId is null or b.geoStateId.categoryId = :categoryId) " +
                                "AND (:yearNull is true OR YEAR(b.geoStateId.producedDate) = :year) " +
                                "AND (:dateNull is true or (b.geoStateId.producedDate >= :fromDate and b.geoStateId.producedDate <= :toDate)) " +
                                "and (:filterBy is null or b."
                                + Optional.ofNullable(filterBy).orElse("countryGeoId") + " = :geoId) " +
                                "and (:testManufacturerIdsNull is true or b.geoStateId.manufacturerId NOT IN (:testManufacturerIds))", Object[].class)
                .setParameter("categoryId", categoryId)
                .setParameter("filterBy", filterBy)
                .setParameter("geoId", geoId)
                .setParameter("year", year)
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.size() <= 0)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate() == null ? new Date() : dto.getToDate());

        if (dto.getFromDate() == null) {
            query.setParameter("yearNull", false);
            query.setParameter("dateNull", true);
        } else {
            query.setParameter("yearNull", true);
            query.setParameter("dateNull", false);
        }
        Object[] object = query.getSingleResult();
        Double[] results = new Double[2];
        results[0] = (Double) (((Object[]) object)[0]);
        results[1] = (Double) (((Object[]) object)[1]);
        return results;
    }

    public Double[] getGeoAggregatedTestingSum(Long categoryId, String filterBy, String geoId, List<Long> testManufacturerIds, Integer year, DashboardRequestDto dto) {
        TypedQuery<Object[]> query = em.createQuery(
                        "select" +
                                " sum(b.batchTestedQuantity) ," +
                                " sum(b.lotSampleTestInProgressQuantity + b.batchSampleTestInProgressQuantity), " +
                                " sum(b.approvedQuantity), " +
                                " sum(b.rejectedQuantity)," +
                                " sum(b.batchTestApprovedQuantity), " +
                                " sum(b.batchTestRejectedQuantity), " +
                                " sum(b.lotTestApprovedQuantity), " +
                                " sum(b.lotTestRejectedQuantity), " +
                                " sum(b.lotTestedQuantity) " +
                                "from BatchStateGeo b " +
                                "where (:categoryId is null or b.geoStateId.categoryId = :categoryId) " +
                                "AND (:yearNull is true OR YEAR(b.geoStateId.producedDate) = :year) " +
                                "AND (:dateNull is true or (b.geoStateId.producedDate >= :fromDate and b.geoStateId.producedDate <= :toDate)) " +
                                "and (:filterBy is null or b."
                                + Optional.ofNullable(filterBy).orElse("countryGeoId") + " = :geoId) " +
                                "and (:testManufacturerIdsNull is true or b.geoStateId.manufacturerId NOT IN (:testManufacturerIds))", Object[].class)
                .setParameter("categoryId", categoryId)
                .setParameter("filterBy", filterBy)
                .setParameter("geoId", geoId)
                .setParameter("year", year)
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.size() <= 0)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate() == null ? new Date() : dto.getToDate());

        if (dto.getFromDate() == null) {
            query.setParameter("yearNull", false);
            query.setParameter("dateNull", true);
        } else {
            query.setParameter("yearNull", true);
            query.setParameter("dateNull", false);
        }

        Object[] object = query.getSingleResult();
        Double[] results = new Double[9];
        results[0] = (Double) (((Object[]) object)[0]);
        results[1] = (Double) (((Object[]) object)[1]);
        results[2] = (Double) (((Object[]) object)[2]);
        results[3] = (Double) (((Object[]) object)[3]);
        results[4] = (Double) (((Object[]) object)[4]);
        results[5] = (Double) (((Object[]) object)[5]);
        results[6] = (Double) (((Object[]) object)[6]);
        results[7] = (Double) (((Object[]) object)[7]);
        results[8] = (Double) (((Object[]) object)[8]);
        return results;
    }

    public List<ProductionResponseDto> getGeoAggregatedProductionQuantity(Long categoryId, String groupBy, String filterBy,
                                                                          String geoId, List<Long> testManufacturerIds, Integer year, DashboardRequestDto dto) {
        TypedQuery<Object[]> query = em.createQuery(
                        "select b." + groupBy + " ," +
                                " sum(b.producedQuantity) ," +
                                " sum(b.inProductionQuantity) " +
                                "from BatchStateGeo b " +
                                "where (:categoryId is null or b.geoStateId.categoryId = :categoryId) " +
                                "AND (:yearNull is true OR YEAR(b.geoStateId.producedDate) = :year) " +
                                "AND (:dateNull is true or (b.geoStateId.producedDate >= :fromDate and b.geoStateId.producedDate <= :toDate)) " +
                                "and (:filterBy is null or b." +
                                Optional.ofNullable(filterBy).orElse("countryGeoId") + " = :geoId) " +
                                "and (:testManufacturerIdsNull is true or b.geoStateId.manufacturerId NOT IN (:testManufacturerIds)) " +
                                "group by b." + groupBy, Object[].class)
                .setParameter("categoryId", categoryId)
                .setParameter("filterBy", filterBy)
                .setParameter("geoId", geoId)
                .setParameter("year", year)
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.size() <= 0)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate() == null ? new Date() : dto.getToDate());

        if (dto.getFromDate() == null) {
            query.setParameter("yearNull", false);
            query.setParameter("dateNull", true);
        } else {
            query.setParameter("yearNull", true);
            query.setParameter("dateNull", false);
        }
        List<Object[]> object = query.getResultList();
        return (List<ProductionResponseDto>) object.stream().map(d -> {
            Object[] resp = (Object[]) d;
            ProductionResponseDto productionResponseDto = new ProductionResponseDto();
            productionResponseDto.setId((String) resp[0]);
            productionResponseDto.setProducedQuantity((Double) resp[1]);
            productionResponseDto.setInProductionQuantity((Double) resp[2]);
            return productionResponseDto;
        }).toList();
    }

    public List<TestingResponseDto> getGeoAggregatedTestingQuantity(Long categoryId, String groupBy, String filterBy, String geoId,
                                                                    List<Long> testManufacturerIds, Integer year, DashboardRequestDto dto) {
        TypedQuery<Object[]> query = em.createQuery(
                        "select b." + groupBy + " ," +
                                " sum(b.batchTestedQuantity) ," +
                                " sum(b.lotSampleTestInProgressQuantity + b.batchSampleTestInProgressQuantity), " +
                                " sum(b.approvedQuantity), " +
                                " sum(b.rejectedQuantity), " +
                                " sum(b.batchTestApprovedQuantity), " +
                                " sum(b.batchTestRejectedQuantity), " +
                                " sum(b.lotTestedQuantity), " +
                                " sum(b.lotTestApprovedQuantity), " +
                                " sum(b.lotTestRejectedQuantity) " +
                                "from BatchStateGeo b " +
                                "where (:categoryId is null or b.geoStateId.categoryId = :categoryId) " +
                                "AND (:yearNull is true OR YEAR(b.geoStateId.producedDate) = :year) " +
                                "AND (:dateNull is true or (b.geoStateId.producedDate >= :fromDate and b.geoStateId.producedDate <= :toDate)) " +
                                "and (:filterBy is null or b." +
                                Optional.ofNullable(filterBy).orElse("countryGeoId") + " = :geoId) " +
                                "and (:testManufacturerIdsNull is true or b.geoStateId.manufacturerId NOT IN (:testManufacturerIds)) " +
                                "group by b." + groupBy, Object[].class)
                .setParameter("categoryId", categoryId)
                .setParameter("categoryId", categoryId)
                .setParameter("filterBy", filterBy)
                .setParameter("geoId", geoId)
                .setParameter("year", year)
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.size() <= 0)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate() == null ? new Date() : dto.getToDate());
        if (dto.getFromDate() == null) {
            query.setParameter("yearNull", false);
            query.setParameter("dateNull", true);
        } else {
            query.setParameter("yearNull", true);
            query.setParameter("dateNull", false);
        }
        List<Object[]> object = query.getResultList();
        return (List<TestingResponseDto>) object.stream().map(d -> {
            Object[] resp = (Object[]) d;
            TestingResponseDto testingResponseDto = new TestingResponseDto();
            testingResponseDto.setId((String) resp[0]);
            testingResponseDto.setTotalTestedQuantity((Double) resp[1]);
            testingResponseDto.setUnderTestingQuantity((Double) resp[2]);
            testingResponseDto.setApprovedQuantity((Double) resp[3]);
            testingResponseDto.setRejectedQuantity((Double) resp[4]);
            testingResponseDto.setBatchTestApprovedQuantity((Double) resp[5]);
            testingResponseDto.setBatchTestRejectedQuantity((Double) resp[6]);
            testingResponseDto.setTotalLotTestedQuantity((Double) resp[7]);
            testingResponseDto.setLotTestApprovedQuantity((Double) resp[8]);
            testingResponseDto.setLotTestRejectedQuantity((Double) resp[9]);
            return testingResponseDto;
        }).toList();
    }

    public List<Double[]> getGeoAggregatedProductionSum(String geoIdType, String geoId, Integer year, List<Long> testManufacturerIds, DashboardRequestDto dto) {
        TypedQuery<Object[]> query = em.createQuery(
                        "select " +
                                " sum(b.producedQuantity) as producedQuantity," +
                                " sum(b.inProductionQuantity) as inProductionQuantity, " +
                                "b.geoStateId.categoryId " +
                                "from BatchStateGeo b " +
                                "where ( b." + geoIdType + " = :geoId) " +
                                "and (:testManufacturerIdsNull is true or b.geoStateId.manufacturerId NOT IN (:testManufacturerIds)) " +
                                "AND (:yearNull is true OR YEAR(b.geoStateId.producedDate) = :year) " +
                                "AND (:dateNull is true or (b.geoStateId.producedDate >= :fromDate and b.geoStateId.producedDate <= :toDate)) " +
                                "group by b.geoStateId.categoryId", Object[].class)
                .setParameter("geoId", geoId)
                .setParameter("year", year)
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.size() <= 0)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate() == null ? new Date() : dto.getToDate());


        if (dto.getFromDate() == null) {
            query.setParameter("yearNull", false);
            query.setParameter("dateNull", true);
        } else {
            query.setParameter("yearNull", true);
            query.setParameter("dateNull", false);
        }
        List<Object[]> objects = query.getResultList();


        return objects.stream().map(object -> {
            Double[] results = new Double[3];
            results[0] = (Double) (((Object[]) object)[0]);
            results[1] = (Double) (((Object[]) object)[1]);
            Long categoryId = (Long) (((Object[]) object)[2]);
            results[2] = categoryId.doubleValue();
            return results;
        }).toList();
    }

    public List<Object[]> getAllSourceManufacturersAggregate(Long categoryId, DashboardRequestDto dto, List<Long> manufacturerIds, List<Long> testManufacturerIds) {
        TypedQuery<Object[]> query = em.createQuery(
                        "select" +
                                " b.geoStateId.manufacturerId," +
                                " b.districtGeoId," +
                                " sum(b.producedQuantity), " +
                                " sum(b.batchTestApprovedQuantity), " +
                                " sum(b.batchTestRejectedQuantity), " +
                                " sum(b.totalQuantity - b.batchTestedQuantity), " +
                                " sum(b.approvedQuantity), " +
                                " sum(b.lotRejected), " +
                                " sum(b.inTransitQuantity + b.receivedQuantity - b.lotRejected - b.approvedQuantity), " +
                                " sum(b.inTransitQuantity + b.receivedQuantity) " +
                                "from BatchStateGeo b " +
                                "where (:categoryId is null or b.geoStateId.categoryId = :categoryId) " +
                                "and b.geoStateId.manufacturerId in :manufacturerIds " +
                                "and (:testManufacturerIdsNull is true or b.geoStateId.manufacturerId not in :testManufacturerIds) " +
                                "AND (:dateNull is true or (b.geoStateId.producedDate >= :fromDate and b.geoStateId.producedDate <= :toDate)) " +
                                "group by b.geoStateId.manufacturerId", Object[].class)
                .setParameter("categoryId", categoryId)
                .setParameter("manufacturerIds", manufacturerIds)
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("dateNull", dto.getFromDate() == null)
                .setParameter("toDate", dto.getToDate() == null ? new Date() : dto.getToDate());

        if(dto.getFromDate() == null) {
            query.setParameter("dateNull", true);
        }
        return  query.getResultList();
    }

    public List<Object[]> getAllAgenciesAggregate(DashboardRequestDto dto, List<Long> testManufacturerIds, List<Long> sourceManufacturerIds) {
        TypedQuery<Object[]> query = em.createQuery(
                        "select" +
                                " b.geoStateId.manufacturerId," +
                                " b.districtGeoId," +
                                " sum(b.producedQuantity + b.inProductionQuantity), " +
                                " sum(b.batchTestApprovedQuantity), " +
                                " sum(b.batchTestRejectedQuantity), " +
                                " sum(b.totalQuantity - b.batchTestedQuantity), " +
                                " sum(b.approvedQuantity), " +
                                " sum(b.lotRejected), " +
                                " sum(b.inTransitQuantity + b.receivedQuantity - b.lotRejected - b.approvedQuantity), " +
                                " sum(b.inTransitQuantity + b.receivedQuantity), " +
                                " sum(b.availableTested) " +
                                "from BatchStateGeo b " +
                                "where (:categoryId is null or b.geoStateId.categoryId = :categoryId) " +
                                "and (:sourceManufacturerIdsNull is true or b.geoStateId.manufacturerId in :sourceManufacturerIds) " +
                                "and (:countryGeoId is null or b.countryGeoId = :countryGeoId) " +
                                "and (:stateGeoId is null or b.stateGeoId = :stateGeoId) " +
                                "and (:districtGeoId is null or b.districtGeoId = :districtGeoId) " +
                                "and (:testManufacturerIdsNull is true or b.geoStateId.manufacturerId not in :testManufacturerIds) " +
                                "AND (:dateNull is true or (b.geoStateId.producedDate >= :fromDate and b.geoStateId.producedDate <= :toDate)) " +
                                "group by b.geoStateId.manufacturerId", Object[].class)
                .setParameter("categoryId", dto.getCategoryId())
                .setParameter("countryGeoId", dto.getSourceCountryGeoId())
                .setParameter("stateGeoId", dto.getSourceStateGeoId())
                .setParameter("districtGeoId", dto.getSourceDistrictGeoId())
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("sourceManufacturerIds", sourceManufacturerIds)
                .setParameter("sourceManufacturerIdsNull", sourceManufacturerIds==null)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate() == null ? new Date() : dto.getToDate())
                .setParameter("dateNull", dto.getFromDate()==null);

        return  query.getResultList();
    }

    public List<Object[]> getAllAgenciesEmpanelAggregate(DashboardRequestDto dto, List<Long> testManufacturerIds, List<Long> sourceManufacturerIds) {
        TypedQuery<Object[]> query = em.createQuery(
                        "select" +
                                " b.geoStateId.manufacturerId," +
                                " b.districtGeoId," +
                                " sum(b.producedQuantity + b.inProductionQuantity), " +
                                " sum(b.batchTestApprovedQuantity), " +
                                " sum(b.batchTestRejectedQuantity), " +
                                " sum(b.totalQuantity - b.batchTestedQuantity), " +
                                " sum(b.inTransitQuantity + b.receivedQuantity), " +
                                " sum(b.availableTested) " +
                                "from BatchStateGeo b " +
                                "where (:categoryId is null or b.geoStateId.categoryId = :categoryId) " +
                                "and (:sourceManufacturerIdsNull is true or b.geoStateId.manufacturerId in :sourceManufacturerIds) " +
                                "and (:countryGeoId is null or b.countryGeoId = :countryGeoId) " +
                                "and (:testManufacturerIdsNull is true or b.geoStateId.manufacturerId not in :testManufacturerIds) " +
                                "AND (:dateNull is true or (b.geoStateId.producedDate >= :fromDate and b.geoStateId.producedDate <= :toDate)) " +
                                "group by b.geoStateId.manufacturerId", Object[].class)
                .setParameter("categoryId", dto.getCategoryId())
                .setParameter("countryGeoId", dto.getSourceCountryGeoId())
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("sourceManufacturerIds", sourceManufacturerIds)
                .setParameter("sourceManufacturerIdsNull", sourceManufacturerIds==null)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate() == null ? new Date() : dto.getToDate())
                .setParameter("dateNull", dto.getFromDate()==null);

        return  query.getResultList();
    }

    public List<Object[]> getAllAgenciesEmpanelAggregateFilter(DashboardRequestDto dto, List<Long> testManufacturerIds, List<Long> sourceManufacturerIds, String empanelledStateGeoId) {
        TypedQuery<Object[]> query = em.createQuery(
                        "select" +
                                " b.geoStateId.manufacturerId," +
                                " b.districtGeoId," +
                                " sum(b.producedQuantity + b.inProductionQuantity), " +
                                " sum(b.batchTestApprovedQuantity), " +
                                " sum(b.batchTestRejectedQuantity), " +
                                " sum(b.totalQuantity - b.batchTestedQuantity), " +
                                " sum(b.inTransitQuantity + b.receivedQuantity), " +
                                " sum(b.availableTested) " +
                                "from BatchStateGeo b " +
                                "where (:categoryId is null or b.geoStateId.categoryId = :categoryId) " +
                                "and (:sourceManufacturerIdsNull is true or b.geoStateId.manufacturerId in :sourceManufacturerIds) " +
                                "and (:countryGeoId is null or b.countryGeoId = :countryGeoId) " +
                                "and (:stateGeoId is null or b.stateGeoId = :stateGeoId) "  +
                                "and (:districtGeoId is null or b.districtGeoId = :districtGeoId) " +
                                "and (:testManufacturerIdsNull is true or b.geoStateId.manufacturerId not in :testManufacturerIds) " +
                                "AND (:dateNull is true or (b.geoStateId.producedDate >= :fromDate and b.geoStateId.producedDate <= :toDate)) " +
                                "group by b.geoStateId.manufacturerId", Object[].class)
                .setParameter("categoryId", dto.getCategoryId())
                .setParameter("countryGeoId", dto.getSourceCountryGeoId())
                .setParameter("stateGeoId", dto.getSourceStateGeoId())
                .setParameter("districtGeoId", dto.getSourceDistrictGeoId())
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("sourceManufacturerIds", sourceManufacturerIds)
                .setParameter("sourceManufacturerIdsNull", sourceManufacturerIds==null)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate() == null ? new Date() : dto.getToDate())
                .setParameter("dateNull", dto.getFromDate()==null);

        return  query.getResultList();
    }

    public List<Object[]> getEmpaneledGeoAggregatedSumForCategory(DashboardRequestDto dto, List<Long> testManufacturerIds, List<Long> sourceManufacturerIds) {
        TypedQuery<Object[]> query = em.createQuery(
                        "select" +
                                " b.geoStateId.manufacturerId," +
                                " b.districtGeoId," +
                                " sum(b.producedQuantity + b.inProductionQuantity), " +
                                " sum(b.batchTestApprovedQuantity), " +
                                " sum(b.batchTestRejectedQuantity), " +
                                " sum(b.totalQuantity - b.batchTestedQuantity), " +
                                " sum(b.inTransitQuantity + b.receivedQuantity), " +
                                " sum(b.availableTested) " +
                                "from BatchStateGeo b " +
                                "where (:categoryId is null or b.geoStateId.categoryId = :categoryId) " +
                                "and (:sourceManufacturerIdsNull is true or b.geoStateId.manufacturerId in :sourceManufacturerIds) " +
                                "and (:countryGeoId is null or b.countryGeoId = :countryGeoId) " +
                                "and (:testManufacturerIdsNull is true or b.geoStateId.manufacturerId not in :testManufacturerIds) " +
                                "AND (:dateNull is true or (b.geoStateId.producedDate >= :fromDate and b.geoStateId.producedDate <= :toDate)) " +
                                "group by b.geoStateId.manufacturerId", Object[].class)
                .setParameter("categoryId", dto.getCategoryId())
                .setParameter("countryGeoId", dto.getSourceCountryGeoId())
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("sourceManufacturerIds", sourceManufacturerIds)
                .setParameter("sourceManufacturerIdsNull", sourceManufacturerIds==null)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate() == null ? new Date() : dto.getToDate())
                .setParameter("dateNull", dto.getFromDate()==null);

        return  query.getResultList();
    }

    public List<Object[]> getAllAgenciesEmpanelAggregateLotQuantity(DashboardRequestDto dto, List<Long> testManufacturerIds, List<Long> sourceManufacturerIds) {
        TypedQuery<Object[]> query = em.createQuery(
                        "select" +
                                " l.manufacturerId," +
                                " COALESCE(sum(case when l.state.name = :approvedState then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), 0), " +
                                " COALESCE(sum(case when l.state.name in :rejectedStates then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), 0), " +
                                " COALESCE(sum(case when l.state.name in :approvelpending then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), 0) " +
                                "from Lot l  " +
                                "where (:categoryId is null or l.category.id = :categoryId) " +
                                "and (:sourceManufacturerIdsNull is true or l.manufacturerId in :sourceManufacturerIds) " +
                                "and (:stateGeoId is null or l.targetStateGeoId = :stateGeoId) " +
                                "and (:districtGeoId is null or l.targetDistrictGeoId = :districtGeoId) " +
                                "and (:testManufacturerIdsNull is true or l.manufacturerId not in :testManufacturerIds) " +
                                "and (:dateNull is true or (l.dateOfDispatch >= :fromDate and l.dateOfDispatch <= :toDate)) " +
                                "and (:testManufacturerIdsNull is true or (l.manufacturerId not in :testManufacturerIds " +
                                "and l.targetManufacturerId not in :testManufacturerIds)) " +
                                "group by l.manufacturerId", Object[].class)
                .setParameter("categoryId", dto.getCategoryId())
                .setParameter("approvedState", "approved")
                .setParameter("stateGeoId", dto.getSourceStateGeoId())
                .setParameter("districtGeoId", dto.getSourceDistrictGeoId())
                .setParameter("rejectedStates", List.of("toSendBackRejected", "sentBackRejected","receivedRejected","lotSampleRejected"))
                .setParameter("approvelpending", List.of("dispatched","lotReceived","selfTestedLot","sentLotSampleToLabTest","lotSampleInLab","lotSampleLabTestDone"))
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("sourceManufacturerIds", sourceManufacturerIds)
                .setParameter("sourceManufacturerIdsNull", sourceManufacturerIds==null)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate() == null ? new Date() : dto.getToDate())
                .setParameter("dateNull", dto.getFromDate()==null);

        return  query.getResultList();
    }

    public List<Object[]> getAllAgenciesEmpanelAggregateLotQuantityFilter(DashboardRequestDto dto, List<Long> testManufacturerIds, List<Long> sourceManufacturerIds, String empanelledStateGeoId) {
        TypedQuery<Object[]> query = em.createQuery(
                        "select" +
                                " l.manufacturerId," +
                                " COALESCE(sum(case when l.state.name = :approvedState then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), 0), " +
                                " COALESCE(sum(case when l.state.name in :rejectedStates then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), 0), " +
                                " COALESCE(sum(case when l.state.name in :approvelpending then (l.totalQuantity * l.uom.conversionFactorKg) else 0D end), 0) " +
                                "from Lot l  " +
                                "where (:categoryId is null or l.category.id = :categoryId) " +
                                "and (:sourceManufacturerIdsNull is true or l.manufacturerId in :sourceManufacturerIds) " +
                                "and (:stateGeoId is null or l.targetStateGeoId = :stateGeoId) " +
                                "and (:sourceStateGeoId is null or l.sourceStateGeoId =:sourceStateGeoId) " +
                                "and (:districtGeoId is null or l.sourceDistrictGeoId = :districtGeoId) " +
                                "and (:testManufacturerIdsNull is true or l.manufacturerId not in :testManufacturerIds) " +
                                "and (:dateNull is true or (l.dateOfDispatch >= :fromDate and l.dateOfDispatch <= :toDate)) " +
                                "and (:testManufacturerIdsNull is true or (l.manufacturerId not in :testManufacturerIds " +
                                "and l.targetManufacturerId not in :testManufacturerIds)) " +
                                "group by l.manufacturerId", Object[].class)
                .setParameter("categoryId", dto.getCategoryId())
                .setParameter("approvedState", "approved")
                .setParameter("stateGeoId", empanelledStateGeoId)
                .setParameter("sourceStateGeoId", dto.getSourceStateGeoId())
                .setParameter("districtGeoId", dto.getSourceDistrictGeoId())
                .setParameter("rejectedStates", List.of("toSendBackRejected", "sentBackRejected","receivedRejected","lotSampleRejected"))
                .setParameter("approvelpending", List.of("dispatched","lotReceived","selfTestedLot","sentLotSampleToLabTest","lotSampleInLab","lotSampleLabTestDone"))
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("sourceManufacturerIds", sourceManufacturerIds)
                .setParameter("sourceManufacturerIdsNull", sourceManufacturerIds==null)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate() == null ? new Date() : dto.getToDate())
                .setParameter("dateNull", dto.getFromDate()==null);

        return  query.getResultList();
    }

    public Double[] getGeoAggregatedProductionSum(String filterBy, List<Long> testManufacturerIds, DashboardRequestDto dto, List<Long> manufacturerIds) {
        TypedQuery<Object[]> query = em.createQuery(
                        "select " +
                                "sum(b.producedQuantity + b.inProductionQuantity) as totalProduction, " +
                                "sum(b.approvedQuantity) as approvedQuantity, " +
                                "sum(b.availableTested) as availableTested, " +
                                "sum(b.availableNotTested) as availableNotTested " +
                                "from BatchStateGeo b " +
                                "where (b.geoStateId.categoryId = :categoryId) " +
                                "and (:manufacturerIdsNull is true or b.geoStateId.manufacturerId in :manufacturerIds) " +
                                "AND (b.geoStateId.producedDate >= :fromDate and b.geoStateId.producedDate <= :toDate) " +
                                "and (:filterBy is null or b."
                                + Optional.ofNullable(filterBy).orElse("countryGeoId") + " = :geoId) " +
                                "and (:testManufacturerIdsNull is true or b.geoStateId.manufacturerId NOT IN (:testManufacturerIds))", Object[].class)
                .setParameter("categoryId", dto.getCategoryId())
                .setParameter("filterBy", filterBy)
                .setParameter("geoId", dto.getGeoId())
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.size() <= 0)
                .setParameter("manufacturerIds", manufacturerIds)
                .setParameter("manufacturerIdsNull", manufacturerIds==null)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate());

        Object[] object = query.getSingleResult();
        Double[] results = new Double[4];
        results[0] = (Double) (((Object[]) object)[0]);
        results[1] = (Double) (((Object[]) object)[1]);
        results[2] = (Double) (((Object[]) object)[2]);
        results[3] = (Double) (((Object[]) object)[3]);
        return results;
    }

    public List<DashboardProductionResponseDto> getGeoAggregatedProductionSumForCategory(String filterBy, String groupBy, List<Long> testManufacturerIds, DashboardRequestDto dto) {
        TypedQuery<Object[]> query = em.createQuery(
                        "select " +
                                "b." + groupBy + ", " +
                                "sum(b.producedQuantity + b.inProductionQuantity) as totalProduction, " +
                                "sum(b.approvedQuantity) as approvedQuantity, " +
                                "sum(b.availableTested) as availableTested, " +
                                "sum(b.availableNotTested) as availableNotTested " +
                                "from BatchStateGeo b " +
                                "where (b.geoStateId.categoryId = :categoryId) " +
                                "AND (b.geoStateId.producedDate >= :fromDate and b.geoStateId.producedDate <= :toDate) " +
                                "and (:filterBy is null or b."
                                + Optional.ofNullable(filterBy).orElse("countryGeoId") + " = :geoId) " +
                                "and (:testManufacturerIdsNull is true or b.geoStateId.manufacturerId NOT IN (:testManufacturerIds)) " +
                                "group by b." + groupBy, Object[].class)
                .setParameter("categoryId", dto.getCategoryId())
                .setParameter("filterBy", filterBy)
                .setParameter("geoId", dto.getGeoId())
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.size() <= 0)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate());

        List<Object[]> objects = query.getResultList();
        return objects.stream().map(res -> {
            DashboardProductionResponseDto responseDto = new DashboardProductionResponseDto();
            responseDto.setId((String) res[0]);
            responseDto.setTotalProduction((Double) res[1]);
            responseDto.setApprovedQuantity((Double) res[2]);
            responseDto.setAvailableTested((Double) res[3]);
            responseDto.setAvailableNotTested((Double) res[4]);
            responseDto.setCategoryId(dto.getCategoryId());
            return responseDto;
        }).toList();
    }

    public Double[] getGeoAggregatedTestingSum(String filterBy, List<Long> testManufacturerIds, DashboardRequestDto dto) {
        TypedQuery<Object[]> query = em.createQuery(
                        "select " +
                                "sum(b.batchTestedQuantity), " +
                                "sum(b.lotTestApprovedQuantity), " +
                                "sum(b.lotTestRejectedQuantity), " +
                                "sum(b.inTransitQuantity), " +
                                "sum(b.availableTested), " +
                                "sum(b.batchTestRejectedQuantity), " +
                                "sum(b.batchTestApprovedQuantity), " +
                                "sum(b.inTransitQuantity  + b.receivedQuantity) " +
                                "from BatchStateGeo b " +
                                "where (b.geoStateId.categoryId = :categoryId) " +
                                "AND (b.geoStateId.producedDate >= :fromDate and b.geoStateId.producedDate <= :toDate) " +
                                "and (:filterBy is null or b."
                                + Optional.ofNullable(filterBy).orElse("countryGeoId") + " = :geoId) " +
                                "and (:testManufacturerIdsNull is true or b.geoStateId.manufacturerId NOT IN (:testManufacturerIds))", Object[].class)
                .setParameter("categoryId", dto.getCategoryId())
                .setParameter("filterBy", filterBy)
                .setParameter("geoId", dto.getGeoId())
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate());

        Object[] object = query.getSingleResult();
        Double[] results = new Double[8];
        results[0] = (Double) (((Object[]) object)[0]);
        results[1] = (Double) (((Object[]) object)[1]);
        results[2] = (Double) (((Object[]) object)[2]);
        results[3] = (Double) (((Object[]) object)[3]);
        results[4] = (Double) (((Object[]) object)[4]);
        results[5] = (Double) (((Object[]) object)[5]);
        results[6] = (Double) (((Object[]) object)[6]);
        results[7] = (Double) (((Object[]) object)[7]);
        return results;
    }

    public List<DashboardTestingResponseDto> getGeoAggregatedTestingSumForCategory(String filterBy, String groupBy, List<Long> testManufacturerIds, DashboardRequestDto dto) {
        TypedQuery<Object[]> query = em.createQuery(
                        "select " +
                                "b." + groupBy + ", " +
                                "sum(b.batchTestedQuantity), " +
                                "sum(b.lotTestApprovedQuantity), " +
                                "sum(b.lotTestRejectedQuantity), " +
                                "sum(b.inTransitQuantity), " +
                                "sum(b.availableTested), " +
                                "sum(b.batchTestRejectedQuantity) " +
                                "from BatchStateGeo b " +
                                "where (b.geoStateId.categoryId = :categoryId) " +
                                "AND (b.geoStateId.producedDate >= :fromDate and b.geoStateId.producedDate <= :toDate) " +
                                "and (:filterBy is null or b."
                                + Optional.ofNullable(filterBy).orElse("countryGeoId") + " = :geoId) " +
                                "and (:testManufacturerIdsNull is true or b.geoStateId.manufacturerId NOT IN (:testManufacturerIds)) " +
                                "group by b." + groupBy, Object[].class)
                .setParameter("categoryId", dto.getCategoryId())
                .setParameter("filterBy", filterBy)
                .setParameter("geoId", dto.getGeoId())
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.isEmpty())
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate());

        List<Object[]> objects = query.getResultList();

        return objects.stream().map(res -> {
            DashboardTestingResponseDto responseDto = new DashboardTestingResponseDto();
            responseDto.setId((String) res[0]);
            responseDto.setBatchTested((Double) res[1]);
            responseDto.setLotApproved((Double) res[2]);
            responseDto.setLotRejected((Double) res[3]);
            responseDto.setLotInTransit((Double) res[4]);
            responseDto.setAvailableTested((Double) res[5]);
            responseDto.setBatchRejected((Double) res[6]);
            responseDto.setCategoryId(dto.getCategoryId());
            return responseDto;
        }).toList();
    }

    public List<Object[]> getGeoAggregatedProductionQuantity(String filterBy, GeoManufacturerProductionResponseType responseType, List<Long> testManufacturerIds, DashboardRequestDto dto) {
        String column = "sum(b." + responseType + ")";
        if(responseType.equals(GeoManufacturerProductionResponseType.totalProduction)){
            column = "sum(b.producedQuantity + b.inProductionQuantity)";
        }
        TypedQuery<Object[]> query = em.createQuery(
                        "select " +
                                "b.geoStateId.manufacturerId, " +
                                "b.districtGeoId, " +
                                "b.stateGeoId, " +
                                column +
                                " from BatchStateGeo b " +
                                "where (b.geoStateId.categoryId = :categoryId) " +
                                "AND (b.geoStateId.producedDate >= :fromDate and b.geoStateId.producedDate <= :toDate) " +
                                "and (:filterBy is null or b."
                                + Optional.ofNullable(filterBy).orElse("countryGeoId") + " = :geoId) " +
                                "and (:testManufacturerIdsNull is true or b.geoStateId.manufacturerId NOT IN (:testManufacturerIds)) " +
                                "group by b.geoStateId.manufacturerId " +
                                "HAVING "+ column + " <>0", Object[].class)
                .setParameter("categoryId", dto.getCategoryId())
                .setParameter("filterBy", filterBy)
                .setParameter("geoId", dto.getGeoId())
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.size() <= 0)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate());

        return query.getResultList();
    }

    public List<Object[]> getGeoAggregatedTestingQuantity(String filterBy, GeoManufacturerTestingResponseType responseType, List<Long> testManufacturerIds, DashboardRequestDto dto) {
        String column = "sum(b." + responseType + ")";
        if(responseType.equals(GeoManufacturerTestingResponseType.approvedQuantity)){
            column = "sum(b.lotTestApprovedQuantity)";
        }
        if(responseType.equals(GeoManufacturerTestingResponseType.lotRejected)){
            column = "sum(b.lotTestRejectedQuantity)";
        }
        if(responseType.equals(GeoManufacturerTestingResponseType.transitQuantity)){
            column = "sum(b.inTransitQuantity)";
        }
        TypedQuery<Object[]> query = em.createQuery(
                        "select " +
                                "b.geoStateId.manufacturerId, " +
                                "b.districtGeoId, " +
                                "b.stateGeoId, " +
                                column +
                                " from BatchStateGeo b " +
                                "where (b.geoStateId.categoryId = :categoryId) " +
                                "AND (b.geoStateId.producedDate >= :fromDate and b.geoStateId.producedDate <= :toDate) " +
                                "and (:filterBy is null or b."
                                + Optional.ofNullable(filterBy).orElse("countryGeoId") + " = :geoId) " +
                                "and (:testManufacturerIdsNull is true or b.geoStateId.manufacturerId NOT IN (:testManufacturerIds)) " +
                                "group by b.geoStateId.manufacturerId " +
                                "HAVING "+ column + "<> 0", Object[].class)
                .setParameter("categoryId", dto.getCategoryId())
                .setParameter("filterBy", filterBy)
                .setParameter("geoId", dto.getGeoId())
                .setParameter("testManufacturerIds", testManufacturerIds)
                .setParameter("testManufacturerIdsNull", testManufacturerIds.size() <= 0)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate());

        return query.getResultList();
    }
}