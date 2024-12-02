package org.path.iam.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDocResponseDto extends BaseResponseDto{
    private Long id;

    private Long categoryId;

    private DocTypeResponseDto docType;

    private Boolean isMandatory;
    private Boolean isEnabled;
}
