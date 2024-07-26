package com.beehyv.fortification.mapper;

import com.beehyv.fortification.dto.requestDto.TransportVehicleDetailsRequestDto;
import com.beehyv.fortification.dto.responseDto.TransportVehicleDetailsResponseDto;
import com.beehyv.fortification.entity.TransportVehicleDetails;

public class TransportVehicleDetailsMapper implements Mappable<TransportVehicleDetailsResponseDto, TransportVehicleDetailsRequestDto, TransportVehicleDetails>{
    @Override
    public TransportVehicleDetailsResponseDto toDto(TransportVehicleDetails entity) {
        TransportVehicleDetailsResponseDto dto = new TransportVehicleDetailsResponseDto();
        dto.setId(entity.getId());
        dto.setDriverContactNo(entity.getDriverContactNo());
        dto.setDriverLicense(entity.getDriverLicense());
        dto.setDriverName(entity.getDriverName());
        dto.setVehicleNo(entity.getVehicleNo());
        dto.setTotalBags(entity.getTotalBags());
        dto.setTotalTruckQuantity(entity.getTotalTruckQuantity());
        dto.setChildPurchaseOrderId(entity.getChildPurchaseId());
        return dto;
    }

    @Override
    public TransportVehicleDetails toEntity(TransportVehicleDetailsRequestDto dto) {
        TransportVehicleDetails transportVehicleDetails = new TransportVehicleDetails();
        transportVehicleDetails.setDriverName(dto.getDriverName());
        transportVehicleDetails.setDriverLicense(dto.getDriverLicense());
        transportVehicleDetails.setDriverContactNo(dto.getDriverContactNo());
        transportVehicleDetails.setTotalBags(dto.getTotalBags());
        transportVehicleDetails.setVehicleNo(dto.getVehicleNo());
        transportVehicleDetails.setTotalTruckQuantity(dto.getTotalTruckQuantity());
        transportVehicleDetails.setChildPurchaseId(dto.getChildPurchaseOrderId());
        return transportVehicleDetails;
    }
}
