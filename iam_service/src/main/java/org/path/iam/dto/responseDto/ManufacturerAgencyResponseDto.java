package org.path.iam.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ManufacturerAgencyResponseDto extends BaseResponseDto{
    private Long id;
    private String agencyName;
    private String districtGeoId;
    private String districtName;
    private String stateName;
    private String licenseNumber;
    private String stateGeoId;
}
