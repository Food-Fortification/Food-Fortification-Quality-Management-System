package com.beehyv.lab.dto.responseDto;

import com.beehyv.lab.enums.CategoryDocRequirementType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CategoryDocumentRequirementResponseDTO  extends BaseResponseDTO{
    private Long id;
    private Long categoryId;
    private DocTypeResponseDTO docType;
    private Boolean isMandatory;
    private Boolean isEnabled;
    private CategoryDocRequirementType categoryDocRequirementType;
}
