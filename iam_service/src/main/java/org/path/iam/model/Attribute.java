package org.path.iam.model;

import org.path.iam.enums.AttributeScoreType;
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
@SQLDelete(sql = "UPDATE attribute SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class Attribute extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Boolean isActive;
    private Double weightage;
    private Integer totalScore;
    private Integer defaultScore;

    @Enumerated(value = EnumType.STRING)
    private AttributeScoreType type;

    private Long categoryId;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "attribute_category_id", referencedColumnName = "id")
    private AttributeCategory attributeCategory;

    public Attribute(Long id){
        this.id = id;
    }
}
