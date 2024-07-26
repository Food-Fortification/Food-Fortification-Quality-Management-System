package com.beehyv.broadcast.dto.labResponseDto;

import com.beehyv.broadcast.dto.commonDtos.BaseResponseDto;
import com.beehyv.broadcast.enums.CategoryDocRequirementType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CategoryDocumentRequirementResponseDTO extends BaseResponseDto {
    private Long id;
    private Long categoryId;
    private DocTypeResponseDTO docType;
    private Boolean isMandatory;
    private Boolean isEnabled;
    private CategoryDocRequirementType categoryDocRequirementType;
}
