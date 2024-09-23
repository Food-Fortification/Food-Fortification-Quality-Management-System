package com.beehyv.iam.dto.responseDto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturerCategoryAttributesResponseDto extends BaseResponseDto{

    private Long id;

    @JsonIncludeProperties(value = {"id","name","vendorType","manufacturerType"})
    private ManufacturerResponseDto manufacturer;

    @JsonIncludeProperties(value = {"id","userName","email"})
    private UserResponseDto user;

    @JsonIgnoreProperties(value = {"manufacturerCategoryAttributes"})
    private Set<AttributeCategoryScoreResponseDto> attributeCategoryScores;
}
