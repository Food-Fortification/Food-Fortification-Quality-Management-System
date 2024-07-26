package com.beehyv.lab.dto.requestDto;

import com.beehyv.lab.enums.CategoryDocRequirementType;
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
