package com.beehyv.fortification.dto.responseDto;

import com.beehyv.fortification.entity.Category;
import com.beehyv.fortification.entity.RoleCategoryType;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleCategoryResponseDto extends BaseResponseDto {
    private Long id;
    private String roleName;
    @JsonIncludeProperties(value = {"uuid", "id", "name"})
    private CategoryResponseDto category;
    private RoleCategoryType roleCategoryType;
    private Set<StateResponseDto> states;
}
