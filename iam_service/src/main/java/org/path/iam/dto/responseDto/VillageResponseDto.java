package org.path.iam.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VillageResponseDto extends BaseResponseDto{
    private Long id;
    private String name;
    private String code;
    private String geoId;
    private DistrictResponseDto district;

}
