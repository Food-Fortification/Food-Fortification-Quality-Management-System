package org.path.fortification.manager;

import org.path.fortification.dao.BatchStateGeoDao;
import org.path.fortification.dto.requestDto.DashboardRequestDto;
import org.path.fortification.dto.responseDto.ProductionResponseDto;
import org.path.fortification.dto.responseDto.TestingResponseDto;
import org.path.fortification.entity.BatchStateGeo;
import org.path.fortification.entity.GeoStateId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BatchStateGeoManagerTest {

    @InjectMocks
    private BatchStateGeoManager batchStateGeoManager;

    @Mock
    private BatchStateGeoDao batchStateGeoDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByGeoStateId() {
        GeoStateId geoStateId = new GeoStateId();
        BatchStateGeo batchStateGeo = new BatchStateGeo();
        when(batchStateGeoDao.findByCategoryIdAndManufacturerIdAndYear(geoStateId)).thenReturn(batchStateGeo);

        BatchStateGeo result = batchStateGeoManager.findByGeoStateId(geoStateId);

        assertEquals(batchStateGeo, result);
    }

    // For example:
    @Test
    public void testFindByCategoryIdsAndManufacturerId() {
        // Setup
        List<Long> categoryIds = Collections.singletonList(1L);
        Long manufacturerId = 1L;
        Integer year = 2020;
        DashboardRequestDto dto = new DashboardRequestDto();
        List<Object[]> expected = Collections.singletonList(new Object[]{});
        when(batchStateGeoDao.findByCategoryIdsAndManufacturerId(categoryIds, manufacturerId, year, dto)).thenReturn(expected);

        // Execution
        List<Object[]> result = batchStateGeoManager.findByCategoryIdsAndManufacturerId(categoryIds, manufacturerId, year, dto);

        // Verification
        assertEquals(expected, result);
    }

    @Test
    public void testFindManufacturerQuantityByCategoryIdAndState() {
        // Setup
        Long categoryId = 1L;
        String filterBy = "filter";
        String geoId = "geoId";
        Set<Long> mids = new HashSet<>(Arrays.asList(1L, 2L));
        Integer year = 2020;
        Integer pageNumber = 1;
        Integer pageSize = 10;
        DashboardRequestDto dto = new DashboardRequestDto();
        List<BatchStateGeo> expected = Collections.singletonList(new BatchStateGeo());
        when(batchStateGeoDao.findManufacturerQuantityByCategoryIdAndState(categoryId, filterBy, geoId, "o", mids, year, pageNumber, pageSize, dto)).thenReturn(expected);

        // Execution
        List<BatchStateGeo> result = batchStateGeoManager.findManufacturerQuantityByCategoryIdAndState(categoryId, filterBy, geoId, "o", mids, year, pageNumber, pageSize, dto);

        // Verification
        assertEquals(expected, result);
    }

    @Test
    public void testGetGeoAggregatedProductionSum() {
        // Setup
        Long categoryId = 1L;
        String filterBy = "filter";
        String geoId = "geoId";
        List<Long> testManufacturerIds = Arrays.asList(1L, 2L);
        Integer year = 2020;
        DashboardRequestDto dto = new DashboardRequestDto();
        Double[] expected = new Double[]{1.0, 2.0};
        when(batchStateGeoDao.getGeoAggregatedProductionSum(categoryId, filterBy, geoId, testManufacturerIds, year, dto)).thenReturn(expected);

        // Execution
        Double[] result = batchStateGeoManager.getGeoAggregatedProductionSum(categoryId, filterBy, geoId, testManufacturerIds, year, dto);

        // Verification
        assertArrayEquals(expected, result);
    }

    @Test
    public void testGetGeoAggregatedProductionQuantity() {
        // Setup
        Long categoryId = 1L;
        String groupBy = "groupBy";
        String filterBy = "filter";
        String geoId = "geoId";
        List<Long> testManufacturerIds = Arrays.asList(1L, 2L);
        Integer year = 2020;
        DashboardRequestDto dto = new DashboardRequestDto();
        List<ProductionResponseDto> expected = Collections.singletonList(new ProductionResponseDto());
        when(batchStateGeoDao.getGeoAggregatedProductionQuantity(categoryId, groupBy, filterBy, geoId, testManufacturerIds, year, dto)).thenReturn(expected);

        // Execution
        List<ProductionResponseDto> result = batchStateGeoManager.getGeoAggregatedProductionQuantity(categoryId, groupBy, filterBy, geoId, testManufacturerIds, year, dto);

        // Verification
        assertEquals(expected, result);
    }

    @Test
    public void testGetGeoAggregatedTestingSum() {
        // Setup
        Long categoryId = 1L;
        String filterBy = "filter";
        String geoId = "geoId";
        List<Long> testManufacturerIds = Arrays.asList(1L, 2L);
        Integer year = 2020;
        DashboardRequestDto dto = new DashboardRequestDto();
        Double[] expected = new Double[]{1.0, 2.0};
        when(batchStateGeoDao.getGeoAggregatedTestingSum(categoryId, filterBy, geoId, testManufacturerIds, year, dto)).thenReturn(expected);

        // Execution
        Double[] result = batchStateGeoManager.getGeoAggregatedTestingSum(categoryId, filterBy, geoId, testManufacturerIds, year, dto);

        // Verification
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFindManufacturerQuantityByCategoryIdAndStateCount() {
        // Setup
        int size = 10;
        Long categoryId = 1L;
        String filterBy = "filter";
        String geoId = "geoId";
        Integer year = 2020;
        Integer pageNumber = 1;
        Integer pageSize = 10;
        DashboardRequestDto dto = new DashboardRequestDto();
        Long expected = 10L;
        when(batchStateGeoDao.findManufacturerQuantityByCategoryIdAndStateCount(categoryId, filterBy, geoId, year, dto)).thenReturn(expected);

        // Execution
        Long result = batchStateGeoManager.findManufacturerQuantityByCategoryIdAndStateCount(size, categoryId, filterBy, geoId, year, pageNumber, pageSize, dto);

        // Verification
        assertEquals(expected, result);
    }

    @Test
    public void testFindTotalQuantityForCategoriesByGeoId() {
        // Setup
        String geoIdType = "state";
        String geoId = "geoId";
        Integer year = 2020;
        List<Long> testManufacturerIds = Arrays.asList(1L, 2L);
        DashboardRequestDto dto = new DashboardRequestDto();
        List<Double[]> expected = Collections.singletonList(new Double[]{1.0, 2.0});
        when(batchStateGeoDao.getGeoAggregatedProductionSum(geoIdType, geoId, year, testManufacturerIds, dto)).thenReturn(expected);

        // Execution
        List<Double[]> result = batchStateGeoManager.findTotalQuantityForCategoriesByGeoId(geoIdType, geoId, year, testManufacturerIds, dto);

        // Verification
        assertEquals(expected, result);
    }

    @Test
    public void testGetAllAgenciesAggregate() {
        // Setup
        DashboardRequestDto dto = new DashboardRequestDto();
        List<Long> testManufacturerIds = Arrays.asList(1L, 2L);
        List<Long> sourceManufacturerIds = Arrays.asList(3L, 4L);
        List<Object[]> expected = Collections.singletonList(new Object[]{});
        when(batchStateGeoDao.getAllAgenciesAggregate(dto, testManufacturerIds, sourceManufacturerIds)).thenReturn(expected);

        // Execution
        List<Object[]> result = batchStateGeoManager.getAllAgenciesAggregate(dto, testManufacturerIds, sourceManufacturerIds);

        // Verification
        assertEquals(expected, result);
    }

    @Test
    public void testGetGeoAggregatedTestingQuantity() {
        // Setup
        Long categoryId = 1L;
        String groupBy = "groupBy";
        String filterBy = "filter";
        String geoId = "geoId";
        List<Long> testManufacturerIds = Arrays.asList(1L, 2L);
        Integer year = 2020;
        DashboardRequestDto dto = new DashboardRequestDto();
        List<TestingResponseDto> expected = Collections.singletonList(new TestingResponseDto());
        when(batchStateGeoDao.getGeoAggregatedTestingQuantity(categoryId, groupBy, filterBy, geoId, testManufacturerIds, year, dto)).thenReturn(expected);

        // Execution
        List<TestingResponseDto> result = batchStateGeoManager.getGeoAggregatedTestingQuantity(categoryId, groupBy, filterBy, geoId, testManufacturerIds, year, dto);

        // Verification
        assertEquals(expected, result);
    }

// Continue this pattern for all other methods in the BatchStateGeoManager class
// Note: You will need to create mock data for each method's parameters and expected return values.

// Continue this pattern for all other methods in the BatchStateGeoManager class
// Note: You will need to create mock data for each method's parameters and expected return values.

// Continue this pattern for all other methods in the BatchStateGeoManager class
// Note: You will need to create mock data for each method's parameters and expected return values.
    // Add more test methods for other methods in the BatchStateGeoManager class
}