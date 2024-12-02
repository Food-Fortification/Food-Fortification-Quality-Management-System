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
@SQLDelete(sql = "UPDATE role_category_state SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class RoleCategoryState extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "role_category_id")
    private RoleCategory roleCategory;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "state_id")
    private State state;
}