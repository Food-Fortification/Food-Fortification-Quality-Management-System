package com.beehyv.iam.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttributeCategoryScoreResponseDto extends BaseResponseDto{
    private Long id;

    @JsonIgnoreProperties(value = {"attributes"})
    private AttributeCategoryResponseDto attributeCategory;

    @JsonIgnoreProperties(value = {"attributeCategoryScore"})
    private Set<ManufacturerAttributeScoreResponseDto> attributeScore;

    @JsonIgnoreProperties(value = {"attributeCategoryScores"})
    private ManufacturerCategoryAttributesResponseDto manufacturerCategoryAttributes;
}
