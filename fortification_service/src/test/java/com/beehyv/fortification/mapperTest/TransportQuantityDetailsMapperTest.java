package com.beehyv.fortification.mapperTest;

import com.beehyv.fortification.dto.requestDto.TransportQuantityDetailsRequestDto;
import com.beehyv.fortification.dto.requestDto.TransportVehicleDetailsRequestDto;
import com.beehyv.fortification.dto.responseDto.TransportQuantityDetailsResponseDto;
import com.beehyv.fortification.entity.Lot;
import com.beehyv.fortification.entity.TransportQuantityDetails;
import com.beehyv.fortification.entity.TransportVehicleDetails;
import com.beehyv.fortification.mapper.TransportQuantityDetailsMapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransportQuantityDetailsMapperTest {

    @Test
    void testAllMethods() {
        // Arrange
        TransportQuantityDetailsRequestDto dto = new TransportQuantityDetailsRequestDto();
        dto.setGrossWeight(100.0);
        dto.setNetWeight(90.0);
        dto.setTareWeight(10.0);
        dto.setTotalNoOfBags(5L);
        dto.setPurchaseOrderId("1L");
        dto.setDestinationId("1L");
        TransportVehicleDetailsRequestDto vehicleDto = new TransportVehicleDetailsRequestDto();
        vehicleDto.setVehicleNo("TestVehicle");
        dto.setVehicleDetailsRequestDtos(List.of(vehicleDto));

        Lot lot = new Lot();
        lot.setId(1L);

        TransportQuantityDetails entity = new TransportQuantityDetails();
        entity.setGrossWeight(dto.getGrossWeight());
        entity.setNetWeight(dto.getNetWeight());
        entity.setTareWeight(dto.getTareWeight());
        entity.setTotalNoOfBags(dto.getTotalNoOfBags());
        entity.setPurchaseOrderId(dto.getPurchaseOrderId());
        entity.setDestinationId(dto.getDestinationId());
        TransportVehicleDetails vehicleEntity = new TransportVehicleDetails();
        vehicleEntity.setVehicleNo(vehicleDto.getVehicleNo());
        entity.setTransportVehicleDetailsSet(new HashSet<>(Collections.singletonList(vehicleEntity)));
        entity.setLot(lot);

        TransportQuantityDetailsMapper mapper = new TransportQuantityDetailsMapper();

        // Act
        TransportQuantityDetails mappedEntity = mapper.toEntity(dto);
        TransportQuantityDetailsResponseDto mappedDto = mapper.toDto(entity);

        // Assert
        assertEquals(dto.getGrossWeight(), mappedEntity.getGrossWeight());
        assertEquals(dto.getNetWeight(), mappedEntity.getNetWeight());
        assertEquals(dto.getTareWeight(), mappedEntity.getTareWeight());
        assertEquals(dto.getTotalNoOfBags(), mappedEntity.getTotalNoOfBags());
        assertEquals(dto.getPurchaseOrderId(), mappedEntity.getPurchaseOrderId());
        assertEquals(dto.getDestinationId(), mappedEntity.getDestinationId());
        assertEquals(dto.getVehicleDetailsRequestDtos().iterator().next().getVehicleNo(), mappedEntity.getTransportVehicleDetailsSet().iterator().next().getVehicleNo());

        assertEquals(entity.getGrossWeight(), mappedDto.getGrossWeight());
        assertEquals(entity.getNetWeight(), mappedDto.getNetWeight());
        assertEquals(entity.getTareWeight(), mappedDto.getTareWeight());
        assertEquals(entity.getTotalNoOfBags(), mappedDto.getTotalNoOfBags());
        assertEquals(entity.getPurchaseOrderId(), mappedDto.getPurchaseOrderId());
        assertEquals(entity.getTransportVehicleDetailsSet().iterator().next().getVehicleNo(), mappedDto.getVehicleDetailsResponseDtos().iterator().next().getVehicleNo());
    }
}