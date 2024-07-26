package com.beehyv.fortification.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransportQuantityDetailsRequestDto extends BaseRequestDto {
    private Long lotId;
    private String purchaseOrderId;
    private String destinationId;
    private Long totalNoOfBags;
    private Double dispatchedQuantity;
    private Double grossWeight;
    private Double tareWeight;
    private Double netWeight;
    private List<TransportVehicleDetailsRequestDto> vehicleDetailsRequestDtos;
}
