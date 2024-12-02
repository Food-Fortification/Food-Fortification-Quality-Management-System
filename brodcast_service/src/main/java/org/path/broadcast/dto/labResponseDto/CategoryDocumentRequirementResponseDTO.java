package org.path.broadcast.dto.labResponseDto;

import org.path.broadcast.dto.commonDtos.BaseResponseDto;
import org.path.broadcast.enums.CategoryDocRequirementType;
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
