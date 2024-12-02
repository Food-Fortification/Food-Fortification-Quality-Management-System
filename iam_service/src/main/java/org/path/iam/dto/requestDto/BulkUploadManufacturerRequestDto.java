package org.path.iam.dto.requestDto;


import org.path.iam.dto.responseDto.AddressResponseDto;
import org.path.iam.enums.ManufacturerType;
import org.path.iam.enums.VendorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BulkUploadManufacturerRequestDto {
    private String name;
    private String licenseNumber;
    private String type;
    private String accreditedByAgency;
    private VendorType vendorType;
    private String agencyName;
    private String premix;
    private String frk;
    private String miller;
    private ManufacturerType manufacturerType;
    private String laneOne;
    private String laneTwo;
    private String pinCode;
    private Double latitude;
    private Double longitude;
    private String villageName;
    private String code;
    private String geoId;
    private String districtName;
    private String stateName;
    private String countryName;
    private AddressResponseDto address;
}
