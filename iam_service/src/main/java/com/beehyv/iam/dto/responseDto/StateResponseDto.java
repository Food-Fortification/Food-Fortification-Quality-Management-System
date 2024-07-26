package com.beehyv.iam.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StateResponseDto extends BaseResponseDto{
    private Long id;
    private String name;
    private String code;
    private String geoId;
    private LocationResponseDto country;
}
