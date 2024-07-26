package com.beehyv.fortification.dto.requestDto;

import com.beehyv.fortification.enums.DashboardExcelReportType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuantityAggregatesExcelRequestDto extends BaseRequestDto{
    private DashboardRequestDto dashboardRequestDto;
    private String districtGeoId;
    private String stateGeoId;
    private Long sourceManufacturerId;
    private Long targetManufacturerId;
    private DashboardExcelReportType reportType;
}
