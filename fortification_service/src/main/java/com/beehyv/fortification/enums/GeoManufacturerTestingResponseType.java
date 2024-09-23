package com.beehyv.fortification.enums;

public enum GeoManufacturerTestingResponseType {

    batchTestedQuantity,      //setBatchTested
    approvedQuantity,         //setLotApproved
    lotRejected,              //setLotRejected
    transitQuantity ,         // b.inTransitQuantity  + b.receivedQuantity, //setLotInTransit
    availableTested,          //setAvailableTested
    batchTestRejectedQuantity, //setBatchRejected
    sampleInTransit,
    batchTestApprovedQuantity,  //setBatchApproved
    batchNotTestedQuantity
}
