package com.beehyv.fortification.dto.external;

import lombok.*;

import javax.validation.constraints.PositiveOrZero;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MixMappingExternalRequestDto {
    private String sourceLotNo;
    @PositiveOrZero(message = "Quantity must be positive")
    private Double quantityUsed;
}
