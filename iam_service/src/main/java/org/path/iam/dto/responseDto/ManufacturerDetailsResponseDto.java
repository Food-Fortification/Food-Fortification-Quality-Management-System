package org.path.iam.dto.responseDto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerDetailsResponseDto {
    private Long id;
    private String name;
    private String type;
    private String accreditedByAgency;
    private String CompleteAddress;
    private String licenseNumber;
    private String vendorType;
}
