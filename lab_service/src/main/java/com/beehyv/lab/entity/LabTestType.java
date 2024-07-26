package com.beehyv.lab.entity;

import java.util.List;
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
@Table(name="lab_test_type")
@Entity
@SQLDelete(sql = "UPDATE lab_test_type SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class LabTestType extends Base {

    public enum Type{
        PHYSICAL,MICRONUTRIENT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(mappedBy = "labTestType",cascade = CascadeType.ALL)
    private List<LabTestReferenceMethod> labTestReferenceMethods;
    private String name;
    @Column(name = "category_id")
    private Long categoryId;
    @Column(name = "is_mandatory")
    private Boolean isMandatory;
    @Enumerated(EnumType.STRING)
    private Type type;
    private String geoId;
}
