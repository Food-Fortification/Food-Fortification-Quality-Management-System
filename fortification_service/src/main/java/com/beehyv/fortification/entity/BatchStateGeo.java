package com.beehyv.fortification.entity;


import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "batch_state_geo")
@SQLDelete(sql = "UPDATE batch_state_geo SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class BatchStateGeo extends Base {

    @EmbeddedId
    private GeoStateId geoStateId;

    private String pincode;
    private String districtGeoId;
    private String stateGeoId;
    private String countryGeoId;

    private Double totalQuantity = 0d;
    private Double inProductionQuantity = 0d; // created
    private Double producedQuantity = 0d; // ready to dispatch + partially dispatch + fully dispatched
    private Double inTransitQuantity = 0d; // add while creating a lot
    private Double receivedQuantity = 0d; // add while receiving a lot
    private Double approvedQuantity = 0d; // add while approving a lot
    private Double rejectedQuantity = 0d; // add while rejecting a lot or batch

    private Double batchSampleInTransitQuantity = 0d;
    private Double batchSampleTestInProgressQuantity = 0d;
    private Double batchTestedQuantity = 0d;
    private Double batchTestApprovedQuantity = 0d; //add when batch sample test passed
    private Double batchTestRejectedQuantity = 0d; //add when batch sample test failed

    private Double lotSampleInTransitQuantity = 0d;
    private Double lotSampleTestInProgressQuantity = 0d;
    private Double lotTestedQuantity = 0d;
    private Double lotSampleRejectedQuantity = 0d;
    private Double lotRejected = 0d;
    private Double lotTestApprovedQuantity = 0d; //add when lot sample test passed
    private Double lotTestRejectedQuantity = 0d; //add when lot sample test failed

    private Double availableTested = 0d;
    private Double availableNotTested = 0d;
    private Double dispatchedTested = 0d;
    private Double dispatchedNotTested = 0d;

    // lot rejected quantity
    private Double rejectedInTransitQuantity = 0d;
    private Double receivedRejectedQuantity = 0d;

    public void addSelfDeclaredBatchQuantities(Double quantity){
        this.totalQuantity+=quantity;
        this.producedQuantity+=quantity;
        this.batchTestedQuantity+=quantity;
        this.batchTestApprovedQuantity+=quantity;
        this.dispatchedTested+=quantity;
        this.receivedQuantity+=quantity;
        this.approvedQuantity+=quantity;
        this.lotTestedQuantity+=quantity;
        this.lotTestApprovedQuantity+=quantity;
    }

    public void addTotalQuantity(Double quantity) {
        this.totalQuantity += quantity;
    }
    public void addInProductionQuantity(Double quantity) {
        this.inProductionQuantity += quantity;
        this.addTotalQuantity(quantity);
    }
    public void addProducedQuantity(Double quantity) {
        this.producedQuantity = quantity + Optional.ofNullable(this.producedQuantity).orElse(0d);
        this.inProductionQuantity -= quantity;
    }
    public void addInTransitQuantity(Double quantity) {
        this.inTransitQuantity = quantity + Optional.ofNullable(this.inTransitQuantity).orElse(0d);
    }

    public void addAvailableTested(Double quantity) {
        this.availableTested = quantity + Optional.ofNullable(this.availableTested).orElse(0d);
    }

    public void addAvailableNotTested(Double quantity) {
        this.availableNotTested = quantity + Optional.ofNullable(this.availableNotTested).orElse(0d);
    }

    public void addDispatchedTested(Double quantity) {
        this.dispatchedTested = quantity + Optional.ofNullable(this.dispatchedTested).orElse(0d);
        this.availableTested -= quantity;
    }

    public void addDispatchedNotTested(Double quantity) {
        this.dispatchedNotTested = quantity + Optional.ofNullable(this.dispatchedNotTested).orElse(0d);
        this.availableNotTested -= quantity;
    }

    public void addReceivedQuantity(Double quantity) {
        this.receivedQuantity = quantity + Optional.ofNullable(this.receivedQuantity).orElse(0d);
        this.inTransitQuantity -= quantity;
    }
    public void addApprovedQuantity(Double quantity) {
        this.approvedQuantity = quantity + Optional.ofNullable(this.approvedQuantity).orElse(0d);
    }
    public void addRejectedQuantity(Double quantity) {
        this.rejectedQuantity += quantity;
    }

    public void addBatchSampleInTransitQuantity(Double quantity) {
        this.batchSampleInTransitQuantity += quantity;
    }
    public void addBatchSampleTestInProgressQuantity(Double quantity) {
        this.batchSampleTestInProgressQuantity += quantity;
        this.batchSampleInTransitQuantity -= quantity;
    }
    public void addBatchTestedQuantity(Double quantity) {
        this.batchTestedQuantity += quantity;
    }

    public void addBatchTestPassedQuantity(Double quantity){
        this.batchTestedQuantity += quantity;
        this.batchTestApprovedQuantity += quantity;
    }

    public void addOnlyBatchTestPassedQuantity(Double quantity){
        this.batchTestApprovedQuantity += quantity;
    }

    public void addBatchTestRejectedQuantity(Double quantity){
        this.batchTestedQuantity += quantity;
        this.batchTestRejectedQuantity += quantity;
    }

    public void addOnlyBatchTestRejectedQuantity(Double quantity){
        this.batchTestRejectedQuantity += quantity;
    }

    public void addBatchSampleTestedQuantity(Double quantity) {
        this.batchTestedQuantity += quantity;
        this.batchSampleTestInProgressQuantity -= quantity;
    }

    public void addBatchSampleTestPassedQuantity(Double quantity) {
        this.batchTestedQuantity += quantity;
        this.batchSampleTestInProgressQuantity -= quantity;
        this.batchTestApprovedQuantity += quantity;
    }
    public void addBatchSampleTestRejectedQuantity(Double quantity) {
        this.batchTestedQuantity += quantity;
        this.batchSampleTestInProgressQuantity -= quantity;
        this.batchTestRejectedQuantity += quantity;
    }


    public void addLotSampleInTransitQuantity(Double quantity) {
        this.lotSampleInTransitQuantity += quantity;
    }
    public void addLotTestInProgressQuantity(Double quantity) {
        this.lotSampleTestInProgressQuantity += quantity;
        this.lotSampleInTransitQuantity -= quantity;
    }
    public void addLotSampleTestedQuantity(Double quantity) {
        this.lotTestedQuantity += quantity;
        this.lotSampleTestInProgressQuantity -= quantity;
    }
    public void addLotSampleTestPassedQuantity(Double quantity) {
        this.lotTestedQuantity += quantity;
        this.lotSampleTestInProgressQuantity -= quantity;
        this.lotTestApprovedQuantity += quantity;
    }
    public void addLotSampleTestRejectedQuantity(Double quantity) {
        this.lotTestedQuantity += quantity;
        this.lotSampleTestInProgressQuantity -= quantity;
        this.lotTestRejectedQuantity += quantity;
    }
    public void addLotTestedQuantity(Double quantity) {
        this.lotTestedQuantity += quantity;
    }

    public void addLotTestPassedQuantity(Double quantity){
        this.lotTestedQuantity += quantity;
        this.lotTestApprovedQuantity += quantity;
    }

    public void addLotTestRejectedQuantity(Double quantity){
        this.lotTestedQuantity += quantity;
        this.lotTestRejectedQuantity += quantity;
    }

    public void addRejectedInTransitQuantity(Double quantity) {
        this.rejectedInTransitQuantity += quantity;
    }

    public void addReceivedRejectedQuantity(Double quantity) {
        this.receivedRejectedQuantity += quantity;
        this.rejectedInTransitQuantity -= quantity;
    }

    public void addLotSampleRejectedQuantity(Double quantity) {
        this.lotSampleRejectedQuantity += quantity;
        this.lotSampleTestInProgressQuantity -= quantity;
    }

    public void addLotRejected(Double quantity) {
        this.lotRejected += quantity;
    }
}