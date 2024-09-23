package com.beehyv.fortification.entity;


import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.time.Month;
import java.time.Year;

@ToString
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class BatchNoId implements Serializable {
    private Long categoryId;
    private Long manufacturerId;
    private String dateCode;
}
