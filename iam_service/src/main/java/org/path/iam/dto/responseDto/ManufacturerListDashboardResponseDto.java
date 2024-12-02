package org.path.iam.dto.responseDto;

import org.path.iam.enums.VendorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerListDashboardResponseDto {
    private Long id;
    private String name;
    private String completeAddress;
    private String type;
    private Boolean accreditedByAgency;
    private VendorType vendorType;
    private String agencyName;
    private String licenseNumber;
    private String laneOne;
    private String laneTwo;
    private String villageName;
    private String districtName;
    private String districtGeoId;
    private String stateName;
    private String countryName;
    private String pinCode;
    private String latitude;
    private String longitude;
}
