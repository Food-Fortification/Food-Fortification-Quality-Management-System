package com.beehyv.iam.dto.requestDto;

import com.beehyv.iam.enums.ManufacturerType;
import com.beehyv.iam.enums.VendorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FssaiManufacturerRequestDto {
    private List<String> categoryNames;
    private String name;
    private String type;
    private Boolean accreditedByAgency;
    private VendorType vendorType;
    private String agencyName;
    @NotNull
    @NotBlank
    private String licenseNumber;
    private ManufacturerType manufacturerType;
    private String laneOne;
    private String laneTwo;
    private String villageName;
    private String pinCode;
    private Double longitude;
    private Double latitude;
    private String districtName;
    private String stateName;
    private String countryName;
    private String userName;
    private String emailAddress;
    private String firstName;
    private String lastName;
}
