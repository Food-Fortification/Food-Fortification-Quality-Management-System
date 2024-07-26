package com.beehyv.fortification.dto.requestDto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UOMRequestDto extends BaseRequestDto {
    private Long id;
    private String name;
    private Long conversionFactorKg;
}
