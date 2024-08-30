package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.LotStateGeoDao;
import com.beehyv.fortification.dto.requestDto.DashboardRequestDto;
import com.beehyv.fortification.entity.GeoStateId;
import com.beehyv.fortification.entity.LotStateGeo;
import com.beehyv.fortification.enums.GeoManufacturerProductionResponseType;
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

    public Double getUsedQuantityProductionSum(List<Long> testManufacturerIds, DashboardRequestDto dto, List<Long> manufacturerIds) {
        return dao.getUsedQuantityProductionSum(testManufacturerIds, dto, manufacturerIds);
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
