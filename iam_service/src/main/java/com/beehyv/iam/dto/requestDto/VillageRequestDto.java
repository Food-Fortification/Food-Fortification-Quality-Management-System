package com.beehyv.iam.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VillageRequestDto extends BaseRequestDto{
    private Long id;
    private String name;
    private Long districtId;
}