package com.beehyv.fortification.dto.requestDto;

import lombok.*;

import javax.validation.constraints.PositiveOrZero;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MixMappingRequestDto extends BaseRequestDto {
    private Long id;
    private Long sourceLotId;
    private String sourceLotNo;
    @PositiveOrZero(message = "Quantity must be positive")
    private Double quantityUsed;
    private Long uomId;
}
