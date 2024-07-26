package com.beehyv.iam.model;


import com.beehyv.iam.enums.ManufacturerType;
import com.beehyv.iam.enums.VendorType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE manufacturer SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class Manufacturer extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type; //TODO: Think to keep an enum, what is it?
    private Boolean accreditedByAgency;
    private VendorType vendorType;
    private String agencyName;
    private String licenseNumber;
    private Double totalScore;
    @Column(name = "is_raw_materials_procured", columnDefinition = "tinyint(1) default 0")
    private Boolean isRawMaterialsProcured = false;
    private String externalManufacturerId;

    @OneToMany(mappedBy = "manufacturer",cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private Set<ManufacturerDoc> manufacturerDocs = new HashSet<>();

    @OneToMany(mappedBy = "manufacturer",cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private Set<ManufacturerCategory> manufacturerCategories = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id",referencedColumnName = "id")
    private Address address;

    private Boolean licenseStatus;

    @JsonIgnore
    @OneToMany(mappedBy = "manufacturer",cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private Set<User> users = new HashSet<>();

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "Manufacturer_source_mapping",
            joinColumns = {@JoinColumn(name = "source_manufacturer_id")},
            inverseJoinColumns = {@JoinColumn(name = "target_manufacturer_id")}
    )
    private Set<Manufacturer> targetManufacturers;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "material_manufacturer_mapping",
            joinColumns = {@JoinColumn(name = "target_manufacturer_id")},
            inverseJoinColumns = {@JoinColumn(name = "source_manufacturer_id")}
    )
    private Set<Manufacturer> sourceManufacturers;
    private ManufacturerType manufacturerType;

    @OneToMany(mappedBy = "manufacturer",cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy(value = "id DESC")
    @ToString.Exclude
    private Set<ManufacturerCategoryAttributes> manufacturerAttributes = new HashSet<>();

    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @OrderBy(value = "id desc ")
    private Set<ManufacturerProperty> manufacturerProperties = new HashSet<>();

    public Manufacturer(Long id){
        this.id=id;
    }
}
