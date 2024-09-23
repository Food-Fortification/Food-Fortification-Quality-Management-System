package com.beehyv.lab.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SampleRequirementsResponseDTO {

    private Long categoryId;
    private List<LabTestReferenceMethodResponseDTO> labTestReferenceMethods;
    private List<CategoryDocumentRequirementResponseDTO> categoryDocumentRequirements;
}

