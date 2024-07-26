package com.beehyv.fortification.dto.requestDto;

import com.beehyv.fortification.enums.EntityType;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EventUpdate {
    private EntityType entity;
    private Long entityId;
    private String stateName;
    private Integer stateGeoId;
}
