package org.path.fortification.manager;

import org.path.fortification.dao.LotConsumedAggregateDao;
import org.path.fortification.entity.LotConsumedAggregate;
import org.path.fortification.enums.LotConsumedType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class LotConsumedAggregateManagerTest {

    @Mock
    private LotConsumedAggregateDao lotConsumedAggregateDao;

    private LotConsumedAggregateManager lotConsumedAggregateManager;

    private String sourceDistrictGeoId;
    private String targetDistrictGeoId;
    private LotConsumedType lotConsumedType;
    private LotConsumedAggregate lotConsumedAggregate;

    @BeforeEach
    public void setUp() {
        lotConsumedAggregateManager = new LotConsumedAggregateManager(lotConsumedAggregateDao);
        sourceDistrictGeoId = "sourceDistrictGeoId";
        targetDistrictGeoId = "targetDistrictGeoId";
        lotConsumedType = LotConsumedType.DIRECT;
        lotConsumedAggregate = new LotConsumedAggregate();

        when(lotConsumedAggregateDao.findBySourceAndTargetDistrictGeoId(sourceDistrictGeoId, targetDistrictGeoId, lotConsumedType)).thenReturn(lotConsumedAggregate);
    }

    @Test
    public void testFindBySourceAndTargetDistrictGeoId() {
        LotConsumedAggregate result = lotConsumedAggregateManager.findBySourceAndTargetDistrictGeoId(sourceDistrictGeoId, targetDistrictGeoId, lotConsumedType);
        assertEquals(lotConsumedAggregate, result);
    }
}