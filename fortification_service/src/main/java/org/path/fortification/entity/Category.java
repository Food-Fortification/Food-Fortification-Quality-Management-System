package org.path.fortification.entity;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE category SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class Category extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "category_source_mapping",
            joinColumns = {@JoinColumn(name = "target_category_id")},
            inverseJoinColumns = {@JoinColumn(name = "source_category_id")}
    )
    private Set<Category> sourceCategories;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<CategoryDoc> documents;
    @Column(columnDefinition="tinyint(1) default 0")
    private boolean independentBatch;
    private Integer sequence;

    public Category(Long categoryId) {
        this.id = categoryId;
    }
}