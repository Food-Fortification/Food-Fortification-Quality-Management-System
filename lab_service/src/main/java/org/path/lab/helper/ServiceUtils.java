package org.path.lab.helper;

import org.path.lab.dto.responseDto.AddressLocationResponseDto;
import org.path.lab.dto.responseDto.LocationResponseDto;
import org.path.lab.entity.Address;
import org.path.lab.entity.Lab;
import org.path.parent.exceptions.CustomException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpStatus;

public class ServiceUtils {

  public static String getCompleteAddressForLab(Lab lab){
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    Address address = lab.getAddress();
    AddressLocationResponseDto dto = new AddressLocationResponseDto();
    dto.setLongitude(address.getLongitude());
    dto.setLatitude(address.getLatitude());
    dto.setPinCode(address.getPinCode());
    dto.setLaneTwo(address.getLaneTwo());
    dto.setLaneOne(address.getLaneOne());
    if (address.getVillage() != null){
      dto.setVillage( new LocationResponseDto(address.getVillage().getId(), address.getVillage().getName(), address.getVillage().getCode(), address.getVillage().getGeoId()));
      if (address.getVillage().getDistrict()!=null){
        dto.setDistrict(new LocationResponseDto(address.getVillage().getDistrict().getId(),address.getVillage().getDistrict().getName()
            ,address.getVillage().getDistrict().getCode(), address.getVillage().getDistrict().getGeoId()));
        if (address.getVillage().getDistrict().getState()!=null){
          dto.setState(new LocationResponseDto(address.getVillage().getDistrict().getState().getId(),address.getVillage().getDistrict().getState().getName()
              ,address.getVillage().getDistrict().getState().getCode(), address.getVillage().getDistrict().getState().getGeoId()));
          if (address.getVillage().getDistrict().getState().getCountry()!=null){
            dto.setCountry(new LocationResponseDto(address.getVillage().getDistrict().getState().getCountry().getId()
                ,address.getVillage().getDistrict().getState().getCountry().getName(),
                address.getVillage().getDistrict().getState().getCountry().getCode(),
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
}
