package org.path.fortification.dto.requestDto;

import org.path.fortification.enums.EntityType;
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
