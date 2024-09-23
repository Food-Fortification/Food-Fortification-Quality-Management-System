package com.beehyv.lab.entity;

import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="lab_test_reference_method")
@Entity
@SQLDelete(sql = "UPDATE lab_test_reference_method SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class LabTestReferenceMethod extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "lab_test_type_id",referencedColumnName = "id")
    private LabTestType labTestType;
    @Column(name = "min_value")
    private Double minValue;
    @Column(name = "max_value")
    private Double maxValue;
    private String uom;
    @Column(name = "default_present")
    private String defaultPresent;
    private String referenceValue;
    private Double minPercentVariationAllowed;
    private Double maxPercentVariationAllowed;
}
