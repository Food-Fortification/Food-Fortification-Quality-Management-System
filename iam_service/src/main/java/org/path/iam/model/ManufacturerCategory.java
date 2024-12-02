package org.path.iam.model;

import org.path.iam.enums.ManufacturerCategoryAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE manufacturer_category SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class ManufacturerCategory extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long categoryId;

    @Column(name = "is_enabled", columnDefinition = "tinyint(1) default 1")
    private Boolean isEnabled = true;

    @Column(columnDefinition = "tinyint(1) default 0")
    private Boolean canSkipRawMaterials = false;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="manufacturer_id",referencedColumnName ="id")
    private Manufacturer manufacturer;
    @Enumerated(EnumType.STRING)
    private ManufacturerCategoryAction action;
}
