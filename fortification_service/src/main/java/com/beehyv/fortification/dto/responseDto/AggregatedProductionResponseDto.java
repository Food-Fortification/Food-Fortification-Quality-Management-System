package com.beehyv.fortification.dto.responseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AggregatedProductionResponseDto extends AggregatedResponseDto<ProductionResponseDto> {
    private Double producedQuantity;
    private Double inProductionQuantity;
}
