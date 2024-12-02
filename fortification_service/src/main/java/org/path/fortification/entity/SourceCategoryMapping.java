package org.path.fortification.entity;

import org.path.fortification.enums.ManufacturerCategoryAction;
import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE source_category_mapping SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
public class SourceCategoryMapping extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "target_category_id")
    private Category targetCategory;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "source_category_id")
    private Category sourceCategory;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "return_category_id")
    private Category returnCategory;
    @Enumerated(EnumType.STRING)
    private ManufacturerCategoryAction categoryAction;
    private Long stateGeoId;
}
