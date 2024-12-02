package org.path.lab.dto.responseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SampleStateCountResponseDto {
    private String id;
    private Long inTransitCount;
    private Long underTestingCount;
    private Long testedCount;
    private Long rejectedCount;
}
