package com.beehyv.fortification.entity;

import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE lot SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class TransportQuantityDetails extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "transportQuantityDetails")
    @JoinColumn(name = "lot_id",referencedColumnName = "id")
    private Lot lot;

    private String purchaseOrderId;
    private String destinationId;
    private Long totalNoOfBags;
    private Double grossWeight;
    private Double tareWeight;
    private Double netWeight;

    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<TransportVehicleDetails> transportVehicleDetailsSet = new HashSet<>();
}
