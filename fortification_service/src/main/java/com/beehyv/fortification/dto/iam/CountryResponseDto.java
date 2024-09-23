package com.beehyv.fortification.dto.iam;

import com.beehyv.fortification.dto.responseDto.BaseResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CountryResponseDto extends BaseResponseDto {
    private Long id;
    private String name;
    private String geoId;
}
