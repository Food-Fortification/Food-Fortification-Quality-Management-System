package com.beehyv.lab.dto.responseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AggregatedSampleStateGeoCountResponseDto extends AggregatedResponseDto<SampleStateCountResponseDto> {
    private Long inTransitCount;
    private Long underTestingCount;
    private Long testedCount;
    private Long rejectedCount;
}
