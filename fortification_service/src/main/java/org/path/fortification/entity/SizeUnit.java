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
@SQLDelete(sql = "UPDATE size_unit SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class SizeUnit extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "batch_id")
    private Batch batch;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "lot_id")
    private Lot lot;
    @ManyToOne
    @JoinColumn(name = "uom_id")
    private UOM uom;
    private Long size;
    private Double quantity;
    @Column(columnDefinition = "tinyint(1) default 0")
    private Boolean isDispatched = false;

    public SizeUnit(Long id) {
        this.id = id;
    }
}
