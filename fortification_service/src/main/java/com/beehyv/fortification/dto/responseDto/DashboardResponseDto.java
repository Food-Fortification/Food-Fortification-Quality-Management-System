package com.beehyv.fortification.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponseDto extends BaseResponseDto{
    private String agencyName = "";
    private Long manufacturerId = 0L;
    private String districtName = "";
    private String districtGeoId = "";
    private Double totalProduction = 0D;
    private Double batchTestApproved = 0D;
    private Double batchTestRejected = 0D;
    private Double notTestedQuantity = 0D;
    private Double totalDemand = 0D;
    private Double totalSupply = 0D;
    private Double lotApprovedQuantity = 0D;
    private Double lotRejectedQuantity = 0D;
    private Double lotApprovalPending = 0D;
    private Double totalAvailable = 0D;
}
