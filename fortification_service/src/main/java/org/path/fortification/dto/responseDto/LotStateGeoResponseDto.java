package org.path.fortification.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LotStateGeoResponseDto extends BaseResponseDto {
    private Long id;

    private CategoryResponseDto category;

    private String pincode;
    private String districtGeoId;
    private String stateGeoId;
    private String countryGeoId;

    private Double totalQuantity;
    private Double inTransitQuantity;
    private Double approvedQuantity;
    private Double rejectedQuantity;
    private Double sentToLabTestQuantity;
    private Double testInProgressQuantity;
    private Double testedQuantity;
}
