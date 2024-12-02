package org.path.fortification.entity;

import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE batch_lot_mapper SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class BatchLotMapping extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "target_lot_id")
    private Lot targetLot;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "source_lot_id")
    private Lot sourceLot;

    private Double quantity;
    private String targetLotStatus;

    public BatchLotMapping(Batch batch, Lot lot) {
        this.batch = batch;
        this.sourceLot = lot;
    }

    public BatchLotMapping(Lot sourceLot, Lot targetLot){
        this.sourceLot = sourceLot;
        this.targetLot = targetLot;
    }

    public Batch getBatch(){
        return this.batch;
    }
}
