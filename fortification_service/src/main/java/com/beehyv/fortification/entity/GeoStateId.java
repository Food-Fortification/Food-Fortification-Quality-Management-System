package com.beehyv.fortification.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.util.Date;

@ToString
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class GeoStateId implements Serializable {
    private Long categoryId;
    private Long manufacturerId;
    @PastOrPresent
    @JsonFormat(timezone = "IST", pattern = "yyyy-MM-dd")
    private Date producedDate;
}
