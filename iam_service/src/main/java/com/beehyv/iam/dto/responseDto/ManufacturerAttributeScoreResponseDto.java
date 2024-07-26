package com.beehyv.iam.dto.responseDto;

import com.beehyv.iam.enums.ComplianceType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturerAttributeScoreResponseDto extends BaseResponseDto{
    private Long id;
    private ComplianceType compliance;
    private String value;
    @JsonIgnoreProperties(value = {"attributeCategory"})
    private AttributeResponseDto attribute;

    @JsonIgnoreProperties(value = {"manufacturerAttributeScores"})
    private AttributeCategoryScoreResponseDto attributeCategoryScore;
}
