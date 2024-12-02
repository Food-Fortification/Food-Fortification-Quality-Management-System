package org.path.fortification.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductionResponseDto {
    private String id;
    private Double producedQuantity;
    private Double inProductionQuantity;
    private Long categoryId;
}
