package com.beehyv.fortification.listeners;

import com.beehyv.fortification.dto.requestDto.BatchStateEvent;
import com.beehyv.fortification.dto.requestDto.LotStateEvent;
import com.beehyv.fortification.entity.BatchStateGeo;
import com.beehyv.fortification.entity.GeoStateId;
import com.beehyv.fortification.entity.LotStateGeo;
import com.beehyv.fortification.enums.SampleTestResult;
import com.beehyv.fortification.manager.BatchStateGeoManager;
import com.beehyv.fortification.manager.CategoryManager;
import com.beehyv.fortification.manager.LotStateGeoManager;
import com.beehyv.fortification.entity.*;
import com.beehyv.fortification.enums.LotConsumedType;
import com.beehyv.fortification.enums.SampleTestResult;
import com.beehyv.fortification.manager.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class StateUpdatesListner {

    private final LotStateGeoManager lotStateGeoManager;
    private final BatchStateGeoManager batchStateGeoManager;
    private final CategoryManager categoryManager;
    private final LotManager lotManager;
    private final LotConsumedAggregateManager lotConsumedAggregateManager;

    @Transactional
    @KafkaListener(topics = "lot-state-geo-update", groupId = "myGroup")
    public void consumeMessage(LotStateEvent lotStateEvent) {
        log.info(String.format("Message received: %s", lotStateEvent));
        LotStateGeo lotStateGeo;
        GeoStateId geoStateId = new GeoStateId(lotStateEvent.getCategoryId(), lotStateEvent.getTargetManufacturerId(), lotStateEvent.getDate());
        try {
            lotStateGeo = lotStateGeoManager.findByCategoryIdAndManufacturerId(geoStateId);
        } catch (NoResultException exception) {
            lotStateGeo = new LotStateGeo();
            BeanUtils.copyProperties(lotStateEvent, lotStateGeo);
            lotStateGeo.setGeoStateId(geoStateId);
        }
        BatchStateGeo batchStateGeo;
        GeoStateId batchGeoStateId = new GeoStateId(lotStateEvent.getCategoryId(), lotStateEvent.getBatchManufacturerId(), lotStateEvent.getDate());
        try {
            batchStateGeo = batchStateGeoManager.findByGeoStateId(batchGeoStateId);
        } catch (NoResultException exception) {
            batchStateGeo = new BatchStateGeo();
            batchStateGeo.setGeoStateId(new GeoStateId(lotStateEvent.getCategoryId(), lotStateEvent.getBatchManufacturerId(), lotStateEvent.getDate()));
        }

        Double quantity = lotStateEvent.getQuantity();

        switch (lotStateEvent.getState()) {
            case "dispatched" -> {
                lotStateGeo.addInTransitQuantity(quantity);
                batchStateGeo.addInTransitQuantity(quantity);
                if (lotStateEvent.getIsBatchTested()) {
                    batchStateGeo.addDispatchedTested(lotStateEvent.getQuantity());
                } else {
                    batchStateGeo.addDispatchedNotTested(lotStateEvent.getQuantity());
                }
            }
            case "lotReceived" -> {
                lotStateGeo.addReceivedQuantity(quantity);
                batchStateGeo.addReceivedQuantity(quantity);
            }
            case "lotSampleLabTestDone" -> {
                lotStateGeo.addSampleTestedQuantity(quantity);
                if (lotStateEvent.getTestResult()!= null && lotStateEvent.getTestResult().equals(SampleTestResult.TEST_PASSED)){
                    batchStateGeo.addLotSampleTestPassedQuantity(lotStateEvent.getQuantity());
                }else {
                    batchStateGeo.addLotSampleTestRejectedQuantity(lotStateEvent.getQuantity());
                }
            }
            case "selfTestedLot" -> {
                lotStateGeo.addTestedQuantity(quantity);
                if (lotStateEvent.getTestResult()!= null && lotStateEvent.getTestResult().equals(SampleTestResult.TEST_PASSED)){
                    batchStateGeo.addLotTestPassedQuantity(lotStateEvent.getQuantity());
                }else {
                    batchStateGeo.addLotTestRejectedQuantity(lotStateEvent.getQuantity());
                }
            }
            case "sentLotSampleToLabTest" -> {
                lotStateGeo.addSampleInTransitQuantity(quantity);
                batchStateGeo.addLotSampleInTransitQuantity(quantity);
            }
            case "lotSampleInLab" -> {
                lotStateGeo.addTestInProgressQuantity(quantity);
                batchStateGeo.addLotTestInProgressQuantity(quantity);
            }
            case "approved" -> {
                lotStateGeo.addApprovedQuantity(quantity);
                batchStateGeo.addApprovedQuantity(quantity);
                addLotConsumedAggregates(lotStateEvent.getLotId());
            }
            case "toSendBackRejected" -> {
                batchStateGeo.addLotRejected(quantity);
                lotStateGeo.addRejectedQuantity(quantity);
                batchStateGeo.addRejectedQuantity(quantity);
            }
            case "sentBackRejected" -> batchStateGeo.addRejectedInTransitQuantity(quantity);
            case "receivedRejected" -> batchStateGeo.addReceivedRejectedQuantity(quantity);
            case "used" -> lotStateGeo.addUsedQuantity(quantity);
            case "lotSampleRejected" -> {
                lotStateGeo.addSampleRejectedQuantity(quantity);
                batchStateGeo.addLotSampleRejectedQuantity(quantity);
            }
        }
        batchStateGeoManager.update(batchStateGeo);
        lotStateGeoManager.update(lotStateGeo);
        log.info(" Completed Lot State Geo insertion");
    }

    @Transactional
    @KafkaListener(topics = "batch-state-geo-update", groupId = "myGroup")
    public void consumeMessage(BatchStateEvent batchStateEvent) {
        List<String> list = Arrays.asList("partiallyDispatched", "fullyDispatched");
        if(list.contains(batchStateEvent.getState())) {
            return;
        }
        log.info(String.format("Message received: %s", batchStateEvent));
        BatchStateGeo batchStateGeo;
        GeoStateId batchGeoStateId = new GeoStateId(batchStateEvent.getCategoryId(),
                batchStateEvent.getManufacturerId(), batchStateEvent.getDate());
        try {
            batchStateGeo = batchStateGeoManager.findByGeoStateId(batchGeoStateId);
        } catch (NoResultException exception) {
            batchStateGeo = new BatchStateGeo();
            BeanUtils.copyProperties(batchStateEvent, batchStateGeo);
            batchStateGeo.setGeoStateId(batchGeoStateId);
        }

        switch (batchStateEvent.getState()) {
            case "created" -> batchStateGeo.addInProductionQuantity(batchStateEvent.getQuantity());
            case "sentBatchSampleToLabTest" ->
                    batchStateGeo.addBatchSampleInTransitQuantity(batchStateEvent.getQuantity());
            case "batchSampleInLab" ->
                    batchStateGeo.addBatchSampleTestInProgressQuantity(batchStateEvent.getQuantity());
            case "batchSampleLabTestDone" -> {
                if (batchStateEvent.getTestResult()!= null && batchStateEvent.getTestResult().equals(SampleTestResult.TEST_PASSED) && batchStateEvent.getQuantity() != null){
                    batchStateGeo.addBatchSampleTestPassedQuantity(batchStateEvent.getQuantity());
                }else if(batchStateEvent.getTestResult()!= null && batchStateEvent.getTestResult().equals(SampleTestResult.TEST_FAILED) && batchStateEvent.getQuantity() != null){
                    batchStateGeo.addBatchSampleTestRejectedQuantity(batchStateEvent.getQuantity());
                }else {
                    batchStateGeo.addBatchSampleTestedQuantity(batchStateEvent.getQuantity());
                }
            }
            case "batchSampleRejected" -> batchStateGeo.addRejectedQuantity(batchStateEvent.getQuantity());
            case "rejected" -> {
                batchStateGeo.addRejectedQuantity(batchStateEvent.getQuantity());
                if(batchStateEvent.getQuantity() != null){
                    batchStateGeo.addOnlyBatchTestRejectedQuantity(batchStateEvent.getQuantity());
                }
            }
            case "batchToDispatch" -> {
                batchStateGeo.addProducedQuantity(batchStateEvent.getQuantity());
                if(batchStateEvent.getQuantity() != null){
                    batchStateGeo.addOnlyBatchTestPassedQuantity(batchStateEvent.getQuantity());
                }
                if (batchStateEvent.getIsBatchTested()) {
                    batchStateGeo.addAvailableTested(batchStateEvent.getQuantity());
                } else {
                    batchStateGeo.addAvailableNotTested(batchStateEvent.getQuantity());
                }
            }
            case "selfTestedBatch" -> {
                if (batchStateEvent.getTestResult()!= null && batchStateEvent.getTestResult().equals(SampleTestResult.TEST_PASSED)){
                    batchStateGeo.addBatchTestPassedQuantity(batchStateEvent.getQuantity());
                }else {
                    batchStateGeo.addBatchTestRejectedQuantity(batchStateEvent.getQuantity());
                }
            }
//                    batchStateGeo.addBatchTestedQuantity(batchStateEvent.getQuantity());
        }
        batchStateGeoManager.update(batchStateGeo);
        log.info(" Completed Batch State Geo insertion");
    }

    private void addLotConsumedAggregates(Long id){
        Lot lot = lotManager.findById(id);
        Set<LotConsumedAggregate> childLotConsumedAggregates = null;
        if(!Objects.equals(lot.getSourceDistrictGeoId(), lot.getTargetDistrictGeoId())) {
            childLotConsumedAggregates = addChildLotConsumedAggregates(lot);
        }

        LotConsumedAggregate lotConsumedAggregate = lotConsumedAggregateManager.findBySourceAndTargetDistrictGeoId(lot.getSourceDistrictGeoId(), lot.getTargetDistrictGeoId(), LotConsumedType.DIRECT);
        if(lotConsumedAggregate == null){
            lotConsumedAggregate = new LotConsumedAggregate();
            lotConsumedAggregate.setCategoryId(lot.getCategory().getId());
            lotConsumedAggregate.setLotConsumedType(LotConsumedType.DIRECT);
            lotConsumedAggregate.setQuantity(lot.getTotalQuantity());
            lotConsumedAggregate.setSourceDistrictGeoId(lot.getSourceDistrictGeoId());
            lotConsumedAggregate.setSourceStateGeoId(lot.getSourceStateGeoId());
            lotConsumedAggregate.setTargetDistrictGeoId(lot.getTargetDistrictGeoId());
            lotConsumedAggregate.setTargetStateGeoId(lot.getTargetStateGeoId());
            lotConsumedAggregate.setChildLotAggregates(childLotConsumedAggregates);
            lotConsumedAggregateManager.create(lotConsumedAggregate);
        }else {
            lotConsumedAggregate.setQuantity(lotConsumedAggregate.getQuantity() + lot.getTotalQuantity());
            if(childLotConsumedAggregates!= null){
                Set<LotConsumedAggregate> childLotAggregates = lotConsumedAggregate.getChildLotAggregates();
                childLotAggregates.addAll(childLotConsumedAggregates);
                lotConsumedAggregate.setChildLotAggregates(childLotAggregates);
                lotConsumedAggregateManager.update(lotConsumedAggregate);
            }
        }
    }

    private Set<LotConsumedAggregate> addChildLotConsumedAggregates(Lot lot){
        if(lot.getBatch() == null) return new HashSet<>();
        return lot.getBatch().getMixes().stream().map(m -> {
            if(!m.getSourceLot().getCategory().isIndependentBatch()){
                LotConsumedAggregate lotConsumedAggregate = lotConsumedAggregateManager.findBySourceAndTargetDistrictGeoId(m.getSourceLot().getSourceDistrictGeoId(), lot.getTargetDistrictGeoId(), LotConsumedType.INDIRECT);
                if(lotConsumedAggregate == null){
                    lotConsumedAggregate = new LotConsumedAggregate();
                    lotConsumedAggregate.setCategoryId(m.getSourceLot().getCategory().getId());
                    lotConsumedAggregate.setLotConsumedType(LotConsumedType.INDIRECT);
                    lotConsumedAggregate.setQuantity(m.getSourceLot().getTotalQuantity());
                    lotConsumedAggregate.setSourceDistrictGeoId(m.getSourceLot().getSourceDistrictGeoId());
                    lotConsumedAggregate.setSourceStateGeoId(m.getSourceLot().getSourceStateGeoId());
                    lotConsumedAggregate.setTargetDistrictGeoId(lot.getTargetDistrictGeoId());
                    lotConsumedAggregate.setTargetStateGeoId(lot.getTargetStateGeoId());
                    return lotConsumedAggregateManager.create(lotConsumedAggregate);
                }else {
                    lotConsumedAggregate.setQuantity(lotConsumedAggregate.getQuantity() + m.getSourceLot().getTotalQuantity());
                    return lotConsumedAggregateManager.update(lotConsumedAggregate);
                }
            }
            return null;
        }).collect(Collectors.toSet());
    }

}
