package org.path.lab.service;

import org.path.lab.dto.requestDto.AddressRequestDTO;
import org.path.lab.dto.responseDto.AddressResponseDTO;
import org.path.lab.dto.responseDto.LabNameAddressResponseDto;

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
