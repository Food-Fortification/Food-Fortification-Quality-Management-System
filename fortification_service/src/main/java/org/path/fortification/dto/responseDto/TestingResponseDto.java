package org.path.fortification.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestingResponseDto {
    private String id;
    private Double totalTestedQuantity;
    private Double underTestingQuantity;
    private Double batchTestApprovedQuantity;
    private Double batchTestRejectedQuantity;
    private Double approvedQuantity;
    private Double rejectedQuantity;
    private Long categoryId;
    private Double lotTestApprovedQuantity;
    private Double lotTestRejectedQuantity;
    private Double totalLotTestedQuantity;
}
