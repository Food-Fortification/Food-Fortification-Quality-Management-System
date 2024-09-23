package com.beehyv.iam.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressLocationResponseDto {
    private String laneOne;
    private String laneTwo;
    @JsonIncludeProperties(value = {"id","name","code","geoId"})
    private LocationResponseDto country;
    @JsonIncludeProperties(value = {"id","name","code","geoId"})
    private LocationResponseDto state;
    @JsonIncludeProperties(value = {"id","name","code","geoId"})
    private LocationResponseDto district;
    @JsonIncludeProperties(value = {"id","name","code","geoId"})
    private LocationResponseDto village;
    private String pinCode;
    private Double latitude;
    private Double longitude;
}
