package org.path.fortification.manager;

import org.path.fortification.dao.LotStateGeoDao;
import org.path.fortification.dto.requestDto.DashboardRequestDto;
import org.path.fortification.entity.GeoStateId;
import org.path.fortification.entity.LotStateGeo;
import org.path.fortification.enums.GeoManufacturerProductionResponseType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LotStateGeoManager extends BaseManager<LotStateGeo, LotStateGeoDao> {
    private final LotStateGeoDao dao;
    public LotStateGeoManager(LotStateGeoDao dao) {
        super(dao);
        this.dao = dao;
    }

    public LotStateGeo findByCategoryIdAndManufacturerId(GeoStateId geoStateId) {
        return dao.findByCategoryIdAndManufacturerId(geoStateId);
    }

    public Double getUsedQuantityProductionSum(String filterBy, List<Long> testManufacturerIds, DashboardRequestDto dto, List<Long> manufacturerIds) {
        return dao.getUsedQuantityProductionSum(filterBy,testManufacturerIds, dto, manufacturerIds);
    }
    public  List<Object[]> getUsedQuantityProductionSumByCategory(String filterByUsed,String groupByUsed,List<Long> testManufacturerIds, DashboardRequestDto dto, List<Long> manufacturerIds){
        return dao.getUsedQuantityProductionSumByCategory(filterByUsed,groupByUsed,testManufacturerIds,dto,manufacturerIds);
    }

    public List<Object[]> getAggregateByDistrictsGeoId(Long categoryId, List<String> districtGeoIds, DashboardRequestDto dto, List<Long> testManufacturerIds) {
        return dao.getAggregateByDistrictsGeoId(categoryId, districtGeoIds, dto, testManufacturerIds);
    }

    public List<Object[]> getAllWarehouseAggregateForMiller(Long categoryId, DashboardRequestDto dto, List<Long> testManufacturerIds) {
        return dao.getAllWarehouseAggregateForMiller(categoryId, dto, testManufacturerIds);
    }

    public List<Object[]> getAllWarehouseAggregateForFRK(Long categoryId, DashboardRequestDto dto, List<Long> testManufacturerIds) {
        return dao.getAllWarehouseAggregateForFRK(categoryId, dto, testManufacturerIds);
    }

    public List<Object[]> getGeoAggregatedUsedQuantity(GeoManufacturerProductionResponseType responseType, List<Long> testManufacturerIds, DashboardRequestDto dto) {
        return dao.getGeoAggregatedUsedQuantity(responseType, testManufacturerIds, dto);
    }
}
