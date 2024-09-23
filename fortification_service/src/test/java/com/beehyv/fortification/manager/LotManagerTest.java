package com.beehyv.fortification.manager;


import com.beehyv.fortification.dao.LotDao;
import com.beehyv.fortification.dto.requestDto.DashboardRequestDto;
import com.beehyv.fortification.dto.requestDto.SearchListRequest;
import com.beehyv.fortification.entity.Lot;
import com.beehyv.fortification.entity.LotStateGeo;
import com.beehyv.fortification.entity.RoleCategoryType;
import com.beehyv.fortification.entity.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class LotManagerTest {

    @Mock
    private LotDao lotDao;

    @Mock
    private CategoryManager categoryManager;

    private LotManager lotManager;

    private Long id;
    private Long manufacturerId;
    private List<Lot> lotList;

    @BeforeEach
    public void setUp() {
        lotManager = new LotManager(lotDao, categoryManager);
        id = 1L;
        manufacturerId = 1L;
        lotList = Arrays.asList(new Lot(), new Lot());

        when(lotDao.findByIdAndManufacturerId(id, manufacturerId)).thenReturn(lotList.get(0));
        when(lotDao.getCount(id)).thenReturn((long) lotList.size());
        when(lotDao.findAllByBatchId(id, 1, 10)).thenReturn(lotList);
    }

    @Test
    public void testFindByIdAndManufacturerId() {
        Lot result = lotManager.findByIdAndManufacturerId(id, manufacturerId);
        assertEquals(lotList.get(0), result);
    }

    @Test
    public void testGetCount() {
        Long result = lotManager.getCount(lotList.size(), id, 1, 10);
        assertEquals(lotList.size(), result);
    }

    @Test
    public void testFindAllByBatchId() {
        List<Lot> result = lotManager.findAllByBatchId(id, 1, 10);
        assertEquals(lotList, result);
    }

    @Test
    public void testFindAllByCategoryId() {
        SearchListRequest searchListRequest = new SearchListRequest();
        List<Lot> result = lotManager.findAllByCategoryId(manufacturerId, id, searchListRequest, 1, 10);
        assertNotNull(result);
    }

    @Test
    public void testGetCountForCategoryId() {
        SearchListRequest searchListRequest = new SearchListRequest();
        Long result = lotManager.getCount(lotList.size(), manufacturerId, id, 1, 10, searchListRequest);
        assertNotNull(result);
    }

    @Test
    public void testFindByUUID() {
        String uuid = "test-uuid";
        Lot lot = new Lot();
        lot.setId(id);
        lot.setUuid(uuid);
        when(lotDao.findByUUID(uuid)).thenReturn(lot);
        Lot result = lotManager.findByUUID(uuid);
        assertEquals(lot, result);
    }


    @Test
    public void testGetAllByIds() {
        List<Long> ids = Arrays.asList(1L, 2L);
        when(lotDao.findAllByIds(ids)).thenReturn(lotList);
        List<Lot> result = lotManager.getAllByIds(ids);
        assertEquals(lotList, result);
    }

    @Test
    public void testSave() {
        Lot lot = new Lot();
        lot.setId(id);
        when(lotDao.update(lot)).thenReturn(lot);
        Lot result = lotManager.save(lot);
        assertEquals(lot, result);
    }


    @Test
    public void testGetLotInventoryCount() {
        SearchListRequest searchListRequest = new SearchListRequest();
        when(lotDao.getLotInventoryCount(manufacturerId, id, searchListRequest, true)).thenReturn((long) lotList.size());
        Long result = lotManager.getLotInventoryCount(lotList.size(), manufacturerId, id, 1, 10, searchListRequest, true);
        assertEquals(lotList.size(), result);
    }

    @Test
    public void testGetAllLotsBySourceCategoryId() {
        when(lotDao.findAllBySourceCategoryId(manufacturerId, id, "", 1, 10)).thenReturn(lotList);
        List<Lot> result = lotManager.getAllLotsBySourceCategoryId(manufacturerId, id, "", 1, 10);
        assertEquals(lotList, result);
    }

    @Test
    public void testFindAllByCategoryIds() {
        SearchListRequest searchListRequest = new SearchListRequest();
        List<Long> testManufacturerIds = Arrays.asList(1L, 2L);
        when(lotDao.findAllByCategoryIds(searchListRequest, categoryManager.getUserCategoryIds(searchListRequest, RoleCategoryType.LAB), 1, 10, testManufacturerIds)).thenReturn(lotList);
        List<Lot> result = lotManager.findAllByCategoryIds(searchListRequest, 1, 10, testManufacturerIds);
        assertEquals(lotList, result);
    }


    @Test
    public void testFindAllLotsForInspection() {
        SearchListRequest searchListRequest = new SearchListRequest();
        List<Long> testManufacturerIds = Arrays.asList(1L, 2L);
        List<Long> lotIds = Arrays.asList(1L, 2L);
        when(lotDao.findAllLotsForInspection(id, searchListRequest, testManufacturerIds, lotIds, 1, 10)).thenReturn(lotList);
        List<Lot> result = lotManager.findAllLotsForInspection(id, searchListRequest, testManufacturerIds, lotIds, 1, 10);
        assertEquals(lotList, result);
    }

    @Test
    public void testGetCountForInspection() {
        SearchListRequest searchListRequest = new SearchListRequest();
        List<Long> testManufacturerIds = Arrays.asList(1L, 2L);
        when(lotDao.getCountForInspection(id, searchListRequest, testManufacturerIds)).thenReturn((long) lotList.size());
        Long result = lotManager.getCountForInspection(id, searchListRequest, testManufacturerIds);
        assertEquals(lotList.size(), result);
    }

    @Test
    public void testFindByLotNo() {
        String lotNo = "test-lotNo";
        when(lotDao.findByLotNo(lotNo)).thenReturn(lotList);
        List<Lot> result = lotManager.findByLotNo(lotNo);
        assertEquals(lotList, result);
    }

    @Test
    public void testGetCountWithSearchListRequestAndTestManufacturerIds() {
        SearchListRequest searchListRequest = new SearchListRequest();
        List<Long> testManufacturerIds = Arrays.asList(1L, 2L);
        when(lotDao.getCount(searchListRequest, categoryManager.getUserCategoryIds(searchListRequest, RoleCategoryType.MODULE), testManufacturerIds)).thenReturn((long) lotList.size());
        Long result = lotManager.getCount(lotList.size(), 1, 10, searchListRequest, testManufacturerIds);
        assertEquals(lotList.size(), result);
    }

    @Test
    public void testGetCountWithManufacturerIdCategoryIdAndSearch() {
        String search = "test";
        when(lotDao.getCount(manufacturerId, id, search)).thenReturn((long) lotList.size() / 2);
        when(lotDao.getTargetLotsCount(manufacturerId, id, search)).thenReturn((long) lotList.size() / 2);
        Long result = lotManager.getCount(lotList.size(), manufacturerId, id, search, 1, 10);
        assertEquals(lotList.size(), result);
    }

//    @Test
//    public void testGetLotInventory() {
//        SearchListRequest searchRequest = new SearchListRequest();
//        Boolean approvedSourceLots = true;
//        when(lotDao.getLotInventory(manufacturerId, id, searchRequest, 1, 10, approvedSourceLots)).thenReturn(lotList.subList(0, lotList.size()/2));
//        when(lotDao.getTargetLotInventory(manufacturerId, id, searchRequest, 1, 10, approvedSourceLots)).thenReturn(lotList.subList(lotList.size()/2, lotList.size()));
//        List<Lot> result = lotManager.getLotInventory(manufacturerId, id, searchRequest, 1, 10, approvedSourceLots);
//        assertEquals(lotList.size(), result.size());
//        for (int i = 0; i < lotList.size(); i++) {
//            assertEquals(lotList.get(i), result.get(i));
//        }
//    }

    @Test
    public void testGetQuantitySum() {
        Double[] expectedQuantities = new Double[]{100.0, 200.0};
        when(lotDao.getQuantitySum(manufacturerId, id)).thenReturn(expectedQuantities);
        Double[] result = lotManager.getQuantitySum(manufacturerId, id);
        assertArrayEquals(expectedQuantities, result);
    }

    @Test
    public void testFindAllAggregateByManufacturerIdAndCategoryId() {
        List<State> states = Arrays.asList(new State(), new State());
        List<LotStateGeo> expectedAggregates = Arrays.asList(new LotStateGeo(), new LotStateGeo());
        when(lotDao.findAllAggregateByManufacturerIdAndCategoryId(1, 10, states)).thenReturn(expectedAggregates);
        List<LotStateGeo> result = lotManager.findAllAggregateByManufacturerIdAndCategoryId(1, 10, states);
        assertEquals(expectedAggregates, result);
    }

    @Test
    public void testFindAllByIds() {
        List<Long> ids = Arrays.asList(1L, 2L);
        when(lotDao.findAllByIds(ids)).thenReturn(lotList);
        List<Lot> result = lotManager.findAllByIds(ids);
        assertEquals(lotList, result);
    }

    @Test
    public void testMigrateData() {
        List<Long[]> expectedData = Arrays.asList(new Long[]{1L, 2L}, new Long[]{3L, 4L});
        when(lotDao.migrateData()).thenReturn(expectedData);
        List<Long[]> result = lotManager.migrateData();
        assertEquals(expectedData, result);
    }

    @Test
    public void testGetWarehouseAggregateProductionQuantities() {
        List<Long> testManufacturerIds = Arrays.asList(1L, 2L);
        Double[] expectedQuantities = new Double[]{100.0, 200.0};
        when(lotDao.getWarehouseAggregateProductionQuantities(id, testManufacturerIds)).thenReturn(expectedQuantities);
        Double[] result = lotManager.getWarehouseAggregateProductionQuantities(id, testManufacturerIds);
        assertArrayEquals(expectedQuantities, result);
    }

    @Test
    public void testGetWarehouseAggregateTestingQuantities() {
        List<Long> testManufacturerIds = Arrays.asList(1L, 2L);
        Double[] expectedQuantities = new Double[]{100.0, 200.0};
        when(lotDao.getWarehouseAggregateTestingQuantities(id, testManufacturerIds)).thenReturn(expectedQuantities);
        Double[] result = lotManager.getWarehouseAggregateTestingQuantities(id, testManufacturerIds);
        assertArrayEquals(expectedQuantities, result);
    }

    @Test
    public void testGetWarehouseAggregateProductionManufacturerQuantities() {
        List<Long> testManufacturerIds = Arrays.asList(1L, 2L);
        List<Object[]> expectedQuantities = Arrays.asList(new Object[]{1L, 100.0}, new Object[]{2L, 200.0});
        when(lotDao.getWarehouseAggregateProductionManufacturerQuantities(id, testManufacturerIds)).thenReturn(expectedQuantities);
        List<Object[]> result = lotManager.getWarehouseAggregateProductionManufacturerQuantities(id, testManufacturerIds);
        assertEquals(expectedQuantities, result);
    }

    @Test
    public void testLotManagerLastMethods() {
        String sourceLotNo = "test-sourceLotNo";
        DashboardRequestDto dto = new DashboardRequestDto();
        List<Long> manufacturerIds = Arrays.asList(1L, 2L);
        List<String> districtGeoIds = Arrays.asList("districtGeoId1", "districtGeoId2");
        List<String> lotStates = Arrays.asList("lotState1", "lotState2");
        List<String> lotRejectedStates = Arrays.asList("lotRejectedState1", "lotRejectedState2");
        List<String> lotApprovalPendingStates = Arrays.asList("lotApprovalPendingState1", "lotApprovalPendingState2");
        String districtGeoId = "test-districtGeoId";
        String stateGeoId = "test-stateGeoId";
        String groupBy = "test-groupBy";
        Long categoryId = 1L;

        when(lotDao.findTargetLotsBySourceLotNo(sourceLotNo, manufacturerId)).thenReturn(lotList);
        when(lotDao.findAllTargetManufacturerIdsBySource(manufacturerIds)).thenReturn(manufacturerIds);
        when(lotDao.findAllSourceManufacturerIdsByDistrictGeoId(categoryId, districtGeoIds, dto, manufacturerIds)).thenReturn(manufacturerIds);
        when(lotDao.findAllTargetManufacturerAggregates(categoryId, districtGeoId, dto, manufacturerId, lotStates, manufacturerIds, lotRejectedStates)).thenReturn(new ArrayList<>());
        when(lotDao.findAllLotsBySourceAndTargetManufacturer(categoryId, dto, manufacturerId, manufacturerId, manufacturerIds)).thenReturn(lotList);
        when(lotDao.getSourceWarehouseAggregate(categoryId, districtGeoId, dto, lotStates, manufacturerIds, lotRejectedStates)).thenReturn(new ArrayList<>());
        when(lotDao.getAggregateByDistrictsGeoId(categoryId, manufacturerId, dto, lotStates, manufacturerIds, lotRejectedStates)).thenReturn(new ArrayList<>());
        when(lotDao.findAllTargetManufacturerAggregates(categoryId, dto, manufacturerId, manufacturerIds, lotApprovalPendingStates, manufacturerIds, lotRejectedStates)).thenReturn(new ArrayList<>());
        when(lotDao.getWarehouseAggregateForDashboardFRK(districtGeoId, stateGeoId, manufacturerIds, dto, categoryId)).thenReturn(new Double[]{100.0, 200.0});
        when(lotDao.getWarehouseAggregateForDashboard(districtGeoId, stateGeoId, manufacturerIds, dto, categoryId)).thenReturn(new Double[]{100.0, 200.0});
        when(lotDao.getWarehouseQuantitiesForFRK(groupBy, manufacturerIds, dto)).thenReturn(new ArrayList<>());
        when(lotDao.getWarehouseQuantitiesForMiller(groupBy, manufacturerIds, dto)).thenReturn(new ArrayList<>());
        when(lotDao.getAllEmpanelledManufacturersAggregate(categoryId, manufacturerIds, dto, manufacturerIds, "", "")).thenReturn(new Double[]{100.0, 200.0});
//        when(lotDao.getAllEmpanelledManufacturersQuantity(categoryId, manufacturerIds, manufacturerIds, dto, manufacturerIds)).thenReturn(new Double[]{100.0, 200.0});
        when(lotDao.getAllEmpanelledManufacturersAggregateForWarehouse(dto, manufacturerIds)).thenReturn(new Double[]{100.0, 200.0});
        when(lotDao.getAllEmpanelledManufacturersQuantityForWarehouse(dto, manufacturerIds)).thenReturn(new ArrayList<>());
        when(lotDao.findAllSourceManufacturerAggregatesByTarget(categoryId, dto, manufacturerId, lotApprovalPendingStates, manufacturerIds, lotRejectedStates, manufacturerIds)).thenReturn(new ArrayList<>());
        when(lotDao.getAllWarehouseAggregateForMiller(categoryId, dto, manufacturerIds)).thenReturn(new ArrayList<>());
        when(lotDao.getAllWarehouseAggregateForFRK(categoryId, dto, manufacturerIds)).thenReturn(new ArrayList<>());

        assertEquals(lotList, lotManager.findTargetLotsBySourceLotNo(sourceLotNo, manufacturerId));
        assertEquals(manufacturerIds, lotManager.findAllTargetManufacturerIdsBySource(manufacturerIds));
        assertEquals(manufacturerIds, lotManager.findAllSourceManufacturerIdsByDistrictGeoId(categoryId, districtGeoIds, dto, manufacturerIds));
        assertNotNull(lotManager.findAllTargetManufacturerAggregates(categoryId, districtGeoId, dto, manufacturerId, lotStates, manufacturerIds, lotRejectedStates));
        assertEquals(lotList, lotManager.findAllLotsBySourceAndTargetManufacturer(categoryId, dto, manufacturerId, manufacturerId, manufacturerIds));
        assertNotNull(lotManager.getSourceWarehouseAggregate(categoryId, districtGeoId, dto, lotStates, manufacturerIds, lotRejectedStates));
        assertNotNull(lotManager.getAggregateByDistrictsGeoId(categoryId, manufacturerId, dto, lotStates, manufacturerIds, lotRejectedStates));
        assertNotNull(lotManager.findAllTargetManufacturerAggregates(categoryId, dto, manufacturerId, manufacturerIds, lotApprovalPendingStates, manufacturerIds, lotRejectedStates));
        assertArrayEquals(new Double[]{100.0, 200.0}, lotManager.getWarehouseAggregateForDashboardFRK(districtGeoId, stateGeoId, manufacturerIds, dto, categoryId));
        assertArrayEquals(new Double[]{100.0, 200.0}, lotManager.getWarehouseAggregateForDashboard(districtGeoId, stateGeoId, manufacturerIds, dto, categoryId));
        assertNotNull(lotManager.getWarehouseQuantitiesForFRK(groupBy, manufacturerIds, dto));
        assertNotNull(lotManager.getWarehouseQuantitiesForMiller(groupBy, manufacturerIds, dto));
        assertArrayEquals(new Double[]{100.0, 200.0}, lotManager.getAllEmpanelledManufacturersAggregate(categoryId, manufacturerIds, dto, manufacturerIds, "", ""));
//        assertArrayEquals(new Double[]{100.0, 200.0}, lotManager.getAllEmpanelledManufacturersQuantity(categoryId, manufacturerIds, manufacturerIds, dto, manufacturerIds));
        assertArrayEquals(new Double[]{100.0, 200.0}, lotManager.getAllEmpanelledManufacturersAggregateForWarehouse(dto, manufacturerIds));
        assertNotNull(lotManager.getAllEmpanelledManufacturersQuantityForWarehouse(dto, manufacturerIds));
        assertNotNull(lotManager.findAllSourceManufacturerAggregatesByTarget(categoryId, dto, manufacturerId, lotApprovalPendingStates, manufacturerIds, lotRejectedStates, manufacturerIds));
        assertNotNull(lotManager.getAllWarehouseAggregateForMiller(categoryId, dto, manufacturerIds));
        assertNotNull(lotManager.getAllWarehouseAggregateForFRK(categoryId, dto, manufacturerIds));
    }

}

