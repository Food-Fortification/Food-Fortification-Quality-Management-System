package org.path.fortification.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransportVehicleDetailsRequestDto extends BaseRequestDto {
    private Long id;
    private Long lotId;
    private String childPurchaseOrderId;
    private String vehicleNo;
    private String driverName;
    private String driverLicense;
    private String driverContactNo;
    private String driverUid;
    private Long totalBags;
    private Double totalTruckQuantity;
}
