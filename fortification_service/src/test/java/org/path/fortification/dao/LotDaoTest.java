package org.path.fortification.dao;

import org.path.fortification.dto.requestDto.DashboardRequestDto;
import org.path.fortification.dto.requestDto.SearchListRequest;
import org.path.fortification.dto.responseDto.DashboardWarehouseResponseDto;
import org.path.fortification.entity.Batch;
import org.path.fortification.entity.Lot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LotDaoTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Lot> typedQuery;
    @Mock
    private TypedQuery<Object[]> typedQueryO;
    @Mock
    private TypedQuery<Long> typedQueryL;
    @Mock
    private TypedQuery<String> typedQueryS;
    @Mock
    private TypedQuery<Batch> typedQueryB;

    @InjectMocks
    private LotDao lotDao;


    @BeforeEach
    void setUp() {
        when(entityManager.createQuery(anyString(), eq(Lot.class)))
                .thenReturn(typedQuery);
        when(entityManager.createQuery(anyString(), eq(Object[].class)))
                .thenReturn(typedQueryO);
        when(entityManager.createQuery(anyString(), eq(Long.class)))
                .thenReturn(typedQueryL);
        when(entityManager.createQuery(anyString(), eq(String.class)))
                .thenReturn(typedQueryS);
        when(entityManager.createQuery(anyString()))
                .thenReturn(typedQueryS);
        when(typedQuery.setParameter(anyString(), any()))
                .thenReturn(typedQuery);
        when(typedQueryO.setParameter(anyString(), any()))
                .thenReturn(typedQueryO);
        when(typedQueryL.setParameter(anyString(), any()))
                .thenReturn(typedQueryL);
        when(typedQueryS.setParameter(anyString(), any()))
                .thenReturn(typedQueryS);

    }

    @Test
    void testFindByIdAndManufacturerId() {
        // Arrange
        Long id = 1L;
        Long manufacturerId = 1L;
        Lot expectedLot = new Lot();
        expectedLot.setManufacturerId(1L);

        when(typedQuery.getSingleResult())
                .thenReturn(expectedLot);

        // Act
        Lot actualLot = lotDao.findByIdAndManufacturerId(id, manufacturerId);

        // Assert
        assertEquals(expectedLot, actualLot);
    }

    @Test
    void testGetAllWarehouseAggregateForFRK() {
        // Arrange
        Long categoryId = 1L;
        DashboardRequestDto dto = new DashboardRequestDto();
        dto.setSourceDistrictGeoId("sourceDistrictGeoId");
        dto.setSourceStateGeoId("sourceStateGeoId");
        dto.setFromDate(new Date());
        dto.setToDate(new Date());
        List<Long> testManufacturerIds = Collections.emptyList();
        List<Object[]> expectedResults = Collections.emptyList();


        when(typedQueryO.getResultList())
                .thenReturn(expectedResults);

        // Act
        List<Object[]> actualResults = lotDao.getAllWarehouseAggregateForFRK(categoryId, dto, testManufacturerIds);

        // Assert
        assertEquals(expectedResults, actualResults);
    }

    @Test
    public void testFindByLotNo() {
        // Arrange
        String lotNo = "testLotNo";
        Lot expectedLot = new Lot();
        expectedLot.setLotNo(lotNo);

        when(typedQuery.getResultList()).thenReturn(Collections.singletonList(expectedLot));

        // Act
        List<Lot> result = lotDao.findByLotNo(lotNo);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedLot, result.get(0));
    }

    @Test
    public void testGetWarehouseAggregateTestingManufacturerQuantities() {
        // Arrange
        Long categoryId = 1L;
        List<Long> testManufacturerIds = Arrays.asList(1L, 2L, 3L);
        List<Object[]> expected = new ArrayList<>();


        when(typedQueryO.getResultList()).thenReturn(expected);

        // Act
        List<Object[]> result = lotDao.getWarehouseAggregateTestingManufacturerQuantities(categoryId, testManufacturerIds);

        // Assert
        assertEquals(expected, result);

    }

    @Test
    public void test_getWarehouseAggregateTestingManufacturerQuantities() {
        List<Object[]> expectedResult = new ArrayList<>();
        when(typedQueryO.getResultList()).thenReturn(expectedResult);
        List<Object[]> actualResult = lotDao.getWarehouseAggregateTestingManufacturerQuantities(1L, Arrays.asList(1L, 2L));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void test_findByLotNo() {
        List<Lot> expectedResult = new ArrayList<>();
        when(typedQuery.getResultList()).thenReturn(expectedResult);
        List<Lot> actualResult = lotDao.findByLotNo("a");
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void test_findTargetLotsBySourceLotNo() {
        List<Lot> expectedResult = new ArrayList<>();
        when(typedQuery.getResultList()).thenReturn(expectedResult);
        List<Lot> actualResult = lotDao.findTargetLotsBySourceLotNo("a", 1L);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void test_findAllTargetManufacturerIdsBySource() {
        List<Long> expectedResult = new ArrayList<>();
        when(typedQueryL.getResultList()).thenReturn(expectedResult);
        List<Long> actualResult = lotDao.findAllTargetManufacturerIdsBySource(Arrays.asList(1L, 2L));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void test_findAllSourceManufacturerIdsByDistrictGeoId() {
        List<Long> expectedResult = new ArrayList<>();
        when(typedQueryL.getResultList()).thenReturn(expectedResult);
        List<Long> actualResult = lotDao.findAllSourceManufacturerIdsByDistrictGeoId(1L,
                List.of("a"),
                new DashboardRequestDto(),
                Arrays.asList(1L, 2L));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void test_findAllTargetManufacturerAggregates() {
        List<Object[]> expectedResult = new ArrayList<>();
        when(typedQueryO.getResultList()).thenReturn(expectedResult);
        List<Object[]> actualResult = lotDao.findAllTargetManufacturerAggregates(1L, new DashboardRequestDto(), 1L, List.of(1L), List.of("a"), List.of(1L), List.of("a"));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void test_getAggregateByDistrictsGeoId() {
        List<Object[]> expectedResult = new ArrayList<>();
        when(typedQueryO.getResultList()).thenReturn(expectedResult);
        List<Object[]> actualResult = lotDao.getAggregateByDistrictsGeoId(1L,
                1L,
                new DashboardRequestDto(),
                List.of("a"),
                List.of(1L),
                List.of("a"));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void test_findAllLotsBySourceAndTargetManufacturer() {
        List<Lot> expectedResult = new ArrayList<>();
        when(typedQuery.getResultList()).thenReturn(expectedResult);
        List<Lot> actualResult = lotDao.findAllLotsBySourceAndTargetManufacturer(1L,
                new DashboardRequestDto(), 1L,
                1L,
                List.of(1L));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void test_getSourceWarehouseAggregate() {
        List<Object[]> expectedResult = new ArrayList<>();
        when(typedQueryO.getResultList()).thenReturn(expectedResult);
        List<Object[]> actualResult = lotDao.getSourceWarehouseAggregate(1L,
                "a",
                new DashboardRequestDto(), List.of("a"),
                List.of(1L),
                List.of("a"));
        assertEquals(expectedResult, actualResult);
    }


    @Test
    public void test_getWarehouseQuantitiesForFRK() {
        List<DashboardWarehouseResponseDto> expectedResult = new ArrayList<>();
        List<Object[]> expected = new ArrayList<>();

        when(typedQueryO.getResultList()).thenReturn(expected);
        List<DashboardWarehouseResponseDto> actualResult = lotDao.getWarehouseQuantitiesForFRK("A",
                List.of(1L),
                new DashboardRequestDto());
        assertEquals(expectedResult, actualResult);
    }


    @Test
    public void test_findAllSourceManufacturerAggregatesByTarget() {
        List<Object[]> expectedResult = new ArrayList<>();
        when(typedQueryO.getResultList()).thenReturn(expectedResult);
        List<Object[]> actualResult = lotDao.findAllSourceManufacturerAggregatesByTarget(1L,
                new DashboardRequestDto(), 1L,
                List.of("a"),
                List.of(1L),
                List.of("a"),
                List.of(1L));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void test_findAllTargetManufacturerAggregates2() {
        List<Object[]> expectedResult = new ArrayList<>();
        when(typedQueryO.getResultList()).thenReturn(expectedResult);
        List<Object[]> actualResult = lotDao.findAllTargetManufacturerAggregates(1L, "String", new DashboardRequestDto(), 1L, List.of("a"), List.of(1L), List.of("a"));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void test_getAllWarehouseAggregateForMiller() {
        List<Object[]> expectedResult = new ArrayList<>();
        when(typedQueryO.getResultList()).thenReturn(expectedResult);
        List<Object[]> actualResult = lotDao.getAllWarehouseAggregateForMiller(1L, new DashboardRequestDto(), List.of(1L));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void test_getAllWarehouseAggregateForFRK() {
        List<Object[]> expectedResult = new ArrayList<>();
        when(typedQueryO.getResultList()).thenReturn(expectedResult);
        List<Object[]> actualResult = lotDao.getAllWarehouseAggregateForFRK(1L, new DashboardRequestDto(), List.of(1L));
        assertEquals(expectedResult, actualResult);
    }


    @Test
    public void testGetCount() {
        when(entityManager.createQuery(anyString())).thenReturn(typedQueryL);
        when(typedQueryL.setParameter(anyString(), any())).thenReturn(typedQueryL);
        when(typedQueryL.getSingleResult()).thenReturn(10L);
        Long count = lotDao.getCount(1L);
        assertEquals(10L, count);
    }

    @Test
    public void testFindAllByBatchId() {

        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        List<Lot> lots = lotDao.findAllByBatchId(1L, 1, 10);
        assertEquals(0, lots.size());
    }


    @Test
    public void testFindAllTargetManufacturerIdsBySource() {
        List<Long> manufacturerIds = Arrays.asList(1L, 2L, 3L);
        when(typedQueryL.getResultList()).thenReturn(manufacturerIds);
        List<Long> result = lotDao.findAllTargetManufacturerIdsBySource(manufacturerIds);
        assertEquals(3, result.size());
    }

    @Test
    public void testFindAllSourceManufacturerIdsByDistrictGeoId() {
        Long categoryId = 1L;
        List<String> districtGeoIds = Arrays.asList("district1", "district2");
        DashboardRequestDto dto = new DashboardRequestDto();
        List<Long> targetManufacturerIds = Arrays.asList(1L, 2L, 3L);
        when(typedQueryL.getResultList()).thenReturn(Arrays.asList(1L, 2L, 3L));
        List<Long> result = lotDao.findAllSourceManufacturerIdsByDistrictGeoId(categoryId, districtGeoIds, dto, targetManufacturerIds);
        assertEquals(3, result.size());
    }

    @Test
    public void testFindAllTargetManufacturerAggregates() {
        Long categoryId = 1L;
        DashboardRequestDto dto = new DashboardRequestDto();
        Long sourceManufacturerId = 1L;
        List<Long> targetManufacturerIds = Arrays.asList(1L, 2L, 3L);
        List<String> lotApprovalPendingStates = Arrays.asList("state1", "state2");
        List<Long> testManufacturerIds = Arrays.asList(1L, 2L, 3L);
        List<String> lotRejectedStates = Arrays.asList("state1", "state2");
        when(typedQueryO.getResultList()).thenReturn(Arrays.asList(new Object[]{1L, 2L, 3L}, new Object[]{4L, 5L, 6L}, new Object[]{7L, 8L, 9L}));
        List<Object[]> result = lotDao.findAllTargetManufacturerAggregates(categoryId, dto, sourceManufacturerId, targetManufacturerIds, lotApprovalPendingStates, testManufacturerIds, lotRejectedStates);
        assertEquals(3, result.size());
    }


    @Test
    public void testGetTargetLotInventory() {
        Long manufacturerId = 1L;
        Long categoryId = 1L;
        SearchListRequest searchRequest = new SearchListRequest();
        Integer pageNumber = 1;
        Integer pageSize = 10;
        Boolean approvedSourceLots = true;

        List<Lot> expectedLots = new ArrayList<>();
        // Add some Lot objects to the expectedLots list


        when(typedQuery.getResultList()).thenReturn(expectedLots);

        List<Lot> actualLots = lotDao.getTargetLotInventory(manufacturerId, categoryId, searchRequest, pageNumber, pageSize, approvedSourceLots);

        assertEquals(expectedLots, actualLots);
    }

    @Test
    public void testGetLotInventoryCount() {

        Long manufacturerId = 1L;
        Long categoryId = 1L;
        SearchListRequest searchRequest = new SearchListRequest();
        Boolean approvedSourceLots = true;
        Long expectedCount = 10L;

        when(typedQueryL.getSingleResult()).thenReturn(expectedCount);

        Long actualCount = lotDao.getLotInventoryCount(manufacturerId, categoryId, searchRequest, approvedSourceLots);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testGetTargetLotInventoryCount() {

        Long manufacturerId = 1L;
        Long categoryId = 1L;
        SearchListRequest searchRequest = new SearchListRequest();
        Boolean approvedSourceLots = true;
        Long expectedCount = 10L;

        when(typedQueryL.getSingleResult()).thenReturn(expectedCount);

        Long actualCount = lotDao.getTargetLotInventoryCount(manufacturerId, categoryId, searchRequest, approvedSourceLots);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testFindAllByCategoryIds() {

        SearchListRequest searchRequest = new SearchListRequest();
        List<Long> userCategoryIds = Arrays.asList(1L, 2L);
        List<Long> testManufacturerIds = Arrays.asList(3L, 4L);
        List<Lot> expectedLots = Arrays.asList(new Lot(), new Lot());

        when(typedQuery.getResultList()).thenReturn(expectedLots);

        List<Lot> actualLots = lotDao.findAllByCategoryIds(searchRequest, userCategoryIds, 1, 10, testManufacturerIds);

        assertEquals(expectedLots, actualLots);
    }

    @Test
    public void testGetCount2() {
        Long manufacturerId = 1L;
        Long categoryId = 2L;
        SearchListRequest searchRequest = new SearchListRequest();
        Long expectedCount = 5L;

        when(typedQueryL.getSingleResult()).thenReturn(expectedCount);

        Long actualCount = lotDao.getCount(manufacturerId, categoryId, searchRequest);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testGetCountForTargetLot() {
        // Arrange
        Long manufacturerId = 1L;
        Long categoryId = 1L;
        SearchListRequest searchRequest = new SearchListRequest();
        when(typedQueryL.getSingleResult()).thenReturn(10L);

        Long result = lotDao.getCountForTargetLot(manufacturerId, categoryId, searchRequest);

        assertNotNull(result);
        assertTrue(result > 0);
    }

    @Test
    public void testGetCount3() {
        // Arrange
        SearchListRequest searchRequest = new SearchListRequest(); // set the required fields
        List<Long> categoryIds = Arrays.asList(1L, 2L, 3L);
        List<Long> testManufacturerIds = Arrays.asList(1L, 2L, 3L);

        when(typedQueryL.getSingleResult()).thenReturn(10L);

        // Act
        Long result = lotDao.getCount(searchRequest, categoryIds, testManufacturerIds);

        // Assert
        assertEquals(10L, result);
    }

    @Test
    public void testFindAllBySourceCategoryId() {
        // Arrange
        Long manufacturerId = 1L;
        Long categoryId = 2L;
        String search = "test";
        Integer pageNumber = 1;
        Integer pageSize = 10;

        Lot lot = new Lot(); // set the required fields
        List<Lot> expectedLots = List.of(lot);


        when(typedQuery.getResultList()).thenReturn(expectedLots);

        // Act
        List<Lot> result = lotDao.findAllBySourceCategoryId(manufacturerId, categoryId, search, pageNumber, pageSize);

        // Assert
        assertEquals(expectedLots, result);
    }

    @Test
    public void testFindAllTargetLotsBySourceCategoryId() {
        // Arrange
        Long manufacturerId = 1L;
        Long categoryId = 1L;
        String search = "test";
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<Lot> expectedLots = new ArrayList<>(); // populate this with expected lots

        when(typedQuery.getResultList()).thenReturn(expectedLots);

        // Act
        List<Lot> actualLots = lotDao.findAllTargetLotsBySourceCategoryId(manufacturerId, categoryId, search, pageNumber, pageSize);

        // Assert
        assertEquals(expectedLots, actualLots);

    }

    @Test
    public void testFindAllLotsForInspection() {
        // Arrange
        Long categoryId = 1L;
        SearchListRequest searchListRequest = new SearchListRequest();
        List<Long> testManufacturerIds = Arrays.asList(1L, 2L);
        List<Long> lotIds = Arrays.asList(3L, 4L);
        Integer pageNumber = 1;
        Integer pageSize = 10;

        Lot lot1 = new Lot();
        Lot lot2 = new Lot();
        List<Lot> expectedLots = Arrays.asList(lot1, lot2);

        when(typedQuery.getResultList()).thenReturn(expectedLots);

        // Act
        List<Lot> actualLots = lotDao.findAllLotsForInspection(categoryId, searchListRequest, testManufacturerIds, lotIds, pageNumber, pageSize);

        assertEquals(expectedLots, actualLots);
    }

    @Test
    public void testGetCountForInspection() {

        Long categoryId = 1L;
        List<Long> testManufacturerIds = Arrays.asList(1L, 2L, 3L);
        SearchListRequest searchListRequest = new SearchListRequest();

        when(typedQueryL.getSingleResult()).thenReturn(10L);

        Long result = lotDao.getCountForInspection(categoryId, searchListRequest, testManufacturerIds);

        assertEquals(10L, result);
    }

    @Test
    public void testFindAllByIds() {

        List<Lot> expectedLots = Arrays.asList(new Lot(), new Lot());
        when(typedQuery.getResultList()).thenReturn(expectedLots);

        List<Long> ids = Arrays.asList(1L, 2L);
        List<Lot> actualLots = lotDao.findAllByIds(ids);

        verify(typedQuery).setParameter("ids", ids);
    }

    @Test
    public void testGetCount4() {
        // Arrange
        Long expectedCount = 5L;
        Long manufacturerId = 1L;
        Long categoryId = 2L;
        String search = "test";

        when(typedQueryL.getSingleResult()).thenReturn(expectedCount);

        Long actualCount = lotDao.getCount(manufacturerId, categoryId, search);

        // Assert
        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testGetTargetLotsCount() {
        // Arrange
        Long manufacturerId = 1L;
        Long categoryId = 1L;
        String search = "test";

        Long expectedCount = 10L;

        when(typedQueryL.getSingleResult()).thenReturn(expectedCount);

        Long actualCount = lotDao.getTargetLotsCount(manufacturerId, categoryId, search);

        assertEquals(expectedCount, actualCount);

    }

    @Test
    public void testMigrateData() {

        String str = "123456789";
        BigInteger bigInt = new BigInteger(str);
        Query query = Mockito.mock(Query.class);
        when(entityManager.createNativeQuery("select id, batch_id from lot where batch_id is not null")).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(new Object[]{new Object[]{bigInt, bigInt}}));
        // Act
        List<Long[]> result = lotDao.migrateData();

        assertEquals(1, result.size());
    }


}

