package com.beehyv.broadcast.dto.requestDto;

import com.beehyv.broadcast.enums.EntityType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EventUpdate {

    private EntityType entity;
    private Long entityId;
    private String stateName;
    private Integer stateGeoId;
}
