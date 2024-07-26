package com.beehyv.fortification.dto.external;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class LotDispatchExternalDto {
    private String lotId;
    private String movementOrderId;
    private String mo_dest_transaction_id;
    private String Supplier_id;
    private String districtId;
    private String vehicleNo;
    private String driverName;
    private String driverLicense;
    private String driverContactNo;
    private String driverUid;
    private Long totBags;
    private Double totTruckQuantity;
    private String destinationId;
    private Long noOfBags;
    private Double dispatchedQuantity;
    private Double grossWeight;
    private Double tareWeight;
    private String truckGenDate;
    private Double netWeight;
}
