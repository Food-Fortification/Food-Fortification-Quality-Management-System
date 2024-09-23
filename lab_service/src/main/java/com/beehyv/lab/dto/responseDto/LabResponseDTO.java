package com.beehyv.lab.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LabResponseDTO extends BaseResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String completeAddress;
    private Set<LabDocumentResponseDTO> labDocs;
    private AddressResponseDTO address;
    @JsonIgnoreProperties(value = {"lab"})
    private Set<LabCategoryResponseDto> labCategories;
    @JsonIgnoreProperties(value = {"lab"})
    private Set<LabManufacturerResponseDTO> labManufacturers;
    @JsonIgnoreProperties(value = {"description"})
    private StatusResponseDto status;
    private String certificateNo;
}
