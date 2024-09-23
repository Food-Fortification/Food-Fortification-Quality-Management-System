package com.beehyv.fortification.dto.responseDto;

import com.beehyv.fortification.enums.VendorType;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerResponseDto extends BaseResponseDto{
    private Long id;
    private String name;
    private String completeAddress;
    private String type;
    private Boolean accreditedByAgency;
    private VendorType vendorType;
    private String agencyName;
    private String licenseNumber;
    private Double totalScore;
    private String externalManufacturerId;
    @JsonIncludeProperties(value = {"id","name","manufacturerType","manufacturerCategory"})
    private Set<ManufacturerResponseDto> targetManufacturers;
}