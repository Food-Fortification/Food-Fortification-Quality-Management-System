package org.path.fortification.listeners;

import org.path.fortification.dto.requestDto.BatchStateEvent;
import org.path.fortification.dto.requestDto.LotStateEvent;
import org.path.fortification.entity.BatchStateGeo;
import org.path.fortification.entity.GeoStateId;
import org.path.fortification.entity.LotStateGeo;
import org.path.fortification.manager.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StateUpdatesListnerTest {

    @Mock
    private LotStateGeoManager lotStateGeoManager;

    @Mock
    private BatchStateGeoManager batchStateGeoManager;

    @Mock
    private CategoryManager categoryManager;

    @Mock
    private LotManager lotManager;

    @Mock
    private LotConsumedAggregateManager lotConsumedAggregateManager;

    private StateUpdatesListner stateUpdatesListner;

    @Before
    public void setUp() {
        stateUpdatesListner = new StateUpdatesListner(lotStateGeoManager, batchStateGeoManager, lotManager, lotConsumedAggregateManager);
    }

    @Test
    public void testConsumeMessageLotStateEvent() {
        // Arrange
        LotStateEvent lotStateEvent = new LotStateEvent();
        lotStateEvent.setCategoryId(1L);
        lotStateEvent.setTargetManufacturerId(2L);
        lotStateEvent.setBatchManufacturerId(3L);
        lotStateEvent.setDate(new Date());
        lotStateEvent.setQuantity(100.0);
        lotStateEvent.setState("dispatched");
        lotStateEvent.setIsBatchTested(true);
        lotStateEvent.setLotId(4L);

        LotStateGeo lotStateGeo = new LotStateGeo();
        BatchStateGeo batchStateGeo = new BatchStateGeo();

        when(lotStateGeoManager.findByCategoryIdAndManufacturerId(any(GeoStateId.class))).thenReturn(lotStateGeo);
        when(batchStateGeoManager.findByGeoStateId(any(GeoStateId.class))).thenReturn(batchStateGeo);

        // Act
        stateUpdatesListner.consumeMessage(lotStateEvent);

        // Assert
        verify(lotStateGeoManager, times(1)).update(lotStateGeo);
        verify(batchStateGeoManager, times(1)).update(batchStateGeo);
    }

    @Test
    public void testConsumeMessageBatchStateEvent() {
        // Arrange
        BatchStateEvent batchStateEvent = new BatchStateEvent();
        batchStateEvent.setCategoryId(1L);
        batchStateEvent.setManufacturerId(2L);
        batchStateEvent.setDate(new Date());
        batchStateEvent.setQuantity(100.0);
        batchStateEvent.setState("created");

        BatchStateGeo batchStateGeo = new BatchStateGeo();

        when(batchStateGeoManager.findByGeoStateId(any(GeoStateId.class))).thenReturn(batchStateGeo);

        // Act
        stateUpdatesListner.consumeMessage(batchStateEvent);

        // Assert
        verify(batchStateGeoManager, times(1)).update(batchStateGeo);
    }


}