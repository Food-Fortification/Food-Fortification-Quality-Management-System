package org.path.fortification.dto.responseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManufacturerProductionQuantityResponseDto extends ManufacturerListDashboardResponseDto {
    private Double inProductionQuantity;
    private Double producedQuantity;
}
