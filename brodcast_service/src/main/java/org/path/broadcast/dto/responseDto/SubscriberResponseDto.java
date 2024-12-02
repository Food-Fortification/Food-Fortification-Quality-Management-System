package org.path.broadcast.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriberResponseDto {

    private Long id;
    private String name;
    private Integer stateGeoId;
}
