package org.path.fortification.dto.responseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManufacturerTestingQuantityResponseDto extends ManufacturerListDashboardResponseDto {
    private Double approvedQuantity;
    private Double rejectedQuantity;
    private Double underTestingQuantity;
    private Double testedQuantity;
}
