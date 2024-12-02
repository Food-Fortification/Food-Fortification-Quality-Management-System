package org.path.iam.dto.responseDto;

import org.path.iam.enums.ManufacturerType;
import org.path.iam.enums.VendorType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private Set<ManufacturerDocsResponseDto> manufacturerDocs;
    @JsonIncludeProperties(value = {"id","name","manufacturerType","manufacturerCategory"})
    private Set<ManufacturerResponseDto> targetManufacturers;
    private AddressResponseDto address;
    private ManufacturerType manufacturerType;
    @JsonIgnoreProperties(value = {"manufacturerDto"})
    private Set<ManufacturerCategoryResponseDto> manufacturerCategory;

    @JsonIgnoreProperties(value = {"manufacturer"})
    private Set<ManufacturerCategoryAttributesResponseDto> manufacturerAttributes;

    private Set<ManufacturerPropertyResponseDto> manufacturerProperties;
}
