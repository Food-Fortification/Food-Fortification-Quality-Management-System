package com.beehyv.fortification.entity;

import com.beehyv.fortification.enums.LotConsumedType;
import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "lot_consumed_aggregates")
@SQLDelete(sql = "UPDATE lot_consumed_aggregates SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class LotConsumedAggregate extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long categoryId;

    @OneToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "lot_aggregates_mapping",
            joinColumns = {@JoinColumn(name = "parent_lot_aggregates_id")},
            inverseJoinColumns = {@JoinColumn(name = "child_lot_aggregates_id")}
    )
    private Set<LotConsumedAggregate> childLotAggregates = new HashSet<>();
    private String sourceDistrictGeoId;
    private String sourceStateGeoId;
    private String targetDistrictGeoId;
    private String targetStateGeoId;
    private Double quantity;

    @Enumerated(EnumType.STRING)
    private LotConsumedType lotConsumedType;
}
