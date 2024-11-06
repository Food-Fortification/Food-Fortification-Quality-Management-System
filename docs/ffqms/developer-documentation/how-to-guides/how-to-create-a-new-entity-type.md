# How to create a new Entity Type

This guide outlines the process of creating a new entity type in FFQMS by extending the base entity system. In this case, we will see and example of `Manufacturer` entity class, which defines attributes and relationships that will be persisted in the database. This is similar to creating entity types in TypeScript, but tailored for Java, Hibernate, and JPA.

**Manufacturer Entity Class**

The `Manufacturer` class extends the base entity class and defines the structure for manufacturer-related data, including relationships with other entities like `ManufacturerDoc`, `ManufacturerCategory`, and `Address`.

Key Annotations:

* `@Entity`: Marks the class as a JPA entity, which means it will be mapped to a database table.
* `@Getter`, `@Setter`: Lombok annotations to automatically generate getter and setter methods for all fields.
* `@AllArgsConstructor` and `@NoArgsConstructor`: Generate constructors with and without arguments.
* `@SQLDelete` and `@Where`: Soft delete functionality, ensuring the `Manufacturer` is not deleted from the database but marked as deleted.
* `@Id` and `@GeneratedValue`: Mark the primary key and define auto-increment behavior.
* `@OneToMany`, `@OneToOne`, and `@ManyToMany`: Define relationships with other entities.

**Example: Manufacturer Class**

```java
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
    private String type;
    private Boolean accreditedByAgency;
    private VendorType vendorType;
    private String agencyName;
    private String licenseNumber;
    private Double totalScore;
    
    @Column(name = "is_raw_materials_procured", columnDefinition = "tinyint(1) default 0")
    private Boolean isRawMaterialsProcured = false;
    
    private String externalManufacturerId;

    // One-to-Many relationship with ManufacturerDoc
    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<ManufacturerDoc> manufacturerDocs = new HashSet<>();

    // One-to-Many relationship with ManufacturerCategory
    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<ManufacturerCategory> manufacturerCategories = new HashSet<>();

    // One-to-One relationship with Address
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    private Boolean licenseStatus;

    @JsonIgnore
    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<User> users = new HashSet<>();

    // Many-to-Many relationships with other manufacturers (source/target mappings)
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

    // One-to-Many relationship with ManufacturerCategoryAttributes
    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy(value = "id DESC")
    @ToString.Exclude
    private Set<ManufacturerCategoryAttributes> manufacturerAttributes = new HashSet<>();

    // One-to-Many relationship with ManufacturerProperty
    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @OrderBy(value = "id desc")
    private Set<ManufacturerProperty> manufacturerProperties = new HashSet<>();

    public Manufacturer(Long id) {
        this.id = id;
    }
}
```

**Key Features**

* **Soft Delete**: The entity uses `@SQLDelete` and `@Where` annotations to handle soft deletion. Instead of removing records, the entity is marked as deleted by updating the `is_deleted` flag.
* **Relationships**: This entity has multiple relationships:
  * `One-to-Many` with `ManufacturerDoc`, `ManufacturerCategory`, `ManufacturerCategoryAttributes`, and `ManufacturerProperty`.
  * `One-to-One` with `Address`.
  * `Many-to-Many` with other `Manufacturer` entities for source and target mappings.
* **Custom Fields**: Attributes like `name`, `type`, `vendorType`, `totalScore`, and `licenseStatus` represent the core business data for a manufacturer. Some fields, like `isRawMaterialsProcured`, have default values specified in the column definition.

**Best Practices**

* **Entity Definition**: Keep business logic separate from persistence logic. The entity class should define attributes but not handle database operations directly.
* **Relationships**: Carefully define relationships with cascading behavior (`CascadeType.ALL`) and `orphanRemoval` to automatically manage related entities when performing operations on the `Manufacturer` entity.
* **Data Integrity**: Use `@JoinColumn`, `@OrderBy`, and `@Column` annotations to enforce data integrity and control how data is stored and retrieved.

**Conclusion**

The entity class uses JPA annotations to map the entity to a database table, including fields, relationships, and soft delete logic, making it a flexible and scalable part of a larger system.
