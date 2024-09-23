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
@SQLDelete(sql = "UPDATE state SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class State extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "country_id",referencedColumnName = "id")
    private Country country;
    private String code;
    private String geoId;
    @OneToMany(mappedBy = "state",cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private Set<District> districts = new HashSet<>();

}
