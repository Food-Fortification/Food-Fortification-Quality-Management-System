package com.beehyv.fortification.dao;

import com.beehyv.fortification.dto.requestDto.DashboardRequestDto;
import com.beehyv.fortification.entity.BatchStateGeo;
import com.beehyv.fortification.entity.GeoStateId;
import com.beehyv.fortification.enums.GeoManufacturerProductionResponseType;
import com.beehyv.fortification.enums.GeoManufacturerTestingResponseType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class BatchStateGeoDaoTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<BatchStateGeo> typedQuery;
    @Mock
    private TypedQuery<Object[]> typedQueryObject;
    @Mock
    private TypedQuery<Long> typedQueryLong;

    @InjectMocks
    private BatchStateGeoDao batchStateGeoDao;

    @BeforeEach
    void setUp() {
        when(entityManager.createQuery(anyString(), eq(BatchStateGeo.class)))
                .thenReturn(typedQuery);
        when(entityManager.createQuery(anyString(), eq(Object[].class)))
                .thenReturn(typedQueryObject);
    }

    @Test
    void testFindByCategoryIdAndManufacturerIdAndYear() {
        // Arrange
        GeoStateId geoStateId = new GeoStateId();
        BatchStateGeo expectedBatchStateGeo = new BatchStateGeo();
        expectedBatchStateGeo.setGeoStateId(geoStateId);

        when(typedQuery.setParameter(anyString(), any()))
                .thenReturn(typedQuery);
        when(typedQuery.getSingleResult())
                .thenReturn(expectedBatchStateGeo);

        // Act
        BatchStateGeo actualBatchStateGeo = batchStateGeoDao.findByCategoryIdAndManufacturerIdAndYear(geoStateId);

        // Assert
        assertEquals(expectedBatchStateGeo, actualBatchStateGeo);
    }

    @Test
    void testFindByCategoryIdsAndManufacturerId() {
        // Arrange
        List<Long> categoryIds = Arrays.asList(1L, 2L);
        Long manufacturerId = 1L;
        Integer year = 2021;
        DashboardRequestDto dto = new DashboardRequestDto();
        List<Object[]> expectedResults = new ArrayList<>();

        when(typedQueryObject.setParameter(anyString(), any()))
                .thenReturn(typedQueryObject);
        when(typedQueryObject.getResultList())
                .thenReturn(expectedResults);

        // Act
        List<Object[]> actualResults = batchStateGeoDao.findByCategoryIdsAndManufacturerId(categoryIds, manufacturerId, year, dto);

        // Assert
        assertEquals(expectedResults, actualResults);
    }


    @Test
    void testFindManufacturerQuantityByCategoryIdAndState() {
        // Arrange
        Long categoryId = 1L;
        String filterBy = "filter";
        String geoId = "geoId";
        String orderBy = "order";
        Set<Long> mids = new HashSet<>();
        Integer year = 2021;
        Integer pageNumber = 1;
        Integer pageSize = 10;
        DashboardRequestDto dto = new DashboardRequestDto();
        List<BatchStateGeo> expectedBatchStateGeos = new ArrayList<>();

        when(entityManager.createQuery(anyString(), eq(BatchStateGeo.class)))
                .thenReturn(typedQuery);
        when(typedQuery.setParameter(anyString(), any()))
                .thenReturn(typedQuery);
        when(typedQuery.getResultList())
                .thenReturn(expectedBatchStateGeos);

        // Act
        List<BatchStateGeo> actualBatchStateGeos = batchStateGeoDao.findManufacturerQuantityByCategoryIdAndState(categoryId, filterBy, geoId, orderBy, mids, year, pageNumber, pageSize, dto);

        // Assert
        assertEquals(expectedBatchStateGeos, actualBatchStateGeos);
    }


    @Test
    void testGetAllAgenciesAggregate() {
        // Arrange
        DashboardRequestDto dto = new DashboardRequestDto();
        List<Long> testManufacturerIds = Arrays.asList(1L, 2L);
        List<Long> sourceManufacturerIds = Arrays.asList(3L, 4L);
        Object[][] expectedResponse = new Object[][]{{1L, "districtGeoId", 100.0, 200.0, 300.0, 400.0, 500.0, 600.0, 700.0}};
        List<Object[]> expectedList = Arrays.asList(expectedResponse);

        when(entityManager.createQuery(any(String.class), any(Class.class))).thenReturn(typedQueryObject);
        when(typedQueryObject.setParameter(any(String.class), any())).thenReturn(typedQueryObject);
        when(typedQueryObject.getResultList()).thenReturn(expectedList);

        // Act
        List<Object[]> actualList = batchStateGeoDao.getAllAgenciesAggregate(dto, testManufacturerIds, sourceManufacturerIds);

        // Assert
        assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertArrayEquals(expectedList.get(i), actualList.get(i));
        }
    }

    @Test
    void testGetAllSourceManufacturersAggregate() {
        // Arrange
        DashboardRequestDto dto = new DashboardRequestDto();
        List<Long> testManufacturerIds = Arrays.asList(1L, 2L);
        List<Long> sourceManufacturerIds = Arrays.asList(3L, 4L);
        Object[][] expectedObject = new Object[][]{{1L, "districtGeoId", 100.0, 200.0, 300.0, 400.0, 500.0, 600.0, 700.0, 800.0}};
        List<Object[]> expectedList = Arrays.asList(expectedObject);

        when(entityManager.createQuery(any(String.class), any(Class.class))).thenReturn(typedQueryObject);
        when(typedQueryObject.setParameter(any(String.class), any())).thenReturn(typedQueryObject);
        when(typedQueryObject.getResultList()).thenReturn(expectedList);

        // Act
        List<Object[]> actualList = batchStateGeoDao.getAllSourceManufacturersAggregate(dto.getCategoryId(), dto, testManufacturerIds, sourceManufacturerIds);

        // Assert
        assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertArrayEquals(expectedList.get(i), actualList.get(i));
        }
    }


    @Test
    void testGetGeoAggregatedTestingQuantity() {
        // Arrange
        DashboardRequestDto dto = new DashboardRequestDto();
        dto.setCategoryId(1L);
        dto.setFromDate(new Date());
        dto.setToDate(new Date());
        dto.setGeoId("geoId");
        List<Long> testManufacturerIds = Arrays.asList(1L, 2L);
        String filterBy = "filterBy";
        GeoManufacturerTestingResponseType responseType = GeoManufacturerTestingResponseType.approvedQuantity;

        Object[][] expectedResponse = new Object[][]{{1L, "districtGeoId", "stateGeoId", 100.0}};
        List<Object[]> expectedList = Arrays.asList(expectedResponse);

        when(typedQueryObject.setParameter(anyString(), any())).thenReturn(typedQueryObject);
        when(typedQueryObject.getResultList()).thenReturn(expectedList);

        // Act
        List<Object[]> actualList = batchStateGeoDao.getGeoAggregatedTestingQuantity(filterBy, responseType, testManufacturerIds, dto);

        // Assert
        assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertArrayEquals(expectedList.get(i), actualList.get(i));
        }
    }

    @Test
    void testGetGeoAggregatedProductionQuantity() {
        // Arrange
        Long categoryId = 1L;
        String groupBy = "groupBy";
        String filterBy = "filterBy";
        String geoId = "geoId";
        List<Long> testManufacturerIds = Arrays.asList(1L, 2L);
        DashboardRequestDto dto = new DashboardRequestDto();
        dto.setFromDate(new Date());
        dto.setToDate(new Date());
        GeoManufacturerProductionResponseType responseType = GeoManufacturerProductionResponseType.approvedQuantity;

        Object[][] expectedResponse = new Object[][]{{1L, 2L, 3L}};
        List<Object[]> expectedList = Arrays.asList(expectedResponse);

        when(typedQueryObject.setParameter(anyString(), any())).thenReturn(typedQueryObject);
        when(typedQueryObject.getResultList()).thenReturn(expectedList);

        // Act
        List<Object[]> actualList = batchStateGeoDao.getGeoAggregatedProductionQuantity(filterBy, responseType, testManufacturerIds, dto);

        // Assert
        assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertArrayEquals(expectedList.get(i), actualList.get(i));
        }
    }

    @Test
    void testGetGeoAggregatedProductionSum() {
        // Arrange
        Long categoryId = 1L;
        String filterBy = "filter";
        List<Long> testManufacturerIds = Arrays.asList(1L, 2L);
        DashboardRequestDto dto = new DashboardRequestDto();
        dto.setFromDate(new Date());
        dto.setToDate(new Date());
        Double[] expectedArray = {1.0, 2.0};

        when(typedQueryObject.setParameter(anyString(), any())).thenReturn(typedQueryObject);
        when(typedQueryObject.getSingleResult()).thenReturn(expectedArray);

        // Act
        Double[] actualArray = batchStateGeoDao.getGeoAggregatedProductionSum(categoryId, filterBy, null, testManufacturerIds, null, dto);

        // Assert
        assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    void testFindManufacturerQuantityByCategoryIdAndStateCount() {
        // Arrange
        Long categoryId = 1L;
        String filterBy = "filter";
        String geoId = "geoId";
        Integer year = 2022;
        DashboardRequestDto dto = new DashboardRequestDto();
        dto.setFromDate(new Date());
        dto.setToDate(new Date());

        Long expectedCount = 10L;

        when(entityManager.createQuery(anyString(), eq(Long.class)))
                .thenReturn(typedQueryLong);
        when(typedQueryLong.setParameter(anyString(), any())).thenReturn(typedQueryLong);
        when(typedQueryLong.getSingleResult()).thenReturn(expectedCount);
        when(typedQueryLong.setParameter(anyString(), any())).thenReturn(typedQueryLong);
        when(typedQueryLong.getSingleResult()).thenReturn(expectedCount);

        // Act
        Long actualCount = batchStateGeoDao.findManufacturerQuantityByCategoryIdAndStateCount(categoryId, filterBy, geoId, year, dto);

        // Assert
        assertEquals(expectedCount, actualCount);
    }
}












