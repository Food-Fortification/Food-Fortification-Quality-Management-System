package com.beehyv.iam.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDto extends BaseResponseDto{
    private Long id;
    private String laneOne;
    private String laneTwo;
    private String pinCode;
    private VillageResponseDto village;
    private Double latitude;
    private Double longitude;
    private ManufacturerDetailsResponseDto manufacturerDetails;
}
