package org.path.fortification.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerDispatchableQuantityResponseDto extends BaseResponseDto{
    private Long id;
    private Double demand;
    private Double supply;
    private String districtGeoId;
}
