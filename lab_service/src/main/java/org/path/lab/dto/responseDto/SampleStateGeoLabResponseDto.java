package org.path.lab.dto.responseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SampleStateGeoLabResponseDto {
    private Long labId;
    private String labName;
    private String labAddress;

    private Long inTransitCount;
    private Long underTestingCount;
    private Long testedCount;
    private Long rejectedCount;
}
