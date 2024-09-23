package com.beehyv.fortification.entity;

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
@SQLDelete(sql = "UPDATE mix_mapping SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class MixMapping extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "lot_id")
    private Lot sourceLot;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "target_batch_id")
    private Batch targetBatch;
    private Double quantityUsed;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "uom_id")
    private UOM uom;

    public MixMapping(Long id) {
        this.id = id;
    }
}
