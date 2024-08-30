package com.beehyv.fortification.dto.responseDto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NewDashboardProductionResponseDto extends BaseResponseDto{
    private Double totalProduction;
    private Double approvedQuantity;
    private Double availableTested;
    private Double availableNotTested;
    private Double totalDispatched;
    private Double lotApproved;
    private Double lotRejected;
    private Double lotInTransit;
    private Double usedQuantity;
    private Long noOfBatches;
    private Long categoryId;
    private String categoryName;
    private String id;
}
