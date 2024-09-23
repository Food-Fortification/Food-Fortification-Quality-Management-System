package com.beehyv.lab.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LabTestDocumentResponseDTO extends BaseResponseDTO {
    private Long id;
    private CategoryDocumentRequirementResponseDTO categoryDocumentRequirement;
    private LabTestResponseDTO labTest;
    private String name;
    private String path;
}
