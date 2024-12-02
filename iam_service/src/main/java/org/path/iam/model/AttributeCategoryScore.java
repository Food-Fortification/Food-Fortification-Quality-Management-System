package org.path.iam.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttributeCategoryScore extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private AttributeCategory attributeCategory;

    @OneToMany(mappedBy = "attributeCategoryScore", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<ManufacturerAttributeScore> attributeScore;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "manufacturer_category_attributes_id")
    private ManufacturerCategoryAttributes manufacturerCategoryAttributes;

    public AttributeCategoryScore(Long id){
        this.id = id;
    }

}
