package com.beehyv.fortification.dto.responseDto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardQuantityDistrictResponseDto extends BaseResponseDto{
    private String agencyName = "";
    private Long manufacturerId;
    private String districtName = "";
    private String stateName = "";
    private String districtGeoId = "";
    private Double lotApprovedQuantity = 0D;
    private Double lotRejectedQuantity = 0D;
    private Double lotApprovalPending = 0D;
    private Double totalAvailable = 0D;
    private Double totalDispatched = 0D;
    private String licenseNumber;
    private String stateGeoId = "";
    private List<DashboardQuantityDistrictResponseDto> agencyList = new ArrayList<>();
}
