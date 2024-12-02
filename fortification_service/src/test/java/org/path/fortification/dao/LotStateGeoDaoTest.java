package org.path.fortification.dao;

import org.path.fortification.dto.requestDto.DashboardRequestDto;
import org.path.fortification.entity.GeoStateId;
import org.path.fortification.entity.LotStateGeo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = org.mockito.quality.Strictness.LENIENT)
class LotStateGeoDaoTest {

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<LotStateGeo> typedQueryG;

    @Mock
    private TypedQuery<Object[]> typedQuery;

    @InjectMocks
    private LotStateGeoDao lotStateGeoDao;

    @BeforeEach
    void setUp() {
        when(em.createQuery(anyString(), eq(Object[].class))).thenReturn(typedQuery);
        when(em.createQuery(anyString(), eq(LotStateGeo.class))).thenReturn(typedQueryG);
        when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
        when(typedQueryG.setParameter(anyString(), any())).thenReturn(typedQueryG);
    }

    @Test
    void testFindByCategoryIdAndManufacturerId() {
        // Arrange
        GeoStateId geoStateId = new GeoStateId();
        LotStateGeo expectedLotStateGeo = new LotStateGeo();
        when(typedQueryG.getSingleResult()).thenReturn(expectedLotStateGeo);

        // Act
        LotStateGeo actualLotStateGeo = lotStateGeoDao.findByCategoryIdAndManufacturerId(geoStateId);

        // Assert
        assertEquals(expectedLotStateGeo, actualLotStateGeo);
    }


    @Test
    void testGetAllWarehouseAggregateForMiller() {
        // Arrange
        Long categoryId = 1L;
        DashboardRequestDto dto = new DashboardRequestDto();
        List<Long> testManufacturerIds = Collections.singletonList(1L);
        Object[] expectedResults = new Object[]{1L, 100, 50, 150, 200};
        when(typedQuery.getResultList()).thenReturn(Collections.singletonList(expectedResults));

        // Act
        List<Object[]> actualResults = lotStateGeoDao.getAllWarehouseAggregateForMiller(categoryId, dto, testManufacturerIds);

        // Assert
        assertEquals(1, actualResults.size());
        assertArrayEquals(expectedResults, actualResults.get(0));
    }

    @Test
    void testGetAllWarehouseAggregateForFRK() {
        // Arrange
        Long categoryId = 1L;
        DashboardRequestDto dto = new DashboardRequestDto();
        List<Long> testManufacturerIds = Collections.singletonList(1L);
        Object[] expectedResults = new Object[]{1L, 100, 50, 150, 200};
        when(typedQuery.getResultList()).thenReturn(Collections.singletonList(expectedResults));

        // Act
        List<Object[]> actualResults = lotStateGeoDao.getAllWarehouseAggregateForFRK(categoryId, dto, testManufacturerIds);

        // Assert
        assertEquals(1, actualResults.size());
        assertArrayEquals(expectedResults, actualResults.get(0));
    }

    // Add more test methods here for other methods in LotStateGeoDao
}