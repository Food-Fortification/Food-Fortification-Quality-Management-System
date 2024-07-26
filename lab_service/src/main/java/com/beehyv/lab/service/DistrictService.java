package com.beehyv.lab.service;

import com.beehyv.lab.dto.requestDto.DistrictRequestDTO;
import com.beehyv.lab.dto.responseDto.DistrictResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.dto.responseDto.LocationResponseDto;

public interface DistrictService {

    void create(DistrictRequestDTO districtRequestDTO);

    DistrictResponseDTO getById(Long id);

    void update(DistrictRequestDTO districtRequestDTO);

    void delete(Long id);

    ListResponse<DistrictResponseDTO> getAll(Integer pageNumber, Integer pageSize);

    ListResponse<LocationResponseDto> getAllDistrictsByStateId(Long stateId, String search, Integer pageNumber, Integer pageSize);
}
