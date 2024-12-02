package org.path.lab.service;

import org.path.lab.dto.requestDto.VillageRequestDTO;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.dto.responseDto.LocationResponseDto;
import org.path.lab.dto.responseDto.VillageResponseDTO;

public interface VillageService {

    Long create(VillageRequestDTO villageRequestDTO);

    VillageResponseDTO getById(Long id);

    void update(VillageRequestDTO villageRequestDTO);

    void delete(Long id);

    ListResponse<VillageResponseDTO> findAll(Integer pageNumber,Integer pageSize);

    ListResponse<LocationResponseDto> getAllVillagesByDistrictId(Long districtId, Integer pageNumber, Integer pageSize);
}
