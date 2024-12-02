package org.path.lab.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class LabTestReferenceMethodResponseDTO extends BaseResponseDTO {
    private Long id;
    private String name;
    @JsonIgnoreProperties(value = {"labTestReferenceMethods"})
    private LabTestTypeResponseDTO labTestType;
    private Double minValue;
    private Double maxValue;
    private String uom;
    private String defaultPresent;
    private String referenceValue;
}
