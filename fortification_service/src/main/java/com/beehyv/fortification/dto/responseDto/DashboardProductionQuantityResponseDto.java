package com.beehyv.fortification.dto.responseDto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DashboardProductionQuantityResponseDto extends BaseResponseDto{
    private Double data;
    private String manufacturerName;
    private Long manufacturerId;
    private String districtGeoId;
    private String stateGeoId;
    private String districtName;
    private String stateName;
    private String licenseNo;
}
