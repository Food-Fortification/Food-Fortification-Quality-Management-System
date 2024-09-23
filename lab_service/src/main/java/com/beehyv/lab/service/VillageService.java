package com.beehyv.lab.service;

import com.beehyv.lab.dto.requestDto.VillageRequestDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.dto.responseDto.LocationResponseDto;
import com.beehyv.lab.dto.responseDto.VillageResponseDTO;

public interface VillageService {

    Long create(VillageRequestDTO villageRequestDTO);

    VillageResponseDTO getById(Long id);

    void update(VillageRequestDTO villageRequestDTO);

    void delete(Long id);

    ListResponse<VillageResponseDTO> findAll(Integer pageNumber,Integer pageSize);

    ListResponse<LocationResponseDto> getAllVillagesByDistrictId(Long districtId, Integer pageNumber, Integer pageSize);
}
