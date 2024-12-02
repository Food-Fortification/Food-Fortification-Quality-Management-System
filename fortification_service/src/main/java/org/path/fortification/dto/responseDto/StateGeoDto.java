package org.path.fortification.dto.responseDto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StateGeoDto {

    private String type;
    private Long categoryId;
    private String categoryName;
    private Boolean isIndependentBatch;

    private String pincode;
    private String districtGeoId;
    private String stateGeoId;
    private String countryGeoId;

    private Double totalQuantity;
    private Double inProductionQuantity; // created
    private Double producedQuantity; // ready to dispatch + partially dispatch + fully dispatched
    private Double inTransitQuantity; // add while creating a lot
    private Double receivedQuantity; // add while receiving a lot
    private Double approvedQuantity; // add while approving a lot
    private Double rejectedQuantity; // add while rejecting a lot or batch

    private Double batchSampleInTransitQuantity;
    private Double batchSampleTestInProgressQuantity;
    private Double batchTestedQuantity;

    private Double lotSampleInTransitQuantity;
    private Double lotSampleTestInProgressQuantity;
    private Double lotTestedQuantity;
    private Double lotRejected;

    // lot rejected quantity
    private Double rejectedInTransitQuantity;
    private Double receivedRejectedQuantity;

    private Double sampleInTransitQuantity;
    private Double testInProgressQuantity;
    private Double testedQuantity; // also self tested

    private Double remainingQuantity;
    private Double usedQuantity;


}
