package org.path.iam.model;

import lombok.*;
import org.hibernate.annotations.Cascade;
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
@SQLDelete(sql = "UPDATE lab_users SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class LabUsers extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long labId;
    @OneToMany(fetch = FetchType.EAGER,orphanRemoval = true)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @ToString.Exclude
    private Set<User> user = new HashSet<>();

    public LabUsers(Long labId, Set<User> users){
        this.labId = labId;
        this.user.addAll(users);
    }
}
