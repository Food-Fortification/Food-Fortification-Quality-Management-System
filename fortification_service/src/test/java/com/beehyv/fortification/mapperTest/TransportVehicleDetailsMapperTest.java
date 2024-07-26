package com.beehyv.fortification.mapperTest;

import com.beehyv.fortification.dto.requestDto.TransportVehicleDetailsRequestDto;
import com.beehyv.fortification.dto.responseDto.TransportVehicleDetailsResponseDto;
import com.beehyv.fortification.entity.TransportVehicleDetails;
import com.beehyv.fortification.mapper.TransportVehicleDetailsMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransportVehicleDetailsMapperTest {

    @Test
    void testAllMethods() {
        // Arrange
        TransportVehicleDetailsRequestDto dto = new TransportVehicleDetailsRequestDto();
        dto.setDriverName("testDriver");
        dto.setDriverLicense("testLicense");
        dto.setDriverContactNo("testContact");
        dto.setTotalBags(10L);
        dto.setVehicleNo("testVehicle");
        dto.setTotalTruckQuantity(100.0);
        dto.setChildPurchaseOrderId("1L");

        TransportVehicleDetails entity = new TransportVehicleDetails();
        entity.setDriverName(dto.getDriverName());
        entity.setDriverLicense(dto.getDriverLicense());
        entity.setDriverContactNo(dto.getDriverContactNo());
        entity.setTotalBags(dto.getTotalBags());
        entity.setVehicleNo(dto.getVehicleNo());
        entity.setTotalTruckQuantity(dto.getTotalTruckQuantity());
        entity.setChildPurchaseId(dto.getChildPurchaseOrderId());

        TransportVehicleDetailsMapper mapper = new TransportVehicleDetailsMapper();

        // Act
        TransportVehicleDetails mappedEntity = mapper.toEntity(dto);
        TransportVehicleDetailsResponseDto mappedDto = mapper.toDto(entity);

        // Assert
        assertEquals(dto.getDriverName(), mappedEntity.getDriverName());
        assertEquals(dto.getDriverLicense(), mappedEntity.getDriverLicense());
        assertEquals(dto.getDriverContactNo(), mappedEntity.getDriverContactNo());
        assertEquals(dto.getTotalBags(), mappedEntity.getTotalBags());
        assertEquals(dto.getVehicleNo(), mappedEntity.getVehicleNo());
        assertEquals(dto.getTotalTruckQuantity(), mappedEntity.getTotalTruckQuantity());
        assertEquals(dto.getChildPurchaseOrderId(), mappedEntity.getChildPurchaseId());

        assertEquals(entity.getDriverName(), mappedDto.getDriverName());
        assertEquals(entity.getDriverLicense(), mappedDto.getDriverLicense());
        assertEquals(entity.getDriverContactNo(), mappedDto.getDriverContactNo());
        assertEquals(entity.getTotalBags(), mappedDto.getTotalBags());
        assertEquals(entity.getVehicleNo(), mappedDto.getVehicleNo());
        assertEquals(entity.getTotalTruckQuantity(), mappedDto.getTotalTruckQuantity());
        assertEquals(entity.getChildPurchaseId(), mappedDto.getChildPurchaseOrderId());
    }
}