package com.beehyv.fortification.entity;

import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE lot SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class TransportVehicleDetails extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String childPurchaseId;
    private String vehicleNo;
    private String driverName;
    private String driverLicense;
    private String driverContactNo;
    private Long totalBags;
    private Double totalTruckQuantity;
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private TransportQuantityDetails transportQuantityDetails;
}
