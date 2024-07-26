package com.beehyv.fortification.dtoTests.external;


import com.beehyv.fortification.dto.external.LotDispatchExternalDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LotDispatchExternalDtoTest {

    private LotDispatchExternalDto lotDispatchExternalDto;

    @BeforeEach
    public void setUp() {
        lotDispatchExternalDto = new LotDispatchExternalDto();
    }

    @Test
    public void testAllFields() {
        String lotId = "lotId";
        String movementOrderId = "movementOrderId";
        String mo_dest_transaction_id = "mo_dest_transaction_id";
        String Supplier_id = "Supplier_id";
        String districtId = "districtId";
        String vehicleNo = "vehicleNo";
        String driverName = "driverName";
        String driverLicense = "driverLicense";
        String driverContactNo = "driverContactNo";
        String driverUid = "driverUid";
        Long totBags = 10L;
        Double totTruckQuantity = 100.0;
        String destinationId = "destinationId";
        Long noOfBags = 5L;
        Double dispatchedQuantity = 50.0;
        Double grossWeight = 200.0;
        Double tareWeight = 100.0;
        String truckGenDate = "2022-01-01";
        Double netWeight = 100.0;

        lotDispatchExternalDto.setLotId(lotId);
        lotDispatchExternalDto.setMovementOrderId(movementOrderId);
        lotDispatchExternalDto.setMo_dest_transaction_id(mo_dest_transaction_id);
        lotDispatchExternalDto.setSupplier_id(Supplier_id);
        lotDispatchExternalDto.setDistrictId(districtId);
        lotDispatchExternalDto.setVehicleNo(vehicleNo);
        lotDispatchExternalDto.setDriverName(driverName);
        lotDispatchExternalDto.setDriverLicense(driverLicense);
        lotDispatchExternalDto.setDriverContactNo(driverContactNo);
        lotDispatchExternalDto.setDriverUid(driverUid);
        lotDispatchExternalDto.setTotBags(totBags);
        lotDispatchExternalDto.setTotTruckQuantity(totTruckQuantity);
        lotDispatchExternalDto.setDestinationId(destinationId);
        lotDispatchExternalDto.setNoOfBags(noOfBags);
        lotDispatchExternalDto.setDispatchedQuantity(dispatchedQuantity);
        lotDispatchExternalDto.setGrossWeight(grossWeight);
        lotDispatchExternalDto.setTareWeight(tareWeight);
        lotDispatchExternalDto.setTruckGenDate(truckGenDate);
        lotDispatchExternalDto.setNetWeight(netWeight);

        assertEquals(lotId, lotDispatchExternalDto.getLotId());
        assertEquals(movementOrderId, lotDispatchExternalDto.getMovementOrderId());
        assertEquals(mo_dest_transaction_id, lotDispatchExternalDto.getMo_dest_transaction_id());
        assertEquals(Supplier_id, lotDispatchExternalDto.getSupplier_id());
        assertEquals(districtId, lotDispatchExternalDto.getDistrictId());
        assertEquals(vehicleNo, lotDispatchExternalDto.getVehicleNo());
        assertEquals(driverName, lotDispatchExternalDto.getDriverName());
        assertEquals(driverLicense, lotDispatchExternalDto.getDriverLicense());
        assertEquals(driverContactNo, lotDispatchExternalDto.getDriverContactNo());
        assertEquals(driverUid, lotDispatchExternalDto.getDriverUid());
        assertEquals(totBags, lotDispatchExternalDto.getTotBags());
        assertEquals(totTruckQuantity, lotDispatchExternalDto.getTotTruckQuantity());
        assertEquals(destinationId, lotDispatchExternalDto.getDestinationId());
        assertEquals(noOfBags, lotDispatchExternalDto.getNoOfBags());
        assertEquals(dispatchedQuantity, lotDispatchExternalDto.getDispatchedQuantity());
        assertEquals(grossWeight, lotDispatchExternalDto.getGrossWeight());
        assertEquals(tareWeight, lotDispatchExternalDto.getTareWeight());
        assertEquals(truckGenDate, lotDispatchExternalDto.getTruckGenDate());
        assertEquals(netWeight, lotDispatchExternalDto.getNetWeight());
    }
}