package org.path.fortification.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransportQuantityDetailsResponseDto extends BaseResponseDto{
    private Long id;
    private Long lotId;
    private String purchaseOrderId;
    private Long totalNoOfBags;
    private Double dispatchedQuantity;
    private Double grossWeight;
    private Double tareWeight;
    private Double netWeight;
    private List<TransportVehicleDetailsResponseDto> vehicleDetailsResponseDtos;
}
