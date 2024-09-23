package com.beehyv.fortification.dto.iam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VillageResponseDto {
    private Long id;
    private String name;
    private DistrictResponseDto district;
}
