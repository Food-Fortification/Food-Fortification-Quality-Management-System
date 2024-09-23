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
@Table(name="lab_document")
@Entity
@SQLDelete(sql = "UPDATE lab_document SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class LabDocument extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "category_doc_id")
    private CategoryDocumentRequirement categoryDoc;
    @Column(name = "path")
    private String path;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "lab_id")
    private Lab lab;
}
