package com.beehyv.lab.entity;

import com.beehyv.lab.enums.LabSampleResult;
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
@Table(name="lab_test")
@Entity
@SQLDelete(sql = "UPDATE lab_test SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class LabTest extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String testName;
    private String value;
    private String defaultPresent;
    private String uom;
    private String testMethodFollowed;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "lab_sample_id")
    private LabSample labSample;
    private Boolean isMandatory;
    private String referenceMethod;
    @Column(name = "min_value")
    private Double minValue;
    @Column(name = "max_value")
    private Double maxValue;
    private LabSampleResult testResult;

}
