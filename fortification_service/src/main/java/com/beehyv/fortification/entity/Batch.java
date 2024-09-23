package com.beehyv.fortification.entity;


import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE batch SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class Batch extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long manufacturerId;
    @Temporal(TemporalType.DATE)
    private Date dateOfManufacture;
    @Temporal(TemporalType.DATE)
    private Date dateOfExpiry;
    private String fssaiLicenseId;
    private String batchNo;
    private Double totalQuantity;
    private Double remainingQuantity;
    @Column(columnDefinition = "tinyint(1) default 0")
    private Boolean isLabTested = false;
    private String comments;
    @Temporal(TemporalType.DATE)
    private Date lastActionDate;
    @Column(columnDefinition = "TEXT")
    private String prefetchedInstructions;
    @Column(columnDefinition = "tinyint(1) default 0")
    private Boolean isBlocked = false;
    private String districtGeoId;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "state_id")
    private State state;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "uom_id")
    private UOM uom;
    private String taskId;
    @Column(columnDefinition="integer default 1")
    private Integer lotSequence = 1;
    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @OrderBy(value = "id desc ")
    private Set<BatchProperty> batchProperties = new HashSet<>();
    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @OrderBy(value = "id desc ")
    private Set<BatchDoc> batchDocs = new HashSet<>();
    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @OrderBy(value = "id desc ")
    private Set<SizeUnit> sizeUnits;
    @OneToMany(mappedBy = "targetBatch", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @OrderBy(value = "id desc ")
    private Set<MixMapping> mixes;
    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @OrderBy(value = "id desc ")
    private Set<Wastage> wastes;

    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<BatchLotMapping> batchLotMapping = new HashSet<>();

    public Batch(Long id) {
        this.id = id;
    }

    public void addLot(Lot lot){
        if (this.batchLotMapping == null) this.batchLotMapping = new HashSet<>();
        this.batchLotMapping.add(new BatchLotMapping(this, lot));
    }
    public Set<Lot> getLots(){
        return this.getBatchLotMapping().stream().map(BatchLotMapping::getSourceLot).collect(Collectors.toSet());
    }
    public void setLots(Set<Lot> lots){
        lots.forEach(l -> new BatchLotMapping());
    }

}
