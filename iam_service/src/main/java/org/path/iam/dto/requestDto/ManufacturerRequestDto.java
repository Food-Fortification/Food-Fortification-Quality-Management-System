package org.path.iam.dto.requestDto;

import org.path.iam.enums.ManufacturerType;
import org.path.iam.enums.VendorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerRequestDto extends BaseRequestDto{
    private Long id;
    private String name;
    private String type;
    private Boolean accreditedByAgency;
    private VendorType vendorType;
    private String agencyName;
    private String licenseNumber;
    private String externalManufacturerId;
    private Set<Long> targetManufacturers;
    private Set<ManufacturerDocsRequestDto> manufacturerDocs;
    private AddressRequestDto address;
    private Set<ManufacturerCategoryRequestDto> manufacturerCategory;
    private ManufacturerType manufacturerType;
    private Boolean isRawMaterialsProcured;
    private Set<ManufacturerPropertyRequestDto> manufacturerProperties;
}
