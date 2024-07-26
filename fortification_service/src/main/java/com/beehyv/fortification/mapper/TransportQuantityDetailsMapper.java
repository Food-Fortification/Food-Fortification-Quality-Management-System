package com.beehyv.fortification.mapper;

import com.beehyv.fortification.dto.requestDto.TransportQuantityDetailsRequestDto;
import com.beehyv.fortification.dto.requestDto.TransportVehicleDetailsRequestDto;
import com.beehyv.fortification.dto.responseDto.TransportQuantityDetailsResponseDto;
import com.beehyv.fortification.dto.responseDto.TransportVehicleDetailsResponseDto;
import com.beehyv.fortification.entity.TransportQuantityDetails;
import com.beehyv.fortification.entity.TransportVehicleDetails;

import java.util.Set;
import java.util.stream.Collectors;

public class TransportQuantityDetailsMapper implements Mappable<TransportQuantityDetailsResponseDto, TransportQuantityDetailsRequestDto, TransportQuantityDetails>{

    private final BaseMapper<TransportVehicleDetailsResponseDto, TransportVehicleDetailsRequestDto, TransportVehicleDetails> transportVehicleDetailsMapper = BaseMapper.getForClass(TransportVehicleDetailsMapper.class);

    @Override
    public TransportQuantityDetailsResponseDto toDto(TransportQuantityDetails entity) {
        TransportQuantityDetailsResponseDto dto = new TransportQuantityDetailsResponseDto();
        dto.setId(entity.getId());
        dto.setGrossWeight(entity.getGrossWeight());
        dto.setTareWeight(entity.getTareWeight());
        dto.setNetWeight(entity.getNetWeight());
        dto.setLotId(entity.getLot().getId());
        dto.setTotalNoOfBags(entity.getTotalNoOfBags());
        dto.setPurchaseOrderId(entity.getPurchaseOrderId());
        dto.setVehicleDetailsResponseDtos(entity.getTransportVehicleDetailsSet().stream().map(transportVehicleDetailsMapper::toDto).toList());
        return dto;
    }

    @Override
    public TransportQuantityDetails toEntity(TransportQuantityDetailsRequestDto dto) {
        TransportQuantityDetails transportQuantityDetails = new TransportQuantityDetails();
        transportQuantityDetails.setGrossWeight(dto.getGrossWeight());
        transportQuantityDetails.setNetWeight(dto.getNetWeight());
        transportQuantityDetails.setTareWeight(dto.getTareWeight());
        transportQuantityDetails.setPurchaseOrderId(dto.getPurchaseOrderId());
        transportQuantityDetails.setDestinationId(dto.getDestinationId());
        transportQuantityDetails.setTotalNoOfBags(dto.getTotalNoOfBags());
        Set<TransportVehicleDetails> vehicleDetailsSet = dto.getVehicleDetailsRequestDtos().stream().map(transportVehicleDetailsRequestDto -> {
            TransportVehicleDetails transportVehicleDetails = transportVehicleDetailsMapper.toEntity(transportVehicleDetailsRequestDto);
            transportVehicleDetails.setTransportQuantityDetails(transportQuantityDetails);
            return transportVehicleDetails;
        }).collect(Collectors.toSet());
        transportQuantityDetails.setTransportVehicleDetailsSet(vehicleDetailsSet);
        return transportQuantityDetails;
    }
}
