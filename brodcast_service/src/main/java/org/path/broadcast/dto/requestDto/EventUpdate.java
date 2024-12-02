package org.path.broadcast.dto.requestDto;

import org.path.broadcast.enums.EntityType;
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
