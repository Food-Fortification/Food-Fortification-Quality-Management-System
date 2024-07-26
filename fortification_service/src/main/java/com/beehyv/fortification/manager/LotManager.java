package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.LotDao;
import com.beehyv.fortification.dto.requestDto.DashboardRequestDto;
import com.beehyv.fortification.dto.requestDto.SearchListRequest;
import com.beehyv.fortification.dto.responseDto.DashboardTestingResponseDto;
import com.beehyv.fortification.dto.responseDto.DashboardWarehouseResponseDto;
import com.beehyv.fortification.dto.responseDto.EmpanelledAggregatesResponseDto;
import com.beehyv.fortification.entity.Lot;
import com.beehyv.fortification.entity.LotStateGeo;
import com.beehyv.fortification.entity.RoleCategoryType;
import com.beehyv.fortification.entity.State;
import com.beehyv.fortification.enums.GeoManufacturerTestingResponseType;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class LotManager extends BaseManager<Lot, LotDao> {
    private final LotDao dao;
    private final CategoryManager categoryManager;
    public LotManager(LotDao dao, CategoryManager categoryManager) {
        super(dao);
        this.dao = dao;
        this.categoryManager = categoryManager;
    }

    public Lot save(Lot lot) {
        return dao.update(lot);
    }

    public Lot findByIdAndManufacturerId(Long id, Long manufacturerId){
        return dao.findByIdAndManufacturerId(id, manufacturerId);
    }

    public Long getCount(int listSize, Long batchId, Integer pageNumber, Integer pageSize) {
        if(pageSize == null || pageNumber == null) {
            return ((Integer) listSize).longValue();
        }
        return dao.getCount(batchId);
    }

    public List<Lot> findAllByBatchId(Long batchId, Integer pageNumber, Integer pageSize) {
        return dao.findAllByBatchId(batchId, pageNumber, pageSize);
    }

    public List<Lot> findAllByCategoryId(Long manufacturerId, Long categoryId, SearchListRequest searchListRequest, Integer pageNumber, Integer pageSize) {
        List<Lot> sourceLots = dao.findAllByCategoryId(manufacturerId, categoryId, searchListRequest);
        sourceLots.addAll(dao.findAllByCategoryIdForTargetLot(manufacturerId, categoryId, searchListRequest));
        List<Lot> sortedLots = sourceLots.stream()
                .sorted(Comparator.comparingLong(Lot::getId))
                .toList();
        int start = (pageNumber - 1) * pageSize;
        int end = Math.min(start + pageSize, sortedLots.size());

        return sortedLots.subList(start, end);
    }

    public List<Lot> findAllByCategoryIds(SearchListRequest searchRequest, Integer pageNumber, Integer pageSize, List<Long> testManufacturerIds) {
        List<Long> categoryIds = categoryManager.getUserCategoryIds(searchRequest, RoleCategoryType.MODULE);
        return dao.findAllByCategoryIds(searchRequest, categoryIds, pageNumber, pageSize, testManufacturerIds);
    }

    public Long getCount(int size, Long manufacturerId, Long categoryId, Integer pageNumber, Integer pageSize, SearchListRequest searchListRequest) {
        if (pageSize == null || pageNumber == null) {
            return ((Integer) size).longValue();
        }
        return dao.getCount(manufacturerId, categoryId, searchListRequest) + dao.getCountForTargetLot(manufacturerId, categoryId, searchListRequest);
    }

    public Long getLotInventoryCount(int size, Long manufacturerId, Long categoryId, Integer pageNumber, Integer pageSize, SearchListRequest searchListRequest, Boolean approvedSourceLots) {
        if (pageSize == null || pageNumber == null) {
            return ((Integer) size).longValue();
        }
        return dao.getLotInventoryCount(manufacturerId, categoryId, searchListRequest, approvedSourceLots) + dao.getTargetLotInventoryCount(manufacturerId, categoryId, searchListRequest, approvedSourceLots);
    }

    public Long getCount(int size, Integer pageNumber, Integer pageSize, SearchListRequest searchListRequest, List<Long> testManufacturerIds) {
        if (pageSize == null || pageNumber == null) {
            return ((Integer) size).longValue();
        }
        return dao.getCount(searchListRequest, categoryManager.getUserCategoryIds(searchListRequest, RoleCategoryType.MODULE), testManufacturerIds);
    }

    public Long getCount(int size, Long manufacturerId, Long categoryId, String search, Integer pageNumber, Integer pageSize) {
        if (pageSize == null || pageNumber == null) {
            return ((Integer) size).longValue();
        }
        return dao.getCount(manufacturerId, categoryId, search) + dao.getTargetLotsCount(manufacturerId, categoryId, search);
    }

    public List<Lot> getAllLotsBySourceCategoryId(Long manufacturerId, Long categoryId, String search, Integer pageNumber, Integer pageSize) {
        List<Lot> sourceLots = dao.findAllBySourceCategoryId(manufacturerId, categoryId, search, pageNumber, pageSize);
        sourceLots.addAll(dao.findAllTargetLotsBySourceCategoryId(manufacturerId, categoryId, search, pageNumber, pageSize));
        return sourceLots.stream().sorted(((o1, o2) ->
        {
            if (Objects.equals(o1.getId(), o2.getId())) return 0;
            return o1.getId() > o2.getId() ? -1 : 1;
        })).toList();
    }

    public List<Lot> getAllByIds(List<Long> ids) {
        return dao.findAllByIds(ids);
    }

    public List<Lot> getLotInventory(Long manufacturerId, Long categoryId, SearchListRequest searchRequest, Integer pageNumber, Integer pageSize, Boolean approvedSourceLots) {
        List<Lot> sourceLots = dao.getLotInventory(manufacturerId, categoryId, searchRequest, pageNumber, pageSize, approvedSourceLots);
        sourceLots.addAll(dao.getTargetLotInventory(manufacturerId, categoryId, searchRequest, pageNumber, pageSize, approvedSourceLots));
        List<Lot> sortedLots = sourceLots.stream()
                .sorted(Comparator.comparingLong(Lot::getId))
                .toList();
        if(pageNumber!=null || pageSize!=null) {
            int start = (pageNumber - 1) * pageSize;
            int end = Math.min(start + pageSize, sortedLots.size());

            return sortedLots.subList(start, end);
        }
        else {
            return sortedLots;
        }
    }

    public Lot findByUUID(String uuid) {
        return dao.findByUUID(uuid);
    }

    public Double[] getQuantitySum(Long manufacturerId, Long categoryId) {
        return dao.getQuantitySum(manufacturerId, categoryId);
    }

    public List<LotStateGeo> findAllAggregateByManufacturerIdAndCategoryId(int pageNumber, int pageSize, List<State> state) {
        return dao.findAllAggregateByManufacturerIdAndCategoryId(pageNumber, pageSize, state);
    }

    public List<Lot> findAllLotsForInspection(Long categoryId, SearchListRequest searchListRequest, List<Long> testManufacturerIds, List<Long> lotIds, Integer pageNumber, Integer pageSize) {
        return dao.findAllLotsForInspection(categoryId, searchListRequest, testManufacturerIds, lotIds, pageNumber, pageSize);
    }

    public Long getCountForInspection(Long categoryId, SearchListRequest searchListRequest, List<Long> testManufacturerIds) {
        return dao.getCountForInspection(categoryId, searchListRequest, testManufacturerIds);
    }

    public List<Lot> findAllByIds(List<Long> ids) {
        return dao.findAllByIds(ids);
    }

    public List<Long[]> migrateData() {
        return dao.migrateData();
    }

    public Double[] getWarehouseAggregateProductionQuantities(Long categoryId, List<Long> testManufacturerIds) {
        return dao.getWarehouseAggregateProductionQuantities(categoryId, testManufacturerIds);
    }

    public Double[] getWarehouseAggregateTestingQuantities(Long categoryId, List<Long> testManufacturerIds) {
        return dao.getWarehouseAggregateTestingQuantities(categoryId, testManufacturerIds);
    }

    public List<Object[]> getWarehouseAggregateProductionManufacturerQuantities(Long categoryId, List<Long> testManufacturerIds) {
        return dao.getWarehouseAggregateProductionManufacturerQuantities(categoryId, testManufacturerIds);
    }

    public List<Object[]> getWarehouseAggregateTestingManufacturerQuantities(Long categoryId, List<Long> testManufacturerIds) {
        return dao.getWarehouseAggregateTestingManufacturerQuantities(categoryId, testManufacturerIds);
    }

    public List<Lot> findByLotNo(String lotNo){
        return dao.findByLotNo(lotNo);
    }

    public List<Lot> findTargetLotsBySourceLotNo(String sourceLotNo, Long manufacturerId){
        return dao.findTargetLotsBySourceLotNo(sourceLotNo, manufacturerId);
    }

    public List<Long> findAllTargetManufacturerIdsBySource(List<Long> manufacturerIds){
        return dao.findAllTargetManufacturerIdsBySource(manufacturerIds).stream().collect(Collectors.toSet()).stream().toList();
    }

    public List<Long> findAllSourceManufacturerIdsByDistrictGeoId(Long categoryId, List<String> districtGeoIds, DashboardRequestDto dto, List<Long> targetManufacturerIds) {
        return dao.findAllSourceManufacturerIdsByDistrictGeoId(categoryId, districtGeoIds, dto, targetManufacturerIds);
    }

    public List<Object[]> findAllTargetManufacturerAggregates(Long categoryId, String districtGeoId, DashboardRequestDto dto, Long sourceManufacturerId, List<String> lotStates, List<Long> testManufacturerIds, List<String> lotRejectedStates){
        return dao.findAllTargetManufacturerAggregates(categoryId, districtGeoId, dto, sourceManufacturerId, lotStates, testManufacturerIds, lotRejectedStates);
    }

    public List<Lot> findAllLotsBySourceAndTargetManufacturer(Long categoryId, DashboardRequestDto dto, Long sourceManufacturerId, Long targetManufacturerId, List<Long> testManufacturerIds){
        return dao.findAllLotsBySourceAndTargetManufacturer(categoryId, dto, sourceManufacturerId, targetManufacturerId, testManufacturerIds);
    }

    public List<Object[]> getSourceWarehouseAggregate(Long categoryId, String districtGeoId, DashboardRequestDto dto, List<String> lotStates, List<Long> testManufacturerIds, List<String> lotRejectedStates) {
        return dao.getSourceWarehouseAggregate(categoryId, districtGeoId, dto, lotStates, testManufacturerIds, lotRejectedStates);
    }

    public List<Object[]> getAggregateByDistrictsGeoId(Long categoryId, Long sourceManufacturerId, DashboardRequestDto dto, List<String> lotStates, List<Long> testManufacturerIds, List<String> lotRejectedStates) {
        return dao.getAggregateByDistrictsGeoId(categoryId, sourceManufacturerId, dto, lotStates, testManufacturerIds, lotRejectedStates);
    }

    public List<Object[]> findAllTargetManufacturerAggregates(Long categoryId, DashboardRequestDto dto, Long sourceManufacturerId, List<Long> targetManufacturerIds, List<String> lotApprovalPendingStates, List<Long> testManufacturerIds, List<String> lotRejectedStates){
        return dao.findAllTargetManufacturerAggregates(categoryId, dto, sourceManufacturerId, targetManufacturerIds, lotApprovalPendingStates, testManufacturerIds, lotRejectedStates);
    }

    public List<Object[]> findAllEmpaneledTargetManufacturerAggregates(Long categoryId, DashboardRequestDto dto, Long sourceManufacturerId, List<Long> targetManufacturerIds, List<String> lotApprovalPendingStates, List<Long> testManufacturerIds, List<String> lotRejectedStates){
        return dao.findAllEmpaneledTargetManufacturerAggregates(categoryId, dto, sourceManufacturerId, targetManufacturerIds, lotApprovalPendingStates, testManufacturerIds, lotRejectedStates);
    }
    public Double[] getWarehouseAggregateForDashboardFRK(String districtGeoId, String stateGeoId, List<Long> testManufacturerIds, DashboardRequestDto dto,Long CategoryId) {
        return dao.getWarehouseAggregateForDashboardFRK(districtGeoId, stateGeoId, testManufacturerIds, dto,CategoryId);
    }

    public Double[] getEmpanelWarehouseAggregateForDashboardFRK(String districtGeoId, String stateGeoId, String geoIdType, List<Long> testManufacturerIds, DashboardRequestDto dto,Long CategoryId, List<Long> targetManufacturerIds) {
        return dao.getEmpanelWarehouseAggregateForDashboardFRK(districtGeoId, stateGeoId, geoIdType, testManufacturerIds, dto,CategoryId, targetManufacturerIds);
    }

    public Double[] getWarehouseAggregateForDashboard(String districtGeoId, String stateGeoId, List<Long> testManufacturerIds, DashboardRequestDto dto,Long CategoryId) {
        return dao.getWarehouseAggregateForDashboard(districtGeoId, stateGeoId, testManufacturerIds, dto,CategoryId);
    }

    public List<DashboardWarehouseResponseDto> getWarehouseQuantitiesForFRK(String groupBy, List<Long> testManufacturerIds, DashboardRequestDto dto) {
        return dao.getWarehouseQuantitiesForFRK(groupBy, testManufacturerIds, dto);
    }

    public List<DashboardWarehouseResponseDto> getEmpanelWarehouseQuantities(String groupBy, List<Long> testManufacturerIds, DashboardRequestDto dto, List<Long> targetManufacturerIds) {
        return dao.getEmpanelWarehouseQuantities(groupBy, testManufacturerIds, dto, targetManufacturerIds);
    }

    public List<DashboardWarehouseResponseDto> getWarehouseQuantitiesForMiller(String groupBy, List<Long> testManufacturerIds, DashboardRequestDto dto) {
        return dao.getWarehouseQuantitiesForMiller(groupBy, testManufacturerIds, dto);
    }

    public Double[] getAllEmpanelledManufacturersAggregate(Long categoryId, List<Long> sourceManufacturerIds, DashboardRequestDto dto, List<Long> testManufacturerIds, String empaneledStategeoId, String filterBy) {
        return dao.getAllEmpanelledManufacturersAggregate(categoryId, sourceManufacturerIds, dto, testManufacturerIds, empaneledStategeoId, filterBy);
    }

    public List<DashboardTestingResponseDto>  getAllEmpanelledManufacturersAggregateSumForCategory(Long categoryId, String filterBy, String groupBy, List<Long> sourceManufacturerIds, DashboardRequestDto dto, List<Long> testManufacturerIds, String empaneledStategeoId) {
        return dao.getAllEmpanelledManufacturersAggregateSumForCategory(categoryId, filterBy, groupBy, sourceManufacturerIds, dto, testManufacturerIds, empaneledStategeoId);
    }

    public List<Object[]> getEmpanelledGeoAggregate(Long categoryId, GeoManufacturerTestingResponseType responseType, List<Long> sourceManufacturerIds, DashboardRequestDto dto, List<Long> testManufacturerIds, String stateGeoId) {
        return dao.getEmpanelledGeoAggregate(categoryId, responseType, sourceManufacturerIds, dto, testManufacturerIds, stateGeoId);
    }

    public Double[] getAllEmpanelledManufacturersAggregateForWarehouse(DashboardRequestDto dto, List<Long> testManufacturerIds) {
        return dao.getAllEmpanelledManufacturersAggregateForWarehouse(dto, testManufacturerIds);
    }

    public List<EmpanelledAggregatesResponseDto> getAllEmpanelledManufacturersQuantityForWarehouse(DashboardRequestDto dto, List<Long> testManufacturerIds) {
        return dao.getAllEmpanelledManufacturersQuantityForWarehouse(dto, testManufacturerIds);
    }

    public List<Object[]> findAllSourceManufacturerAggregatesByTarget(Long categoryId, DashboardRequestDto dto, Long targetManufacturerId, List<String> lotApprovalPendingStates, List<Long> testManufacturerIds, List<String> lotRejectedStates, List<Long> sourceManufacturerIds){
        return dao.findAllSourceManufacturerAggregatesByTarget(categoryId, dto, targetManufacturerId, lotApprovalPendingStates, testManufacturerIds, lotRejectedStates, sourceManufacturerIds);
    }

    public List<Object[]> getAllWarehouseAggregateForMiller(Long categoryId, DashboardRequestDto dto, List<Long> testManufacturerIds) {
        return dao.getAllWarehouseAggregateForMiller(categoryId, dto, testManufacturerIds);
    }

    public List<Object[]> getAllWarehouseAggregateForFRK(Long categoryId, DashboardRequestDto dto, List<Long> testManufacturerIds) {
        return dao.getAllWarehouseAggregateForFRK(categoryId, dto, testManufacturerIds);
    }

    public List<Object[]> getAllEmpanelWarehouseAggregate(Long categoryId, DashboardRequestDto dto, List<Long> testManufacturerIds,List<Long> targetManufacturerIds, String empanelStateGeoId) {
        return dao.getAllEmpanelWarehouseAggregate(categoryId, dto, testManufacturerIds,targetManufacturerIds,empanelStateGeoId);
    }
}
