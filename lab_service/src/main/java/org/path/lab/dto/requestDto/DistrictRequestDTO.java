package org.path.lab.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DistrictRequestDTO {
    private Long id;
    private String name;
    private Long stateId;
}
