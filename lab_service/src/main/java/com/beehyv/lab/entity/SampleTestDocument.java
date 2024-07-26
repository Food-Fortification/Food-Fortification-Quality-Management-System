package com.beehyv.lab.entity;

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
@Table(name="sample_test_document")
@Entity
@SQLDelete(sql = "UPDATE sample_test_document SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class SampleTestDocument extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "category_document_requirement_id")
    private CategoryDocumentRequirement categoryDocumentRequirement;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lab_sample_id")
    private LabSample labSample;
    private String name;
    @Column(name = "path")
    private String path;


}
