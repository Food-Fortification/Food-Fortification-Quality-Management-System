package org.path.fortification.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MixMappingMonitorResponseDto extends BaseResponseDto {
    private Long id;
    private LotMonitorResponseDto sourceLot;
    private Double quantityUsed;
    @JsonIncludeProperties(value = {"uuid", "id", "name", "conversionFactorKg"})
    private UOMResponseDto uom;
}
