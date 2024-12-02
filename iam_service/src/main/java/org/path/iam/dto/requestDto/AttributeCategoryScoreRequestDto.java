package org.path.iam.dto.requestDto;


import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AttributeCategoryScoreRequestDto extends BaseRequestDto{
    private Long id;
    private Long AttributeCategoryId;
    private Set<ManufacturerAttributeScoreRequestDto> manufacturerAttributeScores;
    private Long manufacturerCategoryAttributesId;
}
