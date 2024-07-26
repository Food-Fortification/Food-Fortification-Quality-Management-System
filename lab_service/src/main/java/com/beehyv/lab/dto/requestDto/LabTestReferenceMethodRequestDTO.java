package com.beehyv.lab.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LabTestReferenceMethodRequestDTO {
    private Long id;
    private String name;
    private Long labTestTypeId;
    private Double minValue;
    private Double maxValue;
    private String uom;
    private String defaultPresent;
    private String referenceValue;
}
