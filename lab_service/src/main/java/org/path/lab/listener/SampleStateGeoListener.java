package org.path.lab.listener;


import org.path.lab.dto.requestDto.SampleGeoStateEvent;
import org.path.lab.entity.SampleStateGeo;
import org.path.lab.entity.SampleStateGeoId;
import org.path.lab.enums.LabSampleActionType;
import org.path.lab.manager.SampleStateGeoManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

@Component
@RequiredArgsConstructor
@Slf4j
public class SampleStateGeoListener {
    private final SampleStateGeoManager sampleStateGeoManager;

    @Transactional
    @KafkaListener(topics = "sample-state-count-update", groupId = "lab-events-group")
    public void consumeMessage(SampleGeoStateEvent event) {
        log.info(String.format("Message received: %s", event));
        SampleStateGeo sampleStateGeo;
        SampleStateGeoId stateGeoId = SampleStateGeoId.builder()
                .districtGeoId(event.getDistrictGeoId())
                .countryGeoId(event.getCountryGeoId())
                .stateGeoId(event.getStateGeoId())
                .sampleSentYear(event.getSampleSentYear())
                .labId(event.getLabId())
                .categoryId(event.getCategoryId())
                .manufacturerId(event.getManufacturerId())
                .build();
        try{
            sampleStateGeo = sampleStateGeoManager.findByCategoryIdAndLabIdAndGeoIds(stateGeoId);
        }catch (NoResultException e){
            sampleStateGeo = new SampleStateGeo();
            sampleStateGeo.setStateGeoId(stateGeoId);
        }
        sampleStateGeo.setStateGeoId(stateGeoId);
        Integer count = switch (LabSampleActionType.valueOf(event.getAction())) {
            case create -> 1;
            case delete -> -1;
        };

        switch (event.getState()) {
            case "toReceive" -> sampleStateGeo.addInTransitCount(count);
            case "inProgress" -> sampleStateGeo.addUnderTestingCount(count);
            case "done"-> sampleStateGeo.addTestedCount(count);
            case "rejected" -> sampleStateGeo.addRejectedCount(count);
        }
        sampleStateGeoManager.saveOrUpdate(sampleStateGeo);
        log.info(" Completed Sample State Geo insertion");
    }
}
