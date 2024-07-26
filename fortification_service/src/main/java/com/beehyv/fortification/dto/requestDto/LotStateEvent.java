package com.beehyv.fortification.dto.requestDto;


import com.beehyv.fortification.enums.SampleTestResult;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LotStateEvent {
    private Long categoryId;
    private Long targetManufacturerId;
    private Long batchManufacturerId;
    private Double quantity;
    private String state;
    @JsonFormat(timezone = "IST", pattern = "yyyy-MM-dd")
    private Date date;
    private SampleTestResult testResult;
    private String districtGeoId;
    private String stateGeoId;
    private String countryGeoId;
    private Boolean isBatchTested;
    private Long lotId;
}
