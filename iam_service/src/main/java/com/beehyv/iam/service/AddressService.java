package com.beehyv.iam.service;

import com.beehyv.iam.dto.requestDto.AddressRequestDto;
import com.beehyv.iam.dto.responseDto.AddressLocationResponseDto;
import com.beehyv.iam.dto.responseDto.AddressResponseDto;
import com.beehyv.iam.dto.responseDto.ListResponse;
import com.beehyv.iam.dto.responseDto.LocationResponseDto;
import com.beehyv.iam.manager.AddressManager;
import com.beehyv.iam.model.Address;
import com.beehyv.iam.utils.DtoMapper;
import com.beehyv.parent.exceptions.CustomException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {
    private final AddressManager addressManager;
    private final DtoMapper dtoMapper;

    public void create(AddressRequestDto dto){
        Address address = dtoMapper.mapToEntity(dto);
        addressManager.create(address);
    }
    public void update(AddressRequestDto dto){
        Address address = dtoMapper.mapToEntity(dto);
        addressManager.update(address);
    }
    public AddressResponseDto getById(Long id){
        Address address = addressManager.findById(id);
        return dtoMapper.mapToDto(address);
    }
    public void delete(Long id){
        addressManager.delete(id);
    }
    public ListResponse<AddressResponseDto> findAll(Integer pageNumber,Integer pageSize){
        List<Address> entities = addressManager.findAll(pageNumber,pageSize);
        Long count = addressManager.getCount(entities.size(),pageNumber,pageSize);
        return ListResponse.from(entities, dtoMapper::mapToDto,count);
    }
    public String getCompleteAddressForManufacturer(Long manufacturerId){
        Address address = addressManager.findByManufacturerId(manufacturerId);
        return this.getCompleteAddressForManufacturer(address);
    }
    public String getCompleteAddressForManufacturer(Address address){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        AddressLocationResponseDto dto = new AddressLocationResponseDto();
        dto.setLongitude(address.getLongitude());
        dto.setLatitude(address.getLatitude());
        dto.setPinCode(address.getPinCode());
        dto.setLaneTwo(address.getLaneTwo());
        dto.setLaneOne(address.getLaneOne());
        if (address.getVillage() != null){
            dto.setVillage( new LocationResponseDto(address.getVillage().getId(), address.getVillage().getName(),
                    address.getVillage().getCode(), address.getVillage().getGeoId()));
            if (address.getVillage().getDistrict()!=null){
                dto.setDistrict(new LocationResponseDto(address.getVillage().getDistrict().getId(), address.getVillage().getDistrict().getName()
                        , address.getVillage().getDistrict().getCode(), address.getVillage().getDistrict().getGeoId()));
                if (address.getVillage().getDistrict().getState()!=null){
                    dto.setState(new LocationResponseDto(address.getVillage().getDistrict().getState().getId(), address.getVillage().getDistrict().getState().getName()
                            , address.getVillage().getDistrict().getState().getCode(), address.getVillage().getDistrict().getState().getGeoId()));
                    if (address.getVillage().getDistrict().getState().getCountry()!=null){
                        dto.setCountry(new LocationResponseDto(address.getVillage().getDistrict().getState().getCountry().getId()
                                , address.getVillage().getDistrict().getState().getCountry().getName(), address.getVillage().getDistrict().getState().getCountry().getCode(),
                                address.getVillage().getDistrict().getState().getCountry().getGeoId()));
                    }
                }
            }
        }
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public AddressResponseDto getByManufacturerId(Long manufacturerId){
        Address address = addressManager.findByManufacturerId(manufacturerId);
        return dtoMapper.mapToDto(address);
    }
}
