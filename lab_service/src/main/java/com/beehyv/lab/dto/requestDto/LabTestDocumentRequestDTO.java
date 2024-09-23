package com.beehyv.lab.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LabTestDocumentRequestDTO {
    private Long id;
    private Long categoryDocumentRequirementId;
    private Long labTestId;
    private String name;
    private String path;
}
