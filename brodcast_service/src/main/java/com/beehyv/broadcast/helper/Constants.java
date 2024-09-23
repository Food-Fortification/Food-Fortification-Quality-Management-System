package com.beehyv.broadcast.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Constants {

    public static String lotDetails;
    public static String batchDetails;
    public static String batchLabSampleDetails;
    public static String lotLabSampleDetails;


//    public static String approved; //Lot status update
//    public static String batchSampleInLab; //Batch status update
//    public static String batchSampleLabTestDone; //Batch Response DTO ++ List of lab samples
//    public static String batchSampleRejected; //Batch status update
//    public static String batchToDispatch; //Batch status update
//    public static String batchUpdated; // Batch Response DTO
//    public static String created; // Batch Response DTO
//    public static String dispatched; // Lot Response DTO
//    public static String lotReceived; // Lot status Update
//    public static String lotSampleInLab; //Lot Status Update
//    public static String lotSampleLabTestDone; //Lot response DTO ++ list of lab samples
//    public static String lotSampleRejected; //Lot status update
//    public static String receivedRejected; //Lot status update
//    public static String rejected; //Lot status update
//    public static String selfTestedBatch; //Batch response dto ++ list of lab samples
//    public static String selfTestedLot; //Lot response dto ++ list of lab samples
//    public static String sentBackRejected; // Lot status Update
//    public static String sentBatchSampleToLabTest; //Lot status update
//    public static String sentLotSampleToLabTest; //Lot status update
//    public static String toSendBackRejected; //Lot status update
    public static String manufacturerDetails;
    public static Integer retryCount;
    @Value("${IAM_BASE_URL}")
    public String IAM_BASE_URL;
    @Value("${LAB_BASE_URL}")
    public String LAB_BASE_URL;
    @Value("${FORTIFICATION_BASE_URL}")
    public String FORTIFICATION_BASE_URL;
    @Value("${RETRY_COUNT}")
    public Integer RETRY_COUNT;

    @PostConstruct
    public void setProperties() {
        batchDetails = FORTIFICATION_BASE_URL + "1/batch/event/";
        lotDetails = FORTIFICATION_BASE_URL + "1/lot/event/";
        batchLabSampleDetails = LAB_BASE_URL + "1/sample/event/batch/";
        lotLabSampleDetails = LAB_BASE_URL + "1/sample/event/lot/";
        retryCount = RETRY_COUNT;
    }

}
