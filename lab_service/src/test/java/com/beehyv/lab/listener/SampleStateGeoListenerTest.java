package com.beehyv.lab.listener;

import com.beehyv.lab.dto.requestDto.SampleGeoStateEvent;
import com.beehyv.lab.entity.SampleStateGeo;
import com.beehyv.lab.entity.SampleStateGeoId;
import com.beehyv.lab.enums.LabSampleActionType;
import com.beehyv.lab.manager.SampleStateGeoManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

class SampleStateGeoListenerTest {

    @Mock
    private SampleStateGeoManager sampleStateGeoManager;

    @Mock
    private KafkaTemplate<String, SampleGeoStateEvent> kafkaTemplate;

    @InjectMocks
    private SampleStateGeoListener sampleStateGeoListener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConsumeMessage() {
        SampleGeoStateEvent event = new SampleGeoStateEvent();
        event.setAction(LabSampleActionType.create.name());
        event.setState("toReceive");

        SampleStateGeo sampleStateGeo = new SampleStateGeo();
        SampleStateGeoId stateGeoId = new SampleStateGeoId();
        sampleStateGeo.setStateGeoId(stateGeoId);

        when(sampleStateGeoManager.findByCategoryIdAndLabIdAndGeoIds(any())).thenReturn(sampleStateGeo);

        sampleStateGeoListener.consumeMessage(event);
        event.setState("inProgress");
        sampleStateGeoListener.consumeMessage(event);
        event.setState("done");
        sampleStateGeoListener.consumeMessage(event);
        event.setState("rejected");
        sampleStateGeoListener.consumeMessage(event);

        verify(sampleStateGeoManager, times(4)).findByCategoryIdAndLabIdAndGeoIds(any());
        verify(sampleStateGeoManager, times(4)).saveOrUpdate(any());
    }
}