package org.path.lab.entity;

import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="lab_sample")
@Entity
@SQLDelete(sql = "UPDATE lab_sample SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class LabSample extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long batchId;
    private Long manufacturerId;
    private String batchNo;
    private String lotNo;
    private Long lotId;
    @Temporal(TemporalType.DATE)
    private Date testDate;
    @Temporal(TemporalType.DATE)
    private Date sampleSentDate;
    @Temporal(TemporalType.DATE)
    private Date receivedDate;
    private String performedBy;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "lab_id")
    private Lab lab;
    private Long requestStatusId;
    private Long categoryId;
    @OneToMany(mappedBy = "labSample", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<SampleProperty> sampleProperties = new HashSet<>();

    @OneToMany(mappedBy = "labSample", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<LabTest> labTests = new HashSet<>();

    @OneToMany(mappedBy = "labSample", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<SampleTestDocument> sampleTestDocuments = new HashSet<>();

    private Boolean resultAccepted;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "sample_state")
    private SampleState state;

    @OneToOne(mappedBy = "labSample")
    @JoinColumn(name = "inspection_id",referencedColumnName = "id")
    private Inspection inspection;
    private Double percentageCategoryMix;

}
