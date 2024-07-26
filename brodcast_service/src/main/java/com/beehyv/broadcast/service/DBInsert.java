//package com.beehyv.EventDriven.service;
//
//import com.beehyv.EventDriven.enums.StateType;
//import com.beehyv.EventDriven.manager.EventManager;
//import com.beehyv.EventDriven.model.Event;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class DBInsert implements CommandLineRunner {
//
//    private final EventManager eventManager;
//    @Override
//    public void run(String... args) {
//        Map<String, String> eventMap = new HashMap<>();
//
//        eventMap.put("Batch Created", "created");
//        eventMap.put("Batch Updated", "batchUpdated");
//        eventMap.put("Batch Sample sent to Lab","sentBatchSampleToLabTest");
//        eventMap.put("Batch Sample in Lab","batchSampleInLab");
//        eventMap.put("Batch Sample tested","batchSampleLabTestDone");
//        eventMap.put("Batch Rejected","rejected");
//        eventMap.put("Batch Self certified","selfTestedBatch");
//        eventMap.put("Batch ready to be dispatched","batchToDispatch");
//        eventMap.put("Lot dispatched","dispatched");
//        eventMap.put("Lot received","lotReceived");
//        eventMap.put("Lot Self certified","selfTestedLot");
//        eventMap.put("Lot Sample sent to Lab","sentLotSampleToLabTest");
//        eventMap.put("Lot Sample in Lab","lotSampleInLab");
//        eventMap.put("Lot Sample tested","lotSampleLabTestDone");
//        eventMap.put("Lot Accepted","approved");
//        eventMap.put("Lot to send back","toSendBackRejected");
//        eventMap.put("Lot sent back","sentBackRejected");
//        eventMap.put("Rejected lot received","receivedRejected");
//        eventMap.put("Batch Sample Rejected","batchSampleRejected");
//        eventMap.put("Lot Sample Rejected","lotSampleRejected");
//        List<Event> list = eventMap.entrySet().stream().map(e -> {
//            Event entity = new Event();
//            entity.setDisplayName(e.getKey());
//            entity.setStateName(e.getValue());
//            entity.setStateType(e.getKey().contains("Batch") ? StateType.BATCH : StateType.LOT);
//            entity = eventManager.create(entity);
//            log.info("Event: {}", entity);
//            return entity;
//        }).toList();
//    }
//
//
//
//
//
//
//}
