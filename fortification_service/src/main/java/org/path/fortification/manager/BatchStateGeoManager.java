package org.path.fortification.manager;

import org.path.fortification.dao.BatchStateGeoDao;
import org.path.fortification.dto.requestDto.DashboardRequestDto;
import org.path.fortification.dto.responseDto.DashboardTestingResponseDto;
import org.path.fortification.dto.responseDto.NewDashboardProductionResponseDto;
import org.path.fortification.dto.responseDto.ProductionResponseDto;
import org.path.fortification.dto.responseDto.TestingResponseDto;
import org.path.fortification.entity.BatchStateGeo;
import org.path.fortification.entity.GeoStateId;
import org.path.fortification.enums.GeoManufacturerProductionResponseType;
import org.path.fortification.enums.GeoManufacturerTestingResponseType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class BatchStateGeoManager extends BaseManager<BatchStateGeo, BatchStateGeoDao> {
    private final BatchStateGeoDao dao;

    public BatchStateGeoManager(BatchStateGeoDao dao) {
        super(dao);
        this.dao = dao;
    }

    public BatchStateGeo findByGeoStateId(GeoStateId geoStateId) {
        return dao.findByCategoryIdAndManufacturerIdAndYear(geoStateId);
    }

    public List<Object[]> findByCategoryIdsAndManufacturerId(List<Long> collect, Long manufacturerId, Integer year, DashboardRequestDto dto) {
        return dao.findByCategoryIdsAndManufacturerId(collect, manufacturerId, year, dto);
    }

    public Long getNumberOfBatches(String filterBy, List<Long> testManufacturerIds, DashboardRequestDto dto, List<Long> manufacturerIds){
        return dao.getNumberOfBatches(filterBy, testManufacturerIds, dto, manufacturerIds);
    }
    public Long getFilteredNumberOfBatches(List<String> filterByStates, List<Long> testManufacturerIds, DashboardRequestDto dto, List<Long> manufacturerIds, Boolean isLabTested){
        return dao.getFilteredNumberOfBatches(filterByStates,testManufacturerIds,dto,manufacturerIds, isLabTested);
    }

    public List<BatchStateGeo> findManufacturerQuantityByCategoryIdAndState(Long categoryId, String filterBy, String geoId,
                                                                            String orderBy, Set<Long> mids, Integer year,
                                                                            Integer pageNumber, Integer pageSize, DashboardRequestDto dto) {
        return dao.findManufacturerQuantityByCategoryIdAndState(categoryId, filterBy, geoId, orderBy, mids, year, pageNumber, pageSize, dto);
    }

    public Double[] getGeoAggregatedProductionSum(Long categoryId, String filterBy, String geoId, List<Long> testManufacturerIds, Integer year, DashboardRequestDto dto) {
        return dao.getGeoAggregatedProductionSum(categoryId, filterBy, geoId, testManufacturerIds, year, dto);
    }

    public List<ProductionResponseDto> getGeoAggregatedProductionQuantity(Long categoryId, String groupBy, String filterBy, String geoId, List<Long> testManufacturerIds, Integer year, DashboardRequestDto dto) {
        return dao.getGeoAggregatedProductionQuantity(categoryId, groupBy, filterBy, geoId, testManufacturerIds, year, dto);
    }

    public Double[] getGeoAggregatedTestingSum(Long categoryId, String filterBy, String geoId, List<Long> testManufacturerIds, Integer year, DashboardRequestDto dto) {
        return dao.getGeoAggregatedTestingSum(categoryId, filterBy, geoId, testManufacturerIds, year, dto);
    }

    public List<Object[]> getAllAgenciesAggregate(DashboardRequestDto dto, List<Long> testManufacturerIds, List<Long> sourceManufacturerIds) {
        return dao.getAllAgenciesAggregate(dto, testManufacturerIds, sourceManufacturerIds);
    }

    public List<Object[]> getAllAgenciesEmpanelAggregate(DashboardRequestDto dto, List<Long> testManufacturerIds, List<Long> sourceManufacturerIds) {
        return dao.getAllAgenciesEmpanelAggregate(dto, testManufacturerIds, sourceManufacturerIds);
    }

    public List<Object[]> getAllAgenciesEmpanelAggregateFilter(DashboardRequestDto dto, List<Long> testManufacturerIds, List<Long> sourceManufacturerIds, String empanelledStateGeoId) {
        return dao.getAllAgenciesEmpanelAggregateFilter(dto, testManufacturerIds, sourceManufacturerIds, empanelledStateGeoId);
    }

    public List<Object[]> getEmpaneledGeoAggregatedSumForCategory(DashboardRequestDto dto, List<Long> testManufacturerIds, List<Long> sourceManufacturerIds) {
        return dao.getEmpaneledGeoAggregatedSumForCategory(dto, testManufacturerIds, sourceManufacturerIds);
    }

    public List<Object[]> getAllAgenciesEmpanelAggregateLotQuantity(DashboardRequestDto dto, List<Long> testManufacturerIds, List<Long> sourceManufacturerIds) {
        return dao.getAllAgenciesEmpanelAggregateLotQuantity(dto, testManufacturerIds, sourceManufacturerIds);
    }

    public List<Object[]> getAllAgenciesEmpanelAggregateLotQuantityFilter(DashboardRequestDto dto, List<Long> testManufacturerIds, List<Long> sourceManufacturerIds, String empanelledStateGeoId) {
        return dao.getAllAgenciesEmpanelAggregateLotQuantityFilter(dto, testManufacturerIds, sourceManufacturerIds, empanelledStateGeoId);
    }

    public List<TestingResponseDto> getGeoAggregatedTestingQuantity(Long categoryId, String groupBy, String filterBy, String geoId, List<Long> testManufacturerIds, Integer year, DashboardRequestDto dto) {
        return dao.getGeoAggregatedTestingQuantity(categoryId, groupBy, filterBy, geoId, testManufacturerIds, year, dto);
    }

    public Long findManufacturerQuantityByCategoryIdAndStateCount(int size, Long categoryId, String filterBy, String geoId,
                                                                  Integer year, Integer pageNumber, Integer pageSize, DashboardRequestDto dto) {
        if (pageSize == null || pageNumber == null) {
            return ((Integer) size).longValue();
        }
        return dao.findManufacturerQuantityByCategoryIdAndStateCount(categoryId, filterBy, geoId, year, dto);
    }

    public List<Double[]> findTotalQuantityForCategoriesByGeoId(String geoIdType, String geoId, Integer year, List<Long> testManufacturerIds, DashboardRequestDto dto) {
        return dao.getGeoAggregatedProductionSum(geoIdType, geoId, year, testManufacturerIds, dto);
    }

    public List<Object[]> getAllSourceManufacturersAggregate(Long categoryId, DashboardRequestDto dto, List<Long> manufacturerIds, List<Long> testManufacturerIds) {
        return dao.getAllSourceManufacturersAggregate(categoryId, dto, manufacturerIds, testManufacturerIds);
    }

    public Double[] getGeoAggregatedProductionSum(String filterBy, List<Long> testManufacturerIds, DashboardRequestDto dto, List<Long> manufacturerIds) {
        return dao.getGeoAggregatedProductionSum(filterBy, testManufacturerIds, dto, manufacturerIds);
    }

    public List<NewDashboardProductionResponseDto> getGeoAggregatedProductionSumForCategory(String filterBy, String groupBy, List<Long> testManufacturerIds, DashboardRequestDto dto) {
        return dao.getGeoAggregatedProductionSumForCategory(filterBy, groupBy, testManufacturerIds, dto);
    }

    public Double[] getGeoAggregatedTestingSum(String filterBy, List<Long> testManufacturerIds, DashboardRequestDto dto) {
        return dao.getGeoAggregatedTestingSum(filterBy, testManufacturerIds, dto);
    }

    public List<DashboardTestingResponseDto> getGeoAggregatedTestingSumForCategory(String filterBy, String groupBy, List<Long> testManufacturerIds, DashboardRequestDto dto) {
        return dao.getGeoAggregatedTestingSumForCategory(filterBy, groupBy, testManufacturerIds, dto);
    }

    public List<Object[]> getGeoAggregatedProductionQuantity(String filterBy, GeoManufacturerProductionResponseType responseType, List<Long> testManufacturerIds, DashboardRequestDto dto) {
        return dao.getGeoAggregatedProductionQuantity(filterBy, responseType, testManufacturerIds, dto);
    }

    public List<Object[]> getGeoAggregatedTestingQuantity(String filterBy, GeoManufacturerTestingResponseType responseType, List<Long> testManufacturerIds, DashboardRequestDto dto) {
        return dao.getGeoAggregatedTestingQuantity(filterBy, responseType, testManufacturerIds, dto);
    }
}
