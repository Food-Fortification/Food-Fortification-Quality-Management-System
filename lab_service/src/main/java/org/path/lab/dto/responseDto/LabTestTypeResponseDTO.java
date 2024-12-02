package org.path.lab.dto.responseDto;

import org.path.lab.entity.LabTestType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class LabTestTypeResponseDTO extends BaseResponseDTO {
    private Long id;
    private String name;
    private Long categoryId;
    private Boolean isMandatory;
    private LabTestType.Type type;
    @JsonIgnoreProperties(value = {"labTestType"})
    private List<LabTestReferenceMethodResponseDTO> labTestReferenceMethods;
}
