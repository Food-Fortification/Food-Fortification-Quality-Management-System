package org.path.lab.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SampleTestDocumentResponseDTO extends BaseResponseDTO {
    private Long id;
    private CategoryDocumentRequirementResponseDTO categoryDoc;
    private Long labSampleId;
    private String name;
    private String path;
}
