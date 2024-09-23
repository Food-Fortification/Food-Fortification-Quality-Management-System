package com.beehyv.fortification.entity;

import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE wastage SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class Wastage extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "lot_id")
    private Lot lot;


    private Double wastageQuantity;
    private Date reportedDate;
    private String comments;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "uom_id")
    private UOM uom;

    public Wastage(Long id) {
        this.id = id;
    }
}
