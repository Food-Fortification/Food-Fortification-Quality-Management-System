package com.beehyv.lab.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AddressRequestDTO {
    private Long id;
    private String laneOne;
    private String laneTwo;
    private Long villageId;
    private String pinCode;
    private Double latitude;
    private Double longitude;
}
