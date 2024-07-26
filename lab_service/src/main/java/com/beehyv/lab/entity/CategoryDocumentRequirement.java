package com.beehyv.lab.entity;

import com.beehyv.lab.enums.CategoryDocRequirementType;
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
@Table(name="category_document_requirement")
@Entity
@SQLDelete(sql = "UPDATE category_document_requirement SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class CategoryDocumentRequirement extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "category_id")
    private Long categoryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_type_id")
    private DocType docType;
    @Column(name = "is_mandatory")
    private Boolean isMandatory;
    @Column(name = "is_enabled", columnDefinition = "tinyint(1) default 1")
    private Boolean isEnabled;
    private CategoryDocRequirementType categoryDocRequirementType;

    public CategoryDocumentRequirement(Long id){
        this.id = id;
    }
}