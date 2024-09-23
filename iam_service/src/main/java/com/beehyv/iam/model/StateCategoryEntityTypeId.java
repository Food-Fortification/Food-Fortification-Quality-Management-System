package com.beehyv.iam.model;


import com.beehyv.iam.enums.EntityType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@ToString
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class StateCategoryEntityTypeId implements Serializable {
    private Long categoryId;


    @OneToOne
    private State state;
    @Enumerated(value = EnumType.STRING)
    private EntityType entityType;
}

