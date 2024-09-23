package com.beehyv.fortification.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardQuantityWarehouseResponseDto extends BaseResponseDto{
    private String agencyName = "";
    private Long manufacturerId;
    private Double lotApprovedQuantity = 0D;
    private Double lotRejectedQuantity = 0D;
    private Double lotApprovalPending = 0D;
    private Double totalAvailable = 0D;
    private Double totalDispatched = 0D;
}
