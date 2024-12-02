package org.path.lab.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressLocationResponseDto {
    private String laneOne;
    private String laneTwo;
    private LocationResponseDto country;
    private LocationResponseDto state;
    private LocationResponseDto district;
    private LocationResponseDto village;
    private String pinCode;
    private Double latitude;
    private Double longitude;
}
