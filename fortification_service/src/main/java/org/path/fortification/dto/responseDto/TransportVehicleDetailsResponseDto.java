package org.path.fortification.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransportVehicleDetailsResponseDto extends BaseResponseDto {
    private Long id;
    private Long lotId;
    private String childPurchaseOrderId;
    private String vehicleNo;
    private String driverName;
    private String driverLicense;
    private String driverContactNo;
    private Long totalBags;
    private Double totalTruckQuantity;
}
