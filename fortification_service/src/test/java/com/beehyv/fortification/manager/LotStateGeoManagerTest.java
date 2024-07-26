package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.LotStateGeoDao;
import com.beehyv.fortification.dto.requestDto.DashboardRequestDto;
import com.beehyv.fortification.entity.GeoStateId;
import com.beehyv.fortification.entity.LotStateGeo;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class LotStateGeoManagerTest {

    @Mock
    private LotStateGeoDao lotStateGeoDao;

    private LotStateGeoManager lotStateGeoManager;

    private GeoStateId geoStateId;
    private Long categoryId;
    private List<String> districtGeoIds;
    private DashboardRequestDto dto;
    private List<Long> testManufacturerIds;
    private LotStateGeo lotStateGeo;

    @BeforeEach
    public void setUp() {
        lotStateGeoManager = new LotStateGeoManager(lotStateGeoDao);
        geoStateId = new GeoStateId(); // replace with actual GeoStateId
        categoryId = 1L;
        districtGeoIds = Arrays.asList("districtGeoId1", "districtGeoId2");
        dto = new DashboardRequestDto(); // replace with actual DashboardRequestDto
        testManufacturerIds = Arrays.asList(1L, 2L);
        lotStateGeo = new LotStateGeo();

        when(lotStateGeoDao.findByCategoryIdAndManufacturerId(geoStateId)).thenReturn(lotStateGeo);
        ArrayList<Object[]> emptyList = new ArrayList<>();
        when(lotStateGeoDao.getAggregateByDistrictsGeoId(categoryId, districtGeoIds, dto, testManufacturerIds)).thenReturn(emptyList);
        when(lotStateGeoDao.getAllWarehouseAggregateForMiller(categoryId, dto, testManufacturerIds)).thenReturn(emptyList);
        when(lotStateGeoDao.getAllWarehouseAggregateForFRK(categoryId, dto, testManufacturerIds)).thenReturn(emptyList);
    }

    @Test
    public void testFindByCategoryIdAndManufacturerId() {
        LotStateGeo result = lotStateGeoManager.findByCategoryIdAndManufacturerId(geoStateId);
        assertEquals(lotStateGeo, result);
    }

    @Test
    public void testGetAggregateByDistrictsGeoId() {
        List<Object[]> result = lotStateGeoManager.getAggregateByDistrictsGeoId(categoryId, districtGeoIds, dto, testManufacturerIds);
        assertEquals(List.of(), result);
    }

    @Test
    public void testGetAllWarehouseAggregateForMiller() {
        List<Object[]> result = lotStateGeoManager.getAllWarehouseAggregateForMiller(categoryId, dto, testManufacturerIds);
        assertEquals(List.of(), result);
    }

    @Test
    public void testGetAllWarehouseAggregateForFRK() {
        List<Object[]> result = lotStateGeoManager.getAllWarehouseAggregateForFRK(categoryId, dto, testManufacturerIds);
        assertEquals(List.of(), result);
    }
}