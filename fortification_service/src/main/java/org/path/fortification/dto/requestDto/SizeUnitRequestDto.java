package org.path.fortification.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.PositiveOrZero;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SizeUnitRequestDto extends BaseRequestDto {
    private Long id;
    private Long lotId;
    private Long batchId;
    private Long uomId;
    @PositiveOrZero(message = "Size must be positive")
    private Long size;
    @PositiveOrZero(message = "Quantity must be positive")
    private Double quantity;
    private Boolean isDispatched;
}
