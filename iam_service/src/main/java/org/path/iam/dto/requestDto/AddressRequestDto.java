package org.path.iam.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestDto extends BaseRequestDto{
    private Long id;
    private String laneOne;
    private String laneTwo;
    private Long villageId;
    private String pinCode;
    private Long manufacturerId;
    private Double longitude;
    private Double latitude;
    private String villageName;
    private String districtName;
    private String stateName;
    private String countryName;
}
