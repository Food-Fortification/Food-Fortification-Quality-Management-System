package com.beehyv.fortification.dto.requestDto;

import com.beehyv.fortification.entity.RoleCategoryType;
import lombok.*;

import java.util.HashMap;
import java.util.Set;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequestDto {
    private Set<Long> roleCategoryIds;
    private RoleCategoryType roleCategoryType;
}

