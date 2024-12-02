package org.path.fortification.dto.responseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AggregatedTestingResponseDto extends AggregatedResponseDto<TestingResponseDto> {
    private Double totalTestedQuantity;
    private Double underTestingQuantity;
    private Double approvedQuantity;
    private Double rejectedQuantity;
    private Double batchTestApprovedQuantity;
    private Double batchTestRejectedQuantity;
    private Double lotTestApprovedQuantity;
    private Double lotTestRejectedQuantity;
    private Double totalLotTestedQuantity;
}
