package com.beehyv.fortification.dto.responseDto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardQuantityAgencyResponseDto extends BaseResponseDto{
    private String agencyName = "";
    private Long manufacturerId;
    private Double totalProduction = 0D;
    private Double batchTestApproved = 0D;
    private Double batchTestRejected = 0D;
    private Double notTestedQuantity = 0D;
    private Double lotApprovedQuantity = 0D;
    private Double lotRejectedQuantity = 0D;
    private Double lotApprovalPending = 0D;
    private Double availableTested = 0D;
    private String districtName;
    private String stateName;
    private String licenseNumber;
}
