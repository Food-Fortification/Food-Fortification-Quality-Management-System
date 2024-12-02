package org.path.lab.dto.requestDto;

import org.path.lab.entity.LabTestType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LabTestTypeRequestDTO {
    private Long id;
    private String name;
    private Long categoryId;
    private Boolean isMandatory;
    private LabTestType.Type type;
    private List<LabTestReferenceMethodRequestDTO> labTestReferenceMethods;
}
