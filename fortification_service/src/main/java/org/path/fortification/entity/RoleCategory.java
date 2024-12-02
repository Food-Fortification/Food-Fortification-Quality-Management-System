package org.path.fortification.entity;

import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE role_category SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class RoleCategory extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roleName;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private RoleCategoryType roleCategoryType;

    @OneToMany(mappedBy = "roleCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<RoleCategoryState> roleCategoryStates;

    @OneToMany(mappedBy = "roleCategory",cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private Set<TargetStatesAccess> targetStates;
}