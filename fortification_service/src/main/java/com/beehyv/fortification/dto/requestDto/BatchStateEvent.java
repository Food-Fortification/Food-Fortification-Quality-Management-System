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
public class BatchStateEvent {
    private Long categoryId;
    private Long manufacturerId;

    private String pincode;
    private String districtGeoId;
    private String stateGeoId;
    private String countryGeoId;

    private Double quantity;
    private String state;
    @JsonFormat(timezone = "IST", pattern = "yyyy-MM-dd")
    private Date date;
    private SampleTestResult testResult;
    private Boolean isBatchTested;
}
