package org.path.lab.dto.requestDto;

import org.path.lab.enums.CategoryDocRequirementType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CategoryDocumentRequirementRequestDTO {
        private Long id;
        private Long categoryId;
        private Long docTypeId;
        private Boolean isMandatory;
        private Boolean isEnabled;
        private CategoryDocRequirementType categoryDocRequirementType;
}
