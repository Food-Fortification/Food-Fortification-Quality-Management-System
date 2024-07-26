package com.beehyv.iam.dto.external;

import com.beehyv.iam.enums.UserCategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FssaiLicenseResponseDto {

    @JsonProperty("Premix")
    private UserCategory Premix;

    @JsonProperty("Miller")
    private UserCategory Miller;

    @JsonProperty("FRK")
    private UserCategory FRK;

    private String licenseNo;
    private String districtName;
    private String fboId;
    private String talukName;
    private String displayrefid;
    private String companyName;
    private String villageName;
    private String statusdesc;
    private String appTypeDesc;
    private String stateName;
    private String licenseCategoryName;
    private String premisePincode;
    private String premiseAddress;
    private String refid;
    private Long licenseCategoryId;
    private Boolean licenseActiveFlag;
}
