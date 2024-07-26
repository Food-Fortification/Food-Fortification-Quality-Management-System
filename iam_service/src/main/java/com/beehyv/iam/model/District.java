package com.beehyv.iam.model;

import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE district SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class District extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String geoId;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "state_id",referencedColumnName = "id")
    private State state;
    private String code;

    @OneToMany(mappedBy = "district",cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private Set<Village> villages = new HashSet<>();
}
