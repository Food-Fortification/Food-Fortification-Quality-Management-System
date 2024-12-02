package org.path.fortification.dao;

import org.path.fortification.entity.LotConsumedAggregate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LotConsumedAggregateDaoTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<LotConsumedAggregate> typedQuery;
    @Mock
    private TypedQuery<String> typedQueryS;

    @InjectMocks
    private LotConsumedAggregateDao lotConsumedAggregateDao;

    @BeforeEach
    void setUp() {
        when(entityManager.createQuery(anyString(), eq(LotConsumedAggregate.class)))
                .thenReturn(typedQuery);
        when(entityManager.createQuery(anyString()))
                .thenReturn(typedQueryS);
    }

//    @Test
//    void testFindBySourceAndTargetDistrictGeoId() {
//        // Arrange
//        String sourceDistrictGeoId = "sourceDistrictGeoId";
//        String targetDistrictGeoId = "targetDistrictGeoId";
//        LotConsumedType lotConsumedType = LotConsumedType.DIRECT;
//        LotConsumedAggregate expectedLotConsumedAggregate = new LotConsumedAggregate();
//
//        when(typedQuery.setParameter(anyString(), any()))
//                .thenReturn(typedQuery);
//        when(typedQuery.getSingleResult())
//                .thenReturn(expectedLotConsumedAggregate);
//        when(typedQueryS.setParameter(anyString(), any()))
//                .thenReturn(typedQueryS);
//        when(typedQueryS.getSingleResult())
//                .thenReturn(expectedLotConsumedAggregate.toString());
//
//        LotConsumedAggregate actualLotConsumedAggregate = (LotConsumedAggregate)lotConsumedAggregateDao.findBySourceAndTargetDistrictGeoId(sourceDistrictGeoId, targetDistrictGeoId, lotConsumedType);
//
//        // Assert
//        assertNotNull(actualLotConsumedAggregate);
//    }
}