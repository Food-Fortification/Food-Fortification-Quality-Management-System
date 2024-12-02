package org.path.lab.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationRequestDTO {
    private String laneOne;
    private String laneTwo;
    private String country;
    private String state;
    private String district;
    private String village;
    private String pinCode;
    private Double latitude;
    private Double longitude;
}
