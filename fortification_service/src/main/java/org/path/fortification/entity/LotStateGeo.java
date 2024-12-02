package org.path.fortification.entity;


import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "lot_state_geo")
@SQLDelete(sql = "UPDATE lot_state_geo SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class LotStateGeo extends Base {

    @EmbeddedId
    private GeoStateId geoStateId;

    private String districtGeoId;
    private String stateGeoId;
    private String countryGeoId;
    private Double inTransitQuantity = 0d;
    private Double receivedQuantity = 0d;
    private Double approvedQuantity = 0d;
    private Double rejectedQuantity = 0d;
    private Double usedQuantity = 0d;
    private Double sampleInTransitQuantity = 0d;
    private Double testInProgressQuantity = 0d;
    private Double testedQuantity = 0d; // also self tested
    private Double sampleRejectedQuantity = 0d;
    private Double sentBackRejectedQuantity = 0d;
    private Double receivedRejectedBackQuantity = 0d;

    public void addInTransitQuantity(Double quantity) {
        this.inTransitQuantity += quantity;
    }

    public void addSelfDeclaredLotQuantities(Double quantity){
        this.receivedQuantity += quantity;
        this.approvedQuantity+=quantity;
        this.testedQuantity+=quantity;
    }
    public void addReceivedQuantity(Double quantity) {
        this.receivedQuantity += quantity;
        this.inTransitQuantity -= quantity;
    }

    public void addApprovedQuantity(Double quantity) {
        this.approvedQuantity += quantity;
    }
    public void addRejectedQuantity(Double quantity) {
        this.rejectedQuantity += quantity;
    }

    public void addSampleInTransitQuantity(Double quantity) {
        this.sampleInTransitQuantity += quantity;
    }
    public void addTestInProgressQuantity(Double quantity) {
        this.testInProgressQuantity += quantity;
        this.sampleInTransitQuantity -= quantity;
    }
    public void addTestedQuantity(Double quantity) {
        this.testedQuantity += quantity;
    }

    public void addSampleTestedQuantity(Double quantity) {
        this.testedQuantity += quantity;
        this.testInProgressQuantity -= quantity;
    }

    public void addUsedQuantity(Double quantity) {
        this.usedQuantity += quantity;
    }

    public void addSampleRejectedQuantity(Double quantity) {
        this.sampleRejectedQuantity += quantity;
        this.testInProgressQuantity -= quantity;
    }
}
