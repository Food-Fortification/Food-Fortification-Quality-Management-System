package com.beehyv.lab.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LabDocumentResponseDTO extends BaseResponseDTO {
    private Long id;
    private String name;
    private CategoryDocumentRequirementResponseDTO categoryDoc;
    private String path;
}
