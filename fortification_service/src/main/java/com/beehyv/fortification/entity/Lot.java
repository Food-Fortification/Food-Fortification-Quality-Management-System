package com.beehyv.fortification.entity;

import com.beehyv.fortification.enums.ManufacturerCategoryAction;
import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE lot SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class Lot extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String lotNo;
    private Long targetManufacturerId;
    private Boolean isReceivedAtTarget;
    @Temporal(TemporalType.DATE)
    private Date dateOfReceiving;
    private Boolean isTargetAcknowledgedReport;
    private Boolean isTargetAccepted;
    @Column(columnDefinition="tinyint(1) default 0")
    private Boolean isLabTested;
    @Temporal(TemporalType.DATE)
    private Date dateOfAcceptance;
    @Temporal(TemporalType.DATE)
    private Date dateOfDispatch;
    private Double totalQuantity;
    private Double remainingQuantity;
    private String comments;
    @Temporal(TemporalType.DATE)
    private Date lastActionDate;
    private String taskId;
    @Column(columnDefinition = "tinyint(1) default 0")
    private Boolean isBlocked = false;
    private String sourceDistrictGeoId;
    private String targetDistrictGeoId;
    private String sourceStateGeoId;
    private String targetStateGeoId;
    private String districtGeoId;
    @Column(columnDefinition = "TEXT")
    private String prefetchedInstructions;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "state_id")
    private State state;
//    @ManyToOne(cascade = CascadeType.DETACH)
//    @JoinColumn(name = "batch_id")
//    private Batch batch;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "uom_id")
    private UOM uom;
    @OneToMany(mappedBy = "lot", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<SizeUnit> sizeUnits;
    @OneToMany(mappedBy = "lot", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Wastage> wastes;

    @OneToMany(mappedBy = "lot", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @OrderBy(value = "id desc ")
    private Set<LotProperty> lotProperties = new HashSet<>();

    @OneToMany(mappedBy = "sourceLot", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<BatchLotMapping> sourceBatchMapping = new HashSet<>();

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "category_id")
    private Category category;

    private Long manufacturerId;

    @Enumerated(EnumType.STRING)
    private ManufacturerCategoryAction action;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transportQuantityDetails_id",referencedColumnName = "id")
    private TransportQuantityDetails transportQuantityDetails;

    public Lot(Long sourceLotId) {
        this.id = sourceLotId;
    }

    @OneToMany(mappedBy = "targetLot", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<BatchLotMapping> targetBatchMapping = new HashSet<>();

    public Batch getBatch() {
        Optional<BatchLotMapping> batchLotMapping = this.getSourceBatchMapping().stream().filter(b -> b.getBatch() != null)
            .findFirst();
        return batchLotMapping.map(BatchLotMapping::getBatch).orElse(null);
    }
    public void setBatch(Batch batch, Lot lot){
        this.sourceBatchMapping.add(new BatchLotMapping(batch, lot));
    }

    public void setLots(Set<Lot> lots){
        lots.forEach(l -> {
            this.targetBatchMapping.add(new BatchLotMapping(l, this));
        });
    }

}
