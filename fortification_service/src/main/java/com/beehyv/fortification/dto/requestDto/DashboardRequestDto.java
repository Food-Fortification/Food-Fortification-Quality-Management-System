package com.beehyv.fortification.dto.requestDto;

import com.beehyv.fortification.enums.GeoAggregationType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DashboardRequestDto extends BaseRequestDto{
    @JsonFormat(timezone = "IST", pattern = "yyyy-MM-dd")
    private Date fromDate;
    @JsonFormat(timezone = "IST", pattern = "yyyy-MM-dd")
    private Date toDate;

    private Long categoryId;
    private GeoAggregationType type;
    private String geoId;
    private String sourceStateGeoId;
    private String targetStateGeoId;
    private String sourceDistrictGeoId;
    private String targetDistrictGeoId;
    private String sourceCountryGeoId;
    private String targetCountryGeoId;
    private String empanelledStateGeoId;
}
