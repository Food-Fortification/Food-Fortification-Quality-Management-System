package org.path.iam.model;

import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE village SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class Village extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "district_id",referencedColumnName = "id")
    private District district;

    @OneToMany(mappedBy = "village",cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private Set<Address> address;
    private String code;
    private String geoId;
    public Village(Long id){
        this.id=id;
    }
}
