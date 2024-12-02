package org.path.lab.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LabRequestDTO {
    private Long id;
    private String name;
    private String description;
    private Set<LabDocumentRequestDTO> labDocs;
    private Set<LabCategoryRequestDto> labCategories;
    private Set<LabManufacturerRequestDTO> labManufacturers;
    private AddressRequestDTO address;
    private Long statusId;
}
