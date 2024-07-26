package com.beehyv.iam.model;

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
@SQLDelete(sql = "UPDATE category_doc SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class CategoryDoc extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long categoryId;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name ="doc_type_id",referencedColumnName = "id")
    private DocType docType;

    private Boolean isMandatory;

    @Column(name = "is_enabled", columnDefinition = "tinyint(1) default 1")
    private Boolean isEnabled;

    public CategoryDoc(Long id){
        this.id = id;
    }

}
