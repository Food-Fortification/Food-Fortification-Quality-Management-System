package org.path.broadcast.dto.fortificationResponseDto;

import org.path.broadcast.dto.commonDtos.BaseResponseDto;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MixMappingResponseDto extends BaseResponseDto {
    private Long id;
    @JsonIncludeProperties(value = {"uuid", "id", "name"})
    private CategoryResponseDto sourceCategory;
    private LotListResponseDTO sourceLot;
    private BatchListResponseDTO targetBatch;
    private Double quantityUsed;
    @JsonIncludeProperties(value = {"uuid", "id", "name", "conversionFactorKg"})
    private UOMResponseDto uom;
}
