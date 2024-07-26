package com.beehyv.iam.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturerCategoryAttributesRequestDto extends BaseRequestDto{
    private Long id;
    private Long manufacturerId;
    private Long userId;
    private Set<AttributeCategoryScoreRequestDto> attributeCategoryScores;
}
