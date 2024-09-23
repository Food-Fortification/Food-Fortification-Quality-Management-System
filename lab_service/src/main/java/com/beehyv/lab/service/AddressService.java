package com.beehyv.lab.service;

import com.beehyv.lab.dto.requestDto.AddressRequestDTO;
import com.beehyv.lab.dto.responseDto.AddressResponseDTO;
import com.beehyv.lab.dto.responseDto.LabNameAddressResponseDto;

import java.util.List;
import java.util.Map;

public interface AddressService {

    void create(AddressRequestDTO addressRequestDTO);

    AddressResponseDTO getById(Long id);

    void update(AddressRequestDTO addressRequestDTO);

    void delete(Long id);
    Map<Long, Map<String, String>> getCompleteAddressForLab(List<Long> id);

    LabNameAddressResponseDto getNameAndCompleteAddressForLab(Long labId);
}
