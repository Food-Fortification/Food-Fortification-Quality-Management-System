package com.beehyv.lab.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDTO {
    private Long id;
    private String laneOne;
    private String laneTwo;
    private String pinCode;
    private VillageResponseDTO village;
    private Double latitude;
    private Double longitude;
}
