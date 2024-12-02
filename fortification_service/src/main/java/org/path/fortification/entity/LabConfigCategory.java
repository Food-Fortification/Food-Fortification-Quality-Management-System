package org.path.fortification.entity;

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
@Entity
@SQLDelete(sql = "UPDATE lab_config_category SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class LabConfigCategory extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String labOption;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "source_category_id")
    private Category sourceCategory;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "target_category_id")
    private Category targetCategory;


}