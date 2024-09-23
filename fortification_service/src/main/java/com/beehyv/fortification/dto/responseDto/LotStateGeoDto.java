package com.beehyv.fortification.dto.responseDto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class LotStateGeoDto {

    private Long id;

    private Long categoryId;
    private Long manufacturerId;

    private Double inTransitQuantity = 0d;
    private Double receivedQuantity = 0d;
    private Double approvedQuantity = 0d;
    private Double rejectedQuantity = 0d;
    private Double sampleInTransitQuantity = 0d;
    private Double testInProgressQuantity = 0d;
    private Double testedQuantity = 0d; // also self tested
}
