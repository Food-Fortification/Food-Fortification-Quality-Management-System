package com.beehyv.lab.dto.requestDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class SampleGeoStateEvent {
    private Long categoryId;
    private Long labId;
    private Long manufacturerId;

    private String districtGeoId;
    private String stateGeoId;
    private String countryGeoId;
    private String pincode;

    private String state;
    private String action;
    private Integer sampleSentYear;
}
