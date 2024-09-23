package com.beehyv.fortification.entity;


import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE batch_no_sequence SET is_deleted = true WHERE batch_no_id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class BatchNoSequence extends Base {
    @EmbeddedId
    private BatchNoId batchNoId;

    @Column(columnDefinition="integer default 1")
    private Integer sequence = 1;
}
