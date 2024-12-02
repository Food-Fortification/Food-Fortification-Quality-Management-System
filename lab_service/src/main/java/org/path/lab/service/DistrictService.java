package org.path.lab.service;

import org.path.lab.dto.requestDto.DistrictRequestDTO;
import org.path.lab.dto.responseDto.DistrictResponseDTO;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.dto.responseDto.LocationResponseDto;

public interface DistrictService {

    void create(DistrictRequestDTO districtRequestDTO);

    DistrictResponseDTO getById(Long id);

    void update(DistrictRequestDTO districtRequestDTO);

    void delete(Long id);

    ListResponse<DistrictResponseDTO> getAll(Integer pageNumber, Integer pageSize);

    ListResponse<LocationResponseDto> getAllDistrictsByStateId(Long stateId, String search, Integer pageNumber, Integer pageSize);
}
